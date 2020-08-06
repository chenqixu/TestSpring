package com.cqx.penetrationsystem.vmware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * VMwareServer
 *
 * @author chenqixu
 */
@EnableAutoConfiguration
@RestController
@CrossOrigin
@RequestMapping("/vm")
public class VMwareServer {
    private static final Logger logger = LoggerFactory.getLogger(VMwareServer.class);

    @PostConstruct
    private void init() {
        //监视命令文件--和本机交互
        //处理命令文件
        //发送命令到剪贴板

        //监视剪贴板--和4a.mstsc交互
        //处理剪贴板内容或文件
        //发送结果到共享文件

    }
}
