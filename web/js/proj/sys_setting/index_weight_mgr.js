/*
 *  FileName:menu_mgr
 *  Function:菜单管理
 *  Author:LiuXiang
 *  Date:2012-4-26:9:18:44
 *  Mail:LXiang.tyut@gmail.com
 *  Company:山西省太原市和信至诚科技有限公司
 *
 */
function roleAddDiaologClose() {
    $('#show-add').dialog('close');
    $('#datagrid').datagrid('reload');
}
function modifyClose() {
    $('#show-modify').dialog('close');
    $('#datagrid').datagrid('reload');
}
function showMenuDetail(companyscaleCode) {
    alert('alert');
}
function sessionTimeout() {
    Ext.Msg.confirm('提示', '登录时间过长，请重新登录!', function(btn) {
        if (btn == 'yes') {
            window.location.href = "login.jsp";
        }
    });
}
$(document).ready(function() {

    var renderTo = '#datagrid';
    var columns = [[
        {field:'indexCode',title:'指标编码',width:50 ,sortable:true ,align:'center'},
        {field:'indexName',title:'指标名称',width:100 ,sortable:true ,align:'center'},
        {field:'unusualRange',title:'异常区间',width:100 ,sortable:true ,align:'center'},
        {field:'unusualRange2',title:'异常区间2',width:100,align:'center'},
        {field:'excellentRange',title:'优势区间',width:100 , align:'center'},
        {field:'usualRange',title:'正常区间',width:100,align:'center'} ,
        {field:'weight',title:'权值',width:100,align:'center'}
    ]];

    /*初始化添加菜单*/
    $("#show-add").dialog({
        title:'添加',
        modal:true,
        closed:true
    });

    /*初始化修改菜单*/
    $("#show-modify").dialog({
        title:'修改',
        modal:true,
        closed:true
    });


    var toolbar = [
        {
            text:'修改',
            iconCls:'icon-edit',
            handler:function() {
                var rows = $(renderTo).datagrid('getSelections');
                if (rows.length == 0) {
                    Ext.Msg.alert('Warning', '请选择一条记录！');
                }
                if (rows.length > 1)
                    Ext.Msg.alert('Warning', '一次只能修改一条记录！');
                else {
                    $('#open-modify-win').html("");
                    var id = rows[0].indexCode;
                    $('#open-modify-win')[0].src = 'sysmgr/indexWeight_showModify.action?indexCode=' + id;
                    $('#show-modify').dialog('open');
                }
            }
        }];

    var url = 'sysmgr/indexWeight_query.action';
    var myDataGrid = new MyDataGrid(columns, url, 'id', renderTo);
    myDataGrid.option.toolbar = toolbar;
    /*myDataGrid.option.title = '发展指数过滤条件维护';*/
    myDataGrid.createGataGrid();

    /**//*实现动态加载查询条件中的菜单节点*//*
     $('#query_companyscaleType').combotree({
     url:'sysmgr/companyScale_getJSONTree.action?parentId=-1' ,
     multiple:true,
     required:false,
     onBeforeExpand:function(node) {
     $('#query_companyscaleType').combotree("tree").tree("options").url = "sysmgr/companyscale_getJSONTree.action?parentId=" + node.id;
     }
     });*/

    /*注册点击查询按钮的事件*/
    $("#doQuery").click(function() {
        var indexName = $('#query_indexName').val();
        var queryParams = new Object();
        if (indexName != null && indexName != '') {
            queryParams.indexName = indexName;
        }
        $(myDataGrid.renderTo).datagrid('options').queryParams = queryParams;
        $(myDataGrid.renderTo).datagrid('reload');
    });
});




