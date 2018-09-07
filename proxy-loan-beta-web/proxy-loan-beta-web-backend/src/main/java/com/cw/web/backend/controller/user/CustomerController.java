package com.cw.web.backend.controller.user;/*
 * 描       述:  &lt;描述&gt;
 * 修  改   人:  ${user}
 * 修  改 时 间:  ${date}
 * &lt;修改描述:&gt;
 */

import com.cw.biz.CPContext;
import com.cw.biz.CwException;
import com.cw.biz.channel.app.dto.ThirdOperateDto;
import com.cw.biz.channel.app.service.ThirdOperateAppService;
import com.cw.biz.parameter.app.ParameterEnum;
import com.cw.biz.parameter.app.dto.ParameterDto;
import com.cw.biz.parameter.app.service.ParameterAppService;
import com.cw.biz.tianbei.TianBeiClient;
import com.cw.biz.tianbei.app.dto.TianBeiResultDto;
import com.cw.biz.tianbei.app.service.TianBeiResultAppService;
import com.cw.biz.tianbei.enums.ENUM_TIANBEI_TYPE;
import com.cw.biz.user.app.dto.CustomerAuditDto;
import com.cw.biz.user.app.dto.YxUserInfoDto;
import com.cw.biz.user.app.service.CustomerAppService;
import com.cw.biz.user.domain.entity.SeUser;
import com.cw.biz.user.domain.service.SeUserService;
import com.cw.biz.xinyan.app.service.XinYanAppService;
import com.cw.core.common.util.ObjectHelper;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController  extends AbstractBackendController {

    @Autowired
    private CustomerAppService yxUserInfoAppService;

    @Autowired
    private ParameterAppService parameterAppService;

    @Autowired
    private ThirdOperateAppService thirdOperateAppService;
    /** 天贝接口 **/
    @Autowired
    private TianBeiClient tianBeiClient;
    /** 新颜接口 **/
    @Autowired
    private XinYanAppService xinYanAppService;

    @Autowired
    private SeUserService seUserService;

    @Autowired
    private TianBeiResultAppService tianBeiResultAppService;


    /**
     * 客户查询
     * @param yxUserInfoDto
     * @return
     */
    @PostMapping("/customer/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo userList(@RequestBody YxUserInfoDto yxUserInfoDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<YxUserInfoDto> userInfoDtos = yxUserInfoAppService.findByCondition(yxUserInfoDto);
        cpViewResultInfo.setData(userInfoDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    @GetMapping("/customer/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id) {
      CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
      YxUserInfoDto yxUserInfoDto = yxUserInfoAppService.findById(id);
      cpViewResultInfo.setData(yxUserInfoDto);
      cpViewResultInfo.setSuccess(true);
      cpViewResultInfo.setMessage("查询成功");
      return cpViewResultInfo;
    }
    /** 修改用户信息
     *&lt;功能简述&gt;
     *&lt;功能详细描述&gt;
     * ${tags} [参数说明]
     *
     * @return ${return_type} [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @PostMapping("/customer/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody YxUserInfoDto yxUserInfoDto) {
       CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
       yxUserInfoAppService.update(yxUserInfoDto);
       cpViewResultInfo.setSuccess(true);
       cpViewResultInfo.setMessage("查询成功");
       return cpViewResultInfo;
    }
    /**
    * 天贝全景雷达接口
    * @param customerAuditDto
    * @return
    */
   @PostMapping("/customer/queryTianbeiLeida.json")
   @ResponseBody
   public CPViewResultInfo queryTianbeiLeida(@RequestBody CustomerAuditDto customerAuditDto) {
       CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
       try {
           //TODO 扣取相关请求费用
           ParameterDto parameterDto = new ParameterDto();
           parameterDto.setParameterCode(ParameterEnum.LEIDAI.getKey());
           substractFee(customerAuditDto,parameterDto);
           String result = tianBeiClient.getRadar(customerAuditDto.getName(), customerAuditDto.getPhone(), customerAuditDto.getIdCard());
           cpViewResultInfo.setData(result);
           cpViewResultInfo.setSuccess(true);
           cpViewResultInfo.setMessage("查询成功");
       }catch (Exception e){
           cpViewResultInfo.setSuccess(false);
           throw new CwException(e.getMessage());

       }
       return cpViewResultInfo;
   }


    /**
    * 天贝报告接口
    * @param customerAuditDto
    * @return
    */
   @PostMapping("/customer/queryTianbeiReport.json")
   @ResponseBody
   public CPViewResultInfo queryTianbeiReport(@RequestBody CustomerAuditDto customerAuditDto) {
       CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
       try {
           ParameterDto parameterDto = new ParameterDto();
           parameterDto.setParameterCode(ParameterEnum.REPORT.getKey());
           substractFee(customerAuditDto,parameterDto);
           String result = tianBeiClient.getTianbeiReport(customerAuditDto.getName(), customerAuditDto.getIdCard(), customerAuditDto.getPhone());
           cpViewResultInfo.setData(result);
           cpViewResultInfo.setSuccess(true);
           cpViewResultInfo.setMessage("查询成功");
       }catch (Exception e){
           cpViewResultInfo.setSuccess(false);
           throw new CwException(e.getMessage());

       }
       return cpViewResultInfo;
   }

   /**
    * 运营商报告
    * @param customerAuditDto
    * @return
    */
   @PostMapping("/customer/queryTianbeiYys.json")
   @ResponseBody
   public CPViewResultInfo queryTianbeiYys(@RequestBody CustomerAuditDto customerAuditDto) {
       CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
       try {
           ParameterDto parameterDto = new ParameterDto();
           parameterDto.setParameterCode(ParameterEnum.YUNYINGSHANG.getKey());
           String openId = yxUserInfoAppService.findByPhone(customerAuditDto.getPhone()).getOpenId();
           substractFee(customerAuditDto,parameterDto);
           String result = tianBeiClient.getTelecomOperatorsReport(customerAuditDto.getIdCard(),openId);
           cpViewResultInfo.setData(result);
           cpViewResultInfo.setSuccess(true);
           cpViewResultInfo.setMessage("查询成功");
       }catch (Exception e){
           cpViewResultInfo.setSuccess(false);
           throw new CwException(e.getMessage());
       }
       return cpViewResultInfo;
   }

    /**
       * 黑名单检测
       * @param customerAuditDto
       * @return
       */
      @PostMapping("/customer/queryTianbeiBlackList.json")
      @ResponseBody
      public CPViewResultInfo queryTianbeiBlackList(@RequestBody CustomerAuditDto customerAuditDto) {
          CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
          try {
              ParameterDto parameterDto = new ParameterDto();
              parameterDto.setParameterCode(ParameterEnum.BLACKLIST.getKey());
              substractFee(customerAuditDto,parameterDto);
              String result = tianBeiClient.getBlackList(customerAuditDto.getPhone(), customerAuditDto.getName(),customerAuditDto.getIdCard());
              cpViewResultInfo.setData(result);
              cpViewResultInfo.setSuccess(true);
              cpViewResultInfo.setMessage("查询成功");
          }catch (Exception e){
              cpViewResultInfo.setSuccess(false);
              throw new CwException(e.getMessage());
          }
          return cpViewResultInfo;
      }


    /**
       * 借条逾期
       * @param customerAuditDto
       * @return
       */
      @PostMapping("/customer/queryTianbeiOverdue.json")
      @ResponseBody
      public CPViewResultInfo queryTianbeiOverdue(@RequestBody CustomerAuditDto customerAuditDto) {
          CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
          try {
              ParameterDto parameterDto = new ParameterDto();
              parameterDto.setParameterCode(ParameterEnum.YUNYINGSHANG.getKey());
              substractFee(customerAuditDto,parameterDto);
              String result = tianBeiClient.getNoteOverdue(customerAuditDto.getPhone(),customerAuditDto.getName(), customerAuditDto.getIdCard());
              cpViewResultInfo.setData(result);
              cpViewResultInfo.setSuccess(true);
              cpViewResultInfo.setMessage("查询成功");
          }catch (Exception e){
              cpViewResultInfo.setSuccess(false);
              throw new CwException(e.getMessage());
          }
          return cpViewResultInfo;
      }


    /**
      * 实名认证
      * @param customerAuditDto
      * @return
      */
     @PostMapping("/customer/queryXinyuanNameAuth.json")
     @ResponseBody
     public CPViewResultInfo queryXinyuanNameAuth(@RequestBody CustomerAuditDto customerAuditDto) {
         CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
         try {
             ParameterDto parameterDto = new ParameterDto();
             parameterDto.setParameterCode(ParameterEnum.INFOAUTH.getKey());
             substractFee(customerAuditDto,parameterDto);
             String result = xinYanAppService.getXinYanRealName(customerAuditDto.getIdCard(),customerAuditDto.getName());
             cpViewResultInfo.setData(result);
             cpViewResultInfo.setSuccess(true);
             cpViewResultInfo.setMessage("查询成功");
         }catch (Exception e){
             cpViewResultInfo.setSuccess(false);
             throw new CwException(e.getMessage());
         }
         return cpViewResultInfo;
     }

    /**
      * 逾期档案
      * @param customerAuditDto
      * @return
      */
     @PostMapping("/customer/queryXinyuanOverdue.json")
     @ResponseBody
     public CPViewResultInfo queryXinyuanOverdue(@RequestBody CustomerAuditDto customerAuditDto) {
         CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
         try {
             ParameterDto parameterDto = new ParameterDto();
             parameterDto.setParameterCode(ParameterEnum.OVERDUEFILE.getKey());
             substractFee(customerAuditDto,parameterDto);
             String result = xinYanAppService.getOverdue(customerAuditDto.getIdCard(),customerAuditDto.getName(),customerAuditDto.getPhone(),customerAuditDto.getBankAccount());
             cpViewResultInfo.setData(result);
             cpViewResultInfo.setSuccess(true);
             cpViewResultInfo.setMessage("查询成功");
         }catch (Exception e){
             cpViewResultInfo.setSuccess(false);
             throw new CwException(e.getMessage());
         }
         return cpViewResultInfo;
     }


    /**
      * 芝麻分
      * @param customerAuditDto
      * @return
      */
     @PostMapping("/customer/queryXinyuanZmf.json")
     @ResponseBody
     public CPViewResultInfo queryXinyuanZmf(@RequestBody CustomerAuditDto customerAuditDto) {
         CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
         try {
             ParameterDto parameterDto = new ParameterDto();
             parameterDto.setParameterCode(ParameterEnum.ZMF.getKey());
             substractFee(customerAuditDto,parameterDto);
             String result = "";//xinYanAppService.buildXinYanOrder(customerAuditDto.getPhone(),customerAuditDto.getName(), customerAuditDto.getIdCard());
             cpViewResultInfo.setData(result);
             cpViewResultInfo.setSuccess(true);
             cpViewResultInfo.setMessage("查询成功");
         }catch (Exception e){
             cpViewResultInfo.setSuccess(false);
             throw new CwException(e.getMessage());
         }
         return cpViewResultInfo;
     }



   //调取接口费用
   private void substractFee(CustomerAuditDto customerAuditDto,ParameterDto parameterDto){
       ParameterDto parameterDto1 = parameterAppService.findByCode(parameterDto.getParameterCode());
       //是否是服务商下级用户审核
        ThirdOperateDto thirdOperateDto = thirdOperateAppService.findByUserId(CPContext.getContext().getSeUserInfo().getMerchantId());
        if(thirdOperateDto==null){
            //服务商自己请求接口
            if(!"admin".equals(CPContext.getContext().getSeUserInfo().getUsername())) {
                thirdOperateDto = thirdOperateAppService.findByUserId(CPContext.getContext().getSeUserInfo().getId());
                thirdOperateDto.setInterfaceFee(parameterDto1.getParameterValue());
                thirdOperateAppService.queryInterfaceFee(thirdOperateDto);
            }
        }else{
            //判断是否收取本次费用
            SeUser seUser = seUserService.findOne(CPContext.getContext().getSeUserInfo().getMerchantId());
            //查询客服信息获取用户是否是本组的人员
            TianBeiResultDto tianBeiResultDto=this.tianBeiResultAppService.findByIdcardAndQueryType(customerAuditDto.getIdCard(), ENUM_TIANBEI_TYPE.RADAR.code);
            if(ObjectHelper.isNotEmpty(tianBeiResultDto)){
                String roleIds = seUserService.findOne(tianBeiResultDto.getId()).getRoleIdsStr();
                if(compareStr(roleIds,seUser.getRoleIdsStr())) {
                    return;
                }
            }
            thirdOperateDto.setInterfaceFee(parameterDto1.getParameterValue());
            thirdOperateAppService.queryInterfaceFee(thirdOperateDto);
        }
   }
    //比较2个字符串中是否有相容的
    public static Boolean compareStr(String str1,String str2){
        boolean flag=Boolean.FALSE;
        for(int i=0;i<str1.length();i++)//获取第一个字符串中的单个字符
            for(int j=0;j<str2.length();j++)//获取第er个字符串中的单个字符
            {
                if(str1.charAt(i)==str2.charAt(j))//判断字符是否相同
                    flag = Boolean.TRUE;
            }
        return flag;
    }
}
