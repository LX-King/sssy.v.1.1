// 重点税源数据导入

Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = "js/ext/resources/images/default/s.gif";
	
	var panel = new Ext.Panel({
		title: '重点税源数据导入',
		width: 500,
		height: 200,
		frame: true,
		items: [{
			xtype: 'fieldset',
			title: '选择频度',
			autoHeight: true,
			defaultType: 'button',
			layout: 'column',
			items: [{
				text: '月报', 
				width: 50, 
				listeners: {
	                'click': showMonthWindow
				} 
			}, {
				text: '季报', 
				width: 50,
				listeners: {
					'click': showSeasonWindow
				}
			}]
        }]
	});
	
	// 年份数据配置读取
	var yearStore = new Ext.data.Store({
		url: 'xml/year.xml',
		autoLoad: true,
		reader: new Ext.data.XmlReader({
            record: 'year',
            fields: [
                {name: 'valueField'},
                {name: 'displayField'}
            ]
        })
	});
	
	// 月度数据配置读取
	var monthIntervalStore = new Ext.data.Store({
		url: 'xml/monthInterval.xml',
		autoLoad: true,
		reader: new Ext.data.XmlReader({
            record: 'monthInterval',
            fields: [
                {name: 'valueField'},
                {name: 'displayField'}
            ]
        })
	});
	
	// 季度数据配置读取
	var seasonIntervalStore = new Ext.data.Store({
		url: 'xml/seasonInterval.xml',
		autoLoad: true,
		reader: new Ext.data.XmlReader({
            record: 'seasonInterval',
            fields: [
                {name: 'valueField'},
                {name: 'displayField'}
            ]
        })
	});
	
	// 打开月报窗口
	function showMonthWindow() {
		
		var formPanel = new Ext.form.FormPanel({
			width: 500,
			autoHeight: true,
			labelWidth: 70,
			labelAlign: 'right',
			layout: 'form',
			frame: true,
			buttonAlign: 'center',
			items: [{
				layout: 'column',
				items: [{
					columnWidth: .5,
					layout: 'form',
					items: [{
						xtype: 'combo',
						store: yearStore,
						valueField: 'valueField',
						displayField: 'displayField',
						mode: 'remote',
						forceSelection: true,
						editable: false,
						triggerAction: 'all',
						width: 70,
						fieldLabel: '年份'
					}]
				}, {
					columnWidth: .5,
					layout: 'form',
					items: [{
						xtype: 'combo',
						store: monthIntervalStore,
						valueField: 'valueField',
						displayField: 'displayField',
						mode: 'remote',
						forceSelection: true,
						editable: false,
						triggerAction: 'all',
						width: 70,
						fieldLabel: '月份'
					}]
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
		
		var win = new Ext.Window({
			title: '月报',
			width: 500,
			autoHeight: true,
			modal: true,
			plain: true,
			items: [formPanel]
		});
		
		win.show();
	}
	
	// 打开季报窗口
	function showSeasonWindow() {
		
		var formPanel = new Ext.form.FormPanel({
			autoWidth: true,
			autoHeight: true,
			frame: true,
			labelWidth: 70,
			labelAlign: 'right',
			buttonAlign: 'center',
			items: [{
				layout: 'column',
				items: [{
					columnWidth: .5,
					layout: 'form',
					items: [{
						xtype: 'combo',
						store: yearStore,
						valueField: 'valueField',
						displayField: 'displayField',
						mode: 'remote',
						forceSelection: true,
						editable: false,
						triggerAction: 'all',
						width: 70,
						fieldLabel: '年份'
					}]
				}, {
					columnWidth: .5,
					layout: 'form',
					labelAligh: 'right',
					items: [{
						xtype: 'combo',
						store: seasonIntervalStore,
						valueField: 'valueField',
						displayField: 'displayField',
						mode: 'remote',
						forceSelection: true,
						editable: false,
						triggerAction: 'all',
						width: 70,
						fieldLabel: '月份'
					}]
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
		
		var win = new Ext.Window({
			title: '季报',
			width: 500,
			autoHeight: true,
			modal: true,
			plain: true,
			items: [formPanel]
		});
		
		win.show();
	}
	
	panel.render('data_import');
});