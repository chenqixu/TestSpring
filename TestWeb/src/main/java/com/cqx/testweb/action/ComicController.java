package com.cqx.testweb.action;

import com.alibaba.fastjson.JSON;
import com.cqx.testweb.bean.ComicPageRequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ComicController
 *
 * @author chenqixu
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "/comic")
public class ComicController {
    private static final Logger logger = LoggerFactory.getLogger(ComicController.class);

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/total"
            , method = {RequestMethod.POST}
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String queryTotalCnt(@RequestBody ComicPageRequestBean requestBean) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(1) as cnt from ").append(requestBean.getTableName());
        if (requestBean.getMonth_name() != null && requestBean.getMonth_name().length() > 0) {
            sql.append(" where month_name='")
                    .append(requestBean.getMonth_name())
                    .append("'");
        }
        logger.debug("queryTotalCnt：{}", sql);
        return query(sql.toString());
    }

    @RequestMapping(value = "/page"
            , method = {RequestMethod.POST}
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String queryBeanList(@RequestBody ComicPageRequestBean requestBean) {
        // 计算出startCnt
        int page = requestBean.getPage();
        int pageNum = requestBean.getPageNum();
        int startCnt = (page - 1) * pageNum;
        StringBuilder sql = new StringBuilder(sqlBuilder(requestBean));
        sql.append(" from ")
                .append(requestBean.getTableName());
        if (requestBean.getMonth_name() != null && requestBean.getMonth_name().length() > 0) {
            sql.append(" where month_name='")
                    .append(requestBean.getMonth_name())
                    .append("' ");
        }
        sql.append(" offset ")
                .append(startCnt)
                .append(" rows fetch next ")
                .append(pageNum)
                .append(" rows only")
        ;
        logger.debug("queryBeanList：{}", sql);
        return query(sql.toString());
    }

    @RequestMapping(value = "/detail"
            , method = {RequestMethod.POST}
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String queryDetailsList(@RequestBody ComicPageRequestBean requestBean) {
        StringBuilder sql = new StringBuilder(sqlBuilder(requestBean));
        sql.append(" from ")
                .append(requestBean.getTableName())
                .append(" where month_name='")
                .append(requestBean.getMonth_name())
                .append("' and book_name='")
                .append(requestBean.getBook_name())
                .append("' ")
        ;
        logger.debug("queryDetailsList：{}", sql);
        return query(sql.toString());
    }

    private String sqlBuilder(ComicPageRequestBean requestBean) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        for (int i = 0; i < requestBean.getColumns().size(); i++) {
            sql.append(requestBean.getColumns().get(i));
            if ((i + 1) < requestBean.getColumns().size()) {
                sql.append(",");
            }
        }
        return sql.toString();
    }

    private String query(String sql) {
        List<Map<String, String>> mapList = jdbcTemplate.query(sql, new RowMapper<Map<String, String>>() {
            @Override
            public Map<String, String> mapRow(ResultSet resultSet, int i) throws SQLException {
                Map<String, String> map = new HashMap<>();
                for (int row = 1; row <= resultSet.getMetaData().getColumnCount(); row++) {
                    String columnName = resultSet.getMetaData().getColumnName(row);
                    String value = resultSet.getString(row);
                    map.put(columnName, value);
                }
                return map;
            }
        });
        return JSON.toJSONString(mapList);
    }
}
