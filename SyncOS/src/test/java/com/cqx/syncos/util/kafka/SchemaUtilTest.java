package com.cqx.syncos.util.kafka;

import com.cqx.syncos.task.bean.TaskInfo;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class SchemaUtilTest {

    @Test
    public void getSchema() {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTask_name("oper_history");
        taskInfo.setInterval(30000L);

        taskInfo.setSrc_type("ORACLE");
        taskInfo.setSrc_name("oper_history");
        taskInfo.setSrc_fields("oper_acct,oper_type,oper,oper_time");
        taskInfo.setModify_time_fields("oper_time");
        taskInfo.setAt_time("2020-05-09 14:56:43");
        taskInfo.setScan_split("|");
        Map<String, String> src_conf = new HashMap<>();
        taskInfo.setSrc_conf(src_conf);

        taskInfo.setDst_type("KAFKA");//ORACLE、MYSQL、KAFKA
        taskInfo.setDst_name("ogg_to_kafka");
        taskInfo.setDst_fields("");
        Map<String, String> dst_conf = new HashMap<>();
        taskInfo.setDst_conf(dst_conf);

        SchemaUtil schemaUtil = new SchemaUtil(taskInfo);
        System.out.println(schemaUtil.getSchema());
    }
}