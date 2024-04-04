package com.metauchuu.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseCustomException extends Exception {
    protected Integer code;

    public BaseCustomException(Integer code, String message) {
        super(message);

        this.code = code;
    }
}
