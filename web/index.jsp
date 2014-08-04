<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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


    <script type="text/javascript">
        if (top != self) {
            if (top.location != self.location)
                top.location = self.location;
        }
        $(document).ready(function(){
            $('#user.code').focus();
        }) ;

    </script>


    <style type="text/css">
        body {
            background: url("images/bottom_bg.jpg") repeat;
        }

        .top_panel {
            height: auto;
            width: 100%;
            background: url("images/top_bg.jpg") repeat;
            text-align: center;
        }

        .top_panel .top_main_panel {
            width: 907px;
            height: 170px;
            padding: 0;
            background: url("images/top_main_bg.jpg") no-repeat;
            margin: 0 auto;

        }

        .main_panel {
            height: 396px;
            width: 100%;
            padding: 0;
            background: url("images/main_bg.jpg") repeat-x;
            text-align: center;
        }

        .main_left_panel {
            background: url("images/main_left.jpg") no-repeat;
            width: 454px;
            height: 396px;
            margin-left: 100px;
            _margin-left: 50px;
        }

        .main_right_panel {
            background: url("images/main_right.jpg") no-repeat;
            width: 500px;
            height: 396px;
            margin-right: 100px;
            float: left;
        }

        .bottom_panel {
            width: 100%;
            position: absolute;
            bottom: 0px;
            height: 15px;

            text-align: center;
            font-size: 13px;
            color: white; /*background: url("images/bottom_bg.jpg") repeat;*/
        }

        .login_form {
            width: 230px;
            height: 159px;
            margin-top: 155px;
            margin-left: 112px;
            padding: 0;
            font-size: 12px;
            font-family: Tahoma;
            color: #000000;
        }

        .field {
            height: 45px;
            width: 100%;
            margin-bottom: 2px;
            margin-top: 2px;
        }

        .field label {
            float: left;
            padding: 3px;
        }

        .field input.text {
            border: 1px solid #c5c5c5;
            height: 20px;
        }

        .field .button {
            background: url("images/login_button.jpg") no-repeat left center;
            border: none;
            width: 95px;
            height: 28px;
            cursor: pointer;
        }
    </style>
    
    <!-- jquery easyui -->
    <link rel="stylesheet" type="text/css" href="js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css">
	<script type="text/javascript" src="js/easyui/jquery-1.6.min.js"></script>
	<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<!-- jquery easyui -->
    
    <script type="text/javascript">

		// 显示提示信息
		function showMsg(msg) {
			if (null != msg && "" != msg) {
				$.messager.alert('警告', msg,'warning');
			}
		}

		// 提交表单
		function submitForm() {
			if(document.loginForm.userCode.value.length == 0) {
				$.messager.alert('警告','请输入登录代码!','warning');
				return;
			}

			if(document.loginForm.userCode.value.length < 7) {
				$.messager.alert('警告','登录代码位数不足!','warning');
				return;
			}

			if(document.loginForm.password.value.length == 0) {
				$.messager.alert('警告','请输入密码!','warning');
				return;
			}

			document.loginForm.submit();
		}
    </script>
</head>
<body onload="showMsg('${msg }')">
<div id="login-panel">
    <div class="top_panel">
        <div class="top_main_panel">&nbsp;</div>
    </div>
    <div class="main_panel">
        <table width="954" height="396" style="margin:0 auto;" cellspacing="0">
            <tr>
                <td width="454" height="396">
                    <div class="main_left_panel"></div>
                </td>
                <td width="500" height="396">
                    <div class="main_right_panel">
                        <div class="login_form">
                            <form name="loginForm" id="loginForm" action="sysmgr/user_login.action" method="post">
                                <div class="field">
                                    <label>登录代码：</label>
                                    <input type="text" id="userCode" class="text" name="user.code" style="width:152px;">
                                </div>
                                <div class="field">
                                    <label>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
                                    <input id="password" type="password" class="text" name="user.password" style="width:152px;">
                                </div>
                                <div class="field" align="center">
                                	<input type="button" class="button" onclick="submitForm()">
                                </div>
                            </form>
                        </div>
                    </div>
                </td>
            </tr>

        </table>

    </div>
</div>
<div class="bottom_panel">
</div>
</body>
</html>
