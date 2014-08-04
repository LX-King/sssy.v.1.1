Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL="js/ext/resources/images/default/s.gif";
	Ext.QuickTips.init();
	
	var fm = Ext.form;
	
	// 列模式
	var cm = new Ext.grid.ColumnModel({
		columns: [{
			header: '指标编码',
			dataIndex: 'indexCode',
			editor: new fm.TextField()
		}, {
			header: '指标名称',
			dataIndex: 'indexName',
			editor: new fm.TextField()
		}, {
			header: '异常区间1<br>(YCQJ1)',
			dataIndex: 'exceptionSection1',
			editor: new fm.TextField()
		}, {
			header: '优势区间<br>(YSQJ)',
			dataIndex: 'GoodSection',
			editor: new fm.TextField()
		}, {
			header: '正常区间<br>(ZCQJ)',
			dataIndex: 'normalSection',
			editor: new fm.TextField()
		}, {
			header: '劣势区间<br>(LSQJ)',
			dataIndex: 'badSection',
			editor: new fm.TextField()
		}, {
			header: '异常区间2<br>(YCQJ2)',
			dataIndex: 'exceptionSection2',
			editor: new fm.TextField()
		}, {
			header: '指标权重<br>(ZBQZ)',
			dataIndex: 'indexWeight',
			editor: new fm.TextField()
		}]
	});
	
	// 存储
	var store = new Ext.data.Store({
		autoLoad: true,
		autoDestroy: true,
		url: 'xml/index_weight_score_setting.xml',
		reader: new Ext.data.XmlReader({
            // records will have a 'plant' tag
            record: 'index',
            // use an Array of field definition objects to implicitly create a Record constructor
            fields: [
                // the 'name' below matches the tag name to read, except 'availDate'
                // which is mapped to the tag 'availability'
                {name: 'indexCode', type: 'string'},
                {name: 'indexName', type: 'string'},
                {name: 'exceptionSection1', type: 'string'},
                {name: 'GoodSection', type: 'string'},
                {name: 'normalSection', type: 'string'},
                {name: 'badSection', type: 'string'},
                {name: 'exceptionSection2', type: 'string'},
                {name: 'indexWeight', type: 'string'}
            ]
        })
	});
	
	// 表格
	var grid = new Ext.grid.EditorGridPanel({
		store: store,
		cm: cm,
		renderTo: 'editor-grid',
		width: 900,
		height: 500,
		loadMask: true,
        columnLines: true,
        stripeRows : true,
        trackMouseOver: true,
        title: '指标权重及分值设定',
        frame: true,
        clicksToEdit: 2
	});
});


