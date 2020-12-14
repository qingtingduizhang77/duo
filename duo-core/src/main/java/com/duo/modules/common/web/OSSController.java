package com.duo.modules.common.web;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.duo.DataSourceNames;
import com.duo.MemCache;
import com.duo.config.DynamicDataSource;
import com.duo.config.properties.SQLFormatProperties;
import com.duo.core.BaseController;
import com.duo.core.SpringContextHolder;
import com.duo.core.annotation.DataSource;
import com.duo.core.utils.*;
import com.duo.core.vo.Result;
import com.duo.modules.common.service.CommonService;
import com.duo.modules.common.service.LayoutService;
import com.duo.modules.system.entity.SystemAttachment;
import com.duo.modules.system.mapper.SystemAttachmentMapper;
import com.duo.modules.tool.entity.ToolFunction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2019/8/8]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Controller
@Slf4j
@RequestMapping("/oss")
public class OSSController extends BaseController {
    @Autowired
    private LayoutService layoutService;

    @Autowired
    private SystemAttachmentMapper systemAttachmentMapper;//附件表
    //在配置文件中配置的文件保存路径
    private static SQLFormatProperties sqlprop= SpringContextHolder.getBean("SQLFormatProperties");


    @ResponseBody
    @RequestMapping("/createBucket")
    public  Result<?> createBucket(HttpServletRequest request){
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String bucketName=(String)requestMap.get("bucketName");
        if(bucketName==null||bucketName.equals("")) return Result.failure("500","参数bucketName为空！");
        if(!MinIoUtils.makeBucket(bucketName)) return Result.failure("500","创建bucket【"+bucketName+"】出错！");
        return Result.success();
    }

    @ResponseBody
    @RequestMapping("/deleteFile")
    public  Result<?> deleteFile(HttpServletRequest request){
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String file_id=(String)requestMap.get("file_id");
        if(file_id==null||file_id.equals("")) return Result.failure("500","参数为空或缺少关键参数！");
        SystemAttachment file=systemAttachmentMapper.selectByPrimaryKey(file_id);
        MinIoUtils.removeFile(file.getFile_path(),file_id);

        return Result.success();
    }

    @ResponseBody
    @RequestMapping("/getfileURL")
    public  String getfileURL(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String file_id = (String) requestMap.get("file_id");
        if (file_id == null || file_id.equals("")) return null;
        SystemAttachment file = systemAttachmentMapper.selectByPrimaryKey(file_id);

        return MinIoUtils.getFileURL(file.getFile_path(), file_id);
    }

    @ResponseBody
    @RequestMapping("/uploadFile")
    public  Result<?> uploadFile(@RequestParam("fileName")  MultipartFile multipartFile, HttpServletRequest request
            ,@RequestParam("keyId") String keyId
            ,@RequestParam("funId") String funId
            ,@RequestParam(value="typeId",defaultValue = "100310011005") String typeId){
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String file_id=(String)requestMap.get("file_id");
        if(file_id==null||file_id.equals("")) return Result.failure("500","参数为空或缺少关键参数！");
        String bucketName=(String)requestMap.get("bucketName");
        if(bucketName==null||bucketName.equals("")) bucketName=MinIoUtils.minIo.getOssProperties().getBucketName();
        DynamicDataSource.setDataSource(DataSourceNames.DEFAULT);
        ToolFunction funinfo=layoutService.getFunctionInfo(funId);
        String tableName=funinfo!=null?funinfo.getTable_name():"";
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        String fileType="";
        if(fileName.indexOf(".")>0) fileType= fileName.substring(fileName.lastIndexOf(".")+1);
        logdb.info("上传的文件名为: "+fileName+"\n");
        String fileID= layoutService.getKeyUID();

        SystemAttachment attachment=new SystemAttachment();
        attachment.setFile_id(fileID);
        attachment.setData_id(keyId);
        attachment.setFile_date(new Date());
        attachment.setFile_name(fileName);
        attachment.setFile_path(bucketName);
        attachment.setFile_size(FileUtils.getPrintSize(multipartFile.getSize()));
        attachment.setFile_md5(MinIoUtils.getMd5(multipartFile));
        attachment.setFile_type(fileType.toLowerCase());
        attachment.setDept_id(ShiroUtils.getDeptId());
        attachment.setDept_name(ShiroUtils.getDeptName());
        attachment.setTable_name(tableName);
        attachment.setType_id(typeId);
        attachment.setUpload_ip(WebUtil.getIpAddress(request));
        attachment.setUpload_user(ShiroUtils.getUserName());
        systemAttachmentMapper.insertSelective(attachment);

        DynamicDataSource.clearDataSource();

        if(!MinIoUtils.uploadFile(bucketName,multipartFile,fileID)) return Result.failure("500","上传文件失败！");
        return Result.success();
    }

    @ResponseBody
    @RequestMapping("/downloadFile")
    public  Result<?> downloadFile(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String file_id=(String)requestMap.get("file_id");
        if(file_id==null||file_id.equals("")) return Result.failure("500","参数为空或缺少关键参数！");
        SystemAttachment file=systemAttachmentMapper.selectByPrimaryKey(file_id);
        MinIoUtils.downloadFile(file.getFile_path(),file_id,file.getFile_name(),response);

        return Result.success();
    }

//    private OSS ossClient;
//    private String endpoint = "http://oss-cn-qingdao.aliyuncs.com";
//    private String  accessKeyId = "key";
//    private String accessKeySecret = "secret";
//    private String bucketName = "空间名";
//
//    /*
//     * getOss
//     * @description： 创建OSSClient实例
//     * @params []
//     * @return void
//     */
//    public void getOss() {
//        this.ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//    }
//
//    /*
//     * upload
//     * @description：oss中上传文件，返回文件名
//     * @params [request]
//     * @return java.lang.String
//     */
//    public String OSSUpload(HttpServletRequest request) throws IOException {
//        String newFileName = null;
//        try {
//            //上传文件
//            CommonsMultipartResolver multipartResolver =
//                    new CommonsMultipartResolver(request.getSession().getServletContext());
//            // 判断 request 是否有文件上传,即多部分请求
//            if (multipartResolver.isMultipart(request)) {
//                // 转换成多部分request
//                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
//                // 取得request中的所有文件名
//                Iterator<String> iter = multiRequest.getFileNames();
//                //此处上传单个文件，如需多个文件改为while循环
//                if (iter.hasNext()) {
//                    // 取得上传文件
//                    MultipartFile file = multiRequest.getFile(iter.next());
//                    if (file != null) {
//                        // 取得当前上传文件的文件名称
//                        String myFileName = file.getOriginalFilename();
//                        // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
//                        if (StringUtils.isNotEmpty(myFileName.trim())) {
//                            getOss();
//                            newFileName = System.currentTimeMillis() + myFileName.substring(myFileName.lastIndexOf("."));
//                            // 上传客户端文件到oss，如直接上传服务器文件到oss，可使用new FileInputStream(  new File("服务器文件路径"))
//                            ossClient.putObject(bucketName, newFileName, file.getInputStream());
//                        }
//                    }
//                }
//            }
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        } finally {
//            // 关闭ossClient
//            if (null != ossClient)
//                ossClient.shutdown();
//        }
//        return newFileName;
//    }
//删除
// ossClient.deleteObject(bucketName, oldName);

}
