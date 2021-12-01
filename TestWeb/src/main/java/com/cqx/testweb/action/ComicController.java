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
        sql.append("select count(1) as cnt from ").append(requestBean.getTableName()).append(" where 1=1 ");
        // where条件
        sqlWhereBuilder(requestBean, sql);
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
        sql.append(" from ").append(requestBean.getTableName()).append(" where 1=1 ");
        // where条件
        sqlWhereBuilder(requestBean, sql);
        // gorup by聚合
        sqlGroupByBuilder(requestBean, sql);
        // order by排序
        sqlOrderByBuilder(requestBean, sql);
        // 分页
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
        sql.append(" from ").append(requestBean.getTableName()).append(" where 1=1 ");
        // where条件
        sqlWhereBuilder(requestBean, sql);
        // order by排序
        sqlOrderByBuilder(requestBean, sql);
        logger.debug("queryDetailsList：{}", sql);
        return query(sql.toString());
    }

    /**
     * 拼接查询字段
     *
     * @param requestBean
     * @return
     */
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

    /**
     * 拼接where条件
     *
     * @param requestBean
     * @param sql
     */
    private void sqlWhereBuilder(ComicPageRequestBean requestBean, StringBuilder sql) {
        // where条件
        if (requestBean.getMonth_name() != null && requestBean.getMonth_name().length() > 0) {
            sql.append(" and month_name='")
                    .append(requestBean.getMonth_name())
                    .append("' ");
        }
        if (requestBean.getBook_name() != null && requestBean.getBook_name().length() > 0) {
            sql.append(" and book_name='")
                    .append(requestBean.getBook_name())
                    .append("' ");
        }
        if (requestBean.getBook_type_name() != null && requestBean.getBook_type_name().length() > 0) {
            sql.append(" and book_type_name='")
                    .append(requestBean.getBook_type_name())
                    .append("' ");
        }
    }

    /**
     * 拼接排序条件
     *
     * @param requestBean
     * @param sql
     */
    private void sqlOrderByBuilder(ComicPageRequestBean requestBean, StringBuilder sql) {
        // order by排序
        if (requestBean.getOrder_by_column() != null && requestBean.getOrder_by_column().length() > 0) {
            sql.append(" order by ");
            if (requestBean.isOrder_is_cnt()) {
                sql.append("count(1) desc,");
            }
            if (!requestBean.isOrder_is_length()) {
                sql.append("length(").append(requestBean.getOrder_by_column()).append("),");
            }
            sql.append(requestBean.getOrder_by_column());
        }
    }

    /**
     * 拼接聚合条件
     *
     * @param requestBean
     * @param sql
     */
    private void sqlGroupByBuilder(ComicPageRequestBean requestBean, StringBuilder sql) {
        // group by
        if (requestBean.getGroup_by_column() != null && requestBean.getGroup_by_column().length() > 0) {
            sql.append(" group by ").append(requestBean.getGroup_by_column());
        }
    }

    /**
     * 查询
     *
     * @param sql
     * @return
     */
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
