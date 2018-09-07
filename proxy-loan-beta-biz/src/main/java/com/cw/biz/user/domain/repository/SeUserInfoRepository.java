package com.cw.biz.user.domain.repository;

import com.cw.biz.user.domain.entity.YxUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用户基本信息
 * Created by dujy on 2017-05-20.
 */
public interface SeUserInfoRepository extends JpaRepository<YxUserInfo,Long>,JpaSpecificationExecutor<YxUserInfo> {
    YxUserInfo findByUserId(Long userId);

    YxUserInfo findByPhone(String phone);

}
