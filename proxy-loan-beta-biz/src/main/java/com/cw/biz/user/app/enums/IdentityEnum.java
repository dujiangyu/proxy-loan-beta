package com.cw.biz.user.app.enums;

/**
 * 社会身份
 * Created by Administrator on 2017/6/1.
 */
public enum IdentityEnum {

    SHANGBANZHU("SHANGBANZHU","上班族"),
    GETI("GETI","个体"),
    QIYEZHU("QIYEZHU","企业主"),
    STUDENT("STUDENT","学生"),
    FREEJOB("FREEJOB","自由职业");

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    IdentityEnum(String key, String value){
        this.key = key;
        this.value = value;
    }
}
