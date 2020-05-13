package com.cqx.syncos.util.file;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * FileUtil
 *
 * @author chenqixu
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private static final String fileSparator = File.separator;

    /**
     * 如果文件夹不以\结尾则加上
     *
     * @param path
     * @return
     */
    public static String endWith(String path) {
        if (path.endsWith(fileSparator)) return path;
        else return path + fileSparator;
    }

    /**
     * 创建路径，可以递归
     *
     * @param path
     */
    public static void mkDir(String path) {
        if (path != null && path.length() > 0) {
            File file = new File(path);
            if (!(file.exists() && file.isDirectory())) {
                file.mkdirs();
            }
        }
    }

    /**
     * 判断文件、文件夹是否存在
     *
     * @param path
     * @return
     */
    public static boolean isExists(String path) {
        return new File(path).exists();
    }

    /**
     * 保存配置数据到文件
     *
     * @param path
     * @param data
     */
    public static void saveConfToFile(String path, String data) {
        File writeFile = new File(path);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeFile), StandardCharsets.UTF_8))) {
            writer.write(data);
        } catch (IOException e) {
            logger.error("Save " + data + " To " + path + " IOException. Message:" + e.getMessage());
        }
    }

    /**
     * 保存配置对象到文件
     *
     * @param path
     * @param object
     */
    public static void saveConfToFile(String path, Object object) {
        String data = JSON.toJSONString(object);
        saveConfToFile(path, data);
    }

    /**
     * 保存配置对象到文件--线程
     *
     * @param path
     * @param object
     */
    public static void saveConfToFileByThread(String path, Object object) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                saveConfToFile(path, object);
            }
        }).start();
    }

    /**
     * 从文件读取配置
     *
     * @param path
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T readConfFile(String path, Class<T> cls) {
        String value = readConfFile(path);
        if (value != null) {
            return JSON.parseObject(value, cls);
        } else {
            return null;
        }
    }

    public static String readConfFile(String path) {
        File readFile = new File(path);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(readFile), StandardCharsets.UTF_8))) {
            String _tmp;
            while ((_tmp = reader.readLine()) != null) {
                sb.append(_tmp);
            }
        } catch (IOException e) {
            logger.error("Read " + path + " IOException. Message:" + e.getMessage());
        }
        if (sb.length() > 0) {
            return sb.toString();
        } else {
            return null;
        }
    }

    public static String[] listFile(String path) {
        return listFile(path, null);
    }

    public static String[] listFile(String path, final String keyword) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            if (keyword != null && keyword.length() > 0) {
                logger.info("listFile use keyword：{}.", keyword);
                return file.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.contains(keyword);
                    }
                });
            } else {
                logger.info("listFile not use keyword.");
                return file.list();
            }
        } else {
            logger.warn("path：{}，file not exists：{} or file is not Directory：{}", path, file.exists(), file.isDirectory());
        }
        return new String[0];
    }

    public static String[] listFileEndWith(String path, final String endWith) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            return file.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(endWith);
                }
            });
        } else {
            logger.warn("path：{}，file not exists：{} or file is not Directory：{}", path, file.exists(), file.isDirectory());
        }
        return new String[0];
    }

    /**
     * 通过关键字和文件后缀来过滤文件
     *
     * @param path
     * @param keyword
     * @param endWith
     * @return
     */
    public static String[] listFile(String path, final String keyword, final String endWith) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            if (keyword != null && keyword.length() > 0) {
                logger.info("listFile use keyword：{}，endWith：{}.", keyword, endWith);
                return file.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.contains(keyword) && name.endsWith(endWith);
                    }
                });
            } else {
                logger.info("listFile not use keyword.");
                return file.list();
            }
        } else {
            logger.warn("path：{}，file not exists：{} or file is not Directory：{}", path, file.exists(), file.isDirectory());
        }
        return new String[0];
    }
}
