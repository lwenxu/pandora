package com.ibeetl.admin.console.web.vo;

import com.sun.istack.internal.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by lwen on 2018/9/18
 */
@Data
@Accessors(chain = true)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO<T> implements Serializable {
    private String code;
    private Boolean isSuccess;
    private String msg;
    private T data;

    public ResultVO(String code, Boolean isSuccess) {
        this.code = code;
        this.isSuccess = isSuccess;
    }
}
