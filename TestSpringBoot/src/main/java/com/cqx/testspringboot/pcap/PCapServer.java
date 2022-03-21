package com.cqx.testspringboot.pcap;

import com.cqx.testspringboot.pcap.dao.PCapDao;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * PCap服务
 *
 * @author chenqixu
 */
@EnableAutoConfiguration
@RestController
@RequestMapping("PCapServer")
public class PCapServer {

    @Resource
    private PCapDao pCapDao;

    @RequestMapping(value = "/getFiles", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getFiles(@RequestParam(value = "f") String file_path
            , @RequestParam(value = "e", required = false) String end_with) {
        return pCapDao.getFiles(file_path, end_with);
    }

    @RequestMapping(value = "/parserPCapFile", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String parserPCapFile(@RequestParam(value = "f") String file_path) {
        return pCapDao.parserPCapFile(file_path);
    }

    @RequestMapping(value = "/getFlow", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getFlow(@RequestParam(value = "f") String file_path) {
        return pCapDao.getFlow(file_path);
    }

    @RequestMapping(value = "/getByte", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getByte(@RequestParam(value = "f") String file_path) {
        return pCapDao.getByte(file_path);
    }

    @RequestMapping(value = "/getInfo", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getInfo(@RequestParam(value = "f") String file_path) {
        return pCapDao.getInfo(file_path);
    }
}
