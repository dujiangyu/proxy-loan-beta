package com.cw.web.wechat.controller;/*
 * 描       述:  &lt;描述&gt;
 * 修  改   人:  ${user}
 * 修  改 时 间:  ${date}
 * &lt;修改描述:&gt;
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cw.biz.CPContext;
import com.cw.biz.CwException;
import com.cw.biz.tianbei.TianBeiClient;
import com.cw.biz.user.app.dto.YxUserInfoDto;
import com.cw.biz.user.app.service.CustomerAppService;
import com.cw.biz.xinyan.app.service.XinYanAppService;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerInfoController extends AbstractWechatController{

    @Autowired
    private CustomerAppService yxUserInfoAppService;


    /** 新颜接口 **/
    @Autowired
    private XinYanAppService xinYanAppService;

    /** 天贝接口 **/
    @Autowired
    private TianBeiClient tianBeiClient;
    /** 查询用户信息
    *&lt;功能简述&gt;
    *&lt;功能详细描述&gt;
    * ${tags} [参数说明]
    *
    * @return ${return_type} [返回类型说明]
    * @exception throws [异常类型] [异常说明]
    * @see [类、类#方法、类#成员]
    */
   @GetMapping("/customer/findById.json")
   @ResponseBody
   public CPViewResultInfo findById() {
      String phone = CPContext.getContext().getSeUserInfo().getPhone();
      CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
      YxUserInfoDto yxUserInfoDto = yxUserInfoAppService.findByPhone(phone);
      cpViewResultInfo.setData(yxUserInfoDto);
      cpViewResultInfo.setSuccess(true);
      cpViewResultInfo.setMessage("查询成功");
      return cpViewResultInfo;
   }

    /** 芝麻分授权查询创建新颜订单
     *&lt;功能简述&gt;
     *&lt;功能详细描述&gt;
     * ${tags} [参数说明]
     *
     * @return ${return_type} [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @GetMapping("/customer/buildXinYanOrder.json")
    @ResponseBody
    public CPViewResultInfo buildXinYanOrder() {
       CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
       try{
            String phone = CPContext.getContext().getSeUserInfo().getPhone();
            YxUserInfoDto yxUserInfoDto = yxUserInfoAppService.findByPhone(phone);
            String authUrl = xinYanAppService.buildXinYanOrder(yxUserInfoDto.getCertNo());
            cpViewResultInfo.setData(authUrl);
            cpViewResultInfo.setSuccess(true);
            cpViewResultInfo.setMessage("修改成功");
        }catch (Exception e){
            cpViewResultInfo.setSuccess(false);
            throw new CwException(e.getMessage());
        }
       return cpViewResultInfo;
    }

    @PostMapping("/customer/updateUserInfo.json")
    @ResponseBody
    public CPViewResultInfo updateUserInfo(@RequestBody YxUserInfoDto yxUserInfoDto) {
       CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
       try{
            String phone = CPContext.getContext().getSeUserInfo().getPhone();
            YxUserInfoDto userInfoDto = yxUserInfoAppService.findByPhone(phone);
            yxUserInfoDto.setId(userInfoDto.getId());
            yxUserInfoAppService.update(yxUserInfoDto);
            if("basic".equals(yxUserInfoDto.getShowPageContent())){
                cpViewResultInfo.setData("user-credit.html");
            }else if("credit".equals(yxUserInfoDto.getShowPageContent())){
                cpViewResultInfo.setData("user-income.html");
            }else{
                cpViewResultInfo.setData("user-basic.html");
            }
            cpViewResultInfo.setSuccess(true);
            cpViewResultInfo.setMessage("初始化成功");
        }catch (Exception e){
            cpViewResultInfo.setSuccess(false);
            throw new CwException(e.getMessage());
        }
       return cpViewResultInfo;
    }

    /** 运营商授权初始化
     *&lt;功能简述&gt;
     *&lt;功能详细描述&gt;
     * ${tags} [参数说明]
     *
     * @return ${return_type} [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @PostMapping("/customer/getTelecomOperatorsReportInit.json")
    @ResponseBody
    public CPViewResultInfo getTelecomOperatorsReportInit(@RequestBody YxUserInfoDto yxUserInfoDto) {
       CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
       try{
           String phone = CPContext.getContext().getSeUserInfo().getPhone();
           YxUserInfoDto userInfoDto = yxUserInfoAppService.findByPhone(phone);
           String authUrl = tianBeiClient.getTelecomOperatorsReportInit(userInfoDto.getName(),userInfoDto.getPhone(),userInfoDto
                    .getCertNo(),yxUserInfoDto.getServicePwd());
           JSONObject initJson= JSON.parseObject(authUrl);
           if(initJson.getString("code").equalsIgnoreCase("0")) {
              String data = initJson.getString("data");
              JSONObject dataJson = JSON.parseObject(data);
               if(dataJson.getString("state").equalsIgnoreCase("1")){
                   userInfoDto.setOpenId(dataJson.getString("openId"));
                   yxUserInfoAppService.update(userInfoDto);
               }
           }
           cpViewResultInfo.setData(authUrl);
           cpViewResultInfo.setSuccess(true);
           cpViewResultInfo.setMessage("初始化成功");
        }catch (Exception e){
            cpViewResultInfo.setSuccess(false);
            throw new CwException(e.getMessage());
        }
       return cpViewResultInfo;
    }

   /** 运营商授权提交验证码
     *&lt;功能简述&gt;
     *&lt;功能详细描述&gt;
     * ${tags} [参数说明]
     *
     * @return ${return_type} [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @PostMapping("/customer/submitValidateCode.json")
    @ResponseBody
    public CPViewResultInfo submitValidateCode(@RequestBody YxUserInfoDto yxUserInfoDto) {
       CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
       try{
            String phone = CPContext.getContext().getSeUserInfo().getPhone();
            YxUserInfoDto userInfoDto = yxUserInfoAppService.findByPhone(phone);
            String authUrl = tianBeiClient.submitValidateCode(userInfoDto.getCertNo(),yxUserInfoDto.getOpenId(),yxUserInfoDto.getCaptcha());
            cpViewResultInfo.setData(authUrl);
            cpViewResultInfo.setSuccess(true);
            cpViewResultInfo.setMessage("授权成功");
        }catch (Exception e){
            cpViewResultInfo.setSuccess(false);
            throw new CwException(e.getMessage());
        }
       return cpViewResultInfo;
    }

    /** 运营商授权重新发送验证码
     *&lt;功能简述&gt;
     *&lt;功能详细描述&gt;
     * ${tags} [参数说明]
     *
     * @return ${return_type} [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @PostMapping("/customer/resendVlidateCode.json")
    @ResponseBody
    public CPViewResultInfo resendVlidateCode(@RequestBody YxUserInfoDto yxUserInfoDto) {
       CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
       try{
            String authUrl = tianBeiClient.resendVlidateCode(yxUserInfoDto.getOpenId());
            cpViewResultInfo.setData(authUrl);
            cpViewResultInfo.setSuccess(true);
            cpViewResultInfo.setMessage("授权成功");
        }catch (Exception e){
            cpViewResultInfo.setSuccess(false);
            throw new CwException(e.getMessage());
        }
       return cpViewResultInfo;
    }
}
