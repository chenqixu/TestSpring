<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

//    GreetingClient gc = new GreetingClient();
//    gc.client();
//    Object obj = session.getAttribute("user_name");
//    System.out.println("[session.obj]" + obj);
    String user_name = (String) request.getSession().getAttribute("data");
//    String user_id = (String) request.getSession().getAttribute("user_id");
    System.out.println(user_name);
//    System.out.println(user_id);

    /**
     * 会话标识未更新--缺陷处理
     */
    request.getSession().invalidate();//清空session
    Cookie cookie = request.getCookies()[0];//获取cookie
    cookie.setMaxAge(0);//让cookie过期
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>My JSP 'index.jsp' starting page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

</head>

<body>
This is my JSP page. <br>
<form method="post" action="<%=path%>/mvc/doCBAuth.do">
    <input id="reqBean" name="reqBean" value="test..."/>
    <div>username<input id="username" name="username" value="admin"/></div>
    <div>password<input id="password" name="password" value="123456"/></div>
    <div>
        <button onclick="submit()">submit</button>
    </div>
</form>
<button id="btn" name="btn">login</button>
<button id="addOperHistory" name="addOperHistory">addOperHistory</button>
<button id="session" name="session">session</button>
<div>uuid<input id="uuid" name="uuid" style="width: 500px"/></div>
<script src="<%=path%>/js/jquery.min.js"></script>
<script src="<%=path%>/js/md5.js"></script>
<script src="<%=path%>/js/aes.js"></script>
<script src="<%=path%>/js/core-min.js"></script>
<script src="<%=path%>/js/mode-ecb-min.js"></script>
<script type="text/javascript">
    var path = "<%=path%>";
    /* init */
    $(function () {
        $("#btn").on('click', function () {
            var user = {"username": $("#username").val(), "password": $("#password").val()};
            var bean = {"content": AES_encrypt(JSON.stringify(user))};
            // $.post("mvc/doCBAuth.do", {reqBean: $("#reqBean").val()}, function (data) {
            //     alert(data);
            // });
            // $.post(path + "/test/login.do", {reqBean: $("#reqBean").val()}, function (data) {
            //     alert(data);
            // });
            $.ajax({
                url: path + "/test/login.do",
                type: "post",
                async: false,
                data: bean,
                dataType: "json",
                success: function (data) {
                    //解密
                    var d = AES_Decrypt(data.content);
                    console.info(d)
                    var c = JSON.parse(d);
                    $("#uuid").val(c.id)
                },
                error: function (data) {
                    alert(data)
                }
            });
        });

        $("#addOperHistory").on('click', function () {
            var bean = {"name": "10001", "passwd": "abc123"};
            // $.post("goto/addOperHistory.do", bean, function (data) {
            //     alert(data);
            // });
            $.ajax({
                url: path + "/goto/addOperHistory.do",
                type: "post",
                async: false,
                data: bean,
                dataType: "json",
                success: function (data) {
                    alert("success");
                    console.info(data)
                },
                error: function (data) {
                    alert("error");
                    console.info(data)
                }
            });
        });

        $("#session").on('click', function () {
            $.post(path + "/session", {reqBean: ""}, function (data) {
            });
        });

    });

    /**
     * @return {string}
     */
    function AES_encrypt(str) {
        //aes加密
        var key = CryptoJS.enc.Utf8.parse("newland_computer");
        var srcs = CryptoJS.enc.Utf8.parse(str);
        return CryptoJS.AES.encrypt(srcs, key, {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        }).toString();
    }

    //解密方法
    /**
     * @return {string}
     */
    function AES_Decrypt(str) {
        var key = CryptoJS.enc.Utf8.parse("newland_computer");
        var decrypt = CryptoJS.AES.decrypt(str, key, {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });
        return decrypt.toString(CryptoJS.enc.Utf8);
    }
</script>
</body>
</html>
