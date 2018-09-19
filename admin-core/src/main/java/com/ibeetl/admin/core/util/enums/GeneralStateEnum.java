package com.ibeetl.admin.core.util.enums;

import org.beetl.sql.core.annotatoin.EnumMapping;

/**
 * 描述:数据是否有效
 *
 * @author : lijiazhi
 */
@EnumMapping("value")
public enum GeneralStateEnum {
    /**
     * 禁用
     */
    DISABLE("0"),
    /**
     * 启用
     */
    ENABLE("1"),
    /**
     * 删除
     */
    DELETE("2");

    private String value;

    GeneralStateEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static GeneralStateEnum getEnum(String value) {
        for (GeneralStateEnum stateEnum : GeneralStateEnum.values()) {
            if (stateEnum.value.equals(value)) {
                return stateEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public GeneralStateEnum getState(String state) {
        switch (state) {
            case "0":
                return DISABLE;
            case "1":
                return ENABLE;
            case "2":
                return DELETE;
        }
        return ENABLE;
    }
}
