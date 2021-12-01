<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String APP_PATH = request.getContextPath();
    String month_name = request.getParameter("month_name");
    String book_name = request.getParameter("book_name");
    String book_desc = request.getParameter("book_desc");
%>
<!DOCTYPE html>
<html>
<head>
    <title>动漫之家-<%=book_name%>【书】</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport"
          content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta charset="utf-8"/>
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
            height: auto;
            width: auto \9;
            width: 100%;
        }

        body {
            background-color: #eee;
        }

        body .mydiv {
            width: 100%;
        }

        body .headdiv {
            text-align: center;
            height: 30px;
        }

        body .enddiv {
            text-align: center;
            height: 20px;
        }
    </style>
</head>
<body>
<!-- 表头 -->
<div id="headdiv" class="mydiv headdiv">
    <a href="<%=APP_PATH%>/index.jsp">首页</a>
    &nbsp;&nbsp;&nbsp;
    <a href="<%=APP_PATH%>/goto/comic/comic.do">动漫之家</a>
    ->
    <a href="<%=APP_PATH%>/goto/comic/month.do?month_name=<%=month_name%>"><%=month_name%>
    </a>
    ->
    <a href="<%=APP_PATH%>/goto/comic/book.do?month_name=<%=month_name%>&book_name=<%=book_name%>&book_desc=<%=book_desc%>"><%=book_name%>
    </a>
</div>
<!-- 内容 -->
<div id="contentdiv" class="mydiv">
    <div>
        <p>标题 Vol.92</p>
        <img loading="lazy" src="<%=APP_PATH%>/res/comic/2021/11/emily135275/title.jpg" alt="标题 Vol.92"/>
    </div>
    <img loading="lazy" src="<%=APP_PATH%>/res/comic/2021/11/emily135275/1.jpg" alt="图片1"/>
    <img loading="lazy" src="<%=APP_PATH%>/res/comic/2021/11/emily135275/2.jpg" alt="图片2"/>
</div>
<!-- 底部 -->
<div class="mydiv enddiv">
    <p>comic</p>
</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="<%=APP_PATH%>/js/jquery.min.js"></script>
<script type="text/javascript">
    var curPath = "<%=APP_PATH%>";
    var month_name = "<%=month_name%>";
    var book_name = "<%=book_name%>";
    var book_desc = "<%=book_desc%>";
</script>
<!-- 加载js -->
<script src="<%=APP_PATH%>/js/book.js"></script>
</body>
</html>