package com.cw.web.wechat.controller;/*
 * 描       述:  &lt;描述&gt;
 * 修  改   人:  ${user}
 * 修  改 时 间:  ${date}
 * &lt;修改描述:&gt;
 */

import com.cw.biz.CPContext;
import com.cw.biz.channel.app.dto.ChannelDto;
import com.cw.biz.channel.app.service.ChannelAppService;
import com.cw.biz.user.app.dto.CustomerDto;
import com.cw.biz.user.app.dto.YxUserInfoDto;
import com.cw.biz.user.app.service.CustomerAppService;
import com.cw.web.common.dto.CPViewResultInfo;
import com.cw.web.wechat.AbstractWechatController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChannelUserController extends AbstractWechatController {

    @Autowired
    private CustomerAppService yxUserInfoAppService;

    @Autowired
    private ChannelAppService channelAppService;

    /** 渠道引流客户查询
        *&lt;功能简述&gt;
        *&lt;功能详细描述&gt;
        * ${tags} [参数说明]
        *
        * @return ${return_type} [返回类型说明]
        * @exception throws [异常类型] [异常说明]
        * @see [类、类#方法、类#成员]
        */
       @PostMapping("/customer/findChannelByCondition.json")
       @ResponseBody
       public CPViewResultInfo findChannelByCondition(@RequestBody YxUserInfoDto yxUserInfoDto) {
           CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
           ChannelDto channelDto = channelAppService.findByChannelUserId(CPContext.getContext().getSeUserInfo().getId());
           yxUserInfoDto.setSourceChannel(channelDto.getCode());
           Page<CustomerDto> userInfoDtos = yxUserInfoAppService.findChannelByCondition(yxUserInfoDto);
           cpViewResultInfo.setData(userInfoDtos);
           cpViewResultInfo.setSuccess(true);
           cpViewResultInfo.setMessage("查询成功");
           return cpViewResultInfo;
       }
}
