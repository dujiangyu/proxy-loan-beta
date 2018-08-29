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
 * 虚拟币消耗日志
 * Created by Administrator on 2018/8/28.
 */
@Entity
@Table(name="yx_consume_log")
@Getter
@Setter
public class ConsumeLog extends AggEntity {

    @Column(name="customer_id",columnDefinition="int(11) not null comment '客户Id'")
    private Long customerId;

    @Column(name="consume_fee",columnDefinition="decimal(10,2) comment '消耗金额'")
    private BigDecimal consumeFee;

    @Column(name="consume_date",columnDefinition="datetime comment '消耗时间'")
    private Date consumeDate;

    @Column(name="operate_user_id",columnDefinition="int(11) comment '消耗操作人员Id'")
    private Long operateUserId;

    @Column(name="consume_balance",columnDefinition="decimal(10,2) comment '消耗后余额'")
    private BigDecimal consumeBalance;

    public void prepareSave(){
    }
}
