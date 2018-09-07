package com.cw.web.wechat.controller;

import com.cw.biz.tianbei.TianBeiClient;
import com.cw.biz.tianbei.app.dto.TianBeiRequestParamDto;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: TianBeiController
 * @Description: 天贝接口测试controller
 * @Author: Away
 * @Date: 2018/9/2 23:30
 */
@RestController
@RequestMapping("/common/")
public class TianBeiController{

    private final TianBeiClient tianBeiClient;

    @Autowired
    public TianBeiController(TianBeiClient tianBeiClient) {
        this.tianBeiClient = tianBeiClient;
    }

    @PostMapping("tianbeiReport.json")
    public CPViewResultInfo tianbeiReport(CPViewResultInfo info, @RequestBody TianBeiRequestParamDto tianBeiRequestParamDto){
        try{
            info.newSuccess(tianBeiClient.getTianbeiReport(tianBeiRequestParamDto.getName(),tianBeiRequestParamDto.getIdCard(),tianBeiRequestParamDto.getPhone()));
        }catch (Exception e){
            info.newFalse(e);
        }
        return info;
    }
    @PostMapping("getTelecomOperatorsReportInit.json")
    public CPViewResultInfo getTelecomOperatorsReportInit(CPViewResultInfo info, @RequestBody TianBeiRequestParamDto tianBeiRequestParamDto){
        try{
            info.newSuccess(tianBeiClient.getTelecomOperatorsReportInit(tianBeiRequestParamDto.getName(),tianBeiRequestParamDto.getPhone(),tianBeiRequestParamDto.getIdCard(),tianBeiRequestParamDto.getServicePwd()));
        }catch (Exception e){
            info.newFalse(e);
        }
        return info;
    }
//    @PostMapping("tianbeiReport.json")
//    public CPViewResultInfo tianbeiReport(CPViewResultInfo info, @RequestBody TianBeiRequestParamDto tianBeiRequestParamDto){
//        try{
//            info.newSuccess(tianBeiClient.getTianbeiReport(tianBeiRequestParamDto.getName(),tianBeiRequestParamDto.getIdCard(),tianBeiRequestParamDto.getPhone()));
//        }catch (Exception e){
//            info.newFalse(e);
//        }
//        return info;
//    }

}
