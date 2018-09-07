package com.cw.biz.channel.domain.repository;

import com.cw.biz.channel.domain.entity.ConsumeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 渠道资源
 * Created by Administrator on 2017/7/13.
 */
public interface ConsumeLogRepository extends JpaRepository<ConsumeLog,Long>,JpaSpecificationExecutor<ConsumeLog> {

}
