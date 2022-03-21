package com.cqx.testspringboot.pcap.dao;

import com.cqx.common.utils.OtherUtil;
import com.cqx.common.utils.cmd.LogDealInf;
import com.cqx.common.utils.cmd.ProcessBuilderFactory;
import com.cqx.common.utils.file.FileUtil;
import com.cqx.common.utils.pcap.PcapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * PCap业务逻辑
 *
 * @author chenqixu
 */
@Repository("com.cqx.testspringboot.pcap.dao.PCapDao")
public class PCapDao {
    private static final Logger logger = LoggerFactory.getLogger(PCapDao.class);
    private PcapUtil pcapUtil = new PcapUtil();
    private boolean isWindow = OtherUtil.isWindow();

    public List<String> getFiles(String file_path, String end_with) {
        return getFiles(file_path, end_with, false);
    }

    public List<String> getFiles(String file_path, String end_with, boolean haveFullPath) {
        List<String> files = new ArrayList<>();
        if (end_with != null && end_with.length() > 0) {
            for (String tmp : FileUtil.listFileEndWith(file_path, end_with)) {
                if (haveFullPath) {
                    files.add(FileUtil.endWith(file_path) + tmp);
                } else {
                    files.add(tmp);
                }
            }
        } else {
            for (String tmp : FileUtil.listFile(file_path)) {
                if (haveFullPath) {
                    files.add(FileUtil.endWith(file_path) + tmp);
                } else {
                    files.add(tmp);
                }
            }
        }
        return files;
    }

    public String parserPCapFile(String file_path) {
        String result;
        try {
            result = pcapUtil.parserSIP(file_path);
        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            result = sw.toString();
        }
        logger.info("parserPCapFile：{}", result);
        return result;
    }

    /**
     * 前端js流程有一个缺陷，第一个开始必须是->，如果是<-，那么只会识别到-，而会把<拼接到ip中
     *
     * @param file_path
     * @return
     */
    public String getFlow(String file_path) {
        final String format = "%s%s%s: %s";
        final StringBuilder flow = new StringBuilder();
        ProcessBuilderFactory processBuilderFactory;
        if (isWindow) {
            processBuilderFactory = new ProcessBuilderFactory(false, "UTF-8");
        } else {
            processBuilderFactory = new ProcessBuilderFactory(false);
        }
        processBuilderFactory.execCmdNoWait(new LogDealInf() {
            @Override
            public void logDeal(String logMsg) {
                String[] logArr = logMsg.split(" ", -1);
                AtomicBoolean a1 = new AtomicBoolean(true);
                AtomicBoolean a2 = new AtomicBoolean(true);
                AtomicBoolean a3 = new AtomicBoolean(true);
                AtomicBoolean a4 = new AtomicBoolean(true);
                AtomicBoolean a5 = new AtomicBoolean(true);
                String s1 = "";
                String s2 = "";
                String s3 = "";
                String s4 = "";
                String s5 = "";
                StringBuilder s6 = new StringBuilder();
                // 找到值
                for (String tmp : logArr) {
                    if (tmp.length() > 0) {
                        if (a1.getAndSet(false)) {
                            s1 = tmp;
                        } else if (a2.getAndSet(false)) {
                            s2 = tmp;
                        } else if (a3.getAndSet(false)) {
                            s3 = tmp;
                        } else if (a4.getAndSet(false)) {
                            s4 = tmp;
                        } else if (a5.getAndSet(false)) {
                            s5 = tmp;
                        } else {
                            s6.append(tmp).append(" ");
                        }
                    }
                }
                String result = String.format(format
                        , s3.replaceAll(":", ";")
                        , isWindow ? (s4.equals("→") ? "->" : "<-") : s4
                        , s5.replaceAll(":", ";")
                        , s6);
                // 暂时不考虑把分包去掉
                flow.append(result).append("\n");
            }
        }, "tshark", "-r", file_path);
        String result = String.format("Title: %s\n%s", new File(file_path).getName(), flow.toString());
        logger.info("getflow：{}", result);
        return result;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public String getByte(String file_path) {
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(new File(file_path))) {
            byte[] buff = new byte[fis.available()];
            int ret = fis.read(buff);
            sb.append(bytesToHex(buff));
            logger.info("getByte：{}", sb.toString());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return sb.toString();
    }

    public List<String> getInfo(String file_path) {
        List<StringBuilder> infoList = new ArrayList<>();
        ProcessBuilderFactory processBuilderFactory;
        if (isWindow) {
            processBuilderFactory = new ProcessBuilderFactory(false, "GBK");
        } else {
            processBuilderFactory = new ProcessBuilderFactory(false);
        }
        processBuilderFactory.execCmdNoWait(new LogDealInf() {
            @Override
            public void logDeal(String logMsg) {
                if (logMsg.startsWith("Frame")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(logMsg).append("<br>");
                    infoList.add(sb);
                } else {
                    if (infoList.size() > 0) {
                        StringBuilder sb = infoList.get(infoList.size() - 1);
                        sb.append(logMsg).append("<br>");
                    }
                }
            }
        }, "tshark", "-V", "-r", file_path);
        List<String> resultList = new ArrayList<>();
        for (StringBuilder sb : infoList) {
            resultList.add(sb.toString());
        }
        return resultList;
    }
}
