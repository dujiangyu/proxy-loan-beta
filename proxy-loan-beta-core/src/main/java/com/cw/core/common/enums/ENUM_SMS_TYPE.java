package com.cw.core.common.enums;

/**
 * @Title: ENUM_SMS_TYPE
 * @Description: 短信类型（验证码、链接，等）
 * @Author: Away
 * @Date: 2018/9/1 15:26
 */
public enum ENUM_SMS_TYPE {

    /**
     * 验证码
     */
    VALIDATE("validateCode"),

    /**
     * 链接
     */
    LINK("link");

    public final String value;

    ENUM_SMS_TYPE(String value){
        this.value=value;
    }

}
