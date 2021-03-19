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
        function goto() {
            top.window.location = "<%=APP_PATH%>/video?index=1";
        }
    </script>
</head>
<body onload="goto();"></body>
</html>
