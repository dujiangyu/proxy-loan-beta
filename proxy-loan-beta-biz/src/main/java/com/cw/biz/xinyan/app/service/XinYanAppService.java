package com.cw.biz.xinyan.app.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cw.biz.CwException;
import com.cw.biz.user.app.dto.YxUserInfoDto;
import com.cw.biz.xinyan.ENUM_XINYAN_PHASE_TYPE;
import com.cw.biz.xinyan.ENUM_XINYAN_TYPE;
import com.cw.biz.xinyan.XYConfig;
import com.cw.biz.xinyan.app.dto.XinYanNotifyResultDto;
import com.cw.biz.xinyan.app.dto.XinYanNotifyResultParamDto;
import com.cw.biz.xinyan.app.dto.XinYanResultDto;
import com.cw.biz.xinyan.credit.XYClient;
import com.cw.biz.xinyan.domain.service.XinYanResultDomainService;
import com.cw.core.common.util.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: XinYanAppService
 * @Description: 新颜接口服务
 * @Author: Away
 * @Date: 2018/9/5 16:28
 */
@Service
@Transactional
public class XinYanAppService {

    private final XinYanResultDomainService xinYanResultDomainService;

    private final XYClient xyClient;


    @Autowired
    public XinYanAppService(XinYanResultDomainService xinYanResultDomainService,XYClient xyClient) {
        this.xinYanResultDomainService = xinYanResultDomainService;
        this.xyClient=xyClient;
    }

    /**
     * @Author: Away
     * @Title: getXinYanRealName
     * @Description: 新颜实名认证
     * @Param idCard
     * @Param name
     * @Return: java.lang.String
     * @Date: 2018/9/5 22:18
     * @Version: 1
     */
    public String getXinYanRealName(String idCard,String name) throws Exception {
        XinYanResultDto resultDto=this.xinYanResultDomainService.findByIdCardAndQueryType(idCard,ENUM_XINYAN_TYPE.REAL_NAME.code);
        if(ObjectHelper.isNotEmpty(resultDto))return resultDto.getQueryResult();
        Map<String,String> params=new HashMap<>();
        params.put("id_card",idCard);
        params.put("id_holder",name);
        params.put("industry_type", XYConfig.INDUSTRY_TYPE);
        params.put("is_photo", "noPhoto");
        String result=xyClient.doRequestWithRsa(XYConfig.REAL_NAME_AUTH_URL,params,"post",true);
        JSONObject jsonResult= JSON.parseObject(result);
        if(jsonResult.getBoolean("success")){
            if(jsonResult.getJSONObject("data").getString("code").equalsIgnoreCase("0")){
                this.xinYanResultDomainService.saveOrUpdate(idCard, ENUM_XINYAN_TYPE.REAL_NAME.code,result);
            }
        }
        return result;
    }

    /**
     * @Author: Away
     * @Title: getOverdue
     * @Description: 逾期档案查询
     * @Param idNo
     * @Param idName
     * @Param phoneNo
     * @Param bankcardNo
     * @Return: java.lang.String
     * @Date: 2018/9/5 22:26
     * @Version: 1
     */
    public String getOverdue(String idNo,String idName,String phoneNo,String bankcardNo) throws Exception {
        XinYanResultDto resultDto=this.xinYanResultDomainService.findByIdCardAndQueryType(idNo,ENUM_XINYAN_TYPE.OVERDUE.code);
        if(ObjectHelper.isNotEmpty(resultDto))return resultDto.getQueryResult();
        Map<String,String> params=new HashMap<>();
        params.put("industry_type",XYConfig.INDUSTRY_TYPE);
        params.put("id_no",idNo);
        params.put("id_name",idName);
        params.put("phone_no",phoneNo);
        params.put("bankcard_no",bankcardNo);
        params.put("versions","1.3.0");
        String result=xyClient.doRequestWithRsa(XYConfig.OVERDUE_URL,params,"post",true);
        JSONObject jsonResult=JSON.parseObject(result);
        if(jsonResult.getBoolean("success")){
            if(jsonResult.getJSONObject("data").getString("code").equalsIgnoreCase("0")){
                this.xinYanResultDomainService.saveOrUpdate(idNo, ENUM_XINYAN_TYPE.OVERDUE.code,result);
            }
        }
        return result;
    }

