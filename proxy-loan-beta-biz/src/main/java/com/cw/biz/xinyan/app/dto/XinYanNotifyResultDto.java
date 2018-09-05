package com.cw.biz.xinyan.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Data;

/**
 * @Title: XinYanNotifyResultDto
 * @Description: 新颜芝麻分回调结果
 * @Author: Away
 * @Date: 2018/9/5 23:46
 */

@Data
public class XinYanNotifyResultDto extends BaseDto{

    /**新颜分配商户号**/
    private String memberId;

    /**新颜分配终端号**/
    private String terminalId;

    /**任务类型 CARRIER：运营商**/
    private String taskType;

    /**通知阶段 LOGIN：登录阶段 TASK：采集阶段**/
    private String phaseType;

    /**任务结果 true 任务完成 false 任务失败**/
    private String result;

    /**新颜订单号**/
    private String tradeNo;

    /**商户订单号**/
    private String memberTransId;

    /**描述**/
    private String desc;

    /**是否最终状态 true 最终状态； false 非最终状态**/
    private String ﬁnished;

    /**用户id,本表中为用户身份证**/
    private String userId;

    /**手机号码 部分业务有该属性如运营商**/
    private String mobile;

    /**登录帐号 部分业务有该属性如社保公积金等**/
    private String account;


}
