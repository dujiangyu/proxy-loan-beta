package com.cw.web.common;

import com.cw.biz.CPContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 */
public class CwInterceptor implements HandlerInterceptor {
    public static Logger logger = LoggerFactory.getLogger(CwInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("进入拦截器：{}",request.getRequestURI());
        request.setCharacterEncoding("UTF-8");
        if (null != request && null != handler) {
            CPContext context = CPContext.getContext();
            HttpSession httpSession = request.getSession(false);
            if (null != httpSession) {
                CPContext.SeUserInfo seUserInfo = (CPContext.SeUserInfo) httpSession.getAttribute("seUserInfo");
                if (seUserInfo != null) {
                    context.setSeUserInfo(seUserInfo);
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        CPContext.removeContext();
    }
}
