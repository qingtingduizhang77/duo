package com.duo.core.exception;

import com.duo.core.enums.ResultCodeEnum;

public class DUOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String code;

    private String msg;

    public DUOException(String msg) {
        super(msg);
    }

    public DUOException(ResultCodeEnum resultCodeEnum) {
        this(resultCodeEnum.getCode(), resultCodeEnum.getMsg());
    }

    public DUOException(ResultCodeEnum resultCodeEnum, String msg) {
        super(msg);
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getCode();
    }

    public DUOException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
