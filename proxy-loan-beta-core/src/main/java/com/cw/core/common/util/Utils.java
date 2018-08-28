package com.cw.core.common.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 工具类
 * Created by Administrator on 2017/6/1.
 */
public class Utils {
    /**
     * 产生随机验证码
     * @return
     */
    public static int getRandNum() {
        int randomInt = new Random().nextInt(999999);
        return randomInt;
    }


    /**
     * 时间格式转换
     * @param date
     * @return
     */
    public static String convertDate(Date date){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowDate = simpleDateFormat.format(date);
            return nowDate;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 前一个月的数据
     * @param date
     * @return
     */
    public static String convertNowBeforeDate(Date date){
        try {
            Date dNow = new Date();   //当前时间
            Date dBefore = new Date();
            Calendar calendar = Calendar.getInstance(); //得到日历
            calendar.setTime(dNow);//把当前时间赋给日历
            calendar.add(Calendar.DAY_OF_MONTH, -30);  //设置为前一天
            dBefore = calendar.getTime();   //得到前一天的时间

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
            String defaultStartDate = sdf.format(dBefore);    //格式化前一天
            return defaultStartDate;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取系统时间前一天
     * @return
     */
    public static String beforeDay(){
        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
        String defaultStartDate = sdf.format(dBefore);    //格式化前一天
        return defaultStartDate;
    }

    public static Date strConvertDate(String date){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
            return sdf.parse(date);
        }catch (Exception e){
            return null;
        }
    }
    /**
     * string to xml
     * @param xmlStr
     * @return
     */
    public static String strConvertXml(String xmlStr){
        String result = null;
        try {
            Document document = DocumentHelper.parseText(xmlStr);
            Element root = document.getRootElement();
            result = root.element("returnstatus").getText();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String randomStr(){
        String str = "";
        str += (int)(Math.random()*9+1);
        for(int i = 0; i < 3; i++){
            str += (int)(Math.random()*10);
        }
        return str;
    }

    /**
     * 发送数据接口
     * @param url
     * @return
     */
    public static String sendDateInterface(String url,String send,String sendType) {

        try {
            String backEncodType="UTF-8";
            String sendResult ="";
            if (sendType != null && (sendType.toLowerCase()).equals("get")) {
                sendResult = SmsClientAccessTool.getInstance().doAccessHTTPGet(
                        url + "?" + send.toString(), backEncodType);
            } else {
                sendResult = SmsClientAccessTool.getInstance().doAccessHTTPPost(url,
                        send.toString(), backEncodType);
            }
            return sendResult;
        } catch (Exception e) {
            return "error";
        }
    }
}
