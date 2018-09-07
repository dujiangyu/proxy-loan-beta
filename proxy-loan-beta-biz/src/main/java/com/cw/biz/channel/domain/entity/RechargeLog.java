package com.cw.biz.channel.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 虚拟币充值日志
 * Created by Administrator on 2018/8/28.
 */
@Entity
@Table(name="yx_recharge_log")
@Getter
@Setter
public class RechargeLog extends AggEntity {

    @Column(name="recharge_operate_id",columnDefinition="int(11) not null comment '充值操作用户ID'")
    private Long rechargeOperateId;

    @Column(name="recharge_fee",columnDefinition="decimal(10,2) comment '充值金额'")
    private BigDecimal rechargeFee;

    @Column(name="recharge_date",columnDefinition="datetime comment '充值时间'")
    private Date rechargeDate;

    @Column(name="recharge_object_id",columnDefinition="varchar(100) comment '充值对象Id(代理商、服务商ID)'")
    private Long rechargeObjectId;

    @Column(name="recharge_balance",columnDefinition="decimal(10,2) comment '充值后余额'")
    private BigDecimal rechargeBalance;

    public void prepareSave(){
    }
}
