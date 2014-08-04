Ext.onReady(function() {

	Ext.BLANK_IMAGE_URL="js/ext/resources/images/default/s.gif";
	Ext.QuickTips.init();
	
	// 列模型
	var cm = new Ext.grid.ColumnModel([
		{header: '用户ID', dataIndex: 'userId', sortable: true},
		{header: '用户名', dataIndex: 'username'},
		{header: '密码', dataIndex: 'password'},
		{header: '性别', dataIndex: 'gender'},
		{header: '学校', dataIndex: 'school.schoolName'},
		{header: '生日', dataIndex: 'birthday'},
		{header: '创建日期', dataIndex: 'createTime'}
	]);

	// 存储
	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: 'user/user_list.action'}),
		reader: new Ext.data.JsonReader({
			totalProperty: 'totalProperty',
			root: 'root'
		}, [
			{name: 'userId'},
			{name: 'username'},
			{name: 'password'},
			{name: 'gender'},
			{name: 'school.schoolName'},
			{name: 'birthday'},
			{name: 'createTime'}
		]),
		remoteSort: true
	});

	// 列表 + 分页
	var grid = new Ext.grid.GridPanel({
		loadMask: true,
		width: 900,
		//height: 500,
		autoHeight: true,
		renderTo: 'userGrid',
		store: store,
		cm: cm,
		viewConfig: {
			forceFit: true
		},
		
		// 顶部工具栏
		tbar: new Ext.Toolbar({
        items:['->',{
				text: "添加用户",
				handler: addUser()
			}, {
				text: "修改用户",
				handler: modifyUser()
			}, {
				text: "删除用户",
				handler: deleteUser()
			}]
        }),
		
		// 底部分页工具栏
		bbar: new Ext.PagingToolbar({
			pageSize: 3,
			store: store,
			displayInfo: true
		})
	});
	store.load({params:{start: 0, limit: 3}});
});