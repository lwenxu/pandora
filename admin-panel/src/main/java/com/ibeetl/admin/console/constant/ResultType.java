package com.ibeetl.admin.console.constant;

import lombok.Data;

public enum ResultType {
    SERVICE_FAILURE("内部数据请求失败", 501),;

    private String desc;
    private Integer code;

    ResultType(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
