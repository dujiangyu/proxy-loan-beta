package com.cw.biz.tianbei;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cw.biz.tianbei.app.dto.TianBeiResultDto;
import com.cw.biz.tianbei.app.service.TianBeiResultAppService;
import com.cw.biz.tianbei.enums.ENUM_TIANBEI_TYPE;
import com.cw.core.common.util.ObjectHelper;
import com.zds.common.net.HttpUtil;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Http请求通用工具 <br>
 *
 */
@Service
public class TianBeiClient {

    private final TianBeiResultAppService tianBeiResultAppService;

    @Autowired
    public TianBeiClient(TianBeiResultAppService tianBeiResultAppService) {
        this.tianBeiResultAppService = tianBeiResultAppService;
    }
    /**
     * 天贝报告
     */
    private static final String TIANBEI_REPORT="https://cred2.beikeyuntiao.com/platform-framework/api/mg/report";
    /**
     * 运营商报告
     */
    private static final String TELECOM_OPERATORS_REPORT="http://cred2.beikeyuntiao.com/platform-framework/api/third/jxl/";
    /**
     * 全景雷达
     */
    private static final String REDAR="http://credit.whtianbei.com/api/radar/get-report";

    /**
     * 密钥
     */
    private static final String SECRET="";


    private static final Logger logger = Logger.getLogger(TianBeiClient.class);
    /**
     * 定义编码格式 UTF-8
     */
    public static final String URL_PARAM_DECODECHARSET_UTF8 = "UTF-8";
    private static PoolingHttpClientConnectionManager cm = null;

    private static RequestConfig reqConfig = null;
    private static CloseableHttpClient httpClient=null;

    static {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", plainsf)
            .register("https", sslsf)
            .build();

        cm = new PoolingHttpClientConnectionManager(registry);
        cm.setMaxTotal(20);
        cm.setDefaultMaxPerRoute(5);

        reqConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(5000)
            .setConnectTimeout(5000)
            .setSocketTimeout(5000)
            .setExpectContinueEnabled(false)
            .build();
        
        httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setConnectionManagerShared(false)
                .evictExpiredConnections()
                .setConnectionTimeToLive(60, TimeUnit.SECONDS)
                .setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                .build();
    }


    public static String doRequest(HttpRequestBase httpReq) {
        CloseableHttpResponse httpResp = null;
        String result="";
        try {
            setHeaders(httpReq);

            httpReq.setConfig(reqConfig);

            httpResp = httpClient.execute(httpReq);

            int statusCode = httpResp.getStatusLine().getStatusCode();
            if(statusCode==200){
            	result = EntityUtils.toString(httpResp.getEntity(), "utf-8");
    		}else if(statusCode==400 || statusCode==244){
    			result = EntityUtils.toString(httpResp.getEntity(), "utf-8");
            }else{
            	result = EntityUtils.toString(httpResp.getEntity(), "utf-8");
            	
    			throw new Exception("通信失败,状态返回异常,status="+httpResp.getStatusLine().getStatusCode());
    		}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpResp != null) {
                    httpResp.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 设置请求头
     *
     * @param httpReq
     */
    private static void setHeaders(HttpRequestBase httpReq) {
        httpReq.setHeader("Content-Type", "text/html; charset=utf-8");
    }

    
    public static String doCommonGet(Map<String,String> headerMap,Map<String,String> paramMap , String url) throws Exception{
        StringBuilder sb = new StringBuilder(url);
        if (paramMap!=null){
            sb.append("?");
            int i=0;
            for(String key:paramMap.keySet()){
            	sb.append(key).append("=").append(URLEncoder.encode(paramMap.get(key), "utf-8"));
                if (i<(paramMap.keySet().size()-1)) sb.append("&");
                i++;
            }
        }
        url = sb.toString();
        HttpGet get = new HttpGet(url);
        get.setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1"); 
        //设置头信息
        if(headerMap==null)headerMap=new HashMap<>();
        headerMap.put("X-Mall-Token",SECRET);
        for(String key:headerMap.keySet()){
            get.addHeader(key, headerMap.get(key));
        }
       // logger.info("====doGet(url = {})请求参数为:{}",url,JSON.toJSONString(paramMap));
        return doRequest(get);
    }
    

    private static String doCommonPost(String url, Map<String, String> dataMap, Map<String, String> headerMap){
        if(headerMap==null)headerMap=new HashMap<>();
        if(!headerMap.containsKey("X-Mall-Token"))headerMap.put("X-Mall-Token",SECRET);
        return HttpUtil.getInstance().get(url,dataMap,headerMap,URL_PARAM_DECODECHARSET_UTF8).getBody();
    }

    /**
     * @Author: Away
     * @Title: getTianbeiReport
     * @Description: 天贝报告
     * @Param name 名称
     * @Param idNo 身份证
     * @Param phone 电话
     * @Return: java.lang.String
     * @Date: 2018/9/2 0:14
     * @Version: 1
     */
    public String getTianbeiReport(String name,String idNo,String phone) throws Exception {
        TianBeiResultDto tianBeiResultDto=this.tianBeiResultAppService.findByIdcardAndQueryType(idNo, ENUM_TIANBEI_TYPE.REPORT.code);
        if(ObjectHelper.isNotEmpty(tianBeiResultDto)){
            return tianBeiResultDto.getQueryResult();
        }
        Map<String, String> paramMap=new HashMap<String, String>();
        paramMap.put("phone", phone);
        paramMap.put("name", name);
        paramMap.put("idNo", idNo);
        String resultStr=doCommonGet(null,paramMap,TIANBEI_REPORT);
        JSONObject resultJson=JSON.parseObject(resultStr);
        if(resultJson.getIntValue("code")==0){
            this.tianBeiResultAppService.saveOrUpdate(idNo,ENUM_TIANBEI_TYPE.REPORT.code,resultStr);
        }
        return resultStr;
    }

    /**
     * @Author: Away
     * @Title: getTelecomOperatorsReportInit
     * @Description: 运营商验证初始化
     * @Param name
     * @Param phone
     * @Param idNo 身份证
     * @Param servicePwd 手机服务密码
     * @Return: java.lang.String
     * @Date: 2018/9/2 3:32
     * @Version: 1
     */
    public String getTelecomOperatorsReportInit(String name,String phone,String idNo,String servicePwd){
        TianBeiResultDto tianBeiResultDto=this.tianBeiResultAppService.findByIdcardAndQueryType(idNo, ENUM_TIANBEI_TYPE.TELECOM_OPERATORS_REPORT_RESULT.code);
        if(ObjectHelper.isNotEmpty(tianBeiResultDto)){
            return tianBeiResultDto.getQueryResult();
        }
        //初始化
        String initUrl=TELECOM_OPERATORS_REPORT+"init";
        Map<String,String> initPara=new HashMap<>();
        initPara.put("name",name);
        initPara.put("phone",phone);
        initPara.put("idNo",idNo);
        initPara.put("servicePwd",servicePwd);
        String initResultStr=doCommonPost(initUrl,initPara,null);
        JSONObject initJson= JSON.parseObject(initResultStr);
        if(initJson.getIntValue("code")==0){
            int state=initJson.getIntValue("state");
            if(state==0){
                return getTelecomOperatorsReport(idNo,initJson.getString("openId"));
            }else{
                return initResultStr;
            }
        }else{
            return initResultStr;
        }
    }

    /**
     * @Author: Away
     * @Title: submitValidateCode
     * @Description: 提交验证码
     * @Param idCard 身份证
     * @Param openId openId
     * @Param captcha 验证码
     * @Return: java.lang.String
     * @Date: 2018/9/2 3:31
     * @Version: 1
     */
    public String submitValidateCode(String idCard,String openId,String captcha){
        TianBeiResultDto tianBeiResultDto=this.tianBeiResultAppService.findByIdcardAndQueryType(idCard, ENUM_TIANBEI_TYPE.TELECOM_OPERATORS_REPORT_RESULT.code);
        if(ObjectHelper.isNotEmpty(tianBeiResultDto)){
            return tianBeiResultDto.getQueryResult();
        }
        //提交验证码
        String validateCodeUrl=TELECOM_OPERATORS_REPORT+"captcha/commit";
        Map<String,String> param=new HashMap<>();
        param.put("openId",openId);
        param.put("captcha",captcha);
        String resultStr=doCommonPost(validateCodeUrl,param,null);
        JSONObject resultJson=JSON.parseObject(resultStr);
        if(resultJson.getIntValue("code")==0){
            if(resultJson.getIntValue("state")==0){
                return getTelecomOperatorsReport(idCard, openId);
            }else{
                return resultStr;
            }
        }else {
            return resultStr;
        }
    }

    /**
     * @Author: Away
     * @Title: resendVlidateCode
     * @Description: 重新发送验证码
     * @Param openId
     * @Return: java.lang.String
     * @Date: 2018/9/2 3:35
     * @Version: 1
     */
    public String resendVlidateCode(String openId){
        String url=TELECOM_OPERATORS_REPORT+"captcha/resend";
        Map<String,String> param=new HashMap<>();
        param.put("openId",openId);
        return doCommonPost(url,param,null);
    }

    /**
     * @Author: Away
     * @Title: getRadar
     * @Description: 全景雷达
     * @Param name
     * @Param phone
     * @Param idcard
     * @Return: java.lang.String
     * @Date: 2018/9/2 3:42
     * @Version: 1
     */
    public String getRadar(String name,String phone,String idcard) throws Exception{
        TianBeiResultDto tianBeiResultDto=this.tianBeiResultAppService.findByIdcardAndQueryType(idcard, ENUM_TIANBEI_TYPE.RADAR.code);
        if(ObjectHelper.isNotEmpty(tianBeiResultDto)){
            return tianBeiResultDto.getQueryResult();
        }
        String url="http://credit.whtianbei.com/api/radar/get-report";
        Map<String, String> paramMap=new HashMap<String, String>();
        paramMap.put("phone", phone);
        paramMap.put("name", name);
        paramMap.put("idcard", idcard);
        String resultStr=doCommonGet(null,paramMap,url);
        JSONObject resultJson=JSON.parseObject(resultStr);
        if(resultJson.getIntValue("code")==0){
            this.tianBeiResultAppService.saveOrUpdate(idcard,ENUM_TIANBEI_TYPE.RADAR.code,resultStr);
        }
        return resultStr;

    }

    private String getTelecomOperatorsReport(String idCard,String openId){
        //获得报告
        String getReportUrl=TELECOM_OPERATORS_REPORT+"report";
        Map<String,String> param=new HashMap<>();
        param.put("openId",openId);
        String resultStr=doCommonPost(getReportUrl,param,null);
        JSONObject resultJson=JSON.parseObject(resultStr);
        if(resultJson.getIntValue("code")==0){
            this.tianBeiResultAppService.saveOrUpdate(idCard,ENUM_TIANBEI_TYPE.TELECOM_OPERATORS_REPORT_RESULT.code,resultStr);
        }
        return resultStr;
    }


    
}
