<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String APP_PATH = request.getContextPath();
    String month_name = request.getParameter("month_name");
%>
<!DOCTYPE html>
<html>
<head>
    <title>动漫之家-<%=month_name%>月份</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport"
          content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta charset="utf-8"/>
    <link href="<%=APP_PATH%>/css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">
        html, body {
            color: #222;
            font-family: Microsoft YaHei, Helvitica, Verdana, Tohoma, Arial, san-serif;
            margin: 0;
            padding: 0;
            text-decoration: none;
        }

        ul {
            list-style: none outside none;
            margin: 0;
            padding: 0;
        }

        body {
            background-color: #eee;
        }

        body .left_item {
            width: 50%;
            height: 100%;
            display: inline-block;
            box-sizing: border-box;
            vertical-align: middle;
        }

        body .right_item {
            width: 45%;
            height: 100%;
            color: #999;
            background-color: #FFF;
            font-size: 12px;
            font-weight: bold;
            display: inline-block;
            box-sizing: border-box;
            vertical-align: top;
        }

        body .item_img {
            width: 50%;
            height: 100%;
            border: 5px solid black;
        }

        body .mydiv {
            width: 100%;
        }

        body .headdiv {
            text-align: center;
            height: 30px;
        }

        body .splitpagediv {
            position: relative;
            display: inline-block;
            text-align: center;
            height: 30px;
        }

        body .splitpagediv ul {
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
        }

        body .splitpagediv ul li {
            float: left;
            min-width: 20px;
        }

        body .enddiv {
            text-align: center;
            height: 20px;
        }
    </style>
</head>
<body>
<!-- 表头 -->
<div class="mydiv headdiv">
    <a href="<%=APP_PATH%>/index.jsp">首页</a>
    &nbsp;&nbsp;&nbsp;
    <a href="<%=APP_PATH%>/goto/comic/comic.do">动漫之家</a>
    ->
    <a href="<%=APP_PATH%>/goto/comic/month.do?month_name=<%=month_name%>"><%=month_name%>
    </a>
</div>
<!-- 内容 -->
<div id="maindiv" class="mydiv">
    <a href="<%=APP_PATH%>/goto/comic/book.do" title="book1">
        <img class="left_item item_img" width="640" height="640" src="images/tb01.png" alt="book1" loading="lazy"
             sizes="(max-width: 640px) 100vw, 640px"/>
        <span class="right_item">book1</span>
    </a>
    <a href="<%=APP_PATH%>/goto/comic/book.do" title="book2">
        <img class="left_item item_img" width="640" height="640" src="images/tb02.png" alt="book2" loading="lazy"
             sizes="(max-width: 640px) 100vw, 640px"/>
        <span class="right_item">book2</span>
    </a>
</div>
<!-- 分页 -->
<div id="listpagediv" class="mydiv enddiv">
    <ul class="pagination">
        <li>
            <a href="#">Prev</a>
        </li>
        <li>
            <a href="#">1</a>
        </li>
        <li>
            <a href="#">2</a>
        </li>
        <li>
            <a href="#">3</a>
        </li>
        <li>
            <a href="#">4</a>
        </li>
        <li>
            <a href="#">5</a>
        </li>
        <li>
            <a href="#">Next</a>
        </li>
    </ul>
</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="<%=APP_PATH%>/js/jquery.min.js"></script>
<!-- bootstrap分页 -->
<script src="<%=APP_PATH%>/js/page.js"></script>
<!-- bootstrap -->
<script src="<%=APP_PATH%>/js/bootstrap.min.js"></script>
<script type="text/javascript">
    var curPath = "<%=APP_PATH%>";
    var month_name = "<%=month_name%>";
</script>
<!-- 加载js -->
<script src="<%=APP_PATH%>/js/month.js"></script>
</body>
</html>