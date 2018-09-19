package com.ibeetl.admin.console.web.query;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lwen on 2018/9/11
 */
@Data
public class BatchStatusQuery implements Serializable {
    private List<Long> ids;
    private String status;
}
