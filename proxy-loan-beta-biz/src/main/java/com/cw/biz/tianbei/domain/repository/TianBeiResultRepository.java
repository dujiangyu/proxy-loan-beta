package com.cw.biz.tianbei.domain.repository;

import com.cw.biz.tianbei.domain.entity.TianBeiResult;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Title: TianBeiResultRepository
 * @Description: 天贝接口查询结果自定义操作库
 * @Author: Away
 * @Date: 2018/9/2 2:53
 */
public interface TianBeiResultRepository extends JpaRepository<TianBeiResult,Long>{

    /**
     * @Author: Away
     * @Title: findByIdCardAndQueryType
     * @Description: 按照身份证和查询类型查找数据
     * @Param idCard
     * @Param queryType
     * @Return: com.cw.biz.tianbei.domain.entity.TianBeiResult
     * @Date: 2018/9/2 2:55
     * @Version: 1
     */
    TianBeiResult findByIdCardAndQueryType(String idCard,String queryType);

}
