package com.cw.biz.channel.domain.repository;

import com.cw.biz.channel.domain.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 代理商
 * Created by Administrator on 2017/7/13.
 */
public interface AgentRepository extends JpaRepository<Agent,Long>,JpaSpecificationExecutor<Agent> {

}
