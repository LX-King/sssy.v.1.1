// 生成特征数据库

Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = "js/ext/resources/images/default/s.gif";
	
	var panel = new Ext.Panel({
		title: '生成特征数据库',
		width: 500,
		height: 200,
		frame: true,
		items: [new Ext.Button({
			text: '生成'
		})]
	});
	
	panel.render('data_analysis');
});