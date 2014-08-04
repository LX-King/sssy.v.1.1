// 系统决策报告

Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = "js/ext/resources/images/default/s.gif";
	
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
	
	// 季度数据配置读取
	var seasonStore = new Ext.data.Store({
		url: 'xml/season.xml',
		autoLoad: true,
		reader: new Ext.data.XmlReader({
            record: 'season',
            fields: [
                {name: 'valueField'},
                {name: 'displayField'}
            ]
        })
	});
	
	var formPanel = new Ext.form.FormPanel({
		title: '系统决策报告',
		width: 500,
		height: 100,
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
					store: seasonStore,
					valueField: 'valueField',
					displayField: 'displayField',
					mode: 'remote',
					forceSelection: true,
					editable: false,
					triggerAction: 'all',
					width: 70,
					fieldLabel: '季度'
				}]
			}]
		}],
		buttons: [{
			text: '生成报告', 
			handler: function() {
				window.location.href = 'report_template.jsp'
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