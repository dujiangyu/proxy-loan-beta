package com.cw.biz.xinyan.credit;

import com.cw.biz.xinyan.XYConfig;
import com.cw.biz.xinyan.credit.rsa.RsaCodingUtil;
import com.cw.biz.xinyan.credit.util.HttpUtils;
import com.cw.biz.xinyan.credit.util.YouxinSecurityUtil;
import com.cw.core.common.util.ObjectHelper;
import com.cw.core.common.util.VerifyCodeUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: XYClient
 * @Description: 新颜基础客户端
 * @Author: Away
 * @Date: 2018/9/5 17:21
 */
@Slf4j
@Service
public class XYClient {

    public String doRequestWithRsa(String url, Map<String,String> params,String requestType,boolean paramIsUnderLine) throws Exception {
        /** 1、 商户号 **/
        String member_id= XYConfig.MEMBER_ID;
        /**2、终端号 **/
        String terminal_id=XYConfig.TERMINAL_ID;
        /** 3、 加密数据类型 **/
        String data_type=XYConfig.DATA_TYPE;
        /** 4、加密数据 **/

        String trans_id=""+System.currentTimeMillis()+ VerifyCodeUtil.generateVerifyCode(5);// 商户订单号
        String trade_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 订单日期


        /** 组装参数  **/
        if(ObjectHelper.isEmpty(params))params=new HashMap<>();
        if(paramIsUnderLine){
            if(!params.containsKey("member_id"))params.put("member_id",member_id);
            if(!params.containsKey("terminal_id"))params.put("terminal_id",terminal_id);
            if(!params.containsKey("trans_id"))params.put("trans_id",trans_id);
            if(!params.containsKey("trade_date"))params.put("trade_date",trade_date);
        }else{
            if(!params.containsKey("memberId"))params.put("memberId",member_id);
            if(!params.containsKey("terminalId"))params.put("terminalId",terminal_id);
            if(!params.containsKey("transId"))params.put("transId",trans_id);
        }

        String XmlOrJson = JSONObject.fromObject(params).toString();
        log.info("====请求明文:" + XmlOrJson);

        /** base64 编码 **/
        String base64str = YouxinSecurityUtil.Base64Encode(XmlOrJson);
        log.info("====base64 编码:"+base64str);

        /** rsa加密  **/
//        String pfxpath = "/home/youxin/keys/"+XYConfig.PFX_NAME;// 商户私钥
        String pfxpath = "/Users/mac/Desktop/code/"+XYConfig.PFX_NAME;
        File pfxfile = ResourceUtils.getFile(pfxpath);
        if (!pfxfile.exists()) {
            log.info("私钥文件不存在！");
            throw new RuntimeException("私钥文件不存在！");
        }
        String pfxpwd =XYConfig.PFX_PWD;// 私钥密码

        String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);//加密数据
        log.info("====加密串:"+data_content);

        /**============== http 请求==================== **/
        Map<String, String> headers =new HashMap<>();
        Map<String, Object> httpParams =new HashMap<String,Object>();
        if(paramIsUnderLine){
            httpParams.put("member_id", member_id);
            httpParams.put("terminal_id", terminal_id);
            httpParams.put("data_type", "json");
            httpParams.put("data_content", data_content);
        }else{
            if(!requestType.equalsIgnoreCase("get")){
                httpParams.put("memberId", member_id);
                httpParams.put("terminalId", terminal_id);
                httpParams.put("dataContent", data_content);
            }
        }
        long startTime = System.currentTimeMillis();
        log.info("开始时间："+startTime);
        String PostString="";
        if(requestType.equalsIgnoreCase("post")){
            PostString = HttpUtils.doPostByForm(url, headers,httpParams);
        }else{
            headers.put("memberId",member_id);
            headers.put("terminalId",terminal_id);
            PostString=HttpUtils.doGet(url,headers,httpParams);
        }
        long endTime = System.currentTimeMillis();
        log.info("结束时间："+endTime);
        log.info("消耗时间："+(endTime-startTime));
        log.info("请求返回："+ PostString);
        /** ================处理返回============= **/
        if(PostString.isEmpty()){//判断参数是否为空
            log.info("=====返回数据为空");
            throw new RuntimeException("返回数据为空");
        }
        return PostString;
    }

}
