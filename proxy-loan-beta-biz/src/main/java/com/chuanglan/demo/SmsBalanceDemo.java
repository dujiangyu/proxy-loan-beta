package com.chuanglan.demo;
import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;
import com.chuanglan.sms.request.SmsBalanceRequest;
import com.chuanglan.sms.response.SmsBalanceResponse;
import com.chuanglan.sms.util.ChuangLanSmsUtil;
/**
 * @author tianyh
 * @Description:查询余额
 */
public class SmsBalanceDemo {
    public static final String charset = "utf-8";
    // 请登录zz.253.com 获取创蓝API账号(非登录账号,示例:N1234567)
    public static String account = "";
    // 请登录zz.253.com 获取创蓝API密码(非登录密码)
    public static String pswd = "";


    public static void main(String[] args) throws UnsupportedEncodingException {

        //余额查询 请登录zz.253.com 获取完整的URL接口信息
        String smsBalanceRequestUrl = "https://XXX/msg/balance/json";

        SmsBalanceRequest smsBalanceRequest=new SmsBalanceRequest(account, pswd);

        String requestJson = JSON.toJSONString(smsBalanceRequest);

        System.out.println("before request string is: " + requestJson);

        String response = ChuangLanSmsUtil.sendSmsByPost(smsBalanceRequestUrl, requestJson);

        System.out.println("response after request result is : " + response);

        SmsBalanceResponse smsVarableResponse = JSON.parseObject(response, SmsBalanceResponse.class);

        System.out.println("response  toString is : " + smsVarableResponse);
    }
}
