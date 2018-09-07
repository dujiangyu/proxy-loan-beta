package com.cw.biz.sms.domain.repository;

import com.cw.biz.sms.domain.entity.SmsMould;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Title: SmsMouldRepository
 * @Description: 短信模板自定义操作库
 * @Author: Away
 * @Date: 2018/9/1 16:03
 */
public interface SmsMouldRepository extends JpaRepository<SmsMould,Long>{

    /**
     * @Author: Away
     * @Title: findByMouldCode
     * @Description: 按照模板唯一编号查找
     * @Param mouldCode
     * @Return: com.cw.biz.sms.domain.entity.SmsMould
     * @Date: 2018/9/1 16:06
     * @Version: 1
     */
    SmsMould findByMouldCode(String mouldCode);

}
