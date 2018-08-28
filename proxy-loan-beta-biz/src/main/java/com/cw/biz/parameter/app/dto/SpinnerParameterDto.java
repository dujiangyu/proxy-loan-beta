package com.cw.biz.parameter.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 下拉列表参数类
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class SpinnerParameterDto extends BaseDto{

    private String code;

    private String name;

    private Integer showOrder;

    private String cycle;

    private BigDecimal startValue;

    private BigDecimal endValue;
}
