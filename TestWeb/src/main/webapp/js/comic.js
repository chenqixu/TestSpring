// 分页对象
var plist = new Page("listpagediv", "plist");

// 初始化加载
(function ($) {
    // 查询总记录数
    var tableName;
    if (COMIC_TYPE == "type") {
        tableName = "comic_type";
    } else {
        tableName = "comic_month";
    }
    var bean = {
        tableName: tableName,
        columns: ["cnt"]
    };
    $.ajax({
        url: curPath + "/comic/total.do",
        type: "post",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bean),
        dataType: "json",
        success: function (data) {
            // 初始化分页
            // startCount,pageNum,totalCount,event
            plist.init(1, 15, data[0].CNT, "queryList");
        }
    });
    // 查询第一页
    queryList(1);
})(jQuery);

// 查询
function queryList(start_page) {
    var column;
    var columns;
    var order_by_column;
    var action;
    var _order_is_length = false;
    var _order_is_cnt = false;
    if (COMIC_TYPE == "type") {
        column = "book_type_name";
        action = "type";
        _order_is_length = true;
        _order_is_cnt = true;
    } else {
        column = "month_name";
        action = "month";
    }
    order_by_column = column;
    columns = [column, "count(1) as CNT"];
    var bean = {
        page: start_page,
        pageNum: "15",
        tableName: "comic_book",
        columns: columns,
        order_by_column: order_by_column,
        group_by_column: order_by_column,
        order_is_length: _order_is_length,
        order_is_cnt: _order_is_cnt
    };
    $.ajax({
        url: curPath + "/comic/page.do",
        type: "post",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bean),
        dataType: "json",
        success: function (data) {
            var ul_innerhtml = "";
            for (let index = 0; index < data.length; index++) {
                var query_name = data[index].MONTH_NAME;
                var cnt = data[index].CNT;
                if (COMIC_TYPE == "type") {
                    query_name = data[index].BOOK_TYPE_NAME;
                } else {
                    query_name = data[index].MONTH_NAME;
                }
                ul_innerhtml = ul_innerhtml + "<li><a href='"
                    + curPath + "/goto/comic/" + action + ".do?" + column + "="
                    + query_name + "'><b>" + cnt + "</b><span>"
                    + query_name + "</span></a></li>";
            }
            $("#mainmenu_ul").html(ul_innerhtml);
        },
        error: function (xmlHttpRequest, textStatus, error) {
            console.info("error……")
            console.info(xmlHttpRequest)
            console.info(textStatus)
            console.info(error)
        }
    });
}
