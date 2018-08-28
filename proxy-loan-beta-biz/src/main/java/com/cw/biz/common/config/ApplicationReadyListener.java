
package com.cw.biz.common.config;


import com.cw.biz.common.entity.BaseEntity;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 *
 */
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        BaseEntity.inited();
    }
}
