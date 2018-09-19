package com.ibeetl.admin.console.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lwen on 2018/9/15
 */
@Data
public class ResultDO<T> implements Serializable {
    private T result;
    private String errorMessage;
    private Exception exception;
    private Boolean success;
}
