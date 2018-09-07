package com.cw.web.common.util;

import com.cw.biz.CwException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Properties;

/**
 *
 */
public class VerifyCodeUtil {
    private static final Logger logger = LoggerFactory.getLogger(VerifyCodeUtil.class);

    public static final String VERIFY_CODE_KEY = "CP_VERIFY_CODE_KEY";
    private static final String VERIFY_TIMES_KEY = "CP_VERIFY_TIMES_KEY";
    public static final int MAX_TRY = 10;
//    private static ZDSCaptcha yijiCaptcha = null;

    static {
        Properties properties = new Properties();
        properties.setProperty("kaptcha.textproducer.char.string", "023456789");
//        yijiCaptcha = new ZDSCaptcha(properties);
    }

    public static byte[] generateImage(HttpSession session) throws Exception {
//        Yaptcha yaptcha = yijiCaptcha.generate();
//        session.setAttribute(VERIFY_CODE_KEY, yaptcha.getAnswer());
//        session.setAttribute(VERIFY_TIMES_KEY, 0);
//        return yaptcha.getImageByte();
        return null;
    }

    public static void verify(HttpServletRequest request, String input) {
        HttpSession session = request.getSession();
        if(session.getAttribute(VERIFY_TIMES_KEY)==null){
            CwException.throwIt("已超时，请刷新验证码");
        }
        int time = (int) session.getAttribute(VERIFY_TIMES_KEY);
        if (time <= MAX_TRY) {
            String saved = (String) session.getAttribute(VERIFY_CODE_KEY);
            if (saved.equalsIgnoreCase(input)) {
                session.setAttribute(VERIFY_TIMES_KEY, 0);
            } else {
                session.setAttribute(VERIFY_TIMES_KEY, time + 1);
                CwException.throwIt("验证码不匹配");
            }
        } else {
            logger.warn("客户端ip={}，重试验证码次数={}", request.getRemoteAddr(), time);
            CwException.throwIt("验证码重试次数超过阈值，账户已被锁定，请联系管理员解锁");
        }
    }
}
