package com.chuanglan.demo;
import com.alibaba.fastjson.JSON;
import com.chuanglan.sms.request.SmsVariableRequest;
import com.chuanglan.sms.response.SmsVariableResponse;
import com.chuanglan.sms.util.ChuangLanSmsUtil;

import java.io.UnsupportedEncodingException;
/**
 *
 * @author tianyh
 * @Description:变量短信发送
 */
public class SmsVariableDemo {
    public static final String charset = "utf-8";
    // 请登录zz.253.com 获取创蓝API账号(非登录账号,示例:N1234567)
    public static String account = "N7217027";
    // 请登录zz.253.com 获取创蓝API密码(非登录密码)
    public static String pswd = "Ojb2DeuEcsff2a";

    public static void main(String[] args) throws UnsupportedEncodingException {

        //变量短信发送的URL 请登录zz.253.com 获取完整的URL接口信息
        String smsVariableRequestUrl = "https://smssh1.253.com/msg/variable/json";
        //设置您要发送的内容：其中“【】”中括号为运营商签名符号，多签名内容前置添加提交
        String msg = "【友信科技】您的验证码是：{$var}，{$var}分钟有效！";
        //参数组
        String params = "18623270209,660,5;";
        //状态报告
        String report= "true";

        SmsVariableRequest smsVariableRequest=new SmsVariableRequest(account, pswd, msg, params, report);

        String requestJson = JSON.toJSONString(smsVariableRequest);

        System.out.println("before request string is: " + requestJson);

        String response = ChuangLanSmsUtil.sendSmsByPost(smsVariableRequestUrl, requestJson);

        System.out.println("response after request result is : " + response);

        SmsVariableResponse smsVariableResponse = JSON.parseObject(response, SmsVariableResponse.class);

        System.out.println("response  toString is : " + smsVariableResponse);

    }
}
