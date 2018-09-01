package com.cw.biz.sms.domain.service;

import com.cw.biz.sms.app.dto.SmsMouldDto;
import com.cw.biz.sms.domain.entity.SmsMould;
import com.cw.biz.sms.domain.repository.SmsMouldRepository;
import com.cw.core.common.util.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title: SmsMouldDomainService
 * @Description: 短信模板数据服务
 * @Author: Away
 * @Date: 2018/9/1 17:33
 */
@Service
public class SmsMouldDomainService {

    private final SmsMouldRepository smsMouldRepository;

    @Autowired
    public SmsMouldDomainService(SmsMouldRepository smsMouldRepository) {
        this.smsMouldRepository = smsMouldRepository;
    }

    /**
     * @Author: Away
     * @Title: findByMouldCode
     * @Description: 按照模板唯一编号查找
     * @Param mouldCode
     * @Return: com.cw.biz.sms.domain.entity.SmsMould
     * @Date: 2018/9/1 16:06
     * @Version: 1
     */
    public SmsMouldDto findByMouldCode(String mouldCode){
        SmsMould sourceData=this.smsMouldRepository.findByMouldCode(mouldCode);
        return ObjectHelper.isNotEmpty(sourceData)?sourceData.to(SmsMouldDto.class):null;
    }
}
