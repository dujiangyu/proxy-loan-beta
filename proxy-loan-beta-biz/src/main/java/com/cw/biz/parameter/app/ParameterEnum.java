package com.cw.biz.parameter.app;

/**
 *
 * Created by Administrator on 2017/6/4.
 */
public enum ParameterEnum {

    profitPlat("profitPlat","平台分成"),
    profitChannel("profitChannel","渠道分成百分比");

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

    ParameterEnum(String key,String value){
        this.key = key;
        this.value = value;
    }
}
