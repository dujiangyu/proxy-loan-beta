package com.cw.biz.xinyan.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Data;

/**
 * @Title: XinYanNotifyResultParamDto
 * @Description: 用于接收回调数据
 * @Author: Away
 * @Date: 2018/9/5 23:46
 */

@Data
public class XinYanNotifyResultParamDto extends BaseDto{

    /**新颜分配商户号**/
    private String memberId;

    /**新颜分配终端号**/
    private String terminalId;

    /**任务类型 CARRIER：运营商**/
    private String task_type;

    /**通知阶段 LOGIN：登录阶段 TASK：采集阶段**/
    private String phase_type;

    /**任务结果 true 任务完成 false 任务失败**/
    private String result;

    /**新颜订单号**/
    private String trade_no;

    /**商户订单号**/
    private String member_trans_id;

    /**描述**/
    private String desc;

    /**是否最终状态 true 最终状态； false 非最终状态**/
    private String ﬁnished;

    private TaskContent task_content;


}
