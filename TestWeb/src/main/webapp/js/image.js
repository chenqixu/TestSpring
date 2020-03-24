/**
 * 上一页
 */
function previous_page() {
    var bean = {
        id: "123"
    };
    $.ajax({
        url: curPath + "/v1/t1.do",
        type: "post",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bean),
        dataType: "json",
        success: function (data) {
            console.info("success……")
            console.info(data)
        },
        error : function(xmlHttpRequest, textStatus, error) {
            console.info("error……")
            console.info(xmlHttpRequest)
            console.info(textStatus)
            console.info(error)
        }
    });
}

//初始化加载
(function ($) {
    //设置ajax请求完成后运行的函数
    $.ajaxSetup({
        //设置ajax请求结束后的执行动作
        complete:
        // error :
            function (XMLHttpRequest, textStatus) {
                // 通过XMLHttpRequest取得响应头，error_code
                var error_code = XMLHttpRequest.getResponseHeader("error_code");
                if (error_code == "999") {
                    var win = window;
                    while (win != win.top) {
                        win = win.top;
                    }
                    win.location.href = curPath + "/goto/transfer.do";
                }
            }
    });
})(jQuery);