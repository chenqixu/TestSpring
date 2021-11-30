// 分页对象
var plist = new Page("listpagediv", "plist");

// 初始化加载
(function ($) {
    // 查询总记录数
    var bean = {
        tableName: "comic_month",
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
    var bean = {
        page: start_page,
        pageNum: "15",
        tableName: "comic_month",
        columns: ["month_name"]
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
                var month_name = data[index].MONTH_NAME;
                ul_innerhtml = ul_innerhtml + "<li><a href='"
                    + curPath + "/goto/comic/month.do?month_name="
                    + month_name + "'><b><img alt='Null' src='images/tb01.png'/></b><span>"
                    + month_name + "</span></a></li>";
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
