package com.cw.web.common.aspect;

import com.cw.core.common.annotation.ActionControllerLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 记录系统操作日志
 * Created by Administrator on 2017/8/1.
 */
@Aspect
@Component
public class ControllerAspect {

    public static Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    // 对以下com.cw.web.Controller类中的所有方法进行切入
    @Pointcut("@annotation(com.cw.core.common.annotation.ActionControllerLog)")
    private void weblog(){
        logger.info("=============记录操作日志=============");
    }

    @Before("weblog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        ActionControllerLog controllerLog = giveController(joinPoint);

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();


        logger.info("=============记录操作结束=============");

    }

    /**
     * 是否存在注解，如果存在就记录日志
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private static ActionControllerLog giveController(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if(method != null)
        {
            return method.getAnnotation(ActionControllerLog.class);
        }
        return null;
    }

}

