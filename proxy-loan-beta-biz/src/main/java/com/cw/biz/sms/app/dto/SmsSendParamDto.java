package com.cw.biz.sms.app.dto;

import lombok.Data;

import java.util.List;

/**
 * @Title: SmsSendParamDto
 * @Description: 短信发送参数
 * @Author: Away
 * @Date: 2018/9/1 18:58
 */
@Data
public class SmsSendParamDto {

    /**
     * 手机号
     */
    private String phoneNum;

    /**
     * 变量
     */
    private List<String> variables;

}
