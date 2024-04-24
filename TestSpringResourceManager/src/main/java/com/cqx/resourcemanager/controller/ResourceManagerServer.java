package com.cqx.resourcemanager.controller;

import com.alibaba.fastjson.JSON;
import com.cqx.common.utils.http.HttpUtil;
import com.cqx.common.utils.jdbc.DBBean;
import com.cqx.common.utils.jdbc.DBType;
import com.cqx.common.utils.jdbc.JDBCUtil;
import com.cqx.common.utils.system.SleepUtil;
import com.cqx.springcommon.CommonResBean;
import com.cqx.springcommon.NodeReqBean;
import com.cqx.springcommon.SchedulingResultBean;
import com.cqx.springcommon.SvcRunningTaskBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 资源管理器
 *
 * @author chenqixu
 */
@EnableAutoConfiguration
@RestController
@CrossOrigin
@RequestMapping("/rm")
public class ResourceManagerServer {
    private static final Logger logger = LoggerFactory.getLogger(ResourceManagerServer.class);
    private HttpUtil httpUtil = new HttpUtil();
    private JDBCUtil jdbcUtil;
    private AtomicBoolean redoFlag = new AtomicBoolean(true);
    /**
     * <实际的资源>队列名称，空闲资源
     */
    private ConcurrentHashMap<String, AtomicInteger> nodeResource = new ConcurrentHashMap<>();
    /**
     * <队列分配的资源>队列名称，分配资源
     */
    private ConcurrentHashMap<String, Integer> queueResource = new ConcurrentHashMap<>();
    /**
     * 所有资源
     */
    private AtomicInteger allResource = new AtomicInteger(0);
    /**
     * 已分配资源
     */
    private AtomicInteger assignedResource = new AtomicInteger(0);
    /**
     * 注册节点管理
     */
    private ConcurrentHashMap<String, NodeReqBean> registerNode = new ConcurrentHashMap<>();

    /**
     * 队列分配
     */
    @PostConstruct
    private void init() {
        // 客群跑数、客群清单外送队列
        queueResource.put("grid", 3);
        queueResource.put("electric", 3);
        // 探索队列
        queueResource.put("analyse", 1);
        // 合并队列
        queueResource.put("merge", 1);
        // 数据分发队列
        queueResource.put("send", 1);

        // 初始化队列
        for (Map.Entry<String, Integer> _queue : queueResource.entrySet()) {
            nodeResource.put(_queue.getKey(), new AtomicInteger(0));
        }

        DBBean dbBean = new DBBean();
        dbBean.setDbType(DBType.ORACLE);
        dbBean.setTns("jdbc:oracle:thin:@10.1.8.99:1521/orcl12cpdb1");
        dbBean.setUser_name("receng_dev");
        dbBean.setPass_word("receng_dev");
        jdbcUtil = new JDBCUtil(dbBean);

        // 重新派发任务到NM
        redoWaitTask();

        // 轮询判断容器是否心跳超时，无法直接通信，异步可能存在反馈不及时、响应错误的风险
        // 做法可能是重启pod

    }

    /**
     * 资源查询
     *
     * @return
     */
    @RequestMapping(value = "/queryResource/{queueName}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResBean queryResource(@PathVariable String queueName) {
        CommonResBean resBean = new CommonResBean();
        resBean.setRet_code(1);
        resBean.setBody(nodeResource.get(queueName).get() + "");
        return resBean;
    }

