/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-13 21:01 创建
 */
package com.cw.biz.shiro;

import com.cw.biz.CwException;
import com.cw.biz.user.domain.relm.CwAuthenticationToken;
import com.cw.biz.user.domain.service.SeUserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {


    public RetryLimitHashedCredentialsMatcher() {
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        CwAuthenticationToken authenticationToken = (CwAuthenticationToken) token;

        String username = (String) authenticationToken.getPrincipal();
        Long merchantId = authenticationToken.getMerchantId();
        //retry count + 1
        Integer retryCount = 0;//passwordRetryCache.getRetryCount(username, merchantId);
        retryCount++;
        if (retryCount > 5) {
//            userService.lock(username, merchantId);
            throw new CwException(username + "用户登录失败次数超过限制，帐号被锁定");
        }

        boolean matches = super.doCredentialsMatch(token, info);

        return matches;
    }

}
