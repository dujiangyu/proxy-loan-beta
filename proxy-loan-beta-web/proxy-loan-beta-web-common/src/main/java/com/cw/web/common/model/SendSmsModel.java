package com.cw.web.common.model;

import com.zds.common.lang.validator.MobileNo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *发送短信码
 */
public class SendSmsModel implements Serializable {

    @NotNull
    @MobileNo
    private String phone;

    private String deviceNumber;

    private String applyArea;

    private String channelNo;

    private String appName="MBD";//默认秒必贷

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getApplyArea() {
        return applyArea;
    }

    public void setApplyArea(String applyArea) {
        this.applyArea = applyArea;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
