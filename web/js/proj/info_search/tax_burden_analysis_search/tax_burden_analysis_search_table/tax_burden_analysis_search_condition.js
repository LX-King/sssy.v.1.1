// 税负分析查询表
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
				// 选择核算机关
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
				// 选择注册类型
				xtype: 'combo',
				store: new Ext.data.SimpleStore({
					fields: ['valueField', 'displayField'],
					data: [
						['1', '注册类型1'],
						['2', '注册类型2']
					]
				}),
				valueField: 'valueField',
				displayField: 'displayField',
				forceSelection: true,
				editable: false,
				triggerAction: 'all',
				emptyText : '请选择',
				mode : 'local',
				fieldLabel: '注册类型'
			}, {
				// 选择产业类型
				xtype: 'combo',
				store: new Ext.data.SimpleStore({
					fields: ['valueField', 'displayField'],
					data: [
						['1', '一产'],
						['2', '二产'],
						['3', '三产']
					]
				}),
				valueField: 'valueField',
				displayField: 'displayField',
				forceSelection: true,
				editable: false,
				triggerAction: 'all',
				emptyText : '请选择',
				mode : 'local',
				fieldLabel: '产业类型'
			}, {
				// 选择主要行业
				xtype: 'combo',
				store: new Ext.data.SimpleStore({
					fields: ['valueField', 'displayField'],
					data: [
						['1', '主要行业1'],
						['2', '主要行业2']
					]
				}),
				valueField: 'valueField',
				displayField: 'displayField',
				forceSelection: true,
				editable: false,
				triggerAction: 'all',
				emptyText : '请选择',
				mode : 'local',
				fieldLabel: '主要行业'
			}, {
				// 选择税负类型
				xtype: 'combo',
				store: new Ext.data.SimpleStore({
					fields: ['valueField', 'displayField'],
					data: [
						['1', '总体税负'],
						['2', '营业税税负'],
						['3', '企业所得税税负'],
						['4', '其他税负']
					]
				}),
				valueField: 'valueField',
				displayField: 'displayField',
				forceSelection: true,
				editable: false,
				triggerAction: 'all',
				emptyText : '请选择',
				mode : 'local',
				fieldLabel: '税负类型'
			}, {
				// 输入纳税人名称
				xtype: 'textfield',
				fieldLabel: '纳税人名称',
				width: 160
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
				window.location.href = 'tax_burden_analysis_search_table.jsp'
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