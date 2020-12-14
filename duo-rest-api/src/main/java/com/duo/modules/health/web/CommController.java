package com.duo.modules.health.web;

import com.alibaba.fastjson.JSONObject;
import com.duo.config.DynamicDataSource;
import com.duo.core.enums.ResultCodeEnum;
import com.duo.core.utils.CmdEnum;
import com.duo.core.utils.StringUtils;
import com.duo.core.vo.Result;
import com.duo.modules.health.entity.BaseField;
import com.duo.modules.health.service.CommService;
import com.duo.core.utils.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/api/comm")
public class CommController {

    @Autowired
    public CommService commService;

    //获取区域列表
    @GetMapping("/getAreaList")
    @ResponseBody
    public Result getAreaList(HttpServletRequest request) {
        DynamicDataSource.setDataSource("health");
        List<BaseField> baseFields = this.commService.baseFieldMapper.selectAll();
        DynamicDataSource.clearDataSource();
        return Result.success(baseFields);
    }

    //支付成功回调函数
    @PostMapping(value = "/paySuccess")
    public void paySuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream inputStream = request.getInputStream();// 读取字节流
        InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");// 字节流转字符流
        BufferedReader in = new BufferedReader(reader);//放到字符缓冲流中读取
        StringBuffer buffer = new StringBuffer();
        //1、将微信回调信息转为字符串
        String line;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        in.close();
        inputStream.close();
        //2、将xml格式字符串格式转为map集合
        Map<String, String> params = WXPayUtil.xmlToMap(buffer.toString());
        log.info("微信支付成功回调参数：" + params.toString());

        Map<String, String> result = new HashMap<String, String>();

        if ("SUCCESS".equals(params.get("return_code"))) {
            result.put("return_code", "SUCCESS");
            result.put("return_msg", "OK");
            //修改本地订单状态
            DynamicDataSource.setDataSource("health");
            this.commService.updateOrderStatus(params.get("out_trade_no"));
            DynamicDataSource.clearDataSource();
        }else {
            log.error("微信支付失败：" + params.toString());
        }

        String xml = WXPayUtil.mapToXml(result);
        this.wxpayCallbackResult(response, xml);
    }

    private void wxpayCallbackResult(HttpServletResponse response, String xml) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/xml; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(xml);
        out.close();
    }

    //广告更新，广播通知？
    @PostMapping("/doUpdateAdvert")
    @ResponseBody
    public Result doUpdateAdvert(@RequestBody JSONObject requestJson,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        String deviceId = requestJson.getString("device_id");
        DynamicDataSource.setDataSource("health");
        //通知app获取诊断信息显示
        this.commService.setControlCmd(deviceId, CmdEnum.CMD_AD_UPDATE, null);

        DynamicDataSource.clearDataSource();
        return Result.success();
    }

    // 广告更新(采用断点续传，减少缓存时间)
    @PostMapping(value = "getAdvertFile")
    @ResponseBody
    public Result getAdvertFile(@RequestBody JSONObject requestJson,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        String filePath = requestJson.getString("file_path");
        String fileName = requestJson.getString("file_name");
        if (StringUtils.isBlank(filePath) || StringUtils.isBlank(fileName)) {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }

        String pathName = filePath + "/" + fileName;
        File file = new File(pathName);
        if (!file.exists()) {
            return Result.failure("502", "文件不存在！");
        }
        request.getSession();
        FileInputStream in = new FileInputStream(file);
        Long fileLength = file.length();

        // 客户端请求的下载的文件块的开始字节
        String range = request.getHeader("Range");
        Long pastLength = 0L;

        if (range != null) {
            ////如果是第一次下,还没有断点续传,状态是默认的 200,无需显式设置
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            range = range.replaceAll("bytes=", "");
            int index = range.indexOf("-");
            String brang = range.substring(0, index);
            if (index == 1) {
                pastLength = Long.parseLong(brang);
            }
        }

        OutputStream out = response.getOutputStream();
        //响应所有请求
        response.addHeader("Access-Control-Allow-Origin", "*");
        //告诉客户端允许断点续传多线程连接下载,响应的格式是:Accept-Ranges: bytes
        response.setHeader("Accept-Ranges", "bytes");
        //如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。
        //格式：文件总大小 - 下载块的开始字节
        response.addHeader("Content-Length", (fileLength - pastLength) + "");
        //Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
        response.addHeader("Content-Range", "bytes " + pastLength + "-" + (fileLength - 1) + "/" + fileLength);
        response.addHeader("Content-Type", "application/x-msdownload;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        //设置缓存
        Date date = new Date();
        response.setDateHeader("Last-Modified", date.getTime()); //Last-Modified:页面的最后生成时间
        response.setDateHeader("Expires", date.getTime() + 300000); //Expires:过时期限值 60秒
        response.setHeader("Cache-Control", "public"); //Cache-Control来控制页面的缓存与否,public:浏览器和缓存服务器都可以缓存页面信息；
        response.setHeader("Pragma", "Pragma"); //Pragma:设置页面是否缓存，为Pragma则缓存，no-cache

        in.skip(Long.valueOf(pastLength));

        byte[] data = new byte[1024 * 5];
        int n = 0;
        while ((n = in.read(data)) != -1) {
            out.write(data, 0, n);
        }
        out.flush();
        out.close();
        in.close();

        return Result.success();

    }
}
