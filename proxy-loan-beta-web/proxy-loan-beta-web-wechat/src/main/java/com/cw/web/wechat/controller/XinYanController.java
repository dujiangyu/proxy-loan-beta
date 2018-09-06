package com.cw.web.wechat.controller;

import com.cw.biz.CPContext;
import com.cw.biz.user.app.dto.YxUserInfoDto;
import com.cw.biz.user.app.service.CustomerAppService;
import com.cw.biz.xinyan.app.service.XinYanAppService;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: XinYanController
 * @Description: 新颜controller
 * @Author: Away
 * @Date: 2018/9/6 18:55
 */
@RestController
@RequestMapping("/xinyan/")
public class XinYanController {

    private final XinYanAppService xinYanAppService;

    private final CustomerAppService yxUserInfoAppService;

    @Autowired
    public XinYanController(XinYanAppService xinYanAppService, CustomerAppService yxUserInfoAppService) {
        this.xinYanAppService = xinYanAppService;
        this.yxUserInfoAppService = yxUserInfoAppService;
    }

    /**
     * @Author: Away
     * @Title: startAuthTaobao
     * @Description: 开始芝麻分授权认证
     * @Param info
     * @Return: com.cw.web.common.dto.CPViewResultInfo
     * @Date: 2018/9/6 18:58
     * @Version: 1
     */
    @PostMapping("startAuthTaobao.json")
    public CPViewResultInfo startAuthTaobao(CPViewResultInfo info){
        try{
            String phone = CPContext.getContext().getSeUserInfo().getPhone();
            YxUserInfoDto userInfoDto = yxUserInfoAppService.findByPhone(phone);
            info.newSuccess(this.xinYanAppService.buildXinYanOrder(userInfoDto.getCertNo()));
        }catch (Exception e){
            info.newFalse(e);
        }
        return info;
    }
}
