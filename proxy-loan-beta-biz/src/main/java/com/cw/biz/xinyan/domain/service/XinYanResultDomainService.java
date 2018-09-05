package com.cw.biz.xinyan.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.xinyan.app.dto.XinYanNotifyResultDto;
import com.cw.biz.xinyan.app.dto.XinYanNotifyResultParamDto;
import com.cw.biz.xinyan.app.dto.XinYanResultDto;
import com.cw.biz.xinyan.domain.entity.XinYanNotifyResult;
import com.cw.biz.xinyan.domain.entity.XinYanResult;
import com.cw.biz.xinyan.domain.repository.XinYanNotifyResultRepository;
import com.cw.biz.xinyan.domain.repository.XinYanResultRepository;
import com.cw.core.common.util.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title: XinYanResultDomainService
 * @Description: 新颜查询数据服务
 * @Author: Away
 * @Date: 2018/9/5 21:41
 */
@Service
public class XinYanResultDomainService {

    private final XinYanResultRepository xinYanResultRepository;

    private final XinYanNotifyResultRepository xinYanNotifyResultRepository;

    @Autowired
    public XinYanResultDomainService(XinYanResultRepository xinYanResultRepository, XinYanNotifyResultRepository xinYanNotifyResultRepository) {
        this.xinYanResultRepository = xinYanResultRepository;
        this.xinYanNotifyResultRepository = xinYanNotifyResultRepository;
    }

    /**
     * @Author: Away
     * @Title: findByIdCardAndQueryType
     * @Description: 按照身份证和查询类型查找
     * @Param idCard
     * @Param queryType
     * @Return: com.cw.biz.xinyan.entity.XinYanResult
     * @Date: 2018/9/5 21:33
     * @Version: 1
     */
    public XinYanResultDto findByIdCardAndQueryType(String idCard, String queryType){
        XinYanResult source=this.xinYanResultRepository.findByIdCardAndQueryType(idCard, queryType);
        return ObjectHelper.isNotEmpty(source)?source.to(XinYanResultDto.class):null;
    }


    /**
     * @Author: Away
     * @Title: saveOrUpdate
     * @Description: 更新或保存新颜查询结果
     * @Param idCard
     * @Param queryType
     * @Param result
     * @Return: com.cw.biz.xinyan.app.dto.XinYanResultDto
     * @Date: 2018/9/5 21:53
     * @Version: 1
     */
    public XinYanResultDto saveOrUpdate(String idCard,String queryType,String result){
        XinYanResult source=this.xinYanResultRepository.findByIdCardAndQueryType(idCard, queryType);
        if(ObjectHelper.isEmpty(source))source=new XinYanResult();
        source.setIdCard(idCard);
        source.setQueryType(queryType);
        source.setQueryResult(result);
        source.setQueryUser(ObjectHelper.isNotEmpty(CPContext.getContext().getSeUserInfo())?CPContext.getContext().getSeUserInfo().getId()+"":"");
        this.xinYanResultRepository.saveAndFlush(source);
        return source.to(XinYanResultDto.class);
    }

    /**
     * @Author: Away
     * @Title: findByUserIdAndPhaseType
     * @Description: 按照身份证和通知阶段查找回调数据
     * @Param userId
     * @Param phaseType
     * @Return: com.cw.biz.xinyan.domain.entity.XinYanNotifyResult
     * @Date: 2018/9/6 0:12
     * @Version: 1
     */
    public XinYanNotifyResultDto findByUserIdAndPhaseType(String userId, String phaseType){
        XinYanNotifyResult source=this.xinYanNotifyResultRepository.findByUserIdAndPhaseType(userId, phaseType);
        return ObjectHelper.isNotEmpty(source)?source.to(XinYanNotifyResultDto.class):null;
    }

    /**
     * @Author: Away
     * @Title: saveOrUpdateNotify
     * @Description: 更新或保存回调数据
     * @Param xinYanNotifyResultParamDto
     * @Return: com.cw.biz.xinyan.app.dto.XinYanNotifyResultDto
     * @Date: 2018/9/6 0:35
     * @Version: 1
     */
    public XinYanNotifyResultDto saveOrUpdateNotify(XinYanNotifyResultParamDto xinYanNotifyResultParamDto){
        XinYanNotifyResult source=this.xinYanNotifyResultRepository.findByUserIdAndPhaseType(xinYanNotifyResultParamDto.getTask_content().getUser_id(), xinYanNotifyResultParamDto.getPhase_type());
        if(ObjectHelper.isEmpty(source))source=new XinYanNotifyResult();
        source.setAccount(xinYanNotifyResultParamDto.getTask_content().getAccount());
        source.setDesc(xinYanNotifyResultParamDto.getDesc());
        source.setMemberId(xinYanNotifyResultParamDto.getMemberId());
        source.setMemberTransId(xinYanNotifyResultParamDto.getMember_trans_id());
        source.setMobile(xinYanNotifyResultParamDto.getTask_content().getMobile());
        source.setPhaseType(xinYanNotifyResultParamDto.getPhase_type());
        source.setResult(xinYanNotifyResultParamDto.getResult());
        source.setTaskType(xinYanNotifyResultParamDto.getTask_type());
        source.setTerminalId(xinYanNotifyResultParamDto.getTerminalId());
        source.setTradeNo(xinYanNotifyResultParamDto.getTrade_no());
        source.setUserId(xinYanNotifyResultParamDto.getTask_content().getUser_id());
        source.setﬁnished(xinYanNotifyResultParamDto.getﬁnished());
        this.xinYanNotifyResultRepository.saveAndFlush(source);
        return source.to(XinYanNotifyResultDto.class);
    }

}
