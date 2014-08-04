<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
<head>
    <base href="<%=basePath%>">
    <title>税收税源分析决策管理系统（县局版）</title>

    <!-- 引入Ext相关文件 -->
    <script type="text/javascript" src="js/easyui/jquery-1.7.2.min.js"></script>
    <link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css"/>
    <script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="js/ext/ext-all.js"></script>
    <script type="text/javascript" src="js/ext/src/locale/ext-lang-zh_CN.js"></script>


    <!-- /引入Ext相关文件 -->
    <script type="text/javascript" src="js/proj/menuTree.js"></script>
    <script type="text/javascript" src="js/proj/main.js"></script>
    <script type="text/javascript" src="js/proj/sessionHander.js"></script>
    <link href="css/skin.css" rel="stylesheet" type="text/css">
    <link href="css/proj.css" rel="stylesheet" type="text/css">

</head>
<body>
<div id="username">
	<span style="vertical-align: middle;">
	<img src="images/icon_user.gif"/>
	当前用户:
	${loginUser.name }( IP:${ipAddr })
	&nbsp;&nbsp;&nbsp;&nbsp;
	</span>
</div>
</body>
</html>
