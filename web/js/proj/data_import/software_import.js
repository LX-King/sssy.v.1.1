// 综合征管软件数据导入

Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = "js/ext/resources/images/default/s.gif";
	
	var formPanel = new Ext.form.FormPanel({
		renderTo: 'data_import',
		title: '综合征管软件数据导入',
		width: 500,
		height: 300,
		labelWidth: 70,
		layout: 'form',
		frame: true,
		buttonAlign: 'center',
		items: [{
			xtype: 'fieldset',
			title: '选择导入的数据类型',
			autoHeight: true,
			defaultType: 'checkbox',
			hideLabels: true,
			items: [
				{boxLabel: '入库税收'},
				{boxLabel: '实缴税收'},
				{boxLabel: '待解税金'},
				{boxLabel: '欠缴税金'}
			]
		}, {
			xtype: 'fieldset',
			title: '选择频度',
			autoHeight: true,
			defaultType: 'datefield',
			items: [{
				fieldLabel: '起始日期', 
				autoScroll: true,
				format:'Y/m/d'
			}, {
				fieldLabel: '终止日期', 
				format:'Y/m/d'
			}]
		}],
		buttons: [{
			text: '开始导入', 
			handler: function() {
				formPanel.form.submit({
					waitTitle: '请稍后',
					waitMsg: '数据正在导入，请稍后......'
				});
			}
		}, { 
			text: '重置',
			handler: function() {
				formPanel.form.reset();
			}
		}]
	});
});