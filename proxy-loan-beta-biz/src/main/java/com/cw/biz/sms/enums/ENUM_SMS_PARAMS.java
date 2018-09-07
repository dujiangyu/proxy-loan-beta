package com.cw.biz.sms.enums;

/**
 * @Title: ENUM_SMS_PARAMS
 * @Description: 短信变量参数
 * @Author: Away
 * @Date: 2018/9/1 19:28
 */
public enum ENUM_SMS_PARAMS {
    phone("手机号"),

    expiryTime("过期时间"),

    validateCode("验证码"),

    userName("用户名");


    public final String msg;

    ENUM_SMS_PARAMS(String msg) {
        this.msg=msg;
    }

    public String getMessage(){
        return this.msg;
    }
}
