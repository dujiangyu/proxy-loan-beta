package com.cw.biz.tianbei.app.service;

import com.cw.biz.tianbei.app.dto.TianBeiResultDto;
import com.cw.biz.tianbei.domain.service.TianBeiResultDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Title: TianBeiResultDomainService
 * @Description: 天贝查询结果应用服务
 * @Author: Away
 * @Date: 2018/9/2 2:57
 */
@Service
@Transactional
public class TianBeiResultAppService {

    private final TianBeiResultDomainService tianBeiResultDomainService;

    @Autowired
    public TianBeiResultAppService(TianBeiResultDomainService tianBeiResultDomainService) {
        this.tianBeiResultDomainService=tianBeiResultDomainService;
    }

    /**
     * @Author: Away
     * @Title: findByIdcardAndQueryType
     * @Description: 按照身份证和查询类型查找
     * @Param idcard
     * @Param queryType
     * @Return: com.cw.biz.tianbei.app.dto.TianBeiResultDto
     * @Date: 2018/9/2 3:01
     * @Version: 1
     */
    public TianBeiResultDto findByIdcardAndQueryType(String idcard,String queryType){
        return this.tianBeiResultDomainService.findByIdcardAndQueryType(idcard, queryType);
    }

    /**
     * @Author: Away
     * @Title: saveOrUpdate
     * @Description: 更新或保存查询结果
     * @Param idcard
     * @Param queryType
     * @Param resultStr
     * @Return: com.cw.biz.tianbei.app.dto.TianBeiResultDto
     * @Date: 2018/9/2 3:07
     * @Version: 1
     */
    public TianBeiResultDto saveOrUpdate(String idcard,String queryType,String resultStr){
        return this.tianBeiResultDomainService.saveOrUpdate(idcard, queryType, resultStr);
    }
}
