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
    <li><a href="<%=APP_PATH%>/video?dir=normal&index=1">视频集锦</a></li>
    <br>
    <li><a href="<%=APP_PATH%>/video?dir=pornhub&index=1">PornHub视频</a></li>
    <br>
    <li><a href="<%=APP_PATH%>/video?dir=comic&index=1">动漫视频</a></li>
    <br>
    <li><a href="<%=APP_PATH%>/video?dir=film&index=1">电影集锦</a></li>
    <br>
    <li><a href="<%=APP_PATH%>/goto/comic/comic.do">漫画之家(月份)</a></li>
    <br>
    <li><a href="<%=APP_PATH%>/goto/comic/comic.do?comic_type=type">漫画之家(分类)</a></li>
</ul>
</body>
</html>
