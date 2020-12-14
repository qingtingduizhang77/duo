package com.duo.modules.common.web;

import com.duo.config.properties.SQLFormatProperties;
import com.duo.core.BaseController;
import com.duo.core.SpringContextHolder;
import com.duo.core.utils.FileUtils;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.ShiroUtils;
import com.duo.core.utils.WebUtil;
import com.duo.core.vo.PageResultSet;
import com.duo.core.vo.Result;
import com.duo.modules.system.entity.SystemAttachment;
import com.duo.modules.system.mapper.SystemAttachmentMapper;
import com.duo.modules.tool.entity.ToolFunction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.reflections.vfs.SystemFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author [ Yonsin ] on [2019/8/8]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@Controller
@RequestMapping("/file")
public class FileFeignController extends BaseController {
    @Autowired
    private SystemAttachmentMapper systemAttachmentMapper;//附件表
    //在配置文件中配置的文件保存路径
    private static SQLFormatProperties sqlprop= SpringContextHolder.getBean("SQLFormatProperties");
    /**
     * 用于表设计器生成Entity和Mapper文件
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/entity")
    public Result<?> entity(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String entitypath= (String) requestMap.get("entitypath");
        String mapperpath= (String) requestMap.get("mapperpath");
         String entityFileName= (String) requestMap.get("entityFileName");
        String entityFileBody= (String) requestMap.get("entityFileBody");
        String mapperFileName= (String) requestMap.get("mapperFileName");
        String mapperFileBody= (String) requestMap.get("mapperFileBody");
        entityFileBody=StringEscapeUtils.unescapeHtml(entityFileBody);
        logdb.info(entityFileBody);
        mapperFileBody=StringEscapeUtils.unescapeHtml(mapperFileBody);
        logdb.info(mapperFileBody);
        FileUtils.createUTF8File(entitypath,entityFileName,entityFileBody,false);
        FileUtils.createUTF8File(mapperpath,mapperFileName,mapperFileBody,false);
        return Result.success();
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/upload",produces="application/json;charset=UTF-8")
    public  Result<?>  uploadFile(@RequestParam("fileName") MultipartFile file,HttpServletRequest request
            ,@RequestParam("keyId") String keyId
            ,@RequestParam("funId") String funId
            ,@RequestParam("typeId") String typeId) {

        ToolFunction funinfo=layoutService.getFunctionInfo(funId);
        String tableName=funinfo!=null?funinfo.getTable_name():"";
        String  url;
        logdb.info("上传文件==="+"\n");
        //判断文件是否为空
        if (file.isEmpty()) {
            return Result.failure("500", "上传文件不可为空");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        String fileType="";
        if(fileName.indexOf(".")>0) fileType= fileName.substring(fileName.lastIndexOf(".")+1);
        logdb.info("上传的文件名为: "+fileName+"\n");
        String fileID= layoutService.getKeyUID();
        String newfileName =  funId+fileID;
        logdb.info("（加个时间戳，尽量避免文件名称重复）保存的文件名为: "+newfileName+"\n");
        //加个时间戳，尽量避免文件名称重复
        String path = sqlprop.getFilePath()+ new SimpleDateFormat("yyyyMMdd").format(new Date()) +"/"+newfileName;
        //文件绝对路径
        logdb.info("保存文件绝对路径:"+path+"\n");

        //创建文件路径
        File dest = new File(path);
        //判断文件是否已经存在
        if (dest.exists()) {
            logdb.error("文件已经存在");
            return Result.failure("500",  "文件已经存在");
        }
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            //上传文件
            file.transferTo(dest); //保存文件
            logdb.info("保存文件路径"+path+"\n");
            SystemAttachment attachment=new SystemAttachment();
            attachment.setFile_id(fileID);
            attachment.setData_id(keyId);
            attachment.setFile_date(new Date());
            attachment.setFile_name(fileName);
            attachment.setFile_path(path);
            attachment.setFile_size(FileUtils.getPrintSize(dest.length()));
            attachment.setFile_md5(DigestUtils.md5Hex(new FileInputStream(dest)));
            attachment.setFile_type(fileType.toLowerCase());
            attachment.setDept_id(ShiroUtils.getDeptId());
            attachment.setDept_name(ShiroUtils.getDeptName());
            attachment.setTable_name(tableName);
            attachment.setType_id(typeId);
            attachment.setUpload_ip(WebUtil.getIpAddress(request));
            attachment.setUpload_user(ShiroUtils.getUserName());
            layoutService.addDataInfo(attachment);
            systemAttachmentMapper.insert(attachment);

        } catch (IOException e) {
            logdb.error("上传失败",e);
            return Result.failure("500",  "上传失败");
        }

        return Result.success();
    }

    // 文件下载
    @RequestMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "FileUploadTests.java";
        if (fileName != null) {
            // 当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
            String realPath = request.getServletContext().getRealPath("//WEB-INF//");
            File file = new File(realPath, fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }


    // 多文件上传
    @RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        String filePath = "D://test//";
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                    stream = new BufferedOutputStream(new FileOutputStream(new File(filePath + System.currentTimeMillis() + extName)));
                    stream.write(bytes);
                    stream.close();


                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload " + i + " => " + e.getMessage();
                }
            } else {
                return "You failed to upload " + i + " because the file was empty.";
            }
        }
        return "upload successful";
    }
}
