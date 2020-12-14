package com.duo.modules.redisadmin.service;

import cn.hutool.json.JSONObject;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.duo.core.utils.JsonUtil;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.NumberUtil;
import com.duo.core.utils.StringUtils;
import com.duo.core.vo.PageResultSet;
import com.duo.modules.redisadmin.entity.RedisModel;
import com.duo.modules.redisadmin.entity.ScoreVal;
import com.duo.modules.redisadmin.entity.SysRedis;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author [ Yonsin ] on [2019/11/2]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Service
public class RedisAdminService {

    @Autowired
    @Qualifier("redisTemplateManage")
    private RedisTemplate  template;

    @Autowired
    @Qualifier("stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    public PageResultSet<Map<String,Object>> findPage(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        PageResultSet<Map<String,Object>> resultSet = new PageResultSet<Map<String,Object>>();

        //无设置条数，最多支持1000条结果
        if(!requestMap.isEmpty()&&!requestMap.containsKey("limit")){
            requestMap.put("limit",1000);
            requestMap.put("offset",0);
        }
        Integer limit=(Integer)requestMap.get("limit");
        Integer offset=(Integer)requestMap.get("offset");
        String lastkey=(String)requestMap.get("lastkey");

        Set<String> keySet;
        if (StringUtils.isNotBlank(lastkey)) {
            keySet = template.keys(lastkey);
        } else {
            keySet = template.keys("*");
        }
        List<String> keyList = new ArrayList<String>(keySet);
        resultSet.setTotal(keyList.size());
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        if (!CollectionUtils.isEmpty(keyList)) {
            int start = offset  * limit;
            for (int i = start, j = 0; i < keyList.size() && j < limit; i++, j++) {
                String key = keyList.get(i);
                SysRedis base = this.get(key);
                list.add(Map2EntityUtil.HumpToUnderline(BeanMap.create(base)));
            }
        }
        resultSet.setRows(list);

        return resultSet;
    }



    public void save(SysRedis sysRedis) {
        RedisModel redisModel = new RedisModel();
        redisModel.setDataType(DataType.fromCode(sysRedis.getDataType()));
        redisModel.setKey(sysRedis.getRedisKey());
        redisModel.setValue(sysRedis.getRedisValue());
        redisModel.setHashKey(sysRedis.getHashKey());
        redisModel.setScore(StringUtils.toDouble(sysRedis.getScore()));
        redisModel.setLeft(StringUtils.toInteger(sysRedis.getFromLeft()) == 1);
        redisModel.setExpire(NumberUtil.toLong(sysRedis.getExpire()));

        save(redisModel);
    }


    public void updateExpire(String key,String expire) {
        updateExpire(key, StringUtils.toLong(expire));
    }

    public void addValue(SysRedis sysRedis) {
        String key = sysRedis.getRedisKey();
        DataType type = template.type(key);
        switch (type) {
            case STRING:
                stringRedisTemplate.opsForValue().set(key, sysRedis.getRedisValue());
                break;
            case LIST:
                if ("1".equals(sysRedis.getFromLeft())) {
                    template.opsForList().leftPush(sysRedis.getRedisKey(), sysRedis.getRedisValue());
                } else {
                    template.opsForList().rightPush(sysRedis.getRedisKey(), sysRedis.getRedisValue());
                }
                break;
            case SET:
                template.opsForSet().add(sysRedis.getRedisKey(), sysRedis.getRedisValue());
                break;
            case ZSET:
                template.opsForZSet().add(sysRedis.getRedisKey(), sysRedis.getRedisValue(), StringUtils.toDouble(sysRedis.getScore()));
                break;
            case HASH:
                template.opsForHash().put(sysRedis.getRedisKey(), sysRedis.getHashKey(), sysRedis.getRedisValue());
                break;
            default:
                break;
        }
    }

    public void del(String key) {
        template.delete(key);
    }

    public void remove(String key) {
        SysRedis sysRedis =get(key);
        DataType type = DataType.fromCode(sysRedis.getDataType());
        switch (type) {
            case STRING:
                break;
            case LIST:
                remove(sysRedis.getRedisKey(), sysRedis.getCurrentIndex());
                break;
            case HASH:
                remove(sysRedis.getRedisKey(), sysRedis.getHashKey());
                break;
            default:
                remove(sysRedis.getRedisKey(), sysRedis.getRedisValue());
                break;
        }
    }
    /**
     * 删除集合中的元素
     *
     * @param key     集合的key
     * @param element 集合的元素,如果是list则为元素的索引
     */
    private void remove(Object key, Object element) {
        DataType type = template.type(key);
        switch (type) {
            case STRING:
                break;
            case LIST:
                delListValue(String.valueOf(key), Integer.parseInt(String.valueOf(element)));
                break;
            case SET:
                template.opsForSet().remove(key, element);
                break;
            case ZSET:
                template.opsForZSet().remove(key, element);
                break;
            case HASH:
                template.opsForHash().delete(key, element);
                break;
            case NONE:
                break;
        }
    }


