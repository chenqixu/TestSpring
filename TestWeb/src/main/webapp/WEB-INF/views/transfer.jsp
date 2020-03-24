<%--
  Created by IntelliJ IDEA.
  User: chenqixu
  Date: 2020/3/23
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String APP_PATH = request.getContextPath();
    response.setHeader("sessionstatus", "timeout");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
    <script language="javascript" type="text/JavaScript">
        function goLogout() {
            alert("您已长时间未操作，系统需重新认证，请重新登陆！");
            top.window.location = "<%=APP_PATH%>/goto/logout.do";
        }
    </script>
    <title>transfer</title>
</head>
<body onload="goLogout();"></body>
</html>
