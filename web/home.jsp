<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
<base href="<%=basePath%>">
<style type="text/css">
<!--
body,td,.p1,.p2,.i{font-family:arial}
input{padding-top:0;padding-bottom:0;-moz-box-sizing:border-box;-webkit-box-sizing:border-box;box-sizing:border-box;}
table{border:0}
TD{FONT-SIZE:9pt;LINE-HEIGHT:18px;}
em{font-style:normal;color:#cc0000}
a em{text-decoration:underline;}

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	position:relative
}
-->
</style>

	<!-- jquery easyui -->
    <link rel="stylesheet" type="text/css" href="js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css">
	<script type="text/javascript" src="js/easyui/jquery-1.6.min.js"></script>
	<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/easyui/locale/easyui-lang-zh_CN.js"></script>
    
	 <!-- 引入Ext相关文件 -->
    <link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css"/>
    <script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="js/ext/ext-all.js"></script>
	<script type="text/javascript" src="js/ext/src/locale/ext-lang-zh_CN.js"></script>
	<!-- /引入Ext相关文件 -->

	<link href="css/skin.css" rel="stylesheet" type="text/css">
	<link href="css/proj.css" rel="stylesheet" type="text/css">

</head>

<body>
    
    <table cellpadding="0" cellspacing="0" align="center">
    	<!-- 如果登录用户是 系统管理员 或 查询用户，不显示任务提示 -->
    	<c:if test="${loginUser.roleCode eq '01' || loginUser.roleCode eq '04' }">
  		<tr>
  			<td>
			<style>
			#cal{width:434px;border:1px solid #c3d9ff;font-size:12px;margin:8px 0 0 15px;}
			#cal #top{height:29px;line-height:29px;background:#e7eef8;color:#003784;padding-left:70px;}
			#cal #top select{font-size:12px;}
			#cal #top input{padding:0;}
			#cal ul#wk{margin:0;padding:0;height:25px;}
			#cal ul#wk li{float:left;width:60px;text-align:center;line-height:25px;list-style:none;}
			#cal ul#wk li b{font-weight:normal;color:#c60b02;}
			#cal #cm{clear:left;border-top:1px solid #ddd;border-bottom:1px dotted #ddd;position:relative;}
			#cal #cm .cell{position:absolute;width:42px;height:36px;text-align:center;margin:0 0 0 9px;}
			#cal #cm .cell .so{font:bold 16px arial;}
			#cal #bm{text-align:right;height:24px;line-height:24px;padding:0 13px 0 0;}
			#cal #bm a{color:7977ce;}
			#cal #fd{display:none;position:absolute;border:1px solid #dddddf;background:#feffcd;padding:10px;line-height:21px;width:150px;}
			#cal #fd b{font-weight:normal;color:#c60a00;}
			</style>
		
			<div id="cal">
				<div id="top">公元&nbsp;<select></select>&nbsp;年&nbsp;<select></select>&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;农历<span></span>年&nbsp;[&nbsp;<span></span>年&nbsp;]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="回到今天" title="点击后跳转回今天" style="padding:0px">
				</div>
				<ul id="wk"><li>一</li><li>二</li><li>三</li><li>四</li><li>五</li><li><b>六</b></li><li><b>日</b></li></ul>
				<div id="cm"></div>
				<div id="bm">
				<!-- 
					<a target="_blank" onMouseDown="return c({'fm':'alop','title':this.innerHTML,'url':this.href,'p1':al_c(this),'p2':1})" href="javascript:void(0)">历史上的今天</a>
				 -->
				</div>
			</div>
			<script src="js/bdcalendar.js"></script>
		
			</td>
		</tr>
		</c:if>
		
		<!-- 如果登录用户是 局领导，税收管理员，税务所 显示任务提示 -->
    	<c:if test="${loginUser.roleCode eq '02' || loginUser.roleCode eq '03' || loginUser.roleCode eq '05'}">
  		<tr>
  			<td width="50%">
			<style>
			#cal{width:434px;border:1px solid #c3d9ff;font-size:12px;margin:8px 0 0 15px;}
			#cal #top{height:29px;line-height:29px;background:#e7eef8;color:#003784;padding-left:70px;}
			#cal #top select{font-size:12px;}
			#cal #top input{padding:0;}
			#cal ul#wk{margin:0;padding:0;height:25px;}
			#cal ul#wk li{float:left;width:60px;text-align:center;line-height:25px;list-style:none;}
			#cal ul#wk li b{font-weight:normal;color:#c60b02;}
			#cal #cm{clear:left;border-top:1px solid #ddd;border-bottom:1px dotted #ddd;position:relative;}
			#cal #cm .cell{position:absolute;width:42px;height:36px;text-align:center;margin:0 0 0 9px;}
			#cal #cm .cell .so{font:bold 16px arial;}
			#cal #bm{text-align:right;height:24px;line-height:24px;padding:0 13px 0 0;}
			#cal #bm a{color:7977ce;}
			#cal #fd{display:none;position:absolute;border:1px solid #dddddf;background:#feffcd;padding:10px;line-height:21px;width:150px;}
			#cal #fd b{font-weight:normal;color:#c60a00;}
			</style>
		
			<div id="cal">
				<div id="top">公元&nbsp;<select></select>&nbsp;年&nbsp;<select></select>&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;农历<span></span>年&nbsp;[&nbsp;<span></span>年&nbsp;]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="回到今天" title="点击后跳转回今天" style="padding:0px">
				</div>
				<ul id="wk"><li>一</li><li>二</li><li>三</li><li>四</li><li>五</li><li><b>六</b></li><li><b>日</b></li></ul>
				<div id="cm"></div>
				<div id="bm">
				<!-- 
					<a target="_blank" onMouseDown="return c({'fm':'alop','title':this.innerHTML,'url':this.href,'p1':al_c(this),'p2':1})" href="javascript:void(0)">历史上的今天</a>
				 -->
				</div>
			</div>
			<script src="js/bdcalendar.js"></script>
		
			</td>
			<td>
				<!-- 局领导，显示需要评价的任务列表（微观和总/分量） -->
				<c:if test="${loginUser.roleCode eq '02'}">
					<table class="datagrid" align="center">
						<tr height="35">
						<td>
							<ul>
								<li style="height: 25">
									<span>
									<img src="images/reply.gif">
									<a class="img" href="riskMicroTask_showRiskMicroTaskToExamTableInExamSection.action">
									【微观】需要评价的任务列表<font color="red">（${feedbackRiskMicroTaskListSize }条记录）</font>
									</a>
									</span>
								</li>
								<li style="height: 25">
									<span>
									<img src="images/reply.gif">
									<a class="img" href="macroTask_showMacroTaskToExamTableInExamSection.action">
									【总/分量】需要评价的任务列表<font color="red">（${feedbackMacroTaskListSize }条记录）</font>
									</a>
									</span>
								</li>
							</ul>
						</td>
					</tr>
					</table>
				</c:if>
				
				<!-- 税收管理员，显示上级发布的任务列表（微观和总/分量） 和  上级对你执行任务评价的列表（微观和总/分量） -->
				<c:if test="${loginUser.roleCode eq '03'}">
					<table class="datagrid" align="center">
						<tr height="35">
						<td>
							<ul>
								<li style="height: 25;">
									<span>
									<img src="images/reply.gif">
									<a class="img" href="riskMicroTask_showRiskMicroTaskReceiveTable.action">
									【微观】上级向你发布的任务列表<font color="red">（${publishRiskMicroTaskListSize }条记录）</font>
									</a>
									</span>
								</li>
								<li style="height: 25">
									<span>
									<img src="images/reply.gif">
									<a class="img" href="riskMicroTask_showRiskMicroTaskFinishExamTableInReceiveSection.action">
									【微观】上级对你执行任务评价的列表<font color="red">（${examRiskMicroTaskListSize }条记录）</font>
									</a>
									</span>
								</li>
							</ul>
						</td>
					</tr>
					</table>
				</c:if>
				
				<!-- 税务所，显示上级发布的任务列表（总/分量） 和  上级对你执行任务评价的列表（总/分量） -->
				<c:if test="${loginUser.roleCode eq '05'}">
					<table class="datagrid" align="center">
						<tr height="35">
						<td>
							<ul>
								<li style="height: 25;">
									<span>
									<img src="images/reply.gif">
									<a class="img" href="macroTask_showMacroTaskToReceiveTable.action">
									【总/分量】上级向你发布的任务列表<font color="red">（${publishMacroTaskListSize }条记录）</font>
									</a>
									</span>
								</li>
								<li style="height: 25">
									<span>
									<img src="images/reply.gif">
									<a class="img" href="macroTask_showMacroTaskFinishExamTableInReceiveSection.action">
									【总/分量】上级对你执行任务评价的列表<font color="red">（${examMacroTaskListSize }条记录）</font>
									</a>
									</span>
								</li>
							</ul>
						</td>
					</tr>
					</table>
				</c:if>
				
			</td>
		</tr>
		</c:if>
		</table>
			
</body>
</html>
