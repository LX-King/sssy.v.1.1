/*
 *  FileName:menu_mgr
 *  Function:菜单管理
 *  Author:LiuXiang
 *  Date:2012-4-26:9:18:44
 *  Mail:LXiang.tyut@gmail.com
 *  Company:山西省太原市和信至诚科技有限公司
 *
 */
function menuAddDiaologClose() {
    $('#menu-add').dialog('close');
    $('#datagrid').datagrid('reload');
}
function menuModifyDiaologClose() {
    $('#menu-modify').dialog('close');
    $('#datagrid').datagrid('reload');
}

$(document).ready(function () {
    /*实现动态加载查询条件中的父亲节点*/
    $('#query_menuParentId').combotree({
        url:'sysmgr/menu_getJSONTree.action?parentId=-1',
        onBeforeExpand:function (node) {
            $('#query_menuParentId').combotree("tree").tree("options").url = "sysmgr/menu_getJSONTree.action?parentId=" + node.id;
        }
    });


    var renderTo = '#datagrid';
    var columns = [
        [
            {field:'id', title:'菜单ID', width:100, sortable:true, align:'center'},
            {field:'text', title:'菜单名称', width:100, align:'center'},
            {field:'icon', title:'菜单图标', width:100, align:'center', formatter:function (val, rec) {
                if (val != null && val != '')
                    return '<img src="' + val + '"/>';
                else
                    return '无';
            }},
            {field:'url', title:'URL链接', width:100, align:'center', formatter:function (val, rec) {
                if (val != null && val != '')
                    return val;
                else
                    return '无';
            }},
            {field:'href', title:'超链接地址', width:100, align:'center', formatter:function (val, rec) {
                if (val != null && val != '')
                    return val;
                else
                    return '无';
            }},
            {field:'order', title:'显示顺序', width:100, align:'center', formatter:function (val, rec) {
                if (val != null && val != '' && val != 0)
                    return val;
                else
                    return '无';
            }},
            {field:'hrefTarget', title:'超链接打开', width:100, align:'center', formatter:function (val, rec) {
                if (val != null && val != '')
                    return val;
                else
                    return '无';
            }},
            {field:'leaf', title:'是否是叶子', width:100, align:'center', formatter:function (val, rec) {
                if (val != null && val != '')
                    if (val == 'y')
                        return '是';
                    else
                        return '否';
                else
                    return '无';
            }},
            {field:'parentId', title:'父节点', width:100, align:'center', formatter:function (val, rec) {
                var parentName = '无';
                $.ajax({
                    type:'post',
                    url:'sysmgr/menu_getParentMenu.action',
                    data:{parentId:val},
                    async:false,
                    success:function (res) {
                        parentName = res;
                    }
                });
                return parentName;
            }},
            {field:'desc', title:'描述', width:100, align:'center'}
        ]
    ];

    /*初始化添加菜单*/
    $("#menu-add").dialog({
        title:'添加菜单',
        modal:true,
        closed:true
    });

    /*初始化修改菜单*/
    $("#menu-modify").dialog({
        title:'修改菜单',
        modal:true,
        closed:true
    });


    var toolbar = [
        {
            text:'添加',
            iconCls:'icon-add',
            handler:function () {
                $('#menu-modify').dialog('close');
                var url = 'sysmgr/menu_showAdd.action';
                $('#openShowMenuModify').html('');
                $('#openShowMenuModify')[0].src = url;
                $('#menu-modify').dialog('setTitle', '添加');
                $("#menu-modify").dialog('open');
            }
        },
        '-',
        {
            text:'删除',
            iconCls:'icon-remove',
            handler:function () {
                var rows = $(renderTo).datagrid('getSelections');
                var idArrs = [];
                if (rows.length == 0) {
                    Ext.Msg.alert('Warning', '请选择要删除的记录！');
                } else {

                    for (i = 0; i < rows.length; i++) {
                        idArrs.push(rows[i].id);
                    }
                    var textArr = "";
                    for (i = 0; i < rows.length; i++) {
                        textArr += rows[i].text + ' ';
                    }
                    Ext.Msg.confirm('提示', '确定要删除[' + textArr + ']？', function (btn) {
                        if (btn == 'yes') {
                            $.post('sysmgr/menu_del.action?idArrs=' + idArrs, function (res) {
                                Ext.Msg.alert('info', '删除成功！');
                                $(renderTo).datagrid('load');
                            });
                        }
                    });

                }
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
                    var url = '';
                    var id = rows[0].id;
                    $('#menu-modify').dialog('close');
                    $('#openShowMenuModify').html('');
                    $('#openShowMenuModify')[0].src = 'sysmgr/menu_showModify.action?id=' + id;
                    $('#menu-modify').dialog('setTitle', '修改');
                    $('#menu-modify').dialog('open');
                }
            }
        }
    ];
    var url = 'sysmgr/menu_showMenuList.action';
    var myDataGrid = new MyDataGrid(columns, url, 'id', renderTo);
    myDataGrid.option.toolbar = toolbar;
    myDataGrid.createGataGrid();


    /*注册点击查询按钮的事件*/
    $("#doMenuQuery").click(function () {
        var menuName = $('#query_menuName').val();
        var menuType = $('#query_menuType').combogrid('getValue');
        var menuParentId = $("#query_menuParentId").combotree('getValue');

        var queryParams = new Object();
        if (menuName != null && menuName != '') {
            queryParams.menuName = menuName;
        }
        if (menuType != null && menuType != '')
            queryParams.menuType = menuType;

        if (menuParentId != null && menuParentId != '')
            if (menuParentId != '功能菜单')
                queryParams.menuParentId = menuParentId;
            else
                queryParams.menuParentId = 0;

        $(myDataGrid.renderTo).datagrid('options').queryParams = queryParams;
        $(myDataGrid.renderTo).datagrid('reload');
    });
});




