package com.cqx.testweb.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 将.do路径导向到相对应的jsp页面
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "/goto")
public class GotoController {
    private static final Logger logger = LoggerFactory.getLogger(GotoController.class);

    @RequestMapping(value = "/{filename}")
    public ModelAndView gotoView(@PathVariable String filename) {
        logger.info("filename：{}", filename);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/" + filename);
        return mv;
    }

    @RequestMapping(value = "/{dir}/{filename}")
    public ModelAndView gotoView(@PathVariable String dir, @PathVariable String filename) {
        logger.info("dir：{}，filename：{}", dir, filename);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/" + dir + "/" + filename);
        return mv;
    }

    @RequestMapping(value = "/{dir1}/{dir2}/{filename}")
    public ModelAndView gotoView(@PathVariable String dir1, @PathVariable String dir2, @PathVariable String filename) {
        logger.info("dir1：{}，dir2：{}，filename：{}", dir1, dir2, filename);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/" + dir1 + "/" + dir2 + "/" + filename);
        return mv;
    }

    @RequestMapping(value = "/{dir1}/{dir2}/{dir3}/{filename}")
    public ModelAndView gotoView(@PathVariable String dir1, @PathVariable String dir2, @PathVariable String dir3, @PathVariable String filename) {
        logger.info("dir1：{}，dir2：{}，dir3：{}，filename：{}", dir1, dir2, dir3, filename);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/" + dir1 + "/" + dir2 + "/" + dir3 + "/" + filename);
        return mv;
    }

}
