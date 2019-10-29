package com.cmos.bj.ngtask.enums;

import lombok.Data;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/10 13:06
 */
public enum TaskStatusEnum {

    ABLE(1,"在用"),
    DISABLE(2, "禁用");

    private int code;
    private String value;

    TaskStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
