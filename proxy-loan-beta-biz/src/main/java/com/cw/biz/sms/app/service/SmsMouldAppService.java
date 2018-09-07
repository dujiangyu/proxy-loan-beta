package com.cw.biz.sms.app.service;

import com.cw.biz.sms.app.dto.SmsMouldDto;
import com.cw.biz.sms.domain.service.SmsMouldDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Title: SmsMouldAppService
 * @Description: 短信模板应用服务
 * @Author: Away
 * @Date: 2018/9/1 17:50
 */
@Service
@Transactional
public class SmsMouldAppService {

    private final SmsMouldDomainService smsMouldDomainService;

    @Autowired
    public SmsMouldAppService(SmsMouldDomainService smsMouldDomainService) {
        this.smsMouldDomainService = smsMouldDomainService;
    }

    /**
     * @Author: Away
     * @Title: findByMouldCode
     * @Description: 按照模板编号查找短信模板
     * @Param mouldCode
     * @Return: com.cw.biz.sms.app.dto.SmsMouldDto
     * @Date: 2018/9/1 17:52
     * @Version: 1
     */
    public SmsMouldDto findByMouldCode(String mouldCode){
        return this.smsMouldDomainService.findByMouldCode(mouldCode);
    }


}
