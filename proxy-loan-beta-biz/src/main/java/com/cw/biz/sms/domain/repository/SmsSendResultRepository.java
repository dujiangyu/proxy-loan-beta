package com.cw.biz.sms.domain.repository;

import com.cw.biz.sms.domain.entity.SmsSendResult;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Title: SmsSendResultRepository
 * @Description: 短信发送结果自定义操作库
 * @Author: Away
 * @Date: 2018/9/1 16:08
 */
public interface SmsSendResultRepository extends JpaRepository<SmsSendResult,Long>{

    /**
     * @Author: Away
     * @Title: findTop1ByPhoneNumAndEventTypeOrderByRawAddTimeDesc
     * @Description: 以创建时间为倒序并按照手机号和事件类型查找最新一条数据
     * @Param phoneNum
     * @Param eventType
     * @Return: com.cw.biz.sms.domain.entity.SmsSendResult
     * @Date: 2018/9/1 16:11
     * @Version: 1
     */
    SmsSendResult findTop1ByPhoneNumAndEventTypeAndSmsTypeOrderByRawAddTimeDesc(String phoneNum,String eventType,String smsType);

}
