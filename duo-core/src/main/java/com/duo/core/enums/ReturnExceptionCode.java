package com.duo.core.enums;

/**
 * @author [ Yonsin ] on [2019/3/25]
 * @Description： [ 返回异常枚举 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public enum ReturnExceptionCode {

    /** 操作成功 */
    SUCCESS("SUCCESS_CODE", "SUCCESS_MSG"),

    /** 操作失败 */
    FAIL("FAIL_CODE", "FAIL_MSG"),

    /** 空指针异常 */
    NullPointerException("NPE_CODE", "NPE_MSG"),

    /** 自定义异常之返回值为空 */
    NullResponseException("NRE_CODE", "NRE_MSG"),

    /** 运行时异常 */
    RuntimeException("RTE_CODE","RTE_MSG"),

    /** 请求方式错误异常 */
    HttpRequestMethodNotSupportedException("REQUEST_METHOD_UNSUPPORTED_CODE","REQUEST_METHOD_UNSUPPORTED_MSG"),

    /** INTERNAL_ERROR */
    BindException("BIND_EXCEPTION_CODE","BIND_EXCEPTION_MSG"),

    /** 页面路径不对 */
    UrlError("UE_CODE","UE_MSG");

    private ReturnExceptionCode(String value, String msg){
        this.val = value;
        this.msg = msg;
    }

    public String val() {
        return val;
    }

    public String msg() {
        return msg;
    }

    private String val;
    private String msg;

}
