package com.duo;

import java.util.HashMap;
import java.util.Map;

public class Constants {


    public static String listPermissions="";

    /**
     * 当前用户
     */
    public static final String CURRENT_USER = "user";

    /**
     * 权限集合
     */
    public static final String PERMISSIONS = "permissions";

    /**
     * 组织机构根id
     */
    public static final Long ORG_ROOT_ID = 0L;


    /**
     * 构造函数私有化，避免被实例化
     */
    private Constants() {
    }

}
