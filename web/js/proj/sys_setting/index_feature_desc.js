Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL="js/ext/resources/images/default/s.gif";
	Ext.QuickTips.init();
	
	var fm = Ext.form;
	
	// 列模式
	var cm = new Ext.grid.ColumnModel({
		columns: [{
			header: '指标<br>编码',
			dataIndex: 'indexCode',
			width: 50,
			editor: new fm.TextField()
		}, {
			header: '指标<br>名称',
			dataIndex: 'indexName',
			editor: new fm.TextField()
		}, {
			header: '特征描述(TZ)',
			dataIndex: 'YCQJ1_featureDesc',
			editor: new fm.TextField()
		}, {
			header: '分析疑点(YD)',
			dataIndex: 'YCQJ1_analysisDoubt',
			editor: new fm.TextField()
		}, {
			header: '管理建议(JY)',
			dataIndex: 'YCQJ1_mgrSuggest',
			editor: new fm.TextField()
		}, {
			header: '特征描述(TZ)',
			dataIndex: 'YSQJ_featureDesc',
			editor: new fm.TextField()
		}, {
			header: '分析疑点(YD)',
			dataIndex: 'YSQJ_analysisDoubt',
			editor: new fm.TextField()
		}, {
			header: '管理建议(JY)',
			dataIndex: 'YSQJ_mgrSuggest',
			editor: new fm.TextField()
		}, {
			header: '特征描述(TZ)',
			dataIndex: 'ZCQJ_featureDesc',
			editor: new fm.TextField()
		}, {
			header: '分析疑点(YD)',
			dataIndex: 'ZCQJ_analysisDoubt',
			editor: new fm.TextField()
		}, {
			header: '管理建议(JY)',
			dataIndex: 'ZCQJ_mgrSuggest',
			editor: new fm.TextField()
		}, {
			header: '特征描述(TZ)',
			dataIndex: 'LSQJ_featureDesc',
			editor: new fm.TextField()
		}, {
			header: '分析疑点(YD)',
			dataIndex: 'LSQJ_analysisDoubt',
			editor: new fm.TextField()
		}, {
			header: '管理建议(JY)',
			dataIndex: 'LSQJ_mgrSuggest',
			editor: new fm.TextField()
		}, {
			header: '特征描述(TZ)',
			dataIndex: 'YCQJ2_featureDesc',
			editor: new fm.TextField()
		}, {
			header: '分析疑点(YD)',
			dataIndex: 'YCQJ2_analysisDoubt',
			editor: new fm.TextField()
		}, {
			header: '管理建议(JY)',
			dataIndex: 'YCQJ2_mgrSuggest',
			editor: new fm.TextField()
		}],
		rows: [[
		{},
		{},
		{header: '异常区间1(YCQJ1)', colspan: 3, align: 'center'},
		{header: '优势区间(YSQJ)', colspan: 3, align: 'center'},
		{header: '正常区间(ZCQJ)', colspan: 3, align: 'center'},
		{header: '劣势区间(LSQJ)', colspan: 3, align: 'center'},
		{header: '异常区间2(YCQJ2)', colspan: 3, align: 'center'}
		]]
	});
	
	// 存储
	var store = new Ext.data.Store({
		autoLoad: true,
		autoDestroy: true,
		url: 'xml/index_feature_desc.xml',
		reader: new Ext.data.XmlReader({
            // records will have a 'plant' tag
            record: 'index',
            // use an Array of field definition objects to implicitly create a Record constructor
            fields: [
                // the 'name' below matches the tag name to read, except 'availDate'
                // which is mapped to the tag 'availability'
                {name: 'indexCode', type: 'string'},
                {name: 'indexName', type: 'string'},
                
                {name: 'YCQJ1_featureDesc', type: 'string'},
                {name: 'YCQJ1_analysisDoubt', type: 'string'},
                {name: 'YCQJ1_mgrSuggest', type: 'string'},
                
                {name: 'YSQJ_featureDesc', type: 'string'},
                {name: 'YSQJ_analysisDoubt', type: 'string'},
                {name: 'YSQJ_mgrSuggest', type: 'string'},
                
                {name: 'ZCQJ_featureDesc', type: 'string'},
                {name: 'ZCQJ_analysisDoubt', type: 'string'},
                {name: 'ZCQJ_mgrSuggest', type: 'string'},
                
                {name: 'LSQJ_featureDesc', type: 'string'},
                {name: 'LSQJ_analysisDoubt', type: 'string'},
                {name: 'LSQJ_mgrSuggest', type: 'string'},
                
                {name: 'YCQJ2_featureDesc', type: 'string'},
                {name: 'YCQJ2_analysisDoubt', type: 'string'},
                {name: 'YCQJ2_mgrSuggest', type: 'string'}
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
        title: '各指标特征库描述',
        frame: true,
        clicksToEdit: 2,
        plugins: [new Ext.ux.plugins.GroupHeaderGrid()]
	});
});