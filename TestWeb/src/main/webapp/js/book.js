// 初始化加载
(function ($) {
    var bean = {
        tableName: "comic_book_img",
        columns: ["month_name", "book_name", "img_name"],
        month_name: month_name,
        book_name: book_name,
        order_by_column: "img_name"
    };
    $.ajax({
        url: curPath + "/comic/detail.do",
        type: "post",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bean),
        dataType: "json",
        success: function (data) {
            // 标题
            var ul_innerhtml = "<div>";
            if (data.length > 0) {
                ul_innerhtml = ul_innerhtml + "<img loading='lazy' src='"
                    + curPath + "/res/comic/kamuro/"
                    + data[0].MONTH_NAME + "/" + data[0].BOOK_NAME + "/"
                    + data[0].IMG_NAME + "' alt='" + book_desc + "'/>";
            }
            ul_innerhtml = ul_innerhtml + "<p>" + book_desc + "</p></div>";
            // 内容
            for (let index = 0; index < data.length; index++) {
                var month_name = data[index].MONTH_NAME;
                var book_name = data[index].BOOK_NAME;
                var img_name = data[index].IMG_NAME;
                ul_innerhtml = ul_innerhtml + "<img loading='lazy' src='"
                    + curPath + "/res/comic/kamuro/"
                    + month_name + "/" + book_name + "/"
                    + img_name + "' alt='图片" + index + "'/>";
            }
            $("#contentdiv").html(ul_innerhtml);
        },
        error: function (xmlHttpRequest, textStatus, error) {
            console.info("error……")
            console.info(xmlHttpRequest)
            console.info(textStatus)
            console.info(error)
        }
    });
})(jQuery);