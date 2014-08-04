/*
 *  FileName:menu_mgr
 *  Function:菜单管理
 *  Author:LiuXiang
 *  Date:2012-4-26:9:18:44
 *  Mail:LXiang.tyut@gmail.com
 *  Company:山西省太原市和信至诚科技有限公司
 *
 */
function addDiaologClose() {
    $('#add').dialog('close');
    $('#datagrid').datagrid('reload');
}
function dialogClose() {
    $('#modify').dialog('close');
    $('#datagrid').datagrid('reload');
}

function sessionTimeout() {
    Ext.Msg.confirm('提示', '登录时间过长，请重新登录!', function (btn) {
        if (btn == 'yes') {
            window.location.href = "login.jsp";
        }
    });
}
$(document).ready(function () {

    var renderTo = '#datagrid';
    var frozenColumns = [
        [
            {field:'indexCode', title:'指标代码', width:80, sortable:true, align:'center'},
            {field:'indexName', title:'指标名称', width:100, align:'center'}
        ]
    ];
    var columns = [
        [
            {title:'异常区间1', colspan:3 },
            {title:'异常区间2', colspan:3 },
            {title:'正常区间', colspan:3 },
            {title:'优势区间', colspan:3 },
            {title:'劣势区间', colspan:3 }
        ],
        [
            {field:'yc1_tz', title:'特征', width:130, align:'center' },
            {field:'yc1_yd', title:'疑点', width:150, align:'center'},
            {field:'yc1_jy', title:'建议', width:150, align:'center'},
            {field:'yc2_tz', title:'特征', width:130, align:'center' },
            {field:'yc2_yd', title:'疑点', width:150, align:'center'},
            {field:'yc2_jy', title:'建议', width:150, align:'center'},
            {field:'zc_tz', title:'特征', width:130, align:'center' },
            {field:'zc_yd', title:'疑点', width:150, align:'center'},
            {field:'zc_jy', title:'建议', width:150, align:'center'},
            {field:'ys_tz', title:'特征', width:130, align:'center' },
            {field:'ys_yd', title:'疑点', width:150, align:'center'},
            {field:'ys_jy', title:'建议', width:150, align:'center'},
            {field:'ls_tz', title:'特征', width:130, align:'center' },
            {field:'ls_yd', title:'疑点', width:150, align:'center'},
            {field:'ls_jy', title:'建议', width:150, align:'center'}
        ]
    ];

    /*初始化添加菜单*/
    $("#add").dialog({
        title:'添加',
        modal:true,
        closed:true
    });

    /*初始化修改菜单*/
    $("#modify").dialog({
        title:'修改',
        modal:true,
        closed:true
    });


    var toolbar = [
        {
            text:'修改',
            iconCls:'icon-edit',
            handler:function () {
                var rows = $(renderTo).datagrid('getSelections');
                if (rows.length == 0) {
                    Ext.Msg.alert('Warning', '请选择一条记录！');
                }
                if (rows.length > 1)
                    Ext.Msg.alert('Warning', '一次只能修改一条记录！');
                else {
                    var zb = rows[0].indexCode;
                    $("#openShowModify").html("");
                    $('#openShowModify')[0].src = "sysmgr/indexFeature_showModify.action?zb="+zb;
                    $('#modify').dialog('open');
                }
            }
        }
    ];

    var url = 'sysmgr/indexFeature_query.action';
    /*new MyDataGrid(columns, url, 'id', renderTo);
     myDataGrid.option.toolbar = toolbar;
     myDataGrid.option.frozenColumns = frozenColumns;
     myDataGrid.createGataGrid();
     */

    var myDataGrid = $('#datagrid').datagrid({
        iconCls:'icon-save',
        width:'auto',
        height:'auto',
        nowrap:false,
        striped:true,
        collapsible:true,
        url:'sysmgr/indexFeature_query.action',
        sortName:'code',
        sortOrder:'desc',
        remoteSort:false,
        frozenColumns:frozenColumns,
        columns:columns,
        pagination:true,
        toolbar:toolbar});

    /*注册点击查询按钮的事件*/
    $("#doCompanyQuery").click(function () {
        var indexName = $('#query_name').val();
        var queryParams = new Object();
        if (indexName != null && indexName != '') {
            queryParams.indexName = indexName;
        }
        $(myDataGrid.renderTo).datagrid('options').queryParams = queryParams;
        $(myDataGrid.renderTo).datagrid('reload');
    });
});




