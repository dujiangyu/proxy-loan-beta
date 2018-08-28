package com.cw.biz.channel.domain.repository;

import com.cw.biz.channel.domain.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 渠道资源
 * Created by Administrator on 2017/7/13.
 */
public interface ChannelRepository extends JpaRepository<Channel,Long>,JpaSpecificationExecutor<Channel> {

}
