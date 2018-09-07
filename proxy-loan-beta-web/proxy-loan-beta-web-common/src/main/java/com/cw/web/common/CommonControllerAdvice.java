package com.cw.web.common;

import com.cw.biz.CwException;
import com.cw.web.common.dto.CPViewResultInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 *
 */
@ControllerAdvice
public class CommonControllerAdvice{
    private static final Logger logger = LoggerFactory.getLogger(CommonControllerAdvice.class);
    private static final String ACCEPT_HEADER = "Accept";

    @ExceptionHandler({AuthorizationException.class})
    public Object unauthenticationException(HttpServletRequest req, HttpServletResponse rep, AuthorizationException exception) {
        if (exception.getCause() instanceof AuthorizationException) {
            logger.error("AuthorizationException:{}", exception.getCause().getMessage());
        } else {
            logger.error("AuthorizationException", exception);
        }
        if (isJsonRequest(req)) {
            CPViewResultInfo info = new CPViewResultInfo();
            info.setCode("999996");
            info.setSuccess(false);
            info.setMessage("未授权的访问");
            return new ResponseEntity<>(info, HttpStatus.UNAUTHORIZED);
        } else {
            int statusCode = HttpStatus.UNAUTHORIZED.value();
            rep.setStatus(statusCode);
            ModelAndView mav = new ModelAndView();
            buildModelAndView(req, exception, statusCode, mav);
            return mav;
        }
    }

    @ExceptionHandler({CwException.class})
    public Object exception(HttpServletRequest req, HttpServletResponse rep, CwException exception) {
        logger.error("CwException {}", exception);
        if (isJsonRequest(req)) {
            CPViewResultInfo info = new CPViewResultInfo();
            info.setSuccess(false);
            info.setCode("999995");
            info.setMessage(exception.getMessage());
            return new ResponseEntity<>(info, HttpStatus.OK);
        } else {
            int statusCode = HttpStatus.OK.value();
            rep.setStatus(statusCode);
            ModelAndView mav = new ModelAndView();
            buildModelAndView(req, exception, statusCode, mav);
            return mav;
        }
    }

    private void buildModelAndView(HttpServletRequest req, Exception exception, int statusCode, ModelAndView mav) {
        mav.addObject("timestamp", new Date());
        mav.addObject("status", statusCode);
        mav.addObject("exception", exception);
        setPath(req, mav);
        mav.setViewName("error");
    }

    private void setPath(HttpServletRequest req, ModelAndView mav) {
        Object path = req.getAttribute("javax.servlet.error.request_uri");
        if (path != null) {
            mav.addObject("path", path);
        } else {
            mav.addObject("path", req.getRequestURL());
        }
    }

    private boolean isJsonRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri.endsWith(".json")) {
            return true;
        }
        if (uri.endsWith(".html") || uri.endsWith(".htm")) {
            return false;
        }
        String acceptHeader = request.getHeader(ACCEPT_HEADER);
        if (StringUtils.hasText(acceptHeader)) {
            return acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE);
        }
        return false;
    }
}
