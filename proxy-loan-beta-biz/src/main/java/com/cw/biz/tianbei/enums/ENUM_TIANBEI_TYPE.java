package com.cw.biz.tianbei.enums;

/**
 * @Title: ENUM_TIANBEI_TYPE
 * @Description: 天贝接口类别
 * @Author: Away
 * @Date: 2018/9/1 19:28
 */
public enum ENUM_TIANBEI_TYPE {

    /**
     * 天贝报告
     */
    REPORT("report"),

    /**
     * 运营商报告初始化
     */
    TELECOM_OPERATORS_REPORT_INIT("telecomReportInit"),

    /**
     * 运营商报告提交验证码
     */
    TELECOM_OPERATORS_REPORT_VALIDATE("telecomReportValidate"),

    /**
     * 全景雷达
     */
    RADAR("radar"),

    /**
     * 黑名单
     */
    BLACKLIST("blackList"),

    /**
     * 借条逾期
     */
    NOTEOVERDUE("noteOverdue"),

    /**
     * 运营商报告结果
     */
    TELECOM_OPERATORS_REPORT_RESULT("telecomReportResult");


    public final String code;

    ENUM_TIANBEI_TYPE(String code) {
        this.code=code;
    }

    public String getMessage(){
        return this.code;
    }
}
