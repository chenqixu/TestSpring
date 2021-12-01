package com.cqx.testweb.bean;

import java.util.List;

/**
 * ComicPageRequestBean
 *
 * @author chenqixu
 */
public class ComicPageRequestBean {
    private int page;
    private int pageNum;
    private String tableName;
    private List<String> columns;
    private String book_type_name;
    private String month_name;
    private String book_name;
    private String order_by_column;
    private String group_by_column;
    private boolean order_is_length;// 处理1,11,2排序问题，默认false为处理
    private boolean order_is_cnt;// 默认false为不带count(1)，true则order by中需要带上count(1)

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public String getMonth_name() {
        return month_name;
    }

    public void setMonth_name(String month_name) {
        this.month_name = month_name;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getOrder_by_column() {
        return order_by_column;
    }

    public void setOrder_by_column(String order_by_column) {
        this.order_by_column = order_by_column;
    }

    public String getBook_type_name() {
        return book_type_name;
    }

    public void setBook_type_name(String book_type_name) {
        this.book_type_name = book_type_name;
    }

    public String getGroup_by_column() {
        return group_by_column;
    }

    public void setGroup_by_column(String group_by_column) {
        this.group_by_column = group_by_column;
    }

    public boolean isOrder_is_length() {
        return order_is_length;
    }

    public void setOrder_is_length(boolean order_is_length) {
        this.order_is_length = order_is_length;
    }

    public boolean isOrder_is_cnt() {
        return order_is_cnt;
    }

    public void setOrder_is_cnt(boolean order_is_cnt) {
        this.order_is_cnt = order_is_cnt;
    }
}
