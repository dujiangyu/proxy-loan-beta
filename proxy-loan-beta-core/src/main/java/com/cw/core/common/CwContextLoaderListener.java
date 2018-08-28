package com.cw.core.common;

import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletContext;

/**
 * Created by Administrator on 2017/6/14.
 */
public class CwContextLoaderListener
        extends ContextLoaderListener
{
    protected void customizeContext(ServletContext servletContext, ConfigurableWebApplicationContext applicationContext)
    {
        super.customizeContext(servletContext, applicationContext);
        if ((applicationContext instanceof XmlWebApplicationContext))
        {
            XmlWebApplicationContext context = (XmlWebApplicationContext)applicationContext;
            context.setAllowBeanDefinitionOverriding(false);
        }
    }
}