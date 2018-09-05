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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerInfoController extends AbstractWechatController{

    @Autowired
    private CustomerAppService yxUserInfoAppService;

    /** 查询用户信息
        *&lt;功能简述&gt;
        *&lt;功能详细描述&gt;
        * ${tags} [参数说明]
        *
        * @return ${return_type} [返回类型说明]
        * @exception throws [异常类型] [异常说明]
        * @see [类、类#方法、类#成员]
        */
       @GetMapping("/customer/findById.json")
       @ResponseBody
       public CPViewResultInfo findById() {
          String phone = CPContext.getContext().getSeUserInfo().getPhone();
          CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
          YxUserInfoDto yxUserInfoDto = yxUserInfoAppService.findByPhone(phone);
          cpViewResultInfo.setData(yxUserInfoDto);
          cpViewResultInfo.setSuccess(true);
          cpViewResultInfo.setMessage("查询成功");
          return cpViewResultInfo;
       }

    /** 修改用户信息
         *&lt;功能简述&gt;
         *&lt;功能详细描述&gt;
         * ${tags} [参数说明]
         *
         * @return ${return_type} [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @PostMapping("/customer/updateUserInfo.json")
        @ResponseBody
        public CPViewResultInfo update(@RequestBody YxUserInfoDto yxUserInfoDto) {
           CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
           yxUserInfoAppService.update(yxUserInfoDto);
           cpViewResultInfo.setSuccess(true);
           cpViewResultInfo.setMessage("修改成功");
           return cpViewResultInfo;
        }
}
