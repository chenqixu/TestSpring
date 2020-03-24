package com.cqx.testweb.action;

import com.cqx.testweb.bean.AuthBean;
import com.cqx.testweb.bean.RequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ImageController
 *
 * @author chenqixu
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "/v1")
public class ImageController {
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @RequestMapping(value = "/t1")
    @ResponseBody
    public AuthBean doLogin(@RequestBody RequestBean requestBean)
            throws Exception {
        logger.info("requestBean：{}", requestBean);
        AuthBean authBean = new AuthBean();
        authBean.setId(requestBean.getId());
        return authBean;
    }

}
