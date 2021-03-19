package com.cqx.testweb.bean;

/**
 * VideoBean
 *
 * @author chenqixu
 */
public class VideoBean {
    private String index;
    private String name;
    private String path;
    private String endWith;
    private String APP_PATH;
    private String content;

    public VideoBean(String index, String name, String path, String endWith, String APP_PATH) {
        this.index = index;
        this.name = name;
        this.path = path;
        this.endWith = endWith;
        this.APP_PATH = APP_PATH;
    }

    public String getAllName() {
        return path + name;
    }

    public String getRealAllName() {
        return APP_PATH + path + singleFileName() + ".txt";
    }

    public String singleFileName() {
        return name.replace("." + endWith, "");
    }

    public String toString() {
        return "index：" + index + "，name：" + name + "，path：" + path;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getEndWith() {
        return endWith;
    }

    public void setEndWith(String endWith) {
        this.endWith = endWith;
    }

    public String getAPP_PATH() {
        return APP_PATH;
    }

    public void setAPP_PATH(String APP_PATH) {
        this.APP_PATH = APP_PATH;
    }

    public String getContent() {
        return content == null ? singleFileName() : content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
