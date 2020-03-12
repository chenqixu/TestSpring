package com.cqx.testspring.webservice.operhi.action;

import com.cqx.testspring.webservice.common.BaseController;
import com.cqx.testspring.webservice.operhi.client.OperhisClient;
import com.cqx.testspring.webservice.operhis.model.javabean.LoginBean;
import com.cqx.testspring.webservice.operhis.model.javabean.NullResultBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * OperhisController
 *
 * @author chenqixu
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "/goto")
public class OperhisController extends BaseController {

    private OperhisClient operhisClient = new OperhisClient();

    @RequestMapping(value = "/addOperHistory.do")
    @ResponseBody
    public NullResultBean addOperHistory(LoginBean loginBean) {//@RequestBody
        this.getRequest().getSession().setAttribute("test1", "test1-session");
//        OperhisClient operhisClient = new OperhisClient("LoginServiceImpl");
//        System.out.println("[controller]" + operhisBean);
//        OperhisResultBean operhisResultBean = operhisClient.addOperHistory(operhisBean);
//        System.out.println("[operhisResultBean]" + operhisResultBean);
//        return operhisResultBean;
//        OperhisClient operhisResultBean = new OperhisClient();
//        LoginServiceInf loginServiceInf = operhisResultBean.newInstance(LoginServiceInf.class);
        NullResultBean nullResultBean = operhisClient.exec("LoginServiceImpl", "login", loginBean);
        return nullResultBean;
    }
}
