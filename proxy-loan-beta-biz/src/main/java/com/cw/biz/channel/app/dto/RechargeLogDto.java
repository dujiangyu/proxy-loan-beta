package com.cw.biz.channel.app.dto;

import com.cw.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 虚拟币充值日志
 * Created by Administrator on 2018/8/28.
 */
@Getter
@Setter
public class RechargeLogDto extends PageDto {

    private Long id;

    private Long rechargeOperateId;

    private BigDecimal rechargeFee;

    private Date rechargeDate;

    private Long rechargeObjectId;

    private BigDecimal rechargeBalance;

}
