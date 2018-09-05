package com.cw.biz.xinyan.domain.repository;

import com.cw.biz.xinyan.domain.entity.XinYanNotifyResult;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Title: XinYanNotifyResultRepository
 * @Description: 新颜回调结果自定义操作库
 * @Author: Away
 * @Date: 2018/9/5 21:29
 */
public interface XinYanNotifyResultRepository extends JpaRepository<XinYanNotifyResult,Long>{

    /**
     * @Author: Away
     * @Title: findByUserIdAndPhaseType
     * @Description: 按照身份证和通知阶段查找
     * @Param userId
     * @Param phaseType
     * @Return: com.cw.biz.xinyan.domain.entity.XinYanNotifyResult
     * @Date: 2018/9/6 0:12
     * @Version: 1
     */
    XinYanNotifyResult findByUserIdAndPhaseType(String userId,String phaseType);
}
