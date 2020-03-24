<%--
  Created by IntelliJ IDEA.
  User: chenqixu
  Date: 2020/3/18
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String APP_PATH = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>image</title>

    <!-- Bootstrap -->
    <link href="<%=APP_PATH%>/css/bootstrap-3.3.7/css/bootstrap.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <div class="row-fluid">
                <div class="span4">
                    <%--<video id="video" controls preload="auto" width="400px" height="300px">--%>
                        <%--<source src="<%=APP_PATH%>/video/KANA.mp4" type="video/mp4">--%>
                    <%--</video>--%>
                </div>
                <div class="span4">
                </div>
                <div class="span4">
                </div>
            </div>
            <div class="pagination">
                <ul>
                    <li>
                        <a href="javascript:previous_page();">上一页</a>
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
                        <a href="#">下一页</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="<%=APP_PATH%>/js/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="<%=APP_PATH%>/css/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<!-- 加载image.js -->
<script src="<%=APP_PATH%>/js/image.js"></script>
<script type="text/javascript">
    var curPath = '<%=APP_PATH%>';
</script>
</body>
</html>
