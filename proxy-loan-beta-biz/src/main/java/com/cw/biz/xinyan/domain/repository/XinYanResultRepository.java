package com.cw.biz.xinyan.domain.repository;

import com.cw.biz.xinyan.domain.entity.XinYanResult;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Title: XinYanResultRepository
 * @Description: 新颜查询结果自定义操作库
 * @Author: Away
 * @Date: 2018/9/5 21:29
 */
public interface XinYanResultRepository extends JpaRepository<XinYanResult,Long>{

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
    XinYanResult findByIdCardAndQueryType(String idCard,String queryType);
}
