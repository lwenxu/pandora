package com.ibeetl.admin.console.web.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lwen on 2018/9/18
 */
@Data
class GroupVO implements Serializable {
    private long id;
    private String name;
    private String description;
    private List<GroupVO> children;
}
