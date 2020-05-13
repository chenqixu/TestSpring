package com.cqx.syncos.util;

/**
 * TimeCostUtil
 *
 * @author chenqixu
 */
public class TimeCostUtil {
    long start;
    long end;
    long lastCheckTime = System.currentTimeMillis();

    public void intLastCheckTime() {
        lastCheckTime = System.currentTimeMillis();
    }

    public void start() {
        start = System.currentTimeMillis();
    }

    public void end() {
        end = System.currentTimeMillis();
    }

    /**
     * 间隔多久触发一次
     *
     * @param limitTime
     * @return
     */
    public boolean tag(long limitTime) {
        if (System.currentTimeMillis() - lastCheckTime > limitTime) {
            lastCheckTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    /**
     * 间隔多久触发一次
     *
     * @param at_time
     * @param interval
     * @return
     */
    public boolean tag(long at_time, long interval) {
        return System.currentTimeMillis() >= (at_time + interval);
    }

    /**
     * 花费的时间
     *
     * @return
     */
    public long getCost() {
        return end - start;
    }
}
