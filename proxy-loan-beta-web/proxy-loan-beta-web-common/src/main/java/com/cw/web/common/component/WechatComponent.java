package com.cw.web.common.component;

import com.alibaba.fastjson.JSONObject;
import com.cw.core.common.util.SmsClientAccessTool;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 微信接口请求
 * Created by Administrator on 2017/8/2.
 */
@Service
public class WechatComponent {

    private String appid="wx1b6d629e26977d00";
    private String appSecret="6d88b62f0a84294f76bb3f04a5019a9a";
    private String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appSecret+"";
    private String backEncodType="utf-8";
    private String send="";

    /**
     * 获取微信jsapi_ticket
     * @return
     */
    public String getWechatApiToken(HttpServletRequest request)
    {
        try {

            //判断token是否失效
            if(request.getSession().getAttribute("expires_in")!=null)
            {
                Date tokenDate = (Date)request.getSession().getAttribute("expires_in");
                if(tokenDate.compareTo(new Date())<0)
                {
                    send =  SmsClientAccessTool.getInstance().doAccessHTTPGet(url, backEncodType);
                }else{
                    send = (String) request.getSession().getAttribute("access_token");
                }
            }
            //读取token
            String result = "{\"access_token\":\"BDeiOohxVU76otQbu2nwgmVr182USKMQOVlWAGKfsDhk8uVca_XyK8hStruQWKenH7_yZ3zeWPfMnZwpYE8mKSiMp9nMRVZJLuCFXUYq9_yCikNuOhrX6AK2Rgpm_qkNQRTfAIABCE\",\"expires_in\":7200}";
            JSONObject jsonObject = JSONObject.parseObject(result);
            String accessToken = (String)jsonObject.get("access_token");

            Calendar c = new GregorianCalendar();
            Date date = new Date();
            c.setTime(date);//设置参数时间
            c.add(Calendar.SECOND,jsonObject.getInteger("expires_in"));//把日期往后增加SECOND 秒.整数往后推,负数往前移动
            date=c.getTime(); //这个时间就是日期往后推一天的结果
            request.getSession().setAttribute("access_token",accessToken);
            request.getSession().setAttribute("expires_in",date);

           //获取ticket
            String ticketUrl ="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
            send = SmsClientAccessTool.getInstance().doAccessHTTPGet(ticketUrl, backEncodType);
            JSONObject ticketJson = JSONObject.parseObject(send);
            send = (String)ticketJson.get("ticket");

            //生成签名
            String httpUrl = "https://www.pingxundata.com/static/video/video.html";
            String noncestr = "lingshiJR666666";
            Long timestamp = System.currentTimeMillis()/1000;

            ArrayList list=new ArrayList<>();
            list.add(noncestr);
            list.add(timestamp);
            list.add(send);
            list.add(httpUrl);
            Collections.sort(list);
            //构造参数
            String param = "jsapi_ticket="+list.get(2)+"&noncestr="+list.get(0)+"&timestamp="+list.get(1)+"&url="+list.get(3);
            //获取签名
            send = SHA1Digest(param);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return send;
    }


    private final int[] abcde = { 0x67452301, 0xefcdab89, 0x98badcfe,
            0x10325476, 0xc3d2e1f0 };

    // 摘要数据存储数组
    private int[] digestInt = new int[5];

    // 计算过程中的临时数据存储数组
    private int[] tmpData = new int[80];

    /**
     * 计算sha-1摘要
     *
     * @param bytedata
     * @return
     */
    private int process_input_bytes(byte[] bytedata) {
        // 初试化常量
        System.arraycopy(abcde, 0, digestInt, 0, abcde.length);
        // 格式化输入字节数组，补10及长度数据
        byte[] newbyte = byteArrayFormatData(bytedata);
        // 获取数据摘要计算的数据单元个数
        int MCount = newbyte.length / 64;
        // 循环对每个数据单元进行摘要计算
        for (int pos = 0; pos < MCount; pos++) {
            // 将每个单元的数据转换成16个整型数据，并保存到tmpData的前16个数组元素中
            for (int j = 0; j < 16; j++) {
                tmpData[j] = byteArrayToInt(newbyte, (pos * 64) + (j * 4));
            }
            // 摘要计算函数
            encrypt();
        }
        return 20;
    }

    /**
     * 格式化输入字节数组格式
     *
     * @param bytedata
     * @return
     */
    private byte[] byteArrayFormatData(byte[] bytedata) {
        // 补0数量
        int zeros = 0;
        // 补位后总位数
        int size = 0;
        // 原始数据长度
        int n = bytedata.length;
        // 模64后的剩余位数
        int m = n % 64;
        // 计算添加0的个数以及添加10后的总长度
        if (m < 56) {
            zeros = 55 - m;
            size = n - m + 64;
        } else if (m == 56) {
            zeros = 63;
            size = n + 8 + 64;
        } else {
            zeros = 63 - m + 56;
            size = (n + 64) - m + 64;
        }
        // 补位后生成的新数组内容
        byte[] newbyte = new byte[size];
        // 复制数组的前面部分
        System.arraycopy(bytedata, 0, newbyte, 0, n);
        // 获得数组Append数据元素的位置
        int l = n;
        // 补1操作
        newbyte[l++] = (byte) 0x80;
        // 补0操作
        for (int i = 0; i < zeros; i++) {
            newbyte[l++] = (byte) 0x00;
        }
        // 计算数据长度，补数据长度位共8字节，长整型
        long N = (long) n * 8;
        byte h8 = (byte) (N & 0xFF);
        byte h7 = (byte) ((N >> 8) & 0xFF);
        byte h6 = (byte) ((N >> 16) & 0xFF);
        byte h5 = (byte) ((N >> 24) & 0xFF);
        byte h4 = (byte) ((N >> 32) & 0xFF);
        byte h3 = (byte) ((N >> 40) & 0xFF);
        byte h2 = (byte) ((N >> 48) & 0xFF);
        byte h1 = (byte) (N >> 56);
        newbyte[l++] = h1;
        newbyte[l++] = h2;
        newbyte[l++] = h3;
        newbyte[l++] = h4;
        newbyte[l++] = h5;
        newbyte[l++] = h6;
        newbyte[l++] = h7;
        newbyte[l++] = h8;
        return newbyte;
    }

    private int f1(int x, int y, int z) {
        return (x & y) | (~x & z);
    }

    private int f2(int x, int y, int z) {
        return x ^ y ^ z;
    }

    private int f3(int x, int y, int z) {
        return (x & y) | (x & z) | (y & z);
    }

    private int f4(int x, int y) {
        return (x << y) | x >>> (32 - y);
    }

    /**
     * 单元摘要计算函数
     */
    private void encrypt() {
        for (int i = 16; i <= 79; i++) {
            tmpData[i] = f4(tmpData[i - 3] ^ tmpData[i - 8] ^ tmpData[i - 14]
                    ^ tmpData[i - 16], 1);
        }
        int[] tmpabcde = new int[5];
        for (int i1 = 0; i1 < tmpabcde.length; i1++) {
            tmpabcde[i1] = digestInt[i1];
        }
        for (int j = 0; j <= 19; j++) {
            int tmp = f4(tmpabcde[0], 5)
                    + f1(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4]
                    + tmpData[j] + 0x5a827999;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int k = 20; k <= 39; k++) {
            int tmp = f4(tmpabcde[0], 5)
                    + f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4]
                    + tmpData[k] + 0x6ed9eba1;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int l = 40; l <= 59; l++) {
            int tmp = f4(tmpabcde[0], 5)
                    + f3(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4]
                    + tmpData[l] + 0x8f1bbcdc;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int m = 60; m <= 79; m++) {
            int tmp = f4(tmpabcde[0], 5)
                    + f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4]
                    + tmpData[m] + 0xca62c1d6;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int i2 = 0; i2 < tmpabcde.length; i2++) {
            digestInt[i2] = digestInt[i2] + tmpabcde[i2];
        }
        for (int n = 0; n < tmpData.length; n++) {
            tmpData[n] = 0;
        }
    }

    /**
     * 4字节数组转换为整数
     *
     * @param bytedata
     * @param i
     * @return
     */
    private int byteArrayToInt(byte[] bytedata, int i) {
        return ((bytedata[i] & 0xff) << 24) | ((bytedata[i + 1] & 0xff) << 16)
                | ((bytedata[i + 2] & 0xff) << 8) | (bytedata[i + 3] & 0xff);
    }

    //
    /**
     * 整数转换为4字节数组
     *
     * @param intValue
     * @param byteData
     * @param i
     */
    private void intToByteArray(int intValue, byte[] byteData, int i) {
        byteData[i] = (byte) (intValue >>> 24);
        byteData[i + 1] = (byte) (intValue >>> 16);
        byteData[i + 2] = (byte) (intValue >>> 8);
        byteData[i + 3] = (byte) intValue;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param ib
     * @return
     */
    private static String byteToHexString(byte ib) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F' };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param bytearray
     * @return
     */
    private static String byteArrayToHexString(byte[] bytearray) {
        String strDigest = "";
        for (int i = 0; i < bytearray.length; i++) {
            strDigest += byteToHexString(bytearray[i]);
        }
        return strDigest;
    }

    /**
     * 计算sha-1摘要，返回相应的字节数组
     *
     * @param byteData
     * @return
     */
    public byte[] getDigestOfBytes(byte[] byteData) {
        process_input_bytes(byteData);
        byte[] digest = new byte[20];
        for (int i = 0; i < digestInt.length; i++) {
            intToByteArray(digestInt[i], digest, i * 4);
        }
        return digest;
    }

    /**
     * 计算sha-1摘要，返回相应的十六进制字符串
     *
     * @param byteData
     * @return
     */
    public String getDigestOfString(byte[] byteData) {
        return byteArrayToHexString(getDigestOfBytes(byteData));
    }

    /**
     * @param data
     * @return
     */
    public String Digest(String data){
        return this.getDigestOfString(data.getBytes());
    }

    /**
     * @param data
     * @return
     */
    public String Digest(String data,String encode){
        try {
            return this.getDigestOfString(data.getBytes(encode));
        } catch (UnsupportedEncodingException e) {
            return this.Digest(data);
        }
    }

    /**
     * @param text
     * @return
     */
    public static String SHA1Digest(String text) {
        String pwd = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(text.getBytes());
            pwd = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return pwd;
    }

}
