package com.cw.biz.common.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 *
 */
@Configuration
public class CommonConfigration {

    @Bean
    public static BeanDefinitionRegistryPostProcessor cpValidatorBeanDefinitionRegistryPostProcessor() {
        return new BeanDefinitionRegistryPostProcessor() {
            @Override
            public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
                ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, false);
                scanner.scan("com.cw");
            }

            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

            }
        };
    }

    @Bean
    public ApplicationReadyListener applicationReadyListener() {
        return new ApplicationReadyListener();
    }

    @PostConstruct
    public void init() {
    }
}
