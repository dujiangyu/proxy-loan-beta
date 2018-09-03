package com.cw.biz.sms.domain.entity;

import com.cw.biz.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Title: SmsSendResult
 * @Description: 短信发送结果
 * @Author: Away
 * @Date: 2018/9/1 15:15
 */
@Entity
@Table(name="yx_sms_result",
        indexes = {@Index(name = "yx_sms_result_index_phone",  columnList="phone_num"),
                @Index(name = "yx_sms_result_index_event_type", columnList="event_type"),
                @Index(name = "yx_sms_result_index_sms_type", columnList="sms_type")})
@org.hibernate.annotations.Table(appliesTo = "yx_sms_result",comment = "短信发送结果")
@Data
public class SmsSendResult extends BaseEntity{

    @Column(name="user_id",columnDefinition="bigint(20)  comment '用户ID'")
    private Long userId;

    @Column(name="expiry_time",columnDefinition="datetime comment '过期时间'")
    private Date expiryTime;

    @Column(name="phone_num",columnDefinition="varchar(11) comment '手机号码'")
    private String phoneNum;

    @Column(name="content",columnDefinition="varchar(200)  comment '发送内容'")
    private String content;

    @Column(name="event_type",columnDefinition="varchar(36)  comment '事件类型'")
    private String eventType;

    @Column(name="sms_type",columnDefinition="varchar(36)  comment '短信类型'")
    private String smsType;

    @Column(name="main_content",columnDefinition="varchar(200)  comment '主要内容（具体的验证码或者具体的链接）'")
    private String mainContent;

}
