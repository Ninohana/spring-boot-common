package com.metauchuu.common.handler;

import com.metauchuu.common.exception.BaseCustomException;
import com.metauchuu.common.model.R;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@AutoConfiguration
@ControllerAdvice
@ConditionalOnProperty(name = "metauchuu.handler.global-exception", havingValue = "true")
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public R<?> exception(Exception e) {
        var fail = R.fail(null);
        fail.setCode(500);
        fail.setMsg(e.getMessage());
        return fail;
    }

    @ResponseBody
    @ExceptionHandler(BaseCustomException.class)
    public R<?> baseCustomException(BaseCustomException e) {
        var fail = R.fail(null);
        fail.setCode(e.getCode());
        fail.setMsg(e.getMsg());
        return fail;
    }

}
