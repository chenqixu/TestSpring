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

/**
 * VideoServlet
 *
 * @author chenqixu
 */
public class VideoServlet extends SpringSupportServlet {
    private static final Logger logger = LoggerFactory.getLogger(VideoServlet.class);
    private static List<String> endWithList = new ArrayList<>();
    private LinkedHashMap<String, VideoBean> videoMap = new LinkedHashMap<>();
    private static final String FS = File.separator;

    static {
        endWithList.add("mp4");
        endWithList.add("avi");
        endWithList.add("wmv");
    }

    /**
     * 初始化
     */
    @Override
    public void prepare() {
        String APP_PATH = this.getServletContext().getRealPath(FS);
        logger.info("============APP_PATH：{}", APP_PATH);
        int i = 1;
        //扫描res/video下的目录
        for (String file : FileUtil.listFile(APP_PATH + "res" + FS + "video" + FS)) {
            String path = "res" + FS + "video" + FS + file + FS;
            logger.info("====扫描到res{}video下的目录：{}", FS, path);
            //根据文件后缀，扫描res/video下目录的目录
            for (String endWith : endWithList) {
                for (String video : FileUtil.listFileEndWith(APP_PATH + path, endWith)) {
                    VideoBean videoBean = new VideoBean(i + "", video, path, endWith, APP_PATH);
                    videoMap.put(i + "", videoBean);
                    //尝试读取对应的txt文件
                    String content = FileManager.readFileHeader(videoBean.getRealAllName(), "GBK");
                    videoBean.setContent(content);
                    logger.info("=扫描到：{}，内容为：{}", videoMap.get(i + ""), content);
                    i++;
                }
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String APP_PATH = request.getContextPath();
        String index = request.getParameter("index");
        logger.info("============index : {}", index);
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
                "                    <p align=\"center\">" + videoMap.get(index).getContent() + "</p><br>" +
                "                    <video id=\"video\" controls preload=\"auto\" width=\"350px\" height=\"300px\">\n" +
                "                        <source src=\"" + APP_PATH + "\\" + videoMap.get(index).getAllName() + "\" type=\"video/mp4\">\n" +
                "                    </video>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"pagination\">\n" +
                "                <ul>\n");
        for (int i = 1; i < videoMap.size() + 1; i++) {
            writer.write("<li>\n" +
                    "                        <a href=\"" + APP_PATH + "/video?index=" + i + "\">" + i + "\n" +
                    videoMap.get(i + "").getContent() +
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
