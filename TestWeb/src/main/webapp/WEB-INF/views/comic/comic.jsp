<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String APP_PATH = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>动漫之家-首页</title>
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

        img {
            border: 0;
        }

        ul {
            list-style: none outside none;
            margin: 0;
            padding: 0;
        }

        body {
            background-color: #eee;
        }

        body .mainmenu:after {
            clear: both;
            content: " ";
            display: block;
        }

        body .mainmenu li {
            float: left;
            margin-left: 2.5%;
            margin-top: 2.5%;
            width: 30%;
            border-radius: 3px;
            overflow: hidden;
        }

        body .mainmenu li a {
            display: block;
            color: #FFF;
            text-align: center;
        }

        body .mainmenu li a b {
            display: block;
            height: 80px;
        }

        body .mainmenu li a img {
            margin: 15px auto 15px;
            width: 50px;
            height: 50px;
        }

        body .mainmenu li a span {
            display: block;
            height: 30px;
            line-height: 30px;
            background-color: #FFF;
            color: #999;
            font-size: 14px;
        }

        body .mainmenu li:nth-child(8n+1) {
            background-color: #36A1DB;
        }

        body .mainmenu li:nth-child(8n+2) {
            background-color: #678ce1;
        }

        body .mainmenu li:nth-child(8n+3) {
            background-color: #8c67df;
        }

        body .mainmenu li:nth-child(8n+4) {
            background-color: #84d018;
        }

        body .mainmenu li:nth-child(8n+5) {
            background-color: #14c760;
        }

        body .mainmenu li:nth-child(8n+6) {
            background-color: #f3b613;
        }

        body .mainmenu li:nth-child(8n+7) {
            background-color: #ff8a4a;
        }

        body .mainmenu li:nth-child(8n+8) {
            background-color: #fc5366;
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
    <a href="<%=APP_PATH%>/index.jsp">首页</a>&nbsp;&nbsp;&nbsp;<a href="<%=APP_PATH%>/goto/comic/comic.do">动漫之家</a>
</div>
<!-- 内容 -->
<div class="mydiv">
    <ul id="mainmenu_ul" class="mainmenu">
        <li><a href="<%=APP_PATH%>/goto/comic/month.do"><b><img src="images/tb01.png"/></b><span>2021-11</span></a></li>
        <li><a href="/"><b><img src="images/tb02.png"/></b><span>2021-10</span></a></li>
        <li><a href="/"><b><img src="images/tb03.png"/></b><span>2021-09</span></a></li>
        <li><a href="/"><b><img src="images/tb04.png"/></b><span>2021-08</span></a></li>
        <li><a href="/"><b><img src="images/tb05.png"/></b><span>2021-07</span></a></li>
        <li><a href="/"><b><img src="images/tb06.png"/></b><span>2021-06</span></a></li>
        <li><a href="/"><b><img src="images/tb06.png"/></b><span>2021-05</span></a></li>
        <li><a href="/"><b><img src="images/tb07.png"/></b><span>2021-04</span></a></li>
        <li><a href="/"><b><img src="images/tb08.png"/></b><span>2021-03</span></a></li>
        <li><a href="/"><b><img src="images/tb06.png"/></b><span>2021-02</span></a></li>
        <li><a href="/"><b><img src="images/tb07.png"/></b><span>2021-01</span></a></li>
        <li><a href="/"><b><img src="images/tb08.png"/></b><span>2020-12</span></a></li>
        <li><a href="/"><b><img src="images/tb06.png"/></b><span>2020-11</span></a></li>
        <li><a href="/"><b><img src="images/tb07.png"/></b><span>2020-10</span></a></li>
        <li><a href="/"><b><img src="images/tb08.png"/></b><span>2020-09</span></a></li>
    </ul>
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
</script>
<!-- 加载js -->
<script src="<%=APP_PATH%>/js/comic.js"></script>
</body>
</html>