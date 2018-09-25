package com.ibeetl.admin.console.exception;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lwen on 2018/9/18
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
public class ServiceExecException extends BaseException {
    public ServiceExecException(String message) {
        super(message);
    }
}
