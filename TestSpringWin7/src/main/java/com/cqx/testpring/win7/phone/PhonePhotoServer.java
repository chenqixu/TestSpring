package com.cqx.testpring.win7.phone;

import com.cqx.common.utils.file.FileUtil;
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

/**
 * CopyPhonePhoto
 *
 * @author chenqixu
 */
@EnableAutoConfiguration
@RestController
@CrossOrigin
@PropertySource(value = "classpath:/application.properties", encoding = "UTF-8")
@RequestMapping("/photo")
public class PhonePhotoServer {
    private static final Logger logger = LoggerFactory.getLogger(PhonePhotoServer.class);
    private FileUtil fileUtil = new FileUtil();

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
}
