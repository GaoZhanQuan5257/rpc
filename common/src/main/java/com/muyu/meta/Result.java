package com.muyu.meta;

import java.io.Serializable;

/**
 * @author 七小鱼
 */
public class Result implements Serializable {
    private Object returnValue;
    private RuntimeException exception;

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public RuntimeException getException() {
        return exception;
    }

    public void setException(RuntimeException exception) {
        this.exception = exception;
    }


}
