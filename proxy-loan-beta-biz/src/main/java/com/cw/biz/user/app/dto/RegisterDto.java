package com.cw.biz.user.app.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class RegisterDto {

    @NotNull
    private String phone;

    private String channelNo;

    private String appName;

    private String applyArea;

    private String verifyCode;

}
