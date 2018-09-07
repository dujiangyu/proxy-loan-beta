/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-31 16:46 创建
 */
package com.cw.biz.user.domain.relm;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 *
 */
public class CwAuthenticationToken extends UsernamePasswordToken {
    private Long merchantId;
    private boolean noPasswordLogin;

    public CwAuthenticationToken(String username, String password, Long merchantId) {
        super(username, password);
        this.merchantId = merchantId;
    }

    public CwAuthenticationToken(String username, boolean noPasswordLogin, Long merchantId) {
        super(username, (String) null);
        this.merchantId = merchantId;
        this.noPasswordLogin =noPasswordLogin;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public boolean isNoPasswordLogin() {
        return noPasswordLogin;
    }

    public void setNoPasswordLogin(boolean noPasswordLogin) {
        this.noPasswordLogin = noPasswordLogin;
    }
}
