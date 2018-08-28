package com.cw.web.common.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 */
public class LoginModel implements Serializable {
    public static interface PasswordLogin {

    }

    public static interface PhoneLogin {

    }

    @NotEmpty
    private String userName;

    @NotEmpty(groups = PasswordLogin.class)
    private String password;

    @NotEmpty(groups = PhoneLogin.class)
    @Length(min = 6, max = 6, groups = PhoneLogin.class, message = "验证码长度错误,请输入6位验证码!")
    private String smsVerifyCode;

    //    @NotNull(message = "图形验证码不能为空",groups = PasswordLogin.class)
    private String verifyCode;

    @NotNull
    private Long merchantId=1L;

    @NotNull
    private String type="user";

    private String channelNo;

    private String appName;

    private String applyArea;

    private String deviceNumber;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSmsVerifyCode() {
        return smsVerifyCode;
    }

    public void setSmsVerifyCode(String smsVerifyCode) {
        this.smsVerifyCode = smsVerifyCode;
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

    public String getApplyArea() {
        return applyArea;
    }

    public void setApplyArea(String applyArea) {
        this.applyArea = applyArea;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }
}
