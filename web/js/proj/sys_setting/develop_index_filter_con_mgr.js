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
function modifyDialogClose() {
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
        {field:'id',title:'ID',width:50 ,sortable:true ,align:'center'},
        {field:'maxValue',title:'最大值',width:100 ,sortable:true ,align:'center'},
        {field:'minValue',title:'最小值',width:100 , align:'center'},
        {field:'maxValueDef',title:'最大值缺省',width:100,align:'center'} ,
        {field:'minValueDef',title:'最小值缺省',width:100,align:'center'}
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
        /*{
            text:'添加',
            iconCls:'icon-add',
            handler:function() {
                $('#open-modify-win').html('');
                $('#open-modify-win')[0].src = 'sysmgr/filterCon_showAddUI.action';
                $('#show-modify').dialog('open');
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
                        $.post('sysmgr/filterCon_del.action?idArrs=' + idArrs, function(res) {
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
                    var id = rows[0].id;
                    $('#open-modify-win')[0].src = 'sysmgr/filterCon_showModify.action?id=' + id;
                    $('#show-modify').dialog('open');
                }
            }
        }];

    var url = 'sysmgr/filterCon_query.action';
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
        /* var csName = $('#query_csName').val();
         var queryParams = new Object();
         if (csName != null && csName != '') {
         queryParams.csName = csName;
         }
         $(myDataGrid.renderTo).datagrid('options').queryParams = queryParams;*/
        $(myDataGrid.renderTo).datagrid('reload');
    });
});




