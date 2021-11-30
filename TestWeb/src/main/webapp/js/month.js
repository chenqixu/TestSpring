// 分页对象
var plist = new Page("listpagediv", "plist");

// 初始化加载
(function ($) {
    // 查询总记录数
    var bean = {
        tableName: "comic_book",
        columns: ["cnt"],
        month_name: month_name
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
        tableName: "comic_book",
        columns: ["month_name", "book_name", "book_desc", "title_img_name"],
        month_name: month_name
    };
    $.ajax({
        url: curPath + "/comic/page.do",
        type: "post",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bean),
        dataType: "json",
        success: function (data) {
            var maindiv_innerhtml = "";
            for (let index = 0; index < data.length; index++) {
                var month_name = data[index].MONTH_NAME;
                var book_name = data[index].BOOK_NAME;
                var book_desc = data[index].BOOK_DESC;
                var title_img_name = data[index].TITLE_IMG_NAME;
                maindiv_innerhtml = maindiv_innerhtml + "<a href='"
                    + curPath + "/goto/comic/book.do?month_name="
                    + month_name + "&book_name="
                    + book_name + "&book_desc="
                    + book_desc + "' title='" + book_name + "'>"
                    + "<img class='left_item item_img' width='640' height='640' src='"
                    + curPath + "/res/comic/kamuro/"
                    + month_name + "/" + book_name + "/"
                    + title_img_name + "' alt='" + book_name + "' loading='lazy' "
                    + "sizes='(max-width: 640px) 100vw, 640px'/><span class='right_item'>" + book_desc + "</span></a>";
                    // + "<li><a href='"
                    // + curPath + "/goto/comic/book.do?month_name="
                    // + month_name + "&book_name="
                    // + book_name + "&book_desc="
                    // + book_desc + "'><b><img alt='"
                    // + book_desc + "' src='"
                    // + curPath + "/res/comic/kamuro/"
                    // + month_name + "/" + book_name + "/"
                    // + title_img_name + "'/></b><span>"
                    // + book_name + "</span></a></li>";
            }
            $("#maindiv").html(maindiv_innerhtml);
        },
        error: function (xmlHttpRequest, textStatus, error) {
            console.info("error……")
            console.info(xmlHttpRequest)
            console.info(textStatus)
            console.info(error)
        }
    });
}