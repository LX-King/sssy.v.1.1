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
    $('#show-add').dialog('close');
    $('#datagrid').datagrid('reload');
}
function dialogClose() {
    $('#show-modify').dialog('close');
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
    var url = 'sysmgr/industryGraphic.action';

    var columns = [
        [
            {field:'id', title:'ID', width:50, sortable:true, align:'center'},
            {field:'industryCode', title:'行业代码', width:100, sortable:true, align:'center'},
            {field:'industryName', title:'行业名称', width:100, align:'center'}
        ]
    ];

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
            text:'添加',
            iconCls:'icon-add',
            handler:function () {
                var rows = $(renderTo).datagrid('getRows');
                if (rows.length >= 5) {
                    Ext.Msg.alert("提示", "<br>最多只能设置[5]种行业大类!<br>");
                } else {
                    $('#open-modify-win').html('');
                    $('#open-modify-win')[0].src = url + "?method=showAdd";
                    $('#show-modify').dialog('open');
                }
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
                    Ext.Msg.alert('提示', '请选择一条记录！');

                if (rows.length == 1) {
                    Ext.Msg.alert("提示", "一次只能删除一条记录!");
                }
                for (var i = 0; i < rows.length; i++) {
                    idArrs.push(rows[i].id);
                }
                var textArr = rows[0].industryName;

                Ext.Msg.confirm('提示', '确定要删除[' + textArr + ']？', function (btn) {
                    if (btn == 'yes') {
                        $.post(url + '?method=del&id=' + idArrs[0], function (res) {
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
                    Ext.Msg.alert('提示', '请选择一条记录！');
                }
                if (rows.length > 1)
                    Ext.Msg.alert('提示', '一次只能修改一条记录！');
                else {
                    var id = rows[0].id;
                    $('#open-modify-win')[0].src = url + "?method=showModify&id=" + id;
                    $('#show-modify').dialog('open');
                }
            }
        }
    ];

    var myDataGrid = new MyDataGrid(columns, url + "?method=query", 'id', renderTo);
    myDataGrid.option.toolbar = toolbar;
    myDataGrid.createGataGrid();
});




