package com.cqx.testpring.win7.phone;

import com.cqx.common.utils.file.FileUtil;
import com.cqx.testpring.win7.db.DbDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * CopyPhonePhoto
 *
 * @author chenqixu
 */
// @EnableAutoConfiguration 触发自动配置
// 它的核心作用是让 Spring Boot 能够根据你项目类路径（Classpath）中引入的 Jar 包依赖。
// 自动推断并配置你的应用所需的大部分 Bean​。
// 当你引入了特定的 Starter 依赖（例如 spring-boot-starter-data-jpa），这个助手会自动检测到。
// 并为你准备好数据库连接、事务管理器等相关的组件，你无需手动编写大量的配置代码。
@EnableAutoConfiguration
// @RestController 用于构建 RESTful Web 服务的核心注解。
// 它本质上是一个组合注解，将 @Controller和 @ResponseBody的功能合二为一，使得开发 Web API 变得非常简洁。
@RestController
// @CrossOrigin 解决 跨域资源共享（CORS）​ 问题。
// 它的核心作用是告诉 Spring 在服务器的 HTTP 响应头中添加一系列 CORS 相关字段。
// 从而指示浏览器允许来自不同“源”的网页脚本访问本服务器的特定资源。
@CrossOrigin
// @PropertySource 加载外部配置文件的核心注解，它能将属性文件中的键值对注入到 Spring 的 Environment中。
// 方便应用程序通过统一的方式访问配置信息。
@PropertySource(value = "classpath:/application.properties", encoding = "UTF-8")
// @RequestMapping 将特定的 HTTP 请求（比如一个 URL 地址）映射到对应的控制器方法上。
// 从而告诉 Spring：“当有这样一个请求过来时，请调用我这个方法来处理。”。
@RequestMapping("/photo")
public class PhonePhotoServer {
    private static final Logger logger = LoggerFactory.getLogger(PhonePhotoServer.class);
    private FileUtil fileUtil = new FileUtil();

    @Resource
    private DbDaoImpl dbDao;

    @PostConstruct
    private void init() {
//        for (String file : FileUtil.listFile("计算机/Redmi Note 8 Pro/内部存储设备/DCIM/")) {
//            logger.info("{}", file);
//        }
    }

    @Value("${phone.photo.copy.file}")
    private String file_path;

    @RequestMapping(value = "/copy", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public void copy() {
        logger.info("file_path: {}", file_path);
        for (String file : FileUtil.listFile(file_path)) {
            logger.info("{}", file);
        }
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public void list() {
        logger.info("file_path: {}", file_path);
        for (String file : FileUtil.listFile(file_path)) {
            logger.info("{}", file);
        }
    }

    @RequestMapping(value = "/db_test", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String dbTest() {
        return dbDao.query1();
    }
}
