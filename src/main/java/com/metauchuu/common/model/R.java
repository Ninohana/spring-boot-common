package com.metauchuu.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class R<T> {
    private Integer code;
    private String msg;
    // @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.setCode(200);
        r.setMsg("ok");
        r.setData(data);
        return r;
    }

    public static <T> R<T> fail(T data) {
        R<T> r = new R<>();
        r.setCode(500);
        r.setMsg("fail");
        r.setData(data);
        return r;
    }
}
