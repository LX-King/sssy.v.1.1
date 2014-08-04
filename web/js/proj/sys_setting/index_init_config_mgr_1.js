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
        {field:'indexCode',title:'指标代码',width:100 ,sortable:true ,align:'center'},
        {field:'indexName',title:'指标名称',width:100 , align:'center'},
        {field:'indexWeight',title:'指标权重',width:100,align:'center'} 
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
        /*{
            /*text:'添加',
            iconCls:'icon-add',
            handler:function() {
                $('#openShowModify').html("");
                $('#openShowModify')[0].src = 'sysmgr/weight_showAddUI.action';
                $('#modify').dialog('open');
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
                    idArrs.push(rows[i].id);
                }
                var textArr = "";
                for (i = 0; i < rows.length; i++) {
                    textArr += rows[i].id + ' ';
                }
                Ext.Msg.confirm('提示', '确定要删除[' + textArr + ']？', function(btn) {
                    if (btn == 'yes') {
                        $.post('sysmgr/weight_del.action?idArrs=' + idArrs, function(res) {
                            Ext.Msg.alert('info', '删除成功！');
                            $(renderTo).datagrid('load');
                        });
                    }
                });
            }
        },'-',*/{
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
                    $('#openShowModify').html("");
                    var id = rows[0].id;
                    $('#openShowModify')[0].src = 'sysmgr/weight_showModify.action?id=' + id;
                    $('#modify').dialog('open');
                }
            }
        }];

    var url = 'sysmgr/weight_query.action';
    var myDataGrid = new MyDataGrid(columns, url, 'id', renderTo);
    myDataGrid.option.toolbar = toolbar;
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
    $("#doCompanyQuery").click(function() {
        var indexName = $('#query_name').val();
        var queryParams = new Object();
        if (indexName != null && indexName != '') {
            queryParams.indexName = indexName;
        }
        $(myDataGrid.renderTo).datagrid('options').queryParams = queryParams;
        $(myDataGrid.renderTo).datagrid('reload');
    });
});




