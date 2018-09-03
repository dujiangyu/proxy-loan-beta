package com.cw.biz.parameter.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 首页配置信息
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class IndexParameterDto extends BaseDto{

    //开始金额
    private BigDecimal leida;
    //结束金额
    private BigDecimal yunyingshang;

    private BigDecimal report;
}
