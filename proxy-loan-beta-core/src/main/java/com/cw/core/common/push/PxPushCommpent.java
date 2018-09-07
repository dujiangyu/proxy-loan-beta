package com.cw.core.common.push;

import cn.jpush.api.push.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;


/**
 * java后台极光推送方式二：使用Java SDK
 */
@SuppressWarnings({ "deprecation", "restriction" })
@Service
public class PxPushCommpent {

    private static final Logger log = LoggerFactory.getLogger(PxPushCommpent.class);
    private static String masterSecret = "c436981acd4eaa2893b66961";
    private static String appKey = "265b62b2080691dd94e2e29c";
    private static String ALERT = "推送信息";
    private static String TITLE = "推送标题信息";
    private static String MSG_CONTENT = "测试推送消息内容</a>";
    /**
     * 极光推送
     */
    public Boolean pushMessage(String sendTitle,String sendContent,String productName,Long productId,String linkUrl,String masterSecret1,String appKey1){
        String alias = "all";//声明别名
        log.info("对别名" + alias + "的用户推送信息");
        TITLE = sendTitle ;
        ALERT = sendContent;
        masterSecret = masterSecret1 ;
        appKey = appKey1;
        PushResult result = push(String.valueOf(alias),ALERT,productName,productId,linkUrl);
        if(result != null && result.isResultOK()){
            log.info("针对别名" + alias + "的信息推送成功！");
            return Boolean.TRUE;
        }else{
            log.info("针对别名" + alias + "的信息推送失败！");
            return Boolean.FALSE;
        }
    }


    public static void main(String[] args){
        String alias = "all";//声明别名
        log.info("对别名" + alias + "的用户推送信息");

        PushResult result = push(String.valueOf(alias),ALERT,"百度",1L,"www.baidu.com");
        if(result != null && result.isResultOK()){
            log.info("针对别名" + alias + "的信息推送成功！");
        }else{
            log.info("针对别名" + alias + "的信息推送失败！");
        }
    }

    /**
     * 生成极光推送对象PushPayload（采用java SDK）
     * @param alias
     * @param alert
     * @return PushPayload
     */
    public static PushPayload buildPushObject_android_ios_alias_alert(String alias,String alert,String productName,Long productId,String linkUrl){
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all()) //发送所有人接收推送信息  //指定别名推送 //指定tag推送
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder().setTitle(TITLE)
                                .addExtra("type", "infomation")
                                .addExtra("productID",productId)
                                .addExtra("productName",productName)
                                .addExtra("linkUrl",linkUrl==null?"":linkUrl)
                                .setAlert(alert)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtra("type", "infomation")
                                .addExtra("productID",productId)
                                .addExtra("productName",productName)
                                .addExtra("linkUrl",linkUrl==null?"":linkUrl)
                                .setAlert(alert)
                                .build())
                        .build())
                .setMessage(Message.content(MSG_CONTENT))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(90)//消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }
    /**
     * 极光推送方法(采用java SDK)
     * @param alias
     * @param alert
     * @return PushResult
     */
    public static PushResult push(String alias,String alert,String productName,Long productId,String linkUrl){
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
        PushPayload payload = buildPushObject_android_ios_alias_alert(alias,alert,productName,productId,linkUrl);
        try {
            return jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
            log.info("Msg ID: " + e.getMsgId());
            return null;
        }
    }
}