package com.cw.biz.sms.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Data;

import java.util.Date;

/**
 * @Title: SmsSendResultDto
 * @Description: 短信发送结果
 * @Author: Away
 * @Date: 2018/9/1 15:15
 */
@Data
public class SmsSendResultDto extends BaseDto{

    /**用户ID**/
    private Long userId;

    /**过期时间**/
    private Date expiryTime;

    /**手机号码**/
    private String phoneNum;

    /**发送内容**/
    private String content;

    /**事件类型**/
    private String eventType;

    /**短信类型**/
    private String smsType;

    /**主要内容（具体的验证码或者具体的链接）**/
    private String mainContent;

}
