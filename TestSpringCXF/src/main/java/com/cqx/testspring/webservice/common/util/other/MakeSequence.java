package com.cqx.testspring.webservice.common.util.other;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * MakeSequence
 *
 * @author chenqixu
 */
public class MakeSequence {
    private static int i = 10000;
    private static String ip = "";
    static MakeSequence instance = null;

    public MakeSequence() {
        try {
            ip = InetAddress.getLocalHost().toString().replaceAll(".", "");
        } catch (UnknownHostException var2) {
            ip = "";
        }

    }

    public static synchronized MakeSequence Instance() {
        if (instance == null && instance == null) {
            instance = new MakeSequence();
        }

        return instance;
    }

    public String getSequence() {
        ++i;
        if (i == 100000) {
            i = 10001;
        }

        Date date = new Date();
        String restr = ip + StringText.getStr(date, "yyyyMMddHHmmss") + StringText.getStr((long) i);
        return restr;
    }
}
