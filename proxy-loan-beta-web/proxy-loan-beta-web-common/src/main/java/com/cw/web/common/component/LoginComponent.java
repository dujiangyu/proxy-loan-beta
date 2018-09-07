package com.cw.web.common.component;

import com.cw.biz.CPContext;
import com.cw.biz.CwException;
import com.cw.biz.user.domain.entity.SeUser;
import com.cw.biz.user.domain.relm.CwAuthenticationToken;
import com.cw.biz.user.domain.service.SeUserService;
import com.cw.web.common.model.LoginModel;
import com.cw.web.common.util.Apps;
import com.zds.common.lang.beans.Copier;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 *
 */
@Service
public class LoginComponent {
    public static Logger logger = LoggerFactory.getLogger(LoginComponent.class);

    @Autowired
    private SeUserService userService;

    private SeUser loginWithPassword(HttpServletRequest httpServletRequest, LoginModel loginModel,String wechatId, String loginType) {
        SeUser seUser = loginVerify(httpServletRequest, loginModel, LoginModel.PasswordLogin.class, loginType);
        seUser.setWechatId(wechatId);
        AuthenticationToken token = new CwAuthenticationToken(loginModel.getUserName(), loginModel.getPassword(), loginModel.getMerchantId());
        login(httpServletRequest, seUser, token);
        return  seUser;
    }

    private SeUser loginVerify(HttpServletRequest httpServletRequest, LoginModel loginModel, Class group, String loginType) {
        //不是微信登陆和手机验证码登陆的需要判断图形验证码
//        if (!isWechatAccess(httpServletRequest) && !"phoneLogin".equals(loginType)) {
//            if (LoginModel.PasswordLogin.class.isAssignableFrom(group)) {
//                if (loginModel.getVerifyCode() == null || ("".trim()).equals(loginModel.getVerifyCode())) {
//                    CwException.throwIt("图形验证码不能为空");
//                } else {
//                    VerifyCodeUtil.verify(httpServletRequest, loginModel.getVerifyCode());
//                }
//            }
//        }
        SeUser seUser = userService.findByUserNameAndMerchantId(loginModel.getUserName(), loginModel.getMerchantId());
        if (seUser == null) {
            CwException.throwIt("用户不存在");
        }
        if (!loginModel.getType().equalsIgnoreCase(seUser.getType())) {
            CwException.throwIt("用户类型不匹配");
        }
        return seUser;
    }

    private SeUser loginWithPhone(HttpServletRequest httpServletRequest, LoginModel loginModel,String wechatId, String loginType) {
        SeUser seUser = loginVerify(httpServletRequest, loginModel, LoginModel.PhoneLogin.class,loginType);
        seUser.setWechatId(wechatId);
//        SmsValidation.validate(httpServletRequest, loginModel.getSmsVerifyCode());
        AuthenticationToken token = new CwAuthenticationToken(loginModel.getUserName(), true, loginModel.getMerchantId());
        login(httpServletRequest, seUser, token);
        return seUser;
    }

    public void loginWithWechatId(HttpServletRequest httpServletRequest, String wechatId, Long merchantId) {
        Assert.notNull(wechatId,"微信绑定登录openid为空!");
        Assert.notNull(merchantId,"微信绑定登录merchantId为空!");
        SeUser seUser = userService.findByWechatIdAndMerchantId(wechatId, merchantId);
        if (seUser == null) {
            CwException.throwIt("用户不存在");
        }
        AuthenticationToken token = new CwAuthenticationToken(seUser.getUsername(), true, merchantId);
        login(httpServletRequest, seUser, token);
    }

    public void loginWithDevModel(HttpServletRequest httpServletRequest, String userName, Long merchantId) {
        SeUser seUser = userService.findByUserNameAndMerchantId(userName, merchantId);
        if (seUser == null) {
            CwException.throwIt("用户不存在");
        }
        AuthenticationToken token = new CwAuthenticationToken(seUser.getUsername(), true, merchantId);
        login(httpServletRequest, seUser, token);
    }

    public void wechatBinding(HttpServletRequest httpServletRequest, LoginModel loginModel, String wechatId,String loginType) {
        logger.info("登录类型,{},openId,{}",loginType,wechatId);
        SeUser seUser=null;
        if(loginType.equals("phoneLogin")){
          seUser =loginWithPhone(httpServletRequest, loginModel,wechatId,"phoneLogin");
        }else if(loginType.equals("passwordLogin")){
           seUser =loginWithPassword(httpServletRequest, loginModel,wechatId,"passwordLogin");
        }
        //微信登录绑定openid
        if(isWechatAccess(httpServletRequest)){
           //userService.updateUser(seUser, false);
        }

    }


    private void login(HttpServletRequest httpServletRequest, SeUser seUser, AuthenticationToken token) {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        } catch (AuthenticationException e) {
            CwException.throwIt(e.getMessage());
        }
        CPContext.SeUserInfo seUserInfo = Copier.copy(seUser, CPContext.SeUserInfo.class);
        httpServletRequest.getSession().setAttribute("seUserInfo", seUserInfo);
    }

    public static boolean isSessionTimeout(HttpServletRequest request) {
        String sessionId = getSessionIdFormCookie(request);
        HttpSession session = request.getSession();
        return sessionId != null && !session.getId().equalsIgnoreCase(sessionId);
    }

    private static String getSessionIdFormCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        logger.info(":::::::::::::::cookies:::{}",cookies);
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(Apps.getAppSessionCookieName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    private static boolean isWechatAccess(HttpServletRequest request) {
        return request.getHeader("User-Agent").contains("MicroMessenger");
    }
}
