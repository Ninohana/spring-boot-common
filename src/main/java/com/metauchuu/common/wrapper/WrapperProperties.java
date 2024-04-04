package com.metauchuu.common.wrapper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "metauchuu.wrapper")
public class WrapperProperties {
    private boolean enable;

    private Class<?> responseTemplate;

    private int defaultCode = 200;

    private String defaultMsg = "success";
}
