package com.cw.biz.xinyan.domain.entity;

import com.cw.biz.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Title: XinYanNotifyResult
 * @Description: 新颜芝麻分回调结果
 * @Author: Away
 * @Date: 2018/9/5 23:46
 */

@Entity
@Table(name="yx_xinyan_notify_result")
@org.hibernate.annotations.Table(appliesTo = "yx_xinyan_notify_result",comment = "新颜芝麻分回调结果")
@Data
public class XinYanNotifyResult extends BaseEntity{

    @Column(name="member_id",columnDefinition="varchar(255) comment '新颜分配商户号'")
    private String memberId;

    @Column(name="terminal_id",columnDefinition="varchar(255) comment '新颜分配终端号'")
    private String terminalId;

    @Column(name="task_type",columnDefinition="varchar(255) comment '任务类型 CARRIER：运营商'")
    private String taskType;

    @Column(name="phase_type",columnDefinition="varchar(255) comment '通知阶段 LOGIN：登录阶段 TASK：采集阶段'")
    private String phaseType;

    @Column(name="result",columnDefinition="varchar(10) comment '任务结果 true 任务完成 false 任务失败'")
    private String result;

    @Column(name="trade_no",columnDefinition="varchar(255) comment '新颜订单号'")
    private String tradeNo;

    @Column(name="member_trans_id",columnDefinition="varchar(255) comment '商户订单号'")
    private String memberTransId;

    @Column(name="xinyan_desc",columnDefinition="varchar(255) comment '描述'")
    private String desc;

    @Column(name="ﬁnished",columnDefinition="varchar(10) comment '是否最终状态 true 最终状态； false 非最终状态'")
    private String ﬁnished;

    @Column(name="id_card",columnDefinition="varchar(255) comment '用户id,本表中为用户身份证'")
    private String userId;

    @Column(name="mobile",columnDefinition="varchar(19) comment '手机号码 部分业务有该属性如运营商'")
    private String mobile;

    @Column(name="account",columnDefinition="varchar(255) comment '登录帐号 部分业务有该属性如社保公积金等'")
    private String account;


}
