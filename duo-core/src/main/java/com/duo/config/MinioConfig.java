package com.duo.config;

import com.duo.MemCache;
import com.duo.config.properties.OssProperties;
import io.minio.MinioClient;
import lombok.Data;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author [ Yonsin ] on [2020/7/23]
 * @Description： [ Minio文件系统初始化 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@Data
@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class MinioConfig {

    private final OssProperties ossProperties;
//    @Value("${oss.endpoint}")
//    private String url;
//
//    @Value("${minio.access-key}")
//    private String accessKey;
//
//    @Value("${minio.secret-key}")
//    private String secretKey;
//
//    @Value("${minio.bucket-name}")
//    private String bucketName;


    /**
     * Minio文件系统配置
     *
     */
    @Bean(name = "minioClient")
    public MinioClient minioClient() throws Exception {
        log.info("oss初始化加载.......");
        MemCache.setSystemParameter("ossBucketName",ossProperties.getBucketName());
        if(!"minio".equals(MemCache.getSystemParameter("fileServerType"))) {
//            log.info("FileServerType==="+MemCache.getSystemParameter("fileServerType")+",MinIO未启用！");
            return null;
        }
        MinioClient minioClient = new MinioClient(ossProperties.getEndpoint(), ossProperties.getAccessKey(), ossProperties.getSecretKey());
        // 判断Bucket是否存在
        boolean isExist = minioClient.bucketExists(ossProperties.getBucketName());
        if(isExist) {
            log.info(".............Bucket已存在.............");
        } else {
            // 不存在创建一个新的Bucket
            minioClient.makeBucket(ossProperties.getBucketName());
            log.info(".............Bucket已创建.............");
        }
        log.info("oss初始化完成......");
        return minioClient;
    }
}
