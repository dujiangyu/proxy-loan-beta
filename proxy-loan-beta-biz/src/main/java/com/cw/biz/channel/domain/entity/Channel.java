package com.cw.biz.channel.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 渠道信息
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="yx_channel")
@Getter
@Setter
public class Channel extends AggEntity {

    @Column(name="code",columnDefinition="varchar(100) not null comment '渠道编码'")
    private String code;

    @Column(name="name",columnDefinition="varchar(100) not null comment '渠道名称'")
    private String name;

    @Column(name="link_person",columnDefinition="varchar(100) comment '联系人'")
    private String linkPerson;

    @Column(name="link_phone",columnDefinition="varchar(100) comment '联系电话'")
    private String linkPhone;

    @Column(name="tg_url",columnDefinition="varchar(100) comment '推广链接'")
    private String tgUrl;

    @Column(name="back_login_url",columnDefinition="varchar(100) comment '后台地址'")
    private String backLoginUrl;

    @Column(name="account_no",columnDefinition="varchar(100) comment '账号'")
    private String accountNo;

    @Column(name="password",columnDefinition="varchar(100) comment '密码'")
    private String password;

    @Column(name="balance",columnDefinition="decimal(10,2) comment '余额'")
    private BigDecimal balance;

    @Column(name="user_id",columnDefinition="int(11) comment '所属用户'")
    private Long userId;

    @Column(name="is_valid",columnDefinition="tinyint(1) comment '是否有效'")
    private Boolean isValid=Boolean.TRUE;

    public void prepareSave(){
    }
}
