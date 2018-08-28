package com.cw.web.backend.controller;

import com.cw.web.common.filter.CwFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 控制器注册
 * Created by dujy on 2017-05-22.
 */
@Configuration
public class BackendWebConfig {

    @Bean(name = "backend_loginFilter_reg")
    public FilterRegistrationBean loginFilterRegistrationBean() {
        FilterRegistrationBean bean = new CwFilter.Builder().loginUrl("/backend/manager/login.html")
                .notNeedLoginUrls("/static/scripts/*","/backend/product/findByCondition.json","/static/css/*","/static/fronts/*","/static/images/*","/static/*.html")
                .urlPatterns("/backend/*").supportUserType("manager").build();
        bean.setName("backend_loginFilter");
        return bean;
    }
}
