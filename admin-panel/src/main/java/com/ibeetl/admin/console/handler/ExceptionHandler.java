package com.ibeetl.admin.console.handler;

import com.ibeetl.admin.console.domain.ResultDO;

/**
 * Created by lwen on 2018/9/15
 */
public abstract class ExceptionHandler {
    public abstract void handle(Exception exception, ResultDO result);
}
