package com.cqx.testweb.servlet;

import com.cqx.common.utils.file.FileManager;
import com.cqx.common.utils.file.FileUtil;
import com.cqx.testweb.bean.VideoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * VideoServlet
 *
 * @author chenqixu
 */
public class VideoServlet extends SpringSupportServlet {
    private static final Logger logger = LoggerFactory.getLogger(VideoServlet.class);
    private static List<String> endWithList = new ArrayList<>();
    private static final String FS = File.separator;
    private LinkedHashMap<String, LinkedHashMap<String, VideoBean>> videoMap = new LinkedHashMap<>();
    private LinkedHashMap<String, AtomicInteger> indexMap = new LinkedHashMap<>();
    private String RealPath;
    private String ContextPath;

    static {
        endWithList.add("mp4");
        endWithList.add("avi");
        endWithList.add("wmv");
        endWithList.add("rmvb");
    }

    public VideoServlet() {
    }

    public VideoServlet(String RealPath) {
        this.RealPath = RealPath;
    }

    private void addVideo(String dir, VideoBean videoBean) {
        LinkedHashMap<String, VideoBean> _map = videoMap.get(dir);
        if (_map == null) {
            _map = new LinkedHashMap<>();
            videoMap.put(dir, _map);
            AtomicInteger _atomicInteger = new AtomicInteger(1);
            indexMap.put(dir, _atomicInteger);
        }
        _map.put(indexMap.get(dir).getAndIncrement() + "", videoBean);
    }

    /**
     * 初始化
     */
    @Override
    public void prepare() {
        if (ContextPath == null || ContextPath.length() == 0) {
            ContextPath = this.getServletContext().getContextPath();
        }
        if (RealPath == null || RealPath.length() == 0) {
            RealPath = FileUtil.endWith(this.getServletContext().getRealPath(""));
        }
        logger.info("============ContextPath：{}", ContextPath);
        logger.info("============RealPath：{}", RealPath);
        //扫描res/video下的目录
        for (String file : FileUtil.listFile(RealPath + "res" + FS + "video" + FS)) {
            String path = "res" + FS + "video" + FS + file + FS;
            logger.info("====扫描到res{}video下的目录：{}", FS, path);
            scan(file, RealPath, RealPath + path);
        }
    }

    private void scan(String dir, String APP_PATH, String _path) {
        for (String file : FileUtil.listFile(_path)) {
            logger.info("file: {}, isFile: {}, isDirectory: {}"
                    , file, FileUtil.isFile(_path + file), FileUtil.isDirectory(_path + file));
            if (FileUtil.isDirectory(_path + file)) {
                scan(dir, APP_PATH, FileUtil.endWith(_path + file));
            } else {
                String endWith = file.substring(file.lastIndexOf(".") + 1);
                for (String _endWith : endWithList) {
                    if (endWith.contains(_endWith)) {
                        VideoBean videoBean = new VideoBean("", file, _path, endWith, APP_PATH);
                        //尝试读取对应的txt文件
                        String content = FileManager.readFileHeader(videoBean.getRealAllName(), "GBK");
                        videoBean.setContent(content);
                        addVideo(dir, videoBean);
                        logger.info("=扫描到：{}，内容为：{}", videoBean, content);
                        break;
                    }
                }
            }
        }
    }

    public VideoBean getVideoBean(String dir, String index) {
        return videoMap.get(dir).get(index);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String APP_PATH = request.getContextPath();
        String dir = request.getParameter("dir");
        String index = request.getParameter("index");
        LinkedHashMap<String, VideoBean> _videoBeanMap = videoMap.get(dir);
        logger.info("============APP_PATH：{}，dir：{}，index : {}，_videoBeanMap.size：{}"
                , APP_PATH, dir, index, _videoBeanMap != null ? _videoBeanMap.size() : -1);
        if (_videoBeanMap == null) {
            _videoBeanMap = new LinkedHashMap<>();
        }
        if (index == null || index.trim().length() == 0) {
            index = "1";
        }
        response.addHeader("P3P", "CP=CAO PSA OUR");//iframe框架下session丢失的问题
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write("<!DOCTYPE html>\n" +
                "<html lang=\"zh-CN\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->\n" +
                "    <title>video" + index + "</title>\n" +
                "    <!-- Bootstrap -->\n" +
                "    <link href=\"" + APP_PATH + "/css/bootstrap-3.3.7/css/bootstrap.css\" rel=\"stylesheet\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container-fluid\">\n" +
                "    <div class=\"row-fluid\">\n" +
                "        <div class=\"span12\">\n" +
                "            <div class=\"row-fluid\">\n" +
                "                <div class=\"span4\">\n" +
                "                    <a href=\"" + APP_PATH + "\\index.jsp\">首页</a>\n" +
                "                </div>\n" +
                "                <div class=\"span4\">\n" +
                "                    <p align=\"center\">" + (_videoBeanMap.size() > 0 ? _videoBeanMap.get(index).getContent() : "404") + "</p><br>" +
                "                    <video id=\"video\" controls preload=\"auto\" width=\"350px\" height=\"300px\">\n" +
                "                        <source src=\"" + APP_PATH + "\\" + (_videoBeanMap.size() > 0 ? _videoBeanMap.get(index).getAllName() : "404") + "\" type=\"video/mp4\">\n" +
                "                    </video>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"pagination\">\n" +
                "                <ul>\n");
        for (int i = 1; i <= _videoBeanMap.size() + 1; i++) {
            writer.write("<li>\n" +
                    "                        <a href=\"" + APP_PATH + "/video?dir=" + dir + "&index=" + i + "\">" + i + "\n" +
                    _videoBeanMap.get(i + "").getContent() +
                    "                        </a>\n" +
                    "                    </li>");
        }
        writer.write("                </ul>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->\n" +
                "<script src=\"" + APP_PATH + "/js/jquery.min.js\"></script>\n" +
                "<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->\n" +
                "<script src=\"" + APP_PATH + "/css/bootstrap-3.3.7/js/bootstrap.min.js\"></script>\n" +
                "<!-- 加载image.js -->\n" +
                "<script src=\"" + APP_PATH + "/js/image.js\"></script>\n" +
                "<script type=\"text/javascript\">\n" +
                "    var curPath = '" + APP_PATH + "';\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>");
        writer.close();
    }
}
