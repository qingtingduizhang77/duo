package com.duo.modules.common.web;

import com.duo.config.properties.SQLFormatProperties;
import com.duo.core.BaseController;
import com.duo.core.SpringContextHolder;
import com.duo.core.utils.*;
import com.duo.core.vo.Result;
import com.duo.modules.common.service.FileService;
import com.duo.modules.system.mapper.SystemAttachmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @author [ Yonsin ] on [2019/8/8]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@Controller
@RequestMapping("/file")
public class FileController extends BaseController {
    @Autowired
    private FileService fileService;
    @Autowired
    private SystemAttachmentMapper systemAttachmentMapper;//附件表
    //在配置文件中配置的文件保存路径
    private static SQLFormatProperties prop= SpringContextHolder.getBean("SQLFormatProperties");

    /**
     * 上传文件
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/upload")
    public  Result<?>  uploadFile(HttpServletRequest request)  {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        String funId= (String) requestMap.get("funId");
        String keyId= (String) requestMap.get("keyId");
        String typeId= (String) requestMap.get("typeId");
        if(StringUtils.isEmpty(keyId)) keyId="zzZZ";
        if(StringUtils.isEmpty(typeId)) typeId="zzZZ";
        //判断参数是否为空
        if (  StringUtils.isEmpty(funId) ) {
            return Result.failure("500", "上传文件参数为空，请检查再上传！");
        }
        String ip=WebUtil.getIpAddress(request);
        return fileService.upload(fileMap,requestMap,ip);
    }

    /**
     * 上传文件
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/uploadCK")
    public FileService.ImageUploadVo uploadFileCK(HttpServletRequest request)  {
        log.info("start.............uploadCK");
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile  file  = multipartRequest.getFile("upload");
        String funId= (String) requestMap.get("funId");
        String keyId= (String) requestMap.get("keyId");
        String typeId= (String) requestMap.get("typeId");
        if(StringUtils.isEmpty(keyId)) keyId="zzZZ";
        if(StringUtils.isEmpty(typeId)) typeId="zzZZ";
        //判断参数是否为空
        if ( file==null|| StringUtils.isEmpty(funId) ) {
            FileService.ImageUploadVo r=new FileService.ImageUploadVo();
            r.setUploaded(0);
            log.error("上传文件参数为空，请检查再上传！");
            return r;
        }
        String ip=WebUtil.getIpAddress(request);
        return fileService.uploadCK(file,requestMap,ip);
    }



    // 文件下载
    @ResponseBody
    @RequestMapping("/deleteFile")
    public Result<?> deleteFile(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//

        String file_id = (String) requestMap.get("file_id");
        String ids=(String)requestMap.get("ids");
        if (StringUtils.isEmpty(file_id)&&StringUtils.isEmpty(ids)) {
            return Result.failure("500","参数为空或缺少关键参数！");
        }
        if(StringUtils.isNotEmpty(file_id)){
            return  fileService.deleteFile(file_id);
        }
        if(StringUtils.isNotEmpty(ids)){
            String[] fileIds=ids.split(";");
            for(String fileId:fileIds) {
                if(StringUtils.isNotEmpty(fileId)) {
                    Result r=  fileService.deleteFile(fileId);
                    if(!r.isSuccess()) return r;
                }
            }
        }
        return Result.success();
    }



    // 文件下载
    @RequestMapping("/download")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        fileService.download(request,response);
    }

    /**
     * 支持断点续传
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/downloaRanges")
    public  void downloaRanges(HttpServletRequest request, HttpServletResponse response) throws IOException {

        fileService.downloaRanges(request,response);
    }

}