    /**
     * 按索引删除元素
     *
     * @param redisKey
     * @param currentIndex
     */
    private  void delListValue(String redisKey, int currentIndex) {
        List<Object> list = template.opsForList().range(redisKey, 0, -1);
        List<Object> newList = new ArrayList<Object>();
        for (int i = 0; i < list.size(); i++) {
            if (i != currentIndex) {
                newList.add(list.get(i));
            }
            template.opsForList().rightPop(redisKey);
        }
        template.opsForList().rightPushAll(redisKey, newList);
    }

    /**
     * 根据数据类型进行保存
     *
     * @param redisModel 各类型缓存统一模型
     */
    private void save(RedisModel redisModel) {
        String key = redisModel.getKey();
        switch (redisModel.getDataType()) {
            case STRING:
                template.opsForValue().set(key, redisModel.getValue());
                break;
            case LIST:
                if (redisModel.isLeft()) {
                    template.opsForList().leftPush(key, redisModel.getValue());
                } else {
                    template.opsForList().rightPush(key, redisModel.getValue());
                }
                break;
            case SET:
                template.opsForSet().add(key, redisModel.getValue());
                break;
            case ZSET:
                template.opsForZSet().add(key, redisModel.getValue(), redisModel.getScore());
                break;
            case HASH:
                template.opsForHash().put(key, redisModel.getHashKey(), redisModel.getValue());
                break;
            default:
                break;
        }
        // 更新过期时间
        updateExpire(key, redisModel.getExpire());
    }



    public void rename(Object oldKey, Object newKey) {
        template.rename(oldKey, newKey);
    }

    /**
     * 更新过期时间
     *
     * @param key    缓存key
     * @param expire 过期时间,单位秒
     */
    private void updateExpire(String key, Long expire) {
        // 忽略等于0的过期时间
        if (expire != null && expire != 0) {
            if (expire < 0) {
                template.persist(key);
            } else {
                template.expire(key, expire * 1000, TimeUnit.MILLISECONDS);
            }
        }
    }


    /**
     * 只查询元素总数及数据类型, 不查询元素值
     *
     * @param key
     */
    private SysRedis baseInfo(String key) {
        DataType type = template.type(key);
        Long expire = template.getExpire(key);
        SysRedis sysRedis = new SysRedis(type.code(), key, null, String.valueOf(expire));
        switch (type) {
            case STRING:
                Object redisValue = template.opsForValue().get(key);
                sysRedis.setRedisValue(String.valueOf(redisValue));
                sysRedis.setElCount(1L);
                break;
            case LIST:
                List<Object> list = template.opsForList().range(key, 0, -1);
                sysRedis.setValList(list);
                sysRedis.setElCount(template.opsForList().size(key));
                break;
            case SET:
                sysRedis.setElCount(template.opsForSet().size(key));
                break;
            case ZSET:
                sysRedis.setElCount(template.opsForZSet().size(key));
                break;
            case HASH:
                sysRedis.setElCount(template.opsForHash().size(key));
                break;
            default:
                break;
        }
        return sysRedis;
    }


    private SysRedis get(String key) {
        DataType type = template.type(key);
        Long expire = template.getExpire(key);
        SysRedis sysRedis = new SysRedis(type.code(), key, null, String.valueOf(expire));
        switch (type) {
            case STRING:
                Object redisValue = template.opsForValue().get(key);
                sysRedis.setRedisValue(String.valueOf(redisValue));
                sysRedis.setElCount(1L);
                break;
            case LIST:
                List<Object> list = template.opsForList().range(key, 0, -1);
                sysRedis.setValList(list);
                sysRedis.setElCount(template.opsForList().size(key));
                break;
            case SET:
                Set<Object> set = template.opsForSet().members(key);
                sysRedis.setValSet(set);
                sysRedis.setElCount(template.opsForSet().size(key));
                break;
            case ZSET:
                List<ScoreVal> zsetList = new ArrayList<ScoreVal>();
                Set<ZSetOperations.TypedTuple<Object>> tuples = template.opsForZSet().rangeWithScores(key, 0, -1);
                Iterator<ZSetOperations.TypedTuple<Object>> it = tuples.iterator();
                while (it.hasNext()) {
                    ZSetOperations.TypedTuple<Object> next = it.next();
                    zsetList.add(new ScoreVal(String.valueOf(next.getScore()), String.valueOf(next.getValue())));
                }
                sysRedis.setZsetList(zsetList);
                sysRedis.setElCount(template.opsForZSet().size(key));
                break;
            case HASH:
                Map<String, Object> map = template.opsForHash().entries(key);
                sysRedis.setValMap(map);
                sysRedis.setElCount(template.opsForHash().size(key));
                break;
            default:
                break;
        }
        return sysRedis;
    }

}
