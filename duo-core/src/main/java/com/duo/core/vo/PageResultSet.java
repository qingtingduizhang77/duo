package com.duo.core.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 通用分页返回结果集
 *
 */
@Data
public class PageResultSet<T> implements Serializable {

    private boolean success;
    private String message;
    // 总数
    private long total;
   // 返回的行数
    private List<T> rows;
//    // 返回含有file的记录
//    private List<Map> files;

}
