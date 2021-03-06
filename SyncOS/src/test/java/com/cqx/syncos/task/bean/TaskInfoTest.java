package com.cqx.syncos.task.bean;

import com.alibaba.fastjson.JSON;
import com.cqx.syncos.util.DateUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TaskInfoTest {

    private static Logger logger = LoggerFactory.getLogger(TaskInfoTest.class);

    @Test
    public void mysqlTomysqlTask() {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTask_name("sync_os_test1");
        taskInfo.setInterval(5000L);

        taskInfo.setSrc_type("MYSQL");
        taskInfo.setSrc_name("sync_os_test1");
        taskInfo.setSrc_fields("id,name,modify_time");
        taskInfo.setModify_time_fields("");
        Map<String, String> src_conf = new HashMap<>();
        src_conf.put("TNS", "jdbc:mysql://127.0.0.1:3306/utap?useUnicode=true");
        src_conf.put("USER_NAME", "udap");
        src_conf.put("PASS_WORD", "udap");
        taskInfo.setSrc_conf(src_conf);

        taskInfo.setDst_type("MYSQL");//ORACLE、MYSQL、KAFKA
        taskInfo.setDst_name("sync_os_test2");
        taskInfo.setDst_fields("id,name,modify_time");
        Map<String, String> dst_conf = new HashMap<>();
        dst_conf.put("TNS", "jdbc:mysql://127.0.0.1:3306/utap?useUnicode=true");
        dst_conf.put("USER_NAME", "udap");
        dst_conf.put("PASS_WORD", "udap");
        taskInfo.setDst_conf(dst_conf);
        logger.info("{}", JSON.toJSONString(taskInfo));
    }

    @Test
    public void oracleTokafkaTask() {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTask_name("oper_history");
        taskInfo.setInterval(30000L);

        taskInfo.setSrc_type("ORACLE");
        taskInfo.setSrc_name("oper_history");
        taskInfo.setSrc_fields("oper_acct,oper_type,oper,oper_time");
        taskInfo.setModify_time_fields("oper_time");
        taskInfo.setAt_time("2020-05-09 14:56:43");
        taskInfo.setScan_split("\\|");
        Map<String, String> src_conf = new HashMap<>();
//        src_conf.put("TNS", "jdbc:oracle:thin:@10.1.8.204:1521:orapri");
//        src_conf.put("USER_NAME", "edc_addressquery");
//        src_conf.put("PASS_WORD", "edc_addressquery");
        taskInfo.setSrc_conf(src_conf);

        taskInfo.setDst_type("KAFKA");//ORACLE、MYSQL、KAFKA
        taskInfo.setDst_name("ogg_to_kafka");
        taskInfo.setDst_fields("");
        Map<String, String> dst_conf = new HashMap<>();
//        dst_conf.put("bootstrap.servers", "edc-mqc-01:9092,edc-mqc-02:9092,edc-mqc-03:9092");
//        dst_conf.put("acks", "0");
//        dst_conf.put("batch.size", "1048576");
//        dst_conf.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        dst_conf.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
//        dst_conf.put("security.protocol", "SASL_PLAINTEXT");
//        dst_conf.put("sasl.mechanism", "PLAIN");
//        dst_conf.put("kafka_username", "admin");
//        dst_conf.put("kafka_password", "admin");
        taskInfo.setDst_conf(dst_conf);
        logger.info("{}", JSON.toJSONString(taskInfo));
    }

    @Test
    public void syncos_100000() {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTask_name("syncos_100000");
        taskInfo.setInterval(30000L);

        taskInfo.setSrc_type("ORACLE");
        taskInfo.setSrc_name("syncos_100000");
        taskInfo.setSrc_fields("visit_code,user_type,user_id,tx_date,trunk_out,trunk_in,term_type,stop_cause,start_time,source_type,service_type,service_code,second_bill_sum_fee,roam_type,prefix_type,other_visit_code,other_user_id,other_nbr,other_lac,other_home_county,other_home_code,other_cell_id,other_brand_id,origianl_sn,new_term_type,new_roam_type,new_prefix_type,new_ldc_type,new_charge_type,new_brand_id,netinout_flag,msrn,msisdn,msc_id,load_time,lfee_add,lfee2,lfee,ldc_type,lac_id,imsi,hot_flag,home_county,home_code,guild_other_nbr,f_lfee_add,f_lfee2,f_lfee,f_cfee_add,f_cfee,fw_calling_nbr,first_bill_sum_fee,fee_type,end_hms,end_date,detail_bill_id,data_up,data_down,cfee_add,cfee,cell_id,cdr_type,cdr_id,call_type,call_duration,brand_id,bill_summing_hms,bill_summing_date,system_type,multimedia_type,fid_cdr_id,team_id,team_type,partial_id,call_reference,communication_type,usage_mode,cost_type,cdr_version,info_type,called_number,deal_id,std_session_id,std_extend_field_1,std_extend_field_2,std_extend_field_3,std_extend_field_4,rn");
        taskInfo.setModify_time_fields("load_time");
        taskInfo.setAt_time("2020-05-12 15:00:00");
        taskInfo.setScan_split("\\|");
        taskInfo.setSrc_conf(new HashMap<>());

        taskInfo.setDst_type("KAFKA");//ORACLE、MYSQL、KAFKA
        taskInfo.setDst_name("ogg_to_kafka");
        taskInfo.setDst_fields("");
        taskInfo.setDst_conf(new HashMap<>());
        logger.info("{}", JSON.toJSONString(taskInfo));
    }

    @Test
    public void cqx_ccs_user() {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTask_name("cqx_ccs_user");
        taskInfo.setInterval(30000L);

        taskInfo.setSrc_type("ORACLE");
        taskInfo.setSrc_name("cqx_ccs_user");
        taskInfo.setSrc_fields("home_city,user_id,network_type,customer_id,type,service_type,msisdn,imsi,user_brand,home_county,creator,create_time,create_site,service_status,password,transfer_time,stop_time,modify_id,modify_site,modify_time,modify_content,rc_sn,rc_expire_time,order_seq,broker_id,history_seq,lock_flag,bill_type,bill_credit,bill_time,expire_time,archives_create_time,password_get_type,password_get_time,password_reset_time,sub_type");
        taskInfo.setModify_time_fields("modify_time");
        taskInfo.setAt_time("2020-05-15 09:59:30");
        taskInfo.setScan_split("\\|");
        taskInfo.setSrc_conf(new HashMap<>());

        taskInfo.setDst_type("KAFKA");//ORACLE、MYSQL、KAFKA
        taskInfo.setDst_name("ogg_to_kafka");
        taskInfo.setDst_fields("");
        taskInfo.setDst_conf(new HashMap<>());
        logger.info("{}", JSON.toJSONString(taskInfo));
    }

    @Test
    public void cqx_css_broadband_reservation() {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTask_name("cqx_css_broadband_reservation");
        taskInfo.setInterval(30000L);

        taskInfo.setSrc_type("ORACLE");
        taskInfo.setSrc_name("cqx_css_broadband_reservation");
        taskInfo.setSrc_fields("home_city,home_county,contact_name,contact_tel,order_state,manager_name,create_time,community_code,address,optional_deal_id,line_covered,sms_send_time,modify_operator,modify_time,remark,manager_tel,confirm_serial,msisdn,community_name,request_source,create_operator,create_operator_name,create_organ_name,create_organ_id,user_id,finish_time,is_succ,cnt_effect_time,resource_remark,order_remark,resource_modify_time,order_modify_time,sale_id,sale_name,product_attr,param,broadband_user_id,open_id,open_time,accept_reason_code,tele_info,line_state,equipment_out_type,user_source,recommend_order_id,recommend_org_id,recommend_msisdn,is_order,res_check_result,search_type,accept_order_state,search_operation_id,pd_contact_tel,pd_contact_name,accept_remark,search_time,search_organ_id,search_operator_id,order_number,sub_order_number");
        taskInfo.setModify_time_fields("modify_time");
        taskInfo.setAt_time("2020-05-15 09:59:30");
        taskInfo.setScan_split("\\|");
        taskInfo.setSrc_conf(new HashMap<>());

        taskInfo.setDst_type("KAFKA");//ORACLE、MYSQL、KAFKA
        taskInfo.setDst_name("nmc_tb_lte_http_test");
        taskInfo.setDst_fields("");
        taskInfo.setDst_conf(new HashMap<>());
        logger.info("{}", JSON.toJSONString(taskInfo));
    }

    @Test
    public void time() {
        logger.info("{}", DateUtil.format("2020-05-09 14:52:23"));
    }
}