package com.cqx.testspringboot.sdtp;

import com.cqx.netty.sdtp.client.SimpleSDTPClient;
import com.cqx.testspringboot.sdtp.bean.DataSendBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Sdtp验证服务
 *
 * @author chenqixu
 */
@EnableAutoConfiguration
@RestController
@RequestMapping("SdtpServer")
public class SdtpServer {
    private static final Logger logger = LoggerFactory.getLogger(SdtpServer.class);

    @RequestMapping(value = "/ruleUpload", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String ruleUpload(@RequestParam(value = "ruleUpload", required = false) MultipartFile file) throws IOException {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("file: {}, fileName: {}", file, fileName);
        // 创建一个空白文件
//        Path dstFile = Files.createFile(Paths.get("/" + fileName));
//        logger.info("fileName: {}, dstFile: {}", fileName, dstFile);
        // 将上传的文件传输到空白文件
//        file.transferTo(dstFile.toFile());
//        return "{\"rule\":\"123\"}";
        return "123";
    }

    @RequestMapping(value = "/dataCheck", method = {RequestMethod.POST}, produces = MediaType.TEXT_HTML_VALUE)
    public String dataCheck(@RequestBody DataSendBean dataSendBean) throws IOException {
        SimpleSDTPClient client = new SimpleSDTPClient();
        return client.check(dataSendBean.getRule(), dataSendBean.getData(), dataSendBean.isLengthcheck());
    }

    @RequestMapping(value = "/dataSend", method = {RequestMethod.POST}, produces = MediaType.TEXT_HTML_VALUE)
    public String dataSend(@RequestBody DataSendBean dataSendBean) throws IOException {
        String[] sdtpserver_array = dataSendBean.getSdtpserver().split(":", -1);
        if (sdtpserver_array.length == 2) {
            String ip = sdtpserver_array[0];
            int port = Integer.valueOf(sdtpserver_array[1]);
            SimpleSDTPClient client = new SimpleSDTPClient();
            client.setIp(ip);
            client.setPort(port);
            // 先校验
            String checkRet = client.check(dataSendBean.getRule(), dataSendBean.getData(), dataSendBean.isLengthcheck());
            if (checkRet.equals("校验成功")) {
                // 需要把第一个规则和第一个数据去掉(因为数据里的长度不正确)
                int firstRule = dataSendBean.getRule().indexOf(",");
                dataSendBean.setRule(dataSendBean.getRule().substring(firstRule + 1));
                int firstData = dataSendBean.getData().indexOf("|");
                dataSendBean.setData(dataSendBean.getData().substring(firstData + 1));
                try {
                    return client.xdr(dataSendBean.getSdtpheader(), dataSendBean.getRule(), dataSendBean.getData());
                } catch (Exception e) {
                    return e.getMessage();
                }
            } else {
                return checkRet;
            }
        } else {
            return "ip、端口填写不正确！";
        }
    }
}
