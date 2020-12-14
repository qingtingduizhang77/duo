package com.duo;

import com.duo.modules.system.entity.SystemUser;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author [ Yonsin ] on [2019/9/21]
 * @Description： [ 统一管理内存缓存，方便统一清空，刷新 ]
 *
 *              //后期切换到Redis支持分布式
 *
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public class MemCache {
    //系统参数
    public static ConcurrentHashMap<String,String> duo=new ConcurrentHashMap<String,String>();
    //权限缓存
    public static ConcurrentHashMap<String,Map<String,Map<String,Object>>> _userRoles=new ConcurrentHashMap<String,Map<String,Map<String,Object>>>();
    public static ConcurrentHashMap<String ,Boolean> _userIsAdmin=new ConcurrentHashMap<String ,Boolean>();//是否admin
    public static ConcurrentHashMap<String ,Boolean> _userRoleModule=new ConcurrentHashMap<String ,Boolean>();//拥有模块权限
    //CommonService缓存
    public static ConcurrentHashMap<String ,String> _selectSQLMap=new ConcurrentHashMap<String ,String>();//存储所有检索到的selectSQL
    public static ConcurrentHashMap<String ,String> _columnsMap=new ConcurrentHashMap<String ,String>();//存储所有检索到的select column
    //LayoutController
    public static ConcurrentHashMap<String ,String> _queryInfos=new ConcurrentHashMap<String ,String>();//存储查询条信息

    //系统全局
    public static  ConcurrentHashMap<String ,Class> _entitys=new ConcurrentHashMap<String ,Class>();//存储所有检索到的Entity类,table_name和eitity实体名字都可以查
    public static  ConcurrentHashMap<String ,Class> _mappers=new ConcurrentHashMap<String ,Class>();//存储所有检索到的Mapper类
    public static  ConcurrentHashMap<String ,String> _tableInfo=new ConcurrentHashMap<String ,String>();//存储所有表的字段信息

    //用户信息
    public static ConcurrentHashMap<String,SystemUser> _userInfo=new ConcurrentHashMap<String,SystemUser>();

    public static String getSystemParameter(String key){
        return duo.get(key);
    }
    public static String setSystemParameter(String key,String value){
        return duo.put(key,value);
    }


}
