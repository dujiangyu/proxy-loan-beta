package com.cw.core.common.enums;

/**
 * @Title: ENUM_EVENT_TYPE
 * @Description: 系统事件类型 例如（注册，登录，修改密码，绑定银行卡，还款，等）
 * @Author: Away
 * @Date: 2018/9/1 15:26
 */
public enum ENUM_EVENT_TYPE {

    /**
     * 注册
     */
    REGISTER("register"),

    /**
     * 登录
     */
    LOGIN("login");

    public final String value;

    ENUM_EVENT_TYPE(String value){
        this.value=value;
    }

}
