package com.cw.biz.user.app.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2017/8/10.
 */
@Setter
@Getter
public class UserPasswordDto {

    private String oldPassword;

    private String password;

    private String confirmPassword;

}
