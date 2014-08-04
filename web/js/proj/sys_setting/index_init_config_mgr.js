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
function modifyDiaologClose() {
    $('#modify').dialog('close');
    $('#datagrid').datagrid('reload');
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
        {field:'indexCode',title:'指标代码',width:50 ,sortable:true ,align:'center'},
        {field:'indexName',title:'指标名称',width:150 , align:'center'},
        {field:'unusualRange',title:'异常区间1',width:100 , align:'center',formatter:function(val,rec){
            return "["+rec.unusualRange+",+∞)";
        }},
        {field:'usualRange',title:'正常区间',width:100 , align:'center',formatter:function(val,rec){
            return "["+rec.usualRange+","+rec.excellentRange+")";
        }},
        {field:'unusualRange2',title:'异常区间2',width:100 , align:'center',formatter:function(val,rec){
            return "["+rec.unusualRange2+","+rec.inferiorRange+")";
        }},
        {field:'excellentRange',title:'优势区间',width:100 , align:'center',formatter:function(val,rec){
            return "["+rec.excellentRange+","+rec.unusualRange+")";
        }},
        {field:'inferiorRange',title:'劣势区间',width:100 , align:'center',formatter:function(val,rec){
            return "["+rec.inferiorRange+","+ rec.usualRange+"]";
        }},
        {field:'unusualRangeDef',title:'异常区间1缺省',width:100 , align:'center',formatter:function(val,rec){
            return "["+rec.unusualRange+",+∞)";
        }},
        {field:'usualRangeDef',title:'正常区间缺省',width:100 , align:'center',formatter:function(val,rec){
            return "["+rec.usualRangeDef+","+rec.excellentRangeDef+")";
        }},
        {field:'unusualRange2Def',title:'异常区间2缺省',width:100 , align:'center',formatter:function(val,rec){
            return "["+rec.unusualRange2Def+","+rec.inferiorRangeDef+")";
        }},
        {field:'excellentRangeDef',title:'优势区间缺省',width:100 , align:'center',formatter:function(val,rec){
            return "["+rec.excellentRangeDef+","+rec.unusualRangeDef+")";
        }},
        {field:'inferiorRangeDef',title:'劣势区间缺省',width:100 , align:'center',formatter:function(val,rec){
            return "["+rec.inferiorRangeDef+","+rec.usualRangeDef+")";
        }}
    ]];

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
                    $('#open-modify-win')[0].src = 'sysmgr/indexInit_showModify.action?indexCode=' + id;
                    $('#modify').dialog('open');
                }
            }
        }];

    var url = 'sysmgr/indexInit_query.action';
    var myDataGrid = new MyDataGrid(columns, url, 'indexCode', renderTo);
    myDataGrid.option.toolbar = toolbar;
    myDataGrid.createGataGrid();


    /*注册点击查询按钮的事件*/
    $("#doQuery").click(function() {
        var name = $('#query_name').val();
        var queryParams = new Object();
        if (name != null && name != '') {
            queryParams.name = name;
        }
        $(myDataGrid.renderTo).datagrid('options').queryParams = queryParams;
        $(myDataGrid.renderTo).datagrid('reload');
    });
});




