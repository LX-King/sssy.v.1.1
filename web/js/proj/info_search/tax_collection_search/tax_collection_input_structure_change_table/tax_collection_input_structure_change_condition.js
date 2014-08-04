// 税收收入结构变化表
// -- 条件选择
Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL="js/ext/resources/images/default/s.gif";
	
	var formPanel = new Ext.form.FormPanel({
		id: 'formPanel',
		title: '条件选择',
		width: 500,
		autoHeight: true,
		labelWidth: 70,
		labelAlign: 'right',
		layout: 'form',
		frame: true,
		buttonAlign: 'center',
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
			// 输入报表期
			xtype: 'textfield',
			itemId: 'tableDate',
			fieldLabel: '报表期',
			width: 160,
			listeners: {
				'focus': showTableDateWindow	// 光标移到文本框弹出选择报表期窗口
			}
		}, {
			// 选择数据类型
			xtype: 'combo',
			store: new Ext.data.SimpleStore({
				fields: ['valueField', 'displayField'],
				data: [
					['1', '入库税收'],
					['2', '实缴税收']
				]
			}),
			
			valueField: 'valueField',
			displayField: 'displayField',
			value: '1',	// 设定默认值
			forceSelection: true,
			editable: false,
			triggerAction: 'all',
			mode : 'local',
			fieldLabel: '数据类型'
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
			value: '1',	// 设定默认值
			forceSelection: true,
			editable: false,
			triggerAction: 'all',
			mode : 'local',
			fieldLabel: '金额单位'
		}, {
			// 选择显示项目
			xtype: 'combo',
			store: new Ext.data.SimpleStore({
				fields: ['valueField', 'displayField'],
				data: [
					['1', '分级次'],
					['2', '分产业'],
					['3', '分行业'],
					['4', '分税种'],
					['5', '分管理机关'],
					['6', '分经济类型'],
					['7', '其他分类']
				]
			}),
			
			valueField: 'valueField',
			displayField: 'displayField',
			forceSelection: true,
			editable: false,
			triggerAction: 'all',
			mode : 'local',
			emptyText : '请选择',
			fieldLabel: '显示项目'
		}],
		buttons: [{
			text: '查询', 
			handler: function() {
				window.location.href = 'tax_collection_input_structure_change_table.jsp'
			}
		}, { 
			text: '重置',
			handler: function() {
				formPanel.form.reset();
			}
		}]
	});
	
	// 弹出选择报表期窗口
	function showTableDateWindow() {
		
		var formPanel = new Ext.form.FormPanel({
			width: 300,
			autoHeight: true,
			labelWidth: 70,
			labelAlign: 'right',
			layout: 'form',
			frame: true,
			buttonAlign: 'center',
			items: [{
				itemId: 'dateTypeCombo',
				xtype: 'combo',
				store: new Ext.data.SimpleStore({
					fields: ['valueField', 'displayField'],
					data: [
						['1', '按日'],
						['2', '按月'],
						['3', '按5日'],
						['4', '按旬'],
						['5', '按季'],
						['6', '按年']
					]
				}),
				
				valueField: 'valueField',
				displayField: 'displayField',
				forceSelection: true,
				editable: false,
				triggerAction: 'all',
				mode : 'local',
				emptyText : '请选择',
				fieldLabel: '报表期类别',
				listeners: {
					'select': showDateField
				}
			}, {
				// 按日填报表期
				itemId: 'dayType',
				xtype: 'textfield',
				fieldLabel: '按日',
				width: 160,
				disabled: true,
				listeners: {
                	'focus': function(){
                		WdatePicker({dateFmt:'yyyy-MM-dd'})
                	}
                }
			}, {
				// 按月填报表期
				itemId: 'monthType',
				xtype: 'textfield',
				fieldLabel: '按月',
				width: 160,
				disabled: true,
				listeners: {
                	'focus': function(){
                		WdatePicker({dateFmt:'yyyy-MM'})
                	}
                }
			}, {
				// 按5日填报表期
				itemId: '5dayType',
				xtype: 'textfield',
				fieldLabel: '按5日',
				width: 160,
				disabled: true,
				listeners: {
                	'focus': function(){
                		WdatePicker({dateFmt:'yyyy-MM'})
                	}
                }
			}, {
				// 按旬填报表期
				itemId: 'xunType',
				xtype: 'textfield',
				fieldLabel: '按旬',
				width: 160,
				disabled: true,
				listeners: {
                	'focus': function(){
                		WdatePicker({dateFmt:'yyyy-MM'})
                	}
                }
			}, {
				// 按季填报表期
				itemId: 'seasonType',
				xtype: 'textfield',
				fieldLabel: '按季',
				width: 160,
				disabled: true,
				listeners: {
                	'focus': function(){
                		WdatePicker({dateFmt:'yyyy-MM'})
                	}
                }
			}, {
				// 按年填报表期
				itemId: 'yearType',
				xtype: 'textfield',
				fieldLabel: '按年',
				width: 160,
				disabled: true,
				listeners: {
                	'focus': function(){
                		WdatePicker({dateFmt:'yyyy'})
                	}
                }
			}],
			buttons: [{
				text: '确定', 
				handler: function() {
//					alert(Ext.getCmp('formPanel'));
//					alert(Ext.getCmp('tableDate').fieldLabel);
					var tableDateValue;
					switch (formPanel.getComponent('dateTypeCombo').value) {
						case '1':
							tableDateValue = formPanel.getComponent('dayType').getRawValue();
//							alert(tableDateValue);
							Ext.getCmp('formPanel').getComponent('tableDate').setValue(tableDateValue);
							win.close();
							break;
						case '2':
							tableDateValue = formPanel.getComponent('monthType').getRawValue();
//							alert(tableDateValue);
							Ext.getCmp('formPanel').getComponent('tableDate').setValue(tableDateValue);
							win.close();
							break;
						case '6':
							tableDateValue = formPanel.getComponent('yearType').getRawValue();
//							alert(tableDateValue);
							Ext.getCmp('formPanel').getComponent('tableDate').setValue(tableDateValue);
							win.close();
							break;
					
					}
//					Ext.getCmp('formPanel').getComponent('option').getComponent('tableDate').setValue('2011-01-01');
//					Ext.getCmp('tableDate').setValue('2011-01-01');
				}
			}, { 
				text: '取消',
				handler: function() {
					win.close();
				}
			}]
		});
		
		// 初始化所有填表期控件
		function initialAllDateType() {
			// 全部初始value为空
			formPanel.getComponent('dayType').setValue('');
			formPanel.getComponent('monthType').setValue('');
			formPanel.getComponent('yearType').setValue('');
			
			// disable全部
			formPanel.getComponent('dayType').setDisabled(true);
			formPanel.getComponent('monthType').setDisabled(true);
			formPanel.getComponent('yearType').setDisabled(true);
		}
		
		function showDateField(combo) {
//			alert(combo.value);
			switch (combo.value) {
				case '1':
//					alert(1);
					initialAllDateType();
					formPanel.getComponent('dayType').setDisabled(false);
					formPanel.getComponent('dayType').focus();
					break;
				case '2':
//					alert(2);
					initialAllDateType();
					formPanel.getComponent('monthType').setDisabled(false);
					formPanel.getComponent('monthType').focus();
					break;
				case '6':
//					alert(2);
					initialAllDateType();
					formPanel.getComponent('yearType').setDisabled(false);
					formPanel.getComponent('yearType').focus();
					break;
			}
		}
		
		var win = new Ext.Window({
			title: '报表期',
			width: 300,
			autoHeight: true,
			modal: true,
			plain: true,
			items: [formPanel]
		});
		
		win.show();
	}
	
	formPanel.render('panel');
});