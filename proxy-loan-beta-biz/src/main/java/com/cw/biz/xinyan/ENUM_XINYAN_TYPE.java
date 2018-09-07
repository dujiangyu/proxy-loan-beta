package com.cw.biz.xinyan;

/**
 * @Title: ENUM_XINYAN_TYPE
 * @Description: 新颜查询类型
 * @Author: Away
 * @Date: 2018/9/5 21:42
 */
public enum ENUM_XINYAN_TYPE {

    /**
     * 芝麻信用
     */
    ZHIMA("zhima"),

    /**
     * 实名认证
     */
    REAL_NAME("realName"),

    /**
     * 档案逾期
     */
    OVERDUE("overdu");

    public final String code;

    ENUM_XINYAN_TYPE(String code){
        this.code=code;
    }
}
