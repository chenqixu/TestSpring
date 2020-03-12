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
    <button onclick="submit()">submit</button>
</form>
<button id="btn" name="btn">ajax_click</button>
<button id="addOperHistory" name="addOperHistory">addOperHistory</button>
<button id="session" name="session">session</button>
<script src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript">
    var path = "<%=path%>";
    /* init */
    $(function () {
        $("#btn").on('click', function () {
            // $.post("mvc/doCBAuth.do", {reqBean: $("#reqBean").val()}, function (data) {
            //     alert(data);
            // });
            $.post(path + "/test/login.do", {reqBean: $("#reqBean").val()}, function (data) {
                alert(data);
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
</script>
</body>
</html>
