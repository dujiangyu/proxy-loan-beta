package com.cw.core.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by dujy on 2017-05-29.
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware
    {
        private static ApplicationContext CONTEXT;

        public void setApplicationContext(ApplicationContext context)
                throws BeansException
        {
            if (CONTEXT != null)
            {
                if (context.getParent() == CONTEXT) {
                    setContext(context);
                }
            }
            else {
                setContext(context);
            }
        }

        private static void setContext(ApplicationContext context)
        {
            CONTEXT = context;
        }

        public static ApplicationContext get()
        {
            return CONTEXT;
        }
}
