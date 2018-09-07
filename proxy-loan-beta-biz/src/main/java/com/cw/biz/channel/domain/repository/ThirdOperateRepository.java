package com.cw.biz.channel.domain.repository;

import com.cw.biz.channel.domain.entity.ThirdOperate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 三级用户
 * Created by Administrator on 2017/7/13.
 */
public interface ThirdOperateRepository extends JpaRepository<ThirdOperate,Long>,JpaSpecificationExecutor<ThirdOperate> {
    ThirdOperate findByUserId(Long userId);
}
