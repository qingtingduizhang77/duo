package com.duo.modules.common.web;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.duo.core.BaseController;
import com.duo.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author [ Yonsin ] on [2019/8/8]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Controller
@Slf4j
public class OSSUploadFeignController extends BaseController {
    private OSS ossClient;
    private String endpoint = "http://oss-cn-qingdao.aliyuncs.com";
    private String  accessKeyId = "key";
    private String accessKeySecret = "secret";
    private String bucketName = "空间名";

    /*
     * getOss
     * @description： 创建OSSClient实例
     * @params []
     * @return void
     */
    public void getOss() {
        this.ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    /*
     * upload
     * @description：oss中上传文件，返回文件名
     * @params [request]
     * @return java.lang.String
     */
    public String OSSUpload(HttpServletRequest request) throws IOException {
        String newFileName = null;
        try {
            //上传文件
            CommonsMultipartResolver multipartResolver =
                    new CommonsMultipartResolver(request.getSession().getServletContext());
            // 判断 request 是否有文件上传,即多部分请求
            if (multipartResolver.isMultipart(request)) {
                // 转换成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                // 取得request中的所有文件名
                Iterator<String> iter = multiRequest.getFileNames();
                //此处上传单个文件，如需多个文件改为while循环
                if (iter.hasNext()) {
                    // 取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        // 取得当前上传文件的文件名称
                        String myFileName = file.getOriginalFilename();
                        // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                        if (StringUtils.isNotEmpty(myFileName.trim())) {
                            getOss();
                            newFileName = System.currentTimeMillis() + myFileName.substring(myFileName.lastIndexOf("."));
                            // 上传客户端文件到oss，如直接上传服务器文件到oss，可使用new FileInputStream(  new File("服务器文件路径"))
                            ossClient.putObject(bucketName, newFileName, file.getInputStream());
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            // 关闭ossClient
            if (null != ossClient)
                ossClient.shutdown();
        }
        return newFileName;
    }
//删除
// ossClient.deleteObject(bucketName, oldName);


}
