package com.cw.biz.parameter.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 接口参数配置
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class IndexParameterDto extends BaseDto{

    //全景雷达
    private BigDecimal leida;
    //运营商报告
    private BigDecimal yunyingshang;
    //天贝报告
    private BigDecimal report;
    //借条逾期
    private BigDecimal overdue;
    //黑名单
    private BigDecimal blackList;
    //实名认证
    private BigDecimal infoAuth;
    //芝麻分接口
    private BigDecimal zmf;
    //逾期档案
    private BigDecimal overdueFile;
}
