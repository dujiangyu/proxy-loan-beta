package com.cw.biz.parameter.app.dto;

import com.cw.biz.common.dto.BaseDto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 首页查询参数配置
 * Created by Administrator on 2017/6/1.
 */
public class ParameterDto extends BaseDto{

    private Long id;
    @NotNull
    private String parameterCode;
    @NotNull
    private String parameterName;
    @NotNull
    private BigDecimal parameterValue;

    private String parameterType;

    private Boolean isValid=Boolean.TRUE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParameterCode() {
        return parameterCode;
    }

    public void setParameterCode(String parameterCode) {
        this.parameterCode = parameterCode;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public BigDecimal getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(BigDecimal parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        isValid = isValid;
    }
}
