package com.cqx.syncos.util.file;

import com.cqx.syncos.task.bean.TaskInfo;
import com.cqx.syncos.util.file.FileUtil;
import org.junit.Test;

public class FileUtilTest {

    @Test
    public void saveConfToFile() {
        String path = "D:\\Document\\Workspaces\\Git\\TestSpring\\SyncOS\\data\\1.data";
        String data = "1234";
        FileUtil.saveConfToFile(path, data);
    }

    @Test
    public void readConfFile() {
        String path = "D:\\Document\\Workspaces\\Git\\TestSpring\\SyncOS\\data\\sync_os_test1\\sync_os_test1.cache";
        TaskInfo taskInfo = FileUtil.readConfFile(path, TaskInfo.class);
        if (taskInfo != null) System.out.println(taskInfo.getTask_name());
    }
}