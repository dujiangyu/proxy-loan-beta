package com.cw.biz.parameter.app;

/**
 *
 * Created by Administrator on 2017/6/4.
 */
public enum ParameterEnum {

    LEIDAI("tianbeileidai","天贝全景雷达"),
    YUNYINGSHANG("yingyunshang","天贝运营商"),
    BLACKLIST("blacklist","天贝黑名单"),
    OVERDUE("overdue","天贝借条逾期"),
    REPORT("tianbeireport","天贝报告"),
    INFOAUTH("infoauth","新颜实名认证"),
    OVERDUEFILE("overduefile","新颜逾期档案"),
    ZMF("zmf","新颜支付宝芝麻分");

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
