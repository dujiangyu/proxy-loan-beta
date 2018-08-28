package com.cw;

import com.cw.core.common.util.DataExtract;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=App.class)// 指定spring-boot的启动类
public class BaseTest{

    private Logger log = LoggerFactory.getLogger(BaseTest.class);

    @Autowired
    private DataExtract dataExtract;

    @Test
    public void getNetDataImportDb()  {
        log.info("拉取最新数据");
        //50袋鼠达人
        //46泰盛金
        //华亿工匠
        String[] test= new String[]{"50","46","74"};
        for(String idx:test) {
            dataExtract.getNetDataImportDb(Long.parseLong(idx));
        }
    }

}

