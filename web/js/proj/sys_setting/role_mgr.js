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
    $('#role-add').dialog('close');
    $('#datagrid').datagrid('reload');
}
function roleModifyDiaologClose() {
    $('#role-modify').dialog('close');
    $('#datagrid').datagrid('reload');
}


function showDetail(code,name) {
    $("#role-add").dialog("open");
    $("#roleName").val(name);
    $("#roleName").text(name);
    $("#categorys").html("");
    var rootNode = new Ext.tree.AsyncTreeNode({
        text:'功能菜单',
        expanded:true,
        leaf:false,
        id:'0',
        singleClickExpand:true
    });
    var tree = new Ext.tree.TreePanel({
        checked:true,
        split:true,
        border:false,
        autoWidth:true,
        height:320,
        autoScroll:true,
        margins:'0 0 0 5',
        loader:new Ext.tree.TreeLoader({}),
        rootVisible:true,
        root:rootNode,
        listeners:{
            beforeload:function (node) {
                this.loader.dataUrl = 'sysmgr/role_showDetail.action?roleCode='+code+'&parentId=' + node.id;
            }
        }
    });
    tree.render(Ext.get("categorys"));
    tree.expandAll();

}

$(document).ready(function () {
    var renderTo = '#datagrid';
    var columns = [
        [
            {field:'roleCode', title:'角色ID', width:100, sortable:true, align:'center'},
            {field:'roleName', title:'角色名称', width:100, align:'center'},
            {field:'categorys', title:'可操作菜单', width:100, align:'center', formatter:function (val, rec) {
                if (val != null && val != '')
                    return '<a href="javascript:void(0);" onclick="showDetail(\''+rec.roleCode+'\' ,\''+ rec.roleName+'\')" style="border-bottom:1px solid blue;color:blue;">查看详情</a>';
                else
                    return '无';
            }}
        ]
    ];

    /*初始化添加菜单*/
    $("#role-add").dialog({
        title:'查看详情',
        modal:true,
        closed:true
    });

    /*初始化修改菜单*/
    $("#role-modify").dialog({
        title:'修改角色',
        modal:true,
        closed:true
    });


    var toolbar = [
        {
            text:'添加',
            iconCls:'icon-add',
            handler:function () {
                $('#openShowRoleModify').html('');
                $('#openShowRoleModify')[0].src = 'sysmgr/role_showAddUI.action';
                $('#role-modify').dialog('setTitle', '添加').dialog('open');
            }
        },
        '-',
        {
            text:'删除',
            iconCls:'icon-remove',
            handler:function () {
                var rows = $(renderTo).datagrid('getSelections');
                var idArrs = [];
                if (rows.length == 0)
                    Ext.Msg.alert('warning', '请选择一条记录！');

                for (i = 0; i < rows.length; i++) {
                    idArrs.push(rows[i].roleCode);
                }
                var textArr = "";
                for (i = 0; i < rows.length; i++) {
                    textArr += rows[i].roleName + ' ';
                }
                Ext.Msg.confirm('提示', '确定要删除[' + textArr + ']？', function (btn) {
                    if (btn == 'yes') {
                        $.post('sysmgr/role_del.action?idArrs=' + idArrs, function (res) {
                            Ext.Msg.alert('info', '删除成功！');
                            $(renderTo).datagrid('load');
                        });
                    }
                });
            }
        },
        '-',
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
                    $('#openShowRoleModify').html('');
                    var id = rows[0].roleCode;
                    $('#openShowRoleModify')[0].src = 'sysmgr/role_showModify.action?roleCode=' + id;
                    $('#role-modify').dialog('setTitle', '修改').dialog('open');
                }
            }
        }
    ];

    var url = 'sysmgr/role_showRoleList.action';
    var myDataGrid = new MyDataGrid(columns, url, 'id', renderTo);
    myDataGrid.option.toolbar = toolbar;
    myDataGrid.createGataGrid();

    /*实现动态加载查询条件中的菜单节点*/
    $('#query_roleType').combotree({
        url:'sysmgr/role_getJSONTree.action?parentId=-1',
        multiple:true,
        required:false,
        onBeforeExpand:function (node) {
            $('#query_roleType').combotree("tree").tree("options").url = "sysmgr/role_getJSONTree.action?parentId=" + node.id;
        }
    });

    /*注册点击查询按钮的事件*/
    $("#doRoleQuery").click(function () {
        var roleName = $('#query_roleName').val();
        var menuCodes = $('#query_roleType').combogrid('getValues');
        var queryParams = new Object();
        if (roleName != null && roleName != '') {
            queryParams.roleName = roleName;
        }
        if (menuCodes != null && menuCodes != '')
            queryParams.menuCodes = menuCodes;

        alert(menuCodes);
        $(myDataGrid.renderTo).datagrid('options').queryParams = queryParams;
        $(myDataGrid.renderTo).datagrid('reload');
    });
});




