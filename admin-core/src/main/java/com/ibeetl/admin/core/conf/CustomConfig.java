package com.ibeetl.admin.core.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by lwen on 2018/9/28
 */
@Component
@ConfigurationProperties("custom")
@Data
public class CustomConfig implements Serializable {
    private String salt;
}
