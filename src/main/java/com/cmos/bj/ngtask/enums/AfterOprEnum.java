package com.cmos.bj.ngtask.enums;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/10 13:06
 */
public enum AfterOprEnum {
    NULL("0", "不做操作"),
    DEL("1", "删除"),
    TRANS("2", "传输"),
    CREATEFILE("3", "生成文件");

    private String code;
    private String value;

    AfterOprEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
