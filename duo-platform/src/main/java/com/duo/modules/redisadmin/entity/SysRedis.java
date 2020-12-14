package com.duo.modules.redisadmin.entity;

import com.duo.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.redis.connection.DataType;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存管理Entity
 *
 * @author yonsin
 * @version 2018-10-20
 */
@Data
@NoArgsConstructor
public class SysRedis extends BaseEntity {

    private static final long serialVersionUID = 1L;
    //公共字段
    private String dataType;            // 数据类型
    private String oldRedisKey;         // 原始的缓存key
    private String redisKey;            // 缓存键
    private String redisValue;          // 缓存值
    private String expire;              // 过期时间

    //List数据类型
    private String fromLeft;            // 是否从左边添加,仅list类型有效:1左,0右
    private String currentIndex;        // 当前操作的元素的索引，仅list类型有效
    private List<Object> valList;       // list类型的value

    //set数据类型
    private Set<Object> valSet;         // set类型的value

    //zset数据类型
    private List<ScoreVal> zsetList;    // zset类型的value
    private String score;               // 分值,仅zset类型有效

    //hash数据类型
    private String hashKey;             // 仅hash类型有效
    private Map<String, Object> valMap; // map类型的value

    private Long elCount;             // 集合元素总数


    public SysRedis(String redisKey, String redisValue) {
        // 默认string类型
        this.dataType = DataType.STRING.code();
        this.redisKey = redisKey;
        this.redisValue = redisValue;
    }

    public SysRedis(String dataType, String redisKey, String redisValue, String expire) {
        this.dataType = dataType;
        this.redisKey = redisKey;
        this.redisValue = redisValue;
        this.expire = expire;
    }

    /**
     * 按key排序
     */
    public int compareTo(SysRedis sysRedis) {
        return this.redisKey.compareTo(sysRedis.getRedisKey());
    }



}