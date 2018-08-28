package com.cw.web.common.controller;

import com.cw.web.common.component.WechatComponent;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信请求接口
 * Created by Administrator on 2017/8/2.
 */
@Controller
@RequestMapping("/common")
public class WechatController {

    @Autowired
    private WechatComponent wechatComponent;

    /**
     * 获取jsapi_ticket
     * @return
     */
    @GetMapping("getWechatApiTicket.json")
    @ResponseBody
    public CPViewResultInfo getWechatApiTicket(HttpServletRequest request)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        String result = wechatComponent.getWechatApiToken(request);
        cpViewResultInfo.setData(result);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }
}
