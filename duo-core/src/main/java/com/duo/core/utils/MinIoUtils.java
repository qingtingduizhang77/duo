package com.duo.core.utils;

import com.duo.MemCache;
import com.duo.config.MinioConfig;
import com.duo.config.properties.SQLFormatProperties;
import com.duo.core.SpringContextHolder;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author [ Yonsin ] on [2020/7/23]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@Component
public class MinIoUtils {
    public static MinioConfig minIo;

    public MinIoUtils(MinioConfig minIo) {
        this.minIo = minIo;
    }

    private static MinioClient instance;

    @PostConstruct
    public void init() {
        try {
            if("minio".equals(MemCache.getSystemParameter("fileServerType"))) {
                instance = new MinioClient(minIo.getOssProperties().getEndpoint(), minIo.getOssProperties().getAccessKey(), minIo.getOssProperties().getSecretKey());
            }else{
                log.info("FileServerType==="+MemCache.getSystemParameter("fileServerType")+",MinIO未启用！");
            }
        } catch (InvalidPortException e) {
            e.printStackTrace();
        } catch (InvalidEndpointException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断 bucket是否存在
     * @param bucketName
     * @return
     */
    public static boolean bucketExists(String bucketName){
        try {
            return instance.bucketExists(bucketName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建 bucket
     * @param bucketName
     */
    public static boolean makeBucket(String bucketName){
        try {
            boolean isExist = instance.bucketExists(bucketName);
            if(!isExist) {
                instance.makeBucket(bucketName);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 文件上传
     * @param bucketName
     * @param multipartFile
     */
    public static boolean uploadFile(String bucketName, MultipartFile multipartFile, String filename){
        try {
            makeBucket(bucketName);//没有该目录先创建
            // PutObjectOptions，上传配置(文件大小，内存中文件分片大小)
            PutObjectOptions putObjectOptions = new PutObjectOptions(multipartFile.getSize(), PutObjectOptions.MIN_MULTIPART_SIZE);
            // 文件的ContentType
            putObjectOptions.setContentType(multipartFile.getContentType());
            instance.putObject(bucketName,filename,multipartFile.getInputStream(),putObjectOptions);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 删除文件
     * @param bucketName
     * @param objectName
     */
    public static boolean removeFile(String bucketName,String objectName){
        try {
            instance.removeObject(bucketName,objectName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取文件URL
     * @param bucketName
     * @param objectName
     */
    public static String getFileURL(String bucketName,String objectName){
        try {
           return instance.getObjectUrl(bucketName,objectName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 下载文件
     * @param bucketName
     * @param fileName
     * @param originalName
     * @param response
     */
    public static void downloadFile(String bucketName,String fileName,String originalName, HttpServletResponse response){
        try {

            InputStream file = instance.getObject(bucketName,fileName);
            String filename = new String(fileName.getBytes("ISO8859-1"), StandardCharsets.UTF_8);
            if(StringUtils.isNotEmpty(originalName)){
                fileName=originalName;
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while((len=file.read(buffer)) > 0){
                servletOutputStream.write(buffer, 0, len);
            }
            servletOutputStream.flush();
            file.close();
            servletOutputStream.close();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }



    /**
     * 下载文件
     * @param bucketName
     * @param fileName
     */
    public static InputStream getFileInputStream(String bucketName,String fileName){
        try {

            return instance.getObject(bucketName,fileName);

        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (Exception  e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取上传文件的md5
     * @param file
     * @return
     */
    public static String getMd5(MultipartFile file) {
        try {
            //获取文件的byte信息
            byte[] uploadBytes = file.getBytes();
            // 拿到一个MD5转换器
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            //转换为16进制
            return new BigInteger(1, digest).toString(16);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
