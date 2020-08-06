package com.cqx.penetrationsystem.mstsc;

import com.cqx.common.utils.system.ClipBoardUtil;
import com.cqx.common.utils.system.SleepUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * MstscServer
 *
 * @author chenqixu
 */
@EnableAutoConfiguration
@RestController
@CrossOrigin
@RequestMapping("/mstsc")
public class MstscServer {
    private static final Logger logger = LoggerFactory.getLogger(MstscServer.class);

    @PostConstruct
    private void init() {
        logger.info("---Task initialization and startup......");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //监视剪贴板
                    try {
                        logger.info("---Monitor clipboard text information , {}", ClipBoardUtil.getSysClipBoardText());
                    } catch (Exception e) {
                        logger.error("===Monitoring clipboard exceptions , information : " + e.getMessage(), e);
                    }
                    SleepUtil.sleepMilliSecond(1000);
                }
            }
        }).start();
    }
}
