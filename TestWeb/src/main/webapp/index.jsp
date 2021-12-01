<%@ page contentType="text/html;charset=utf-8" %>
<%
    String APP_PATH = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
    <script language="javascript" type="text/JavaScript">
    </script>
    <style type="text/css">
        a {
            font-size: 48px;
        }
    </style>
</head>
<body>
<ul>
    <li><a href="<%=APP_PATH%>/video?index=1">视频集锦</a></li>
    <br>
    <li><a href="<%=APP_PATH%>/goto/comic/comic.do">动漫之家(月份)</a></li>
    <br>
    <li><a href="<%=APP_PATH%>/goto/comic/comic.do?comic_type=type">动漫之家(分类)</a></li>
</ul>
</body>
</html>
