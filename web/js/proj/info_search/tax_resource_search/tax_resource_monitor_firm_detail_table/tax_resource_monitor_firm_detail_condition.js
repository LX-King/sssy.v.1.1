// 重点税源监控企业明细表
// -- 条件选择
Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL="js/ext/resources/images/default/s.gif";
	
	var formPanel = new Ext.form.FormPanel({
		title: '条件选择',
		width: 500,
		autoHeight: true,
		labelWidth: 100,
		labelAlign: 'right',
		layout: 'form',
		frame: true,
		buttonAlign: 'center',
		items: [{
			xtype: 'fieldset',
			title: '选择条件：',
			autoHeight: true,
			items:[{
				// 选择管理机关
				xtype: 'combo',
				store: new Ext.data.SimpleStore({
					fields: ['valueField', 'displayField'],
					data: [
						['1', '核算机关'],
						['2', '基层管理机关'],
						['3', '税收管理员']
					]
				}),
				valueField: 'valueField',
				displayField: 'displayField',
				forceSelection: true,
				editable: false,
				triggerAction: 'all',
				emptyText : '请选择',
				mode : 'local',
				fieldLabel: '管理机关'
			}, {
				// 选择行业大类
				xtype: 'combo',
				store: new Ext.data.SimpleStore({
					fields: ['valueField', 'displayField'],
					data: [
						['1', '行业1'],
						['2', '行业2'],
						['3', '行业3']
					]
				}),
				valueField: 'valueField',
				displayField: 'displayField',
				forceSelection: true,
				editable: false,
				triggerAction: 'all',
				emptyText : '请选择',
				mode : 'local',
				fieldLabel: '行业大类'
			}, {
				// 选择上年实缴税收款
				xtype: 'combo',
				store: new Ext.data.SimpleStore({
					fields: ['valueField', 'displayField'],
					data: [
						['1', '实缴税收款为零'],
						['2', '<=10万元'],
						['3', '>10万元且<=50万元'],
						['4', '>50万元且<=100万元'],
						['5', '>100万元且<=500万元'],
						['6', '>500万元且<=1000万元'],
						['7', '>1000万元且<=5000万元'],
						['8', '>5000万元且<=1亿元'],
						['9', '>1亿元']
					]
				}),
				valueField: 'valueField',
				displayField: 'displayField',
				forceSelection: true,
				editable: false,
				triggerAction: 'all',
				emptyText : '请选择',
				mode : 'local',
				fieldLabel: '上年实缴税收款'
			}, {
				// 选择经济类型
				xtype: 'combo',
				store: new Ext.data.SimpleStore({
					fields: ['valueField', 'displayField'],
					data: [
						['1', '经济类型1'],
						['2', '经济类型2'],
						['3', '经济类型3']
					]
				}),
				valueField: 'valueField',
				displayField: 'displayField',
				forceSelection: true,
				editable: false,
				triggerAction: 'all',
				emptyText : '请选择',
				mode : 'local',
				fieldLabel: '经济类型'
			}, {
				// 输入报表期
				xtype: 'datefield',
				fieldLabel: '报表期',
				width: 160,
				format:'Y/m/d'
			}]
		}],
		buttons: [{
			text: '查询', 
			handler: function() {
				window.location.href = 'tax_resource_monitor_firm_detail_table.jsp'
			}
		}, { 
			text: '重置',
			handler: function() {
				formPanel.form.reset();
			}
		}]
	});
	
	formPanel.render('panel');
});