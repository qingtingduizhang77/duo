package com.duo.modules.redisadmin.entity;

import lombok.Data;
import org.springframework.data.redis.connection.DataType;

import java.io.Serializable;

/**
 * redis 各类型缓存统一模型
 *
 * @author: yonsin
 * @Date: 2018/10/31 下午3:49
 */
@Data
public class RedisModel implements Serializable {

    private DataType dataType;//数据类型
    private String key;       //缓存key
    private String value;     //缓存值
    private String hashKey;   //hash键,仅hash类型有效
    private boolean isLeft;   //从list左端添加
    private double score;     //得分
    private Long expire;      //过期时间,单位秒

}
