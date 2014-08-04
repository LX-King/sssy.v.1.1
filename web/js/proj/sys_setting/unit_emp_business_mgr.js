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
    $('#unitEmpBusiness-add').dialog('close');
    $('#datagrid').datagrid('reload');
}
function unitEmpBusinessModifyDiaologClose() {
    $('#unitEmpBusiness-modify').dialog('close');
    $('#datagrid').datagrid('reload');
}
function showMenuDetail(unitEmpBusinessCode) {
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
        {field:'ebCode',title:'行业大类代码',width:100 ,sortable:true ,align:'center'},
        {field:'ebName',title:'行业大类名称',width:100 , align:'center'},
        {field:'industryCode',title:'产业代码',width:100,align:'center' /*,formatter:function(val, rec) {
         if (val != null && val != '')
         return '<a href="javascript:void(0)" onclick="showMenuDetail(' + rec.unitEmpBusinessCode + ') " style="border-bottom:1px solid blue;color:blue;">查看详情</a>';
         else
         return '无';
         }*/},
        {field:'flag',title:'是否是本单位重点行业',width:140,align:'center' , formatter:function(val, rec) {
            if (val != null && val == 'N')
                return '不是'
            else
                return '是';
            }
        }
    ]];

    /*初始化添加菜单*/
    $("#unitEmpBusiness-add").dialog({
        title:'添加',
        modal:true,
        closed:true
    });

    /*初始化修改菜单*/
    $("#unitEmpBusiness-modify").dialog({
        title:'修改',
        modal:true,
        closed:true
    });


    var toolbar = [
        {
            text:'添加',
            iconCls:'icon-add',
            handler:function() {
                $('#unitEmpBusiness-add #openShowUnitEmpBusinessAdd')[0].src = 'sysmgr/unitEmpBusiness_showAddUI.action';
                $('#unitEmpBusiness-add').dialog('open');
            }
        },'-',{
            text:'删除',
            iconCls:'icon-remove',
            handler:function() {
                var rows = $(renderTo).datagrid('getSelections');
                var idArrs = [];
                if (rows.length == 0)
                    Ext.Msg.alert('warning', '请选择一条记录！');

                for (i = 0; i < rows.length; i++) {
                    idArrs.push(rows[i].unitEmpBusinessCode);
                }
                var textArr = "";
                for (i = 0; i < rows.length; i++) {
                    textArr += rows[i].ebName + ' ';
                }
                Ext.Msg.confirm('提示', '确定要删除[' + textArr + ']？', function(btn) {
                    if (btn == 'yes') {
                        $.post('sysmgr/unitEmpBusiness_del.action?idArrs=' + idArrs, function(res) {
                            Ext.Msg.alert('info', '删除成功！');
                            $(renderTo).datagrid('load');
                        });
                    }
                });
            }
        },'-',{
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
                    var id = rows[0].csCode;
                    $('#openShowUnitEmpBusinessModify')[0].src = 'sysmgr/unitEmpBusiness_showModify.action?ebCode=' + id;
                    $('#unitEmpBusiness-modify').dialog('open');
                }
            }
        }];

    var url = 'sysmgr/unitEmpBusiness_query.action';
    var myDataGrid = new MyDataGrid(columns, url, 'csCode', renderTo);
    myDataGrid.option.toolbar = toolbar;
    myDataGrid.createGataGrid();

    /*注册点击查询按钮的事件*/
    $("#doUnitEmpBusinessQuery").click(function() {
        var ebName = $('#query_ebName').val();
        var queryParams = new Object();
        if (ebName != null && ebName != '') {
            queryParams.ebName = ebName;
        }
        $(myDataGrid.renderTo).datagrid('options').queryParams = queryParams;
        $(myDataGrid.renderTo).datagrid('reload');
    });
});




