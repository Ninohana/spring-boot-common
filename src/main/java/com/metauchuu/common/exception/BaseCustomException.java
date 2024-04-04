package com.metauchuu.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class BaseCustomException extends Exception{
    private Integer code;

    private String msg;
}
