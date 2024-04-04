package com.metauchuu.common.wrapper;

import com.metauchuu.common.wrapper.annotation.ResponseBodyWrapping;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

@ControllerAdvice
@AutoConfiguration
@EnableConfigurationProperties(WrapperProperties.class)
@ConditionalOnProperty(name = "metauchuu.wrapper.enable", havingValue = "true")
public class WrappingResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    private final WrapperProperties config;

    public WrappingResponseBodyAdvice(WrapperProperties config) {
        this.config = config;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        var method = returnType.getMethod();
        return Objects.nonNull(method) && method.isAnnotationPresent(ResponseBodyWrapping.class);
    }

    @Override
    @SneakyThrows
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        var clazz = config.getResponseTemplate();
        if (Objects.isNull(clazz)) {
            throw new IllegalArgumentException("未指定Response模板Class");
        }

        Object obj = clazz.getDeclaredConstructor().newInstance();
        try {
            Method setCode = obj.getClass().getDeclaredMethod("setCode", Object.class);
            Method setMsg = obj.getClass().getDeclaredMethod("setMsg", Object.class);
            Method setData = obj.getClass().getDeclaredMethod("setData", Object.class);
            setCode.setAccessible(true);
            setMsg.setAccessible(true);
            setData.setAccessible(true);
            setCode.invoke(obj, config.getDefaultCode());
            setMsg.invoke(obj, config.getDefaultMsg());
            setData.invoke(obj, body);
        } catch (NoSuchMethodException e) {
            Field code = obj.getClass().getDeclaredField("code");
            Field msg = obj.getClass().getDeclaredField("msg");
            Field data = obj.getClass().getDeclaredField("data");
            code.setAccessible(true);
            msg.setAccessible(true);
            data.setAccessible(true);
            code.set(obj, config.getDefaultCode());
            msg.set(obj, config.getDefaultMsg());
            data.set(obj, body);
        }
        return obj;
    }
}
