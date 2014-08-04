// 税收收入完成情况表
// -- 打开图表链接

// 显示饼状图
function showPieChart() {
	//Ext.Msg.alert('pie');
	var win = new Ext.Window({
		title: '饼状图',
		width: 700,
		height: 500,
		modal: true,
		plain: true,
		bodyStyle: 'background-color: white',
		html: "<div id='chartContainer'></div>",
		listeners: {
			'show': drawPieChart
        }
		
	});
	
	win.show();
}

// 显示柱状图
function showBarChart() {
	//Ext.Msg.alert('bar');
	var win = new Ext.Window({
		title: '柱状图',
		width: 700,
		height: 500,
		modal: true,
		plain: true,
		bodyStyle: 'background-color: white',
		html: "<div id='chartContainer'></div>",
		listeners: {
			'show': drawBarChart
        }
	});
	
	win.show();
}

// 显示折线图
function showLineChart() {
	//Ext.Msg.alert('line');
	var win = new Ext.Window({
		title: '折线图',
		width: 700,
		height: 500,
		modal: true,
		plain: true,
		bodyStyle: 'background-color: white',
		html: "<div id='chartContainer'></div>",
		listeners: {
			'show': drawLineChart
        }
	});
	
	win.show();
}

// 绘制饼状图
function drawPieChart() {
//		alert('show');
	$('#chartContainer').insertFusionCharts({
		swfUrl: 'FusionCharts/Pie3D.swf',
		width: '700',
		height: '500',
		id: 'myChart',

		dataFormat: 'json',
		dataSource: {
			'chart': {
				'caption': '销售额',
				'xAxisName': '周',
				'yAxisName': '销售额',
				'numberSuffix': '万元',
				'basefontsize': '12'
			},

			'data': [
			      {'label': '第一周', 'value': '14.4'},
			      {'label': '第二周', 'value': '19.6'},
			      {'label': '第三周', 'value': '24'},
			      {'label': '第四周', 'value': '15.7'}
			]
		}
		
	});
}

// 绘制柱状图
function drawBarChart() {
//		alert('show');
	$('#chartContainer').insertFusionCharts({
		swfUrl: 'FusionCharts/Column3D.swf',
		width: '700',
		height: '500',
		id: 'myChart',

		dataFormat: 'json',
		dataSource: {
			'chart': {
				'caption': '销售额',
				'xAxisName': '周',
				'yAxisName': '销售额',
				'numberSuffix': '万元',
				'basefontsize': '12'
			},

			'data': [
			      {'label': '第一周', 'value': '14.4'},
			      {'label': '第二周', 'value': '19.6'},
			      {'label': '第三周', 'value': '24'},
			      {'label': '第四周', 'value': '15.7'}
			]
		}
		
	});
}

// 绘制折线图
function drawLineChart() {
//		alert('show');
	$('#chartContainer').insertFusionCharts({
		swfUrl: 'FusionCharts/Line.swf',
		width: '700',
		height: '500',
		id: 'myChart',

		dataFormat: 'json',
		dataSource: {
			'chart': {
				'caption': '销售额',
				'xAxisName': '周',
				'yAxisName': '销售额',
				'numberSuffix': '万元',
				'basefontsize': '12'
			},

			'data': [
			      {'label': '第一周', 'value': '12.4'},
			      {'label': '第二周', 'value': '19.6'},
			      {'label': '第三周', 'value': '24'},
			      {'label': '第四周', 'value': '15.7'}
			]
		}
		
	});
}
