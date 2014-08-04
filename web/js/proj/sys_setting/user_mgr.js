/*
 *  FileName:menu_mgr
 *  Function:菜单管理
 *  Author:LiuXiang
 *  Date:2012-4-26:9:18:44
 *  Mail:LXiang.tyut@gmail.com
 *  Company:山西省太原市和信至诚科技有限公司
 *
 */
function userAddDiaologClose() {
    $('#user-add').dialog('close');
    $('#datagrid').datagrid('reload');
}
function userModifyDiaologClose() {
    $('#user-modify').dialog('close');
    $('#datagrid').datagrid('reload');
}

$(document).ready(function() {
    var renderTo = '#datagrid';
    var columns = [[
        {field:'id',title:'用户ID',width:100 ,sortable:true ,align:'center'},
        {field:'code',title:'用户代码',width:100 , align:'center'},
        {field:'name',title:'用户名称',width:100,align:'center' },
        {field:'swjgdm',title:'所属机关名称',width:150,align:'center' ,formatter:function(val,rec){
            var taxUnitName = '无';
            $.ajax({
                type:'post',
                url:'sysmgr/user_getTaxUnitName.action',
                data:{swjgdm:val},
                async:false,
                success:function(res) {
                    taxUnitName = res;
                }
            });
            return taxUnitName;
        }},
        {field:'roleCode',title:'用户角色',width:100,align:'center', formatter:function(val, rec) {
            var roleName = '无';
            $.ajax({
                type:'post',
                url:'sysmgr/user_getRoleName.action',
                data:{roleCode:val},
                async:false,
                success:function(res) {
                    roleName = res;
                }
            });
            return roleName;
        }},
        {field:'isExpired',title:'是否有效',width:100,align:'center',formatter:function(val, rec) {
            if (val == '1') {
                return '有效';
            } else {
                return '无效';
            }
        }}
    ]];

    /*初始化添加菜单*/
    $("#user-add").dialog({
        title:'添加菜单',
        modal:true,
        closed:true
    });

    /*初始化修改菜单*/
    $("#user-modify").dialog({
        title:'修改菜单',
        modal:true,
        closed:true
    });


    var toolbar = [
        {
            text:'添加',
            iconCls:'icon-add',
            handler:function() {
                $('#openShowUserModify').html('');
                $('#openShowUserModify')[0].src = 'sysmgr/user_showAddUI.action';
                $('#user-modify').dialog('setTitle', '添加').dialog('open');
            }
        },'-',{
            text:'删除',
            iconCls:'icon-remove',
            handler:function() {
                var rows = $(renderTo).datagrid('getSelections');
                var idArrs = [];
                for (i = 0; i < rows.length; i++) {
                    idArrs.push(rows[i].id);
                }
                var textArr = "";
                for (i = 0; i < rows.length; i++) {
                    textArr += rows[i].name + ' ';
                }
                Ext.Msg.confirm('提示', '确定要删除[' + textArr + ']？', function(btn) {
                    if (btn == 'yes') {
                        $.post('sysmgr/user_del.action?idArrs=' + idArrs, function(res) {
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
                    $('#openShowUserModify').html('');
                    var id = rows[0].id;
                    $('#openShowUserModify')[0].src = 'sysmgr/user_showModify.action?userId=' + id;
                    $('#user-modify').dialog('setTitle', '修改').dialog('open');
                }
            }
        }];
    var url = 'sysmgr/user_showUserList.action';
    var myDataGrid = new MyDataGrid(columns, url, 'id', renderTo);
    myDataGrid.option.toolbar = toolbar;
    myDataGrid.createGataGrid();


    /*注册点击查询按钮的事件*/
    $("#doUserQuery").click(function() {
        var userName = $('#query_userName').val();
        var userType = $('#query_userType').combobox('getValue');

        var queryParams = new Object();
        if (userName != null && userName != '') {
            queryParams.userName = userName;
        }
        if (userType != null && userType != '')
            queryParams.userType = userType;


        $(myDataGrid.renderTo).datagrid('options').queryParams = queryParams;
        $(myDataGrid.renderTo).datagrid('reload');
    });
});




