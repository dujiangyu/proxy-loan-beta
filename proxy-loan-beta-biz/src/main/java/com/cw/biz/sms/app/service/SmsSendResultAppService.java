package com.cw.biz.sms.app.service;

import com.cw.biz.sms.app.dto.SmsSendResultDto;
import com.cw.biz.sms.domain.service.SmsSendResultDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;

/**
 * @Title: SmsSendResultAppService
 * @Description: 短信发送结果应用服务
 * @Author: Away
 * @Date: 2018/9/1 17:53
 */
@Service
@Transactional
public class SmsSendResultAppService {

    private final SmsSendResultDomainService smsSendResultDomainService;

    @Autowired
    public SmsSendResultAppService(SmsSendResultDomainService smsSendResultDomainService) {
        this.smsSendResultDomainService = smsSendResultDomainService;
    }

    /**
     * @Author: Away
     * @Title: findRecentResultByPhoneAndEventAndType
     * @Description: 按照手机号、事件类型、短信类型查找结果
     * @Param phoneNum 手机号
     * @Param event 事件类型
     * @Param type 短信类型
     * @Return: com.cw.biz.sms.app.dto.SmsSendResultDto
     * @Date: 2018/9/1 17:57
     * @Version: 1
     */
    public SmsSendResultDto findRecentResultByPhoneAndEventAndType(String phoneNum,String event,String type){
        return this.smsSendResultDomainService.findRecentSmsResultByPhoneAndType(phoneNum, event, type);
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
        return this.smsSendResultDomainService.saveResult(eventType, smsType, paramsMap);
    }
}
