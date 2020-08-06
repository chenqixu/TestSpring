<%@ page import="com.cqx.common.utils.system.JarUtil" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: chenqixu
  Date: 2020/6/14
  Time: 22:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    //读取本地jar包
    Map<String, List<String>> codeMap = JarUtil.loadJar(new File("D:\\Document\\Workspaces\\Git\\TestSelf\\TestCommon\\target\\TestCommon-1.0.0-sources.jar"));
    for (String code : codeMap.get("com/cqx/common/utils/file/FileUtil.java")) {
        System.out.println(code);
    }
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>debuggerView</title>
    <style type="text/css">
        li {
            list-style: none;
        }

        li.sel {
            background-color: red;
        }

        .yuan, span {
            width: 10px;
            height: 10px;
            border-radius: 10px;
            background: white;
            display: block;
            float: left;
            margin: 5px;
        }

        span.debugger {
            background: red;
        }

        .div1 {
            width: 100%;
        }

        .div2 {
            float: left;
            width: 60%;
            height: 95%;
        }

        .div3 {
            float: left;
            width: 10%;
            height: 95%;
        }

        .div_tree {
            float: left;
            width: 15%;
            height: 95%;
        }

        .div_code {
            float: left;
            width: 80%;
            height: 95%;
        }

        .div_inner1 {
            height: 30%;
        }

        .div_inner2 {
            height: 65%;
        }

        .max {
            width: 95%;
            height: 95%;
        }

        .max_areatext {
            width: 95%;
            height: 80%;
        }

        .jar_path {
            width: 90%;
        }

        .div_top {
            height: 200px;
        }

        .div_header {
            height: 10%;
        }

        .div_body {
            height: 500px;
        }

        .div_monitor {
            height: 20%;
        }
    </style>
</head>
<body>
<div class="div1 div_top">
    <div class="div2">
        <div class="div_inner1">
            <div>
                <label>要运行的JAR包：</label>
            </div>
            <div>
                <input type="file" id="jar_path" class="jar_path">
            </div>
        </div>
        <div class="div_inner2">
            <label>参数：</label>
            <textarea class="max_areatext"></textarea>
        </div>
    </div>
    <div class="div3">
        <button id="btn_run" class="max">运行</button>
    </div>
</div>
<div class="div1 div_header">
    <button id="step_over">步进</button>
    <button id="step_into">步入</button>
    <button id="resume_program">跳过</button>
</div>
<div class="div1 div_body">
    <div class="div_tree">树</div>
    <div class="div_code">
        <ul>
            <li id="li1"><span id="span1"></span>&nbsp;&nbsp;1 package com.newland.bi.netverify.action;</li>
            <li id="li2"><span id="span2"></span>&nbsp;&nbsp;2</li>
            <li id="li3"><span id="span3"></span>&nbsp;&nbsp;3 import java.io.PrintWriter;</li>
            <li id="li4"><span id="span4"></span>&nbsp;&nbsp;4</li>
            <li id="li5"><span id="span5"></span>&nbsp;&nbsp;5 import org.springframework.context.annotation.Scope;</li>
            <li id="li6"><span id="span6"></span>&nbsp;&nbsp;6 import org.springframework.stereotype.Controller;</li>
            <li id="li7"><span id="span7"></span>&nbsp;&nbsp;7 import
                org.springframework.web.bind.annotation.RequestMapping;
            </li>
            <li id="li8"><span id="span8"></span>&nbsp;&nbsp;8</li>
            <li id="li9"><span id="span9"></span>&nbsp;&nbsp;9 @Scope("prototype")</li>
            <li id="li10"><span id="span10"></span>10 @Controller</li>
            <li id="li11"><span id="span11"></span>11 @RequestMapping(value = "/mvc")</li>
            <li id="li12"><span id="span12"></span>12 public class CoffersApproveController {</li>
            <li id="li13"><span id="span13"></span>13 @RequestMapping(value = "/hello")</li>
            <li id="li14"><span id="span14"></span>14 public String hello(){</li>
            <li id="li15"><span id="span15"></span>15 return "hello";</li>
            <li id="li16"><span id="span16"></span>16 }</li>
            <li id="li17"><span id="span17"></span>17 }</li>
        </ul>
    </div>
</div>
<div class="div1 div_monitor">监视对象</div>
<script src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript">
    var now_sel_li = "li1";
    /* init */
    $(function () {
        $("#li1").addClass("sel");
        $("#step_over").on('click', function () {
            //获取当前选择的li的序号
            var next = parseInt(now_sel_li.substr(2)) + 1;
            var next_sel_li = "li" + next;
            //先判断是否有下一个元素
            if ($("#" + next_sel_li).length > 0) {
                //原有对象移除class
                $("#" + now_sel_li).removeClass("sel");
                now_sel_li = next_sel_li;
                $("#" + next_sel_li).addClass("sel");
            }
        });
        $("#step_into").on('click', function () {

        });
        $("#resume_program").on('click', function () {

        });
        $("#btn_run").on('click', function () {
            alert($("#jar_path").val());
        });
        for (var i = 1; i < 18; i++) {
            $("#li" + i).bind('click', {
                index: i
            }, clickHandler);
        }

        function clickHandler(event) {
            var i = event.data.index;
            //当前设置断点
            $("#span" + i).addClass("debugger");
        }

    });
</script>
</body>
</html>
