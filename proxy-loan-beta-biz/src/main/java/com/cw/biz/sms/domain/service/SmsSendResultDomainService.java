package com.cw.biz.sms.domain.service;

import com.alibaba.fastjson.JSON;
import com.cw.biz.sms.app.dto.SmsSendResultDto;
import com.cw.biz.sms.domain.entity.SmsSendResult;
import com.cw.biz.sms.domain.repository.SmsSendResultRepository;
import com.cw.biz.sms.enums.ENUM_SMS_PARAMS;
import com.cw.core.common.util.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.LinkedHashMap;

/**
 * @Title: SmsSendResultDomainService
 * @Description: 短信发送结果数据服务
 * @Author: Away
 * @Date: 2018/9/1 17:41
 */
@Service
public class SmsSendResultDomainService {

    private final SmsSendResultRepository smsSendResultRepository;

    @Autowired
    public SmsSendResultDomainService(SmsSendResultRepository smsSendResultRepository) {
        this.smsSendResultRepository = smsSendResultRepository;
    }

    /**
     * @Author: Away
     * @Title: findRecentSmsResultByPhoneAndType
     * @Description: 按照短信发送事件类型、短信类型、手机号查找最近的短信发送结果内容
     * @Param phoneNum 手机号
     * @Param eventType 短信发送事件类型
     * @Param smsType 短信类型
     * @Return: com.cw.biz.sms.app.dto.SmsSendResultDto
     * @Date: 2018/9/1 17:45
     * @Version: 1
     */
    public SmsSendResultDto findRecentSmsResultByPhoneAndType(String phoneNum,String eventType,String smsType){
        SmsSendResult sourceData=this.smsSendResultRepository.findTop1ByPhoneNumAndEventTypeAndSmsTypeOrderByRawAddTimeDesc(phoneNum, eventType,smsType);
        return ObjectHelper.isNotEmpty(sourceData)?sourceData.to(SmsSendResultDto.class):null;
    }

    /**
     * @Author: Away
     * @Title: saveResult
     * @Description: 保存短信发送结果
     * @Param eventType 事件类型
     * @Param smsType 短信类型
     * @Param paramsMap 短信变量
     * @Return: com.cw.biz.sms.app.dto.SmsSendResultDto
     * @Date: 2018/9/1 21:46
     * @Version: 1
     */
    public SmsSendResultDto saveResult(String eventType, String smsType, LinkedHashMap<String,String> paramsMap){
        SmsSendResult smsSendResult=new SmsSendResult();
        smsSendResult.setContent(JSON.toJSONString(paramsMap));
        smsSendResult.setEventType(eventType);
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MINUTE,5);
        smsSendResult.setExpiryTime(calendar.getTime());
        smsSendResult.setPhoneNum(paramsMap.get(ENUM_SMS_PARAMS.phone.toString()));
        smsSendResult.setSmsType(smsType);
        smsSendResult=this.smsSendResultRepository.save(smsSendResult);
        return smsSendResult.to(SmsSendResultDto.class);
    }

}
