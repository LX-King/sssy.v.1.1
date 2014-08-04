// 利润表（旧）
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
				// 选择报表类型
				xtype: 'combo',
				store: new Ext.data.SimpleStore({
					fields: ['valueField', 'displayField'],
					data: [
						['1', '旧版报表'],
						['2', '新版报表']
					]
				}),
				valueField: 'valueField',
				displayField: 'displayField',
				forceSelection: true,
				editable: false,
				triggerAction: 'all',
				emptyText : '请选择',
				mode : 'local',
				fieldLabel: '报表类型'
			}, {
				// 选择金额单位
				xtype: 'combo',
				store: new Ext.data.SimpleStore({
					fields: ['valueField', 'displayField'],
					data: [
						['1', '元'],
						['2', '万元']
					]
				}),
				valueField: 'valueField',
				displayField: 'displayField',
				forceSelection: true,
				editable: false,
				triggerAction: 'all',
				value: '1',
				emptyText : '请选择',
				mode : 'local',
				fieldLabel: '金额单位'
			}, {
				// 输入纳税人名称
				xtype: 'textfield',
				fieldLabel: '纳税人名称',
				width: 160
			}, {
				// 输入纳税人编码
				xtype: 'textfield',
				fieldLabel: '纳税人编码',
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
				window.location.href = 'interest_old_table.jsp'
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