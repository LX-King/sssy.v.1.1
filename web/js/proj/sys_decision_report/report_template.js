// 系统决策报告

Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = "js/ext/resources/images/default/s.gif";
	
	var panel = new Ext.Panel({
		title: '系统决策分析报告',
		autoScroll: true,
		contentEl: 'report_sample',
		bbar: [new Ext.Button({
			text: '导出报告'
		})]
	});
	
	panel.render('report_panel');
});