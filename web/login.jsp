<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>登录界面</title>

    <!-- 引入Ext相关文件 -->
    <link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css"/>
    <link rel="stylesheet" type="text/css" href="js/plugin/pnotify-1.1.1/jquery.pnotify.default.css"/>
    <script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="js/ext/ext-all.js"></script>
    <script type="text/javascript" src="js/ext/src/locale/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="js/easyui/jquery-1.7.2.min.js"></script>

    <!-- 引入FusionCharts相关文件 -->
    <script type="text/javascript" src="FusionCharts/FusionCharts.js"></script>
    <script type="text/javascript" src="FusionCharts/jquery.min.js"></script>
    <script type="text/javascript" src="FusionCharts/FusionCharts.jqueryplugin.js"></script>
    <!-- /引入FusionCharts相关文件 -->

    <!-- /引入项目相关文件 -->
    <script type="text/javascript" src="js/proj/chartUtil.js"></script>
    <script type="text/javascript" src="js/proj/login.js"></script>
    <!-- jquery easyui -->
    <link rel="stylesheet" type="text/css" href="js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="js/bootstrap/bootstrap.min.css">

    <!-- jquery easyui -->


    <style type="text/css">
        body {
            font-size: 12px;
            font-family: Tahoma;
            background: url("images/login_bg.jpg") repeat;
            margin: 0;
            padding: 0;
            text-align: center;
        }

        .login {
            margin: 0 auto;
            width: 1024px;
            height: 600px;
            background: url("images/login_bg_col.jpg") repeat-x;
            padding: 0;
        }

        .main_panel {
            margin: 0 auto;
            width: 1024px;
            height: 620px;
            background: url("images/login_main_bg.jpg") no-repeat;
            padding: 0;
        }

        .login_form {
            position: absolute;
            top: 282px;
            left: 512px;
            width: 330px;
            height: 320px;
            padding: 0;
            text-align: left;
        }
    </style>


</head>
<body onload="showMsg('${msg }')">
<div id="login-panel" class="login">
    <div class="main_panel">
        <div class="login_form">
            <form name="loginForm" class="form-horizontal" id="loginForm" action="sysmgr/user_login.action"
                  method="post">
                <div class="form-group">
                    <%-- <label for="userCode" class="col-sm-2 control-label">登录代码</label>--%>
                    <div class="col-sm-10">
                        <input type="text" id="userCode" class="form-control" name="user.code"
                               style="width:252px;" placeholder="请输入登录代码"/>
                    </div>
                </div>
                </br>
                <div class="form-group">
                    <div class="col-sm-10">
                        <%--<label for="password" class="col-sm-2 control-label">登录密码</label>--%>
                        <input id="password" type="password" class="form-control" name="user.password"
                               style="width:252px;" placeholder="请输入密码">
                    </div>
                </div>
                </br></br>
                <p>
                    <button type="button" class="btn btn-primary " style="width:252px;" onclick="submitForm()">登录
                    </button>
                </p>

            </form>
        </div>
    </div>
</div>
<div class="bottom_panel">
</div>
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrap/jquery.placeholder.js"></script>

<script type="text/javascript">


    // 显示提示信息
    function showMsg(msg) {
        if (null != msg && "" != msg) {
            $.messager.alert('警告', msg, 'warning');
        }
    }

    // 提交表单
    function submitForm() {
        if (document.loginForm.userCode.value.length == 0) {
            $.messager.alert('警告', '请输入登录代码!', 'warning');
            return;
        }

        if (document.loginForm.userCode.value.length < 7) {
            $.messager.alert('警告', '登录代码位数不足!', 'warning');
            return;
        }

        if (document.loginForm.password.value.length == 0) {
            $.messager.alert('警告', '请输入密码!', 'warning');
            return;
        }

        document.loginForm.submit();
    }
</script>
<script type="text/javascript">
    $(document).ready(function () {

        if (top != self) {
            if (top.location != self.location)
                top.location = self.location;
        }
        /*聚焦focus*/
        $('#user.code').focus();

        /*IE下利用jQueryPlugin实现placeholder*/
        $('input').placeholder({isUseSpan:true, onInput:false});

       /* *//*监听Enter键*//*
        $("#passoword").keypress(function (event) {
            var key = event.which;
            if (key == 13) {
                alert("XX");
            }
        });*/
    });

</script>
</body>
</html>
