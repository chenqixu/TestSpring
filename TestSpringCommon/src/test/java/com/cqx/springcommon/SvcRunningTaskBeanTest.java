package com.cqx.springcommon;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

public class SvcRunningTaskBeanTest {

    @Test
    public void buildJSON() {
        SvcRunningTaskBean bean = new SvcRunningTaskBean();
        bean.setTask_desc("测试任务1");
        bean.setTask_class("com.cqx.nodemanager.task.TestTask");
        bean.setTask_commands("{\"test1\":\"123abc\"}");
        System.out.println(JSON.toJSON(bean));
// {"req_source":1,"task_class":"com.cqx.nodemanager.task.TestTask","task_redo_cnt":0,"task_desc":"测试任务1","task_commands":"{\"test1\":\"123abc\"}"}
    }
}
