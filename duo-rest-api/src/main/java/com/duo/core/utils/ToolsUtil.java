package com.duo.core.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToolsUtil {

    /***
     * 人民币元转分
     * @param yuan
     * @return
     */
    public static Integer YuanToFen(Double yuan){
        return new BigDecimal(String.valueOf(yuan)).movePointRight(2).intValue();
    }

    /**
     * 获取现在时间
     * @return返回字符串格式yyyyMMddHHmmss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

//    public static void main(String[] args) {
//        String dateString = getStringDate();
//        System.out.println("TIME:::"+dateString);
//    }

}
