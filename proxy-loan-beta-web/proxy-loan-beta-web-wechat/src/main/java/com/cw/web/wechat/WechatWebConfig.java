package com.cw.web.wechat;

import com.cw.web.common.filter.CwFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 控制器注册
 * Created by dujy on 2017-05-22.
 */
@Configuration
public class WechatWebConfig {

    @Bean(name = "wechat_loginFilter_reg")
    public FilterRegistrationBean loginFilterRegistrationBean() {
        FilterRegistrationBean bean = new CwFilter.Builder().loginUrl("/wechat/register.html")
                .notNeedLoginUrls("/css/login.css","/static/scripts/*","/static/css/*","/static/fronts/*","/static/images/*","/static/*.html")
                .urlPatterns("/wechat/*").supportUserType("user").build();
        bean.setName("wechat_loginFilter");
        bean.setOrder(1);
        return bean;
    }
}
