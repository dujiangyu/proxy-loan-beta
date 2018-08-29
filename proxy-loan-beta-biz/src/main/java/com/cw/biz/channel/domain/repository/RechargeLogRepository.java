package com.cw.biz.channel.domain.repository;

import com.cw.biz.channel.domain.entity.RechargeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 渠道资源
 * Created by Administrator on 2017/7/13.
 */
public interface RechargeLogRepository extends JpaRepository<RechargeLog,Long>,JpaSpecificationExecutor<RechargeLog> {

}