    /**
     * 容器节点注册，需要携带容器IP、容器资源、容器唯一名称
     *
     * @return
     */
    @RequestMapping(value = "/nodeRegister", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResBean nodeRegister(@RequestBody NodeReqBean nodeReqBean) {
        CommonResBean resBean = new CommonResBean();
        // 查询节点是否注册过
        if (registerNode.get(nodeReqBean.getNode_name()) == null) {
            resBean.setRet_code(1);
            resBean.setBody(String.format("[%s]节点注册成功", nodeReqBean.getNode_ip() + nodeReqBean.getNode_name()));
            // 节点管理
            registerNode.put(nodeReqBean.getNode_name(), nodeReqBean);
            int _node_source = nodeReqBean.getNode_source();
            logger.info("[{}]节点注册成功，资源={}", nodeReqBean.getNode_ip() + nodeReqBean.getNode_name(), _node_source);
            while (_node_source > 0) {
                // 使用轮询分配资源方式
                for (Map.Entry<String, Integer> _queue : queueResource.entrySet()) {
                    nodeResource.get(_queue.getKey()).incrementAndGet();
                    _node_source--;
                    if (_node_source <= 0) break;
                }
            }
            // 打印当前队列资源
            logger.info("[打印当前队列资源]{}", nodeResource);
        } else {// 节点已经注册过，防止重复注册
            resBean.setBody(String.format("[%s]节点已经注册过", nodeReqBean.getNode_ip() + nodeReqBean.getNode_name()));
        }
        return resBean;
    }

    /**
     * 容器节点反注册
     *
     * @return
     */
    public CommonResBean nodeUnRegister() {
        return null;
    }

    /**
     * 容器节点离线
     *
     * @return
     */
    public CommonResBean nodeOffline() {
        return null;
    }

    /**
     * 容器心跳
     *
     * @return
     */
    @RequestMapping(value = "/nodeHeart", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResBean nodeHeart(@RequestBody NodeReqBean nodeReqBean) {
        CommonResBean resBean = new CommonResBean();
        NodeReqBean _node = registerNode.get(nodeReqBean.getNode_name());
        if (_node == null) {// 未注册过
            resBean.setBody("未注册过");
        } else if (_node.isOff()) {// 已经超时
            resBean.setBody("已经超时");
        } else {// 正常更新心跳
            _node.heart();
            resBean.setRet_code(1);
            resBean.setBody("正常更新心跳");
        }
        return resBean;
    }

    /**
     * 任务提交，需要提供队列名称，申请资源
     *
     * @return
     */
    @RequestMapping(value = "/submitTask", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResBean submitTask(@RequestBody SvcRunningTaskBean taskReqBean) {
        return submitTask(taskReqBean, true);
    }

    /**
     * 更新任务状态
     *
     * @param taskBean
     * @return
     */
    @RequestMapping(value = "/updateTaskStatus", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResBean updateTaskStatus(@RequestBody SvcRunningTaskBean taskBean) {
        CommonResBean res = new CommonResBean();
        try {
            List<SvcRunningTaskBean> datas = new ArrayList<>();
            datas.add(taskBean);
            String fields = "task_status,task_id";
            String updateSql = "update svc_running_task set task_status=?, done_time=sysdate where task_id=?";
            jdbcUtil.executeBatch(updateSql, datas, SvcRunningTaskBean.class, fields);
            res.setRet_code(1);
            // 资源释放
            if (taskBean.getTask_status().equals("end")) {
                logger.info("[资源释放]{}", JSON.toJSONString(taskBean));
                AtomicInteger queueResource = nodeResource.get(taskBean.getQueue_name());
                for (int _i = 0; _i < taskBean.getReq_source(); _i++) {
                    queueResource.incrementAndGet();
                }
                logger.info("[任务运行完成]{}, 资源释放, [队列当前资源]{}={}"
                        , taskBean.getTask_id(), taskBean.getQueue_name(), queueResource.get());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return res;
    }

    private CommonResBean submitTask(SvcRunningTaskBean taskReqBean, boolean isInsertDB) {
        CommonResBean res = new CommonResBean();

        // 先确认队列是否有资源
        String queueName = taskReqBean.getQueue_name();
        if (queueName == null || queueName.trim().length() == 0) {
            res.setBody(String.format("请输入正确的队列[%s]！", queueName));
            return res;
        }
        int reqSource = taskReqBean.getReq_source();
        if (reqSource <= 0) {
            res.setBody(String.format("请输入正确的任务运行资源[%s]！", reqSource));
            return res;
        }
        AtomicInteger queueResource = nodeResource.get(queueName);
        logger.info("提交任务={}, 提交队列={}, 需要资源={}, 队列当前剩余资源={}"
                , taskReqBean.getTask_desc()
                , queueName
                , taskReqBean.getReq_source()
                , queueResource);
        if (queueResource == null || queueResource.get() <= 0 || reqSource > queueResource.get()) {
            res.setBody(String.format("队列[%s]没有可用资源！", queueName));
        } else {
            // 有可用资源
            try {
                List<SvcRunningTaskBean> datas = new ArrayList<>();
                if (isInsertDB) {
                    // 插入任务运行表
                    // 生成任务id
                    String _task_id = "T_" + UUID.randomUUID().toString();
                    taskReqBean.setTask_id(_task_id);
                    taskReqBean.setTask_status("wait");// wait running end
                    datas.add(taskReqBean);
                    String fields = "task_id,task_desc,channel_id,task_class,task_commands,task_status,queue_name,req_source";
                    String insertSql = "insert into svc_running_task(" + fields + ",create_time) values(?,?,?,?,?,?,?,?,sysdate)";
                    jdbcUtil.executeBatch(insertSql, datas, SvcRunningTaskBean.class, fields);
                } else {
                    datas.add(taskReqBean);
                }

                // 尝试向nm提交任务
                boolean isRedo = false;
                try {
                    logger.info("[尝试向nm提交任务]task_id={}", taskReqBean.getTask_id());
                    String ret = httpUtil.doPostThrowException("http://localhost:10802/node-manager/nm/exec", JSON.toJSONString(taskReqBean));
                    logger.info("[尝试向nm提交任务-结果]{}", ret);

                    SchedulingResultBean srb = null;
                    if (ret != null && ret.trim().length() > 0) {
                        srb = JSON.parseObject(ret, SchedulingResultBean.class);
                    }
                    //todo 可能nm繁忙，或者nm故障宕机，需要由另一个线程去派发任务
                    if (srb != null && srb.getRes_code() == 1) {// 提交成功
                        res.setRet_code(1);
                        for (int _i = 0; _i < reqSource; _i++) {
                            queueResource.decrementAndGet();
                        }
                    } else {// 提交失败
                        isRedo = true;
                        res.setBody("[任务提交失败]");
                        logger.warn("[任务提交失败]");
                        // 检索nm的状态，如果nm无返回，就标记下线
//                    int _cnt = 10;
//                    String NMStatus = "";
//                    while (_cnt > 0) {
//                        NMStatus = httpUtil.doGet("http://localhost:10802/node-manager/nm/queryNodeManagerStatus");
//                        if (NMStatus.equals("ok")) {
//                            logger.info("");
//                            break;
//                        }
//                        _cnt--;
//                        SleepUtil.sleepMilliSecond(3000L);
//                    }
//                    // 标记下线
//                    if (!NMStatus.equals("ok")) {
//                        logger.info("标记下线");
//                    }
                    }
                } catch (Exception e) {
                    isRedo = true;
                    res.setBody("[任务提交失败]" + e.getMessage());
                    logger.error("[任务提交失败]" + e.getMessage(), e);
                }

                try {
                    // 任务提交失败，需要延期
                    if (isRedo) {
                        logger.info("[任务提交失败，需要延期]");
                        String fields = "task_id";
                        String updateSql = "update svc_running_task set redo_time=sysdate+1/1440 where task_id=?";
                        int yqRets = jdbcUtil.executeBatch(updateSql, datas, SvcRunningTaskBean.class, fields);
                        logger.info("[任务提交失败，需要延期] 延期成功, 结果={}", yqRets);
                    }
                } catch (Exception e) {
                    logger.error("[任务提交失败，延期异常]" + e.getMessage(), e);
                }
            } catch (Exception e) {
                logger.error("[插入任务运行表异常]" + e.getMessage(), e);
            }
            logger.info("[任务提交完成]当前{}队列剩余资源{}", queueName, queueResource.get());
        }
        return res;
    }

    private void redoWaitTask() {
        final String sql = "select * from svc_running_task WHERE task_status ='wait' AND redo_time<sysdate";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (redoFlag.get()) {
                    SleepUtil.sleepMilliSecond(5000L);
                    try {
                        List<SvcRunningTaskBean> redoList = jdbcUtil.executeQuery(sql, SvcRunningTaskBean.class);
                        logger.info("[任务重新派发到NM] 派发任务个数={}", redoList.size());
                        for (SvcRunningTaskBean bean : redoList) {
                            submitTask(bean, false);
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
