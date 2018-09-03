package com.cw.biz.sms.app.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chuanglan.sms.request.SmsVariableRequest;
import com.chuanglan.sms.util.ChuangLanSmsUtil;
import com.cw.biz.sms.app.dto.SmsMouldDto;
import com.cw.biz.sms.enums.ENUM_SMS_PARAMS;
import com.cw.biz.sms.enums.ENUM_SMS_RESPOND;
import com.cw.core.common.enums.ENUM_SMS_TYPE;
import com.cw.core.common.util.ObjectHelper;
import com.cw.core.common.util.VerifyCodeUtil;
import com.zds.common.lang.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Title: SmsComponent
 * @Description: 短信组件
 * @Author: Away
 * @Date: 2018/9/1 18:11
 */
@Slf4j
@Service
public class SmsComponent {

    /**
     * 短信接口账号
     */
    @Value("${application.sms.account}")
    private String smsAccount;

    /**
     * 短信接口密码
     */
    @Value("${application.sms.pswd}")
    private String smsPassWord;

    /**
     * 短信发送接口
     */
    @Value("${application.sms.sendSmsUrl}")
    private String sendSmsUrl;

    private final SmsMouldAppService smsMouldAppService;

    private final SmsSendResultAppService smsSendResultAppService;

    @Autowired
    public SmsComponent(SmsMouldAppService smsMouldAppService, SmsSendResultAppService smsSendResultAppService) {
        this.smsMouldAppService = smsMouldAppService;
        this.smsSendResultAppService = smsSendResultAppService;
    }


    /**
     * @Author: Away
     * @Title: sendSms
     * @Description: 发送短信通用接口
     * @Param mouldCode 模板编号
     * @Param eventType 事件类型
     * @Param smsType 短信类型
     * @Param paramsMap 短信变量
     * @Return: boolean
     * @Date: 2018/9/1 21:49
     * @Version: 1
     */
    public boolean sendSms(String mouldCode, String eventType, String smsType, LinkedHashMap<String,String> paramsMap) throws BusinessException {
        SmsMouldDto mould = this.smsMouldAppService.findByMouldCode(mouldCode);
        if (ObjectHelper.isNotEmpty(mould)&&ObjectHelper.isNotEmpty(paramsMap)) {
            //设置您要发送的内容：其中“【】”中括号为运营商签名符号，多签名内容前置添加提交
            //参数组
            StringBuilder params=new StringBuilder();
            for(Map.Entry temp:paramsMap.entrySet()){
                params.append(temp.getValue());
                params.append(",");
            }
            params.append(";");
            //状态报告
            String report = "true";
            //短信接口请求参数
            SmsVariableRequest smsVariableRequest = new SmsVariableRequest(smsAccount, smsPassWord, mould.getMouldContent(), params.toString(), report);
            //发送请求
            String response = ChuangLanSmsUtil.sendSmsByPost(sendSmsUrl, JSON.toJSONString(smsVariableRequest));
            JSONObject sendJsonResult=JSONObject.parseObject(response);
            //发送成功
            if(sendJsonResult.getString("code").equalsIgnoreCase("0")){
                //写入发送结果表中
                this.smsSendResultAppService.saveResult(eventType, smsType, paramsMap);
                return true;
            }else{
                throw new BusinessException(sendJsonResult.getString("code"), ENUM_SMS_RESPOND.valueOf("M"+sendJsonResult.getString("code")).getMessage());
            }
        } else {
            log.error("发送短信失败，短信模板编号为空，或者短信变量为空");
            return false;
        }
    }

    /**
     * @Author: Away
     * @Title: sendValidateCode
     * @Description: 发送验证码
     * @Param mouldCode 短信模板编号
     * @Param phoneNum 电话号码
     * @Param validateCode 验证码
     * @Return: boolean
     * @Date: 2018/9/1 22:00
     * @Version: 1
     */
    public boolean sendValidateCode(String mouldCode,String phoneNum){
        String validateCode= VerifyCodeUtil.generateVerifyCode(5);
        LinkedHashMap<String,String> smsPara=new LinkedHashMap<>();
        smsPara.put(ENUM_SMS_PARAMS.phone.toString(),phoneNum);
        smsPara.put(ENUM_SMS_PARAMS.userName.toString(),phoneNum);
        smsPara.put(ENUM_SMS_PARAMS.validateCode.toString(),validateCode);
        //短信有效期5分钟
        smsPara.put(ENUM_SMS_PARAMS.expiryTime.toString(), "5");
        return this.sendSms(mouldCode, null, ENUM_SMS_TYPE.VALIDATE.toString(),smsPara);
    }

}
