package com.cw.biz.channel.app.dto;

import com.cw.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 虚拟币消耗日志
 * Created by Administrator on 2018/8/28.
 */
@Getter
@Setter
public class ConsumeLogDto extends PageDto {

    private Long id;

    private Long customerId;

    private BigDecimal consumeFee;

    private Date consumeDate;

    private Long operateUserId;

    private BigDecimal consumeBalance;

}