    /**
     * @Author: Away
     * @Title: saveOrUpdateNotify
     * @Description: 更新或保存新颜回调数据
     * @Param xinYanNotifyResultParamDto
     * @Return: com.cw.biz.xinyan.app.dto.XinYanNotifyResultDto
     * @Date: 2018/9/6 0:40
     * @Version: 1
     */
    public XinYanNotifyResultDto saveOrUpdateNotify(XinYanNotifyResultParamDto xinYanNotifyResultParamDto) throws Exception {
        XinYanNotifyResultDto source=this.xinYanResultDomainService.saveOrUpdateNotify(xinYanNotifyResultParamDto);
        //采集完成则调用查询淘宝用户全部信息接口
        if(source.getPhaseType().equalsIgnoreCase(ENUM_XINYAN_PHASE_TYPE.TASK.code)&&source.getResult().equalsIgnoreCase("true")){
            Map<String,String> params=new HashMap<>();
            params.put("memberId",XYConfig.MEMBER_ID);
            params.put("terminalId",XYConfig.TERMINAL_ID);
            params.put("orderNo",xinYanNotifyResultParamDto.getTrade_no());
            String result=xyClient.doRequestWithRsa(XYConfig.USER_TAOBAO_ALL_INFO_URL+xinYanNotifyResultParamDto.getTrade_no(),params,"get",true);
            JSONObject jsonResult=JSON.parseObject(result);
            if(jsonResult.getBoolean("success")){
                this.xinYanResultDomainService.saveOrUpdate(xinYanNotifyResultParamDto.getTask_content().getUser_id(),ENUM_XINYAN_TYPE.ZHIMA.code,result);
            }
        }
        return source;
    }
    //获取芝麻分信息
    public String getZmfInfo(YxUserInfoDto yxUserInfoDto) throws Exception {
        XinYanResultDto source=this.xinYanResultDomainService.findByIdCardAndQueryType(yxUserInfoDto.getCertNo(), ENUM_XINYAN_TYPE.ZHIMA.code);
        if(ObjectHelper.isNotEmpty(source))return source.getQueryResult();
        //采集完成则调用查询淘宝用户全部信息接口
        Map<String,String> params=new HashMap<>();
        params.put("memberId",XYConfig.MEMBER_ID);
        params.put("terminalId",XYConfig.TERMINAL_ID);
        params.put("orderNo",yxUserInfoDto.getTradeNo());
        String result=xyClient.doRequestWithRsa(XYConfig.USER_TAOBAO_ALL_INFO_URL+yxUserInfoDto.getTradeNo(),params,"get",true);
        JSONObject jsonResult=JSON.parseObject(result);
        if(jsonResult.getBoolean("success")){
            this.xinYanResultDomainService.saveOrUpdate(yxUserInfoDto.getCertNo(),ENUM_XINYAN_TYPE.ZHIMA.code,result);
        }

        return result;
    }

    /**
     * @Author: Away
     * @Title: buildXinYanOrder
     * @Description: 生成新颜订单，同时返回新颜授权H5页面
     * @Param idCard
     * @Return: java.lang.String
     * @Date: 2018/9/6 1:06
     * @Version: 1
     */
    public String buildXinYanOrder(String idCard) throws Exception {
        XinYanResultDto source=this.xinYanResultDomainService.findByIdCardAndQueryType(idCard, ENUM_XINYAN_TYPE.ZHIMA.code);
        if(ObjectHelper.isNotEmpty(source))return "true";
        Map<String,String> param=new HashMap<>();
        param.put("notifyUrl",XYConfig.NOTIFY_URL);
        param.put("txnType","taobao");
        String result= xyClient.doRequestWithRsa(XYConfig.BUILD_ORDER_URL,param,"post",false);
        JSONObject jsonResult=JSON.parseObject(result);
        if(jsonResult.getBoolean("success")){
            return XYConfig.TAOBAO_URL+"trade_no="+jsonResult.getString("data")+"&user_id="+idCard+"&member_id="+XYConfig.MEMBER_ID+"&terminal_id="+XYConfig.TERMINAL_ID+"&succ_url="+XYConfig.TAOBAO_AUTH_SUCCESS_URL;
        }else{
            return result;
        }
    }
}
