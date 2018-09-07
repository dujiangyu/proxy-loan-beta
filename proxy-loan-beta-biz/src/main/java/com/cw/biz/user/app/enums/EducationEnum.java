package com.cw.biz.user.app.enums;

/**
 * 教育程度
 * Created by Administrator on 2017/6/1.
 */
public enum EducationEnum {

    CHUZHONG("CHUZHONG","初中及以下"),
    GAOZHONG("GAOZHONG","高中"),
    DAZHUAN("DAZHUAN","大专"),
    BENKE("BENKE","本科"),
    YANJIUSHENG("YANJIUSHENG","研究生及以上");

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

    EducationEnum(String key, String value){
        this.key = key;
        this.value = value;
    }
}
