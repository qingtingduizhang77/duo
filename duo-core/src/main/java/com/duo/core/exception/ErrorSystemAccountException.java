package com.duo.core.exception;

import org.apache.shiro.authc.AccountException;

/**
 * @author [ Yonsin ] on [2020/9/23]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public class ErrorSystemAccountException extends AccountException {
    /**
     * Creates a new ErrorSystemAccountException.
     */
    public ErrorSystemAccountException() {
        super();
    }

    /**
     * Constructs a new ErrorSystemAccountException.
     *
     * @param message the reason for the exception
     */
    public ErrorSystemAccountException(String message) {
        super(message);
    }

    /**
     * Constructs a new ErrorSystemAccountException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public ErrorSystemAccountException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new ErrorSystemAccountException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public ErrorSystemAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
