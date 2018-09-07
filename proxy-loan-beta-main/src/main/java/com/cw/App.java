package com.cw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 * Created by dujy on 2017-05-20.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class App {

    public static void main(String[] args){
        System.setProperty("cw.appName","proxy-loan-beta");
        SpringApplication.run(App.class,args);
    }

}
