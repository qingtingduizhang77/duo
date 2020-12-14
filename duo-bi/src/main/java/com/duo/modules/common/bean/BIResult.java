package com.duo.modules.common.bean;

import com.duo.core.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author [ Yonsin ] on [2020/7/17]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Data
@NoArgsConstructor
public class BIResult<T> implements Serializable {
    private int code;
    private boolean success;
    private T data;
    private String msg;

    private BIResult(IResultCode resultCode) {
        this(resultCode,  null, resultCode.getMessage());
    }

    private BIResult(IResultCode resultCode, String msg) {
        this(resultCode,   null, msg);
    }

    private BIResult(IResultCode resultCode, T data) {
        this(resultCode, data, resultCode.getMessage());
    }

    private BIResult(IResultCode resultCode, T data, String msg) {
        this(resultCode.getCode(), data, msg);
    }

    private BIResult(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = ResultCode.SUCCESS.code == code;
    }

    public static boolean isSuccess(@Nullable BIResult<?> result) {
        return (Boolean) Optional.ofNullable(result).map((x) -> {
            return StringUtils.nullSafeEquals(ResultCode.SUCCESS.code, x.code);
        }).orElse(Boolean.FALSE);
    }

    public static boolean isNotSuccess(@Nullable BIResult<?> result) {
        return !isSuccess(result);
    }

    public static <T> BIResult<T> data(T data) {
        return data(data, "操作成功");
    }

    public static <T> BIResult<T> data(T data, String msg) {
        return data(200, data, msg);
    }

    public static <T> BIResult<T> data(int code, T data, String msg) {
        return new BIResult(code, data, data == null ? "暂无承载数据" : msg);
    }

    public static <T> BIResult<T> success(String msg) {
        return new BIResult(ResultCode.SUCCESS, msg);
    }

    public static <T> BIResult<T> success(IResultCode resultCode) {
        return new BIResult(resultCode);
    }

    public static <T> BIResult<T> success(IResultCode resultCode, String msg) {
        return new BIResult(resultCode, msg);
    }

    public static <T> BIResult<T> fail(String msg) {
        return new BIResult(ResultCode.FAILURE, msg);
    }

    public static <T> BIResult<T> fail(int code, String msg) {
        return new BIResult(code, (Object)null, msg);
    }

    public static <T> BIResult<T> fail(IResultCode resultCode) {
        return new BIResult(resultCode);
    }

    public static <T> BIResult<T> fail(IResultCode resultCode, String msg) {
        return new BIResult(resultCode, msg);
    }

    public static <T> BIResult<T> status(boolean flag) {
        return flag ? success("操作成功") : fail("操作失败");
    }


    public String toString() {
        return "R(code=" + this.getCode() + ", success=" + this.isSuccess() + ", data=" + this.getData() + ", msg=" + this.getMsg() + ")";
    }

}
