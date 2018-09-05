package com.cw.biz.xinyan;

/**
 * @Title: ENUM_XINYAN_PHASE_TYPE
 * @Description: 新颜回调进度类型
 * @Author: Away
 * @Date: 2018/9/5 21:42
 */
public enum ENUM_XINYAN_PHASE_TYPE {

    /**
     * 登录阶段
     */
    LOGIN("LOGIN"),

    /**
     * 采集阶段
     */
    TASK("TASK");

    public final String code;

    ENUM_XINYAN_PHASE_TYPE(String code){
        this.code=code;
    }
}
