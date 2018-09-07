package com.cw.web.common.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/9/22.
 */
@Component
@Configurable
@EnableScheduling
public class QuartzJob {

    private Logger logger = LoggerFactory.getLogger(QuartzJob.class);


    /**
     * 定时任务上下架功能
     * @throws Exception
     */
    @Scheduled(cron = "0 5 0 * * ? ") // 每一小时执行一次
    public void goWork() throws Exception {
        logger.info("**************定时任务执行开始***************");
        logger.info("**************定时任务执行结束***************");
    }
}
