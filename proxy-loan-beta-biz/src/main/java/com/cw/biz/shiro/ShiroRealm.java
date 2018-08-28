package com.cw.biz.shiro;

import com.cw.biz.CPContext;
import com.cw.biz.user.domain.entity.SeResource;
import com.cw.biz.user.domain.entity.SeUser;
import com.cw.biz.user.domain.relm.CwAuthenticationToken;
import com.cw.biz.user.domain.service.SeUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dujy on 2017-05-29.
 */
public class ShiroRealm extends AuthorizingRealm {
    private static final transient Logger log = LoggerFactory.getLogger(ShiroRealm.class);
    @Autowired
    private SeUserService seUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("----------doGetAuthorizationInfo方法被调用----------");
        String username = (String) principalCollection.getPrimaryPrincipal();
        CPContext.SeUserInfo seUserInfo = (CPContext.SeUserInfo) SecurityUtils.getSubject().getSession().getAttribute("seUserInfo");
        Assert.notNull(seUserInfo);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(seUserService.findRoles(username, seUserInfo.getMerchantId()));
        Set<SeResource> seResourceSet = seUserService.findPermissions(username, seUserInfo.getMerchantId());
        Set<String> resourceSet = new HashSet<String>();
        for(SeResource seResource:seResourceSet)
        {
            resourceSet.add(seResource.getPermission());
        }
        authorizationInfo.setStringPermissions(resourceSet);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CwAuthenticationToken token = (CwAuthenticationToken) authenticationToken;
        String username = (String) token.getPrincipal();
        Long merchantId = token.getMerchantId();
        SeUser user = seUserService.findByUserNameAndMerchantId(username, merchantId);
        if (user == null) {
            throw new AuthenticationException("用户名或密码错误");
        }
        if (Boolean.TRUE.equals(user.getLocked())) {
            throw new AuthenticationException("用户被锁定，请联系管理员解锁");
        }
        //用户名是否存在验证完毕,将用户输入的用户名 重设为数据库中的用户名 ，因为用户可能会输入用户名或者手机号登陆 modify by 2017.2.8
        token.setUsername(user.getUsername());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        CwAuthenticationToken authenticationToken = (CwAuthenticationToken) token;
        if (!authenticationToken.isNoPasswordLogin()) {
            CredentialsMatcher cm = getCredentialsMatcher();
            if (!cm.doCredentialsMatch(token, info)) {
                throw new AuthenticationException("用户名或密码错误");
            }
        }
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ShiroConfiguration.ALGORITHM_NAME);
        matcher.setHashIterations(ShiroConfiguration.HASH_ITERATIONS);

        setCredentialsMatcher(matcher);
    }
}
