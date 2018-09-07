package com.cw.web.common.controller;

import com.cw.biz.xinyan.app.dto.XinYanNotifyResultParamDto;
import com.cw.biz.xinyan.app.service.XinYanAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/common/")
public class XinYanNotifyController{

    private final XinYanAppService xinYanAppService;

    @Autowired
    public XinYanNotifyController(XinYanAppService xinYanAppService) {
        this.xinYanAppService = xinYanAppService;
    }

    @PostMapping("receiveXinYanNotify.json")
    public void receiveXinYanNotify(@RequestBody XinYanNotifyResultParamDto xinYanNotifyResultParamDto){
        try{
            this.xinYanAppService.saveOrUpdateNotify(xinYanNotifyResultParamDto);
        }catch (Exception e){
            log.error("新颜回调出错",e);
        }
    }
}
