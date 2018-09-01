package com.cw.biz.tianbei.domain.service;

import com.cw.biz.tianbei.app.dto.TianBeiResultDto;
import com.cw.biz.tianbei.domain.entity.TianBeiResult;
import com.cw.biz.tianbei.domain.repository.TianBeiResultRepository;
import com.cw.core.common.util.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title: TianBeiResultDomainService
 * @Description: 天贝查询结果数据服务
 * @Author: Away
 * @Date: 2018/9/2 2:57
 */
@Service
public class TianBeiResultDomainService {

    private final TianBeiResultRepository tianBeiResultRepository;

    @Autowired
    public TianBeiResultDomainService(TianBeiResultRepository tianBeiResultRepository) {
        this.tianBeiResultRepository = tianBeiResultRepository;
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
        TianBeiResult result=this.tianBeiResultRepository.findByIdCardAndQueryType(idcard, queryType);
        return ObjectHelper.isNotEmpty(result)?result.to(TianBeiResultDto.class):null;
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
        TianBeiResult result=this.tianBeiResultRepository.findByIdCardAndQueryType(idcard, queryType);
        if(ObjectHelper.isNotEmpty(result)){
            result.setQueryResult(resultStr);
        }else{
            result=new TianBeiResult();
            result.setQueryResult(resultStr);
            result.setIdCard(idcard);
            result.setQueryType(queryType);
        }
        this.tianBeiResultRepository.saveAndFlush(result);
        return result.to(TianBeiResultDto.class);
    }
}
