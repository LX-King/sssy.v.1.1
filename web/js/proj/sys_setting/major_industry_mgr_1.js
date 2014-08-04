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
    var lastIndex ;
    $(renderTo).datagrid({
        pagination:true,
        width : '100%',
        height :'auto',
        fitColumns: true,
        striped :true,
        rownumbers:true,
        toolbar:[{
            text:'保存',
            iconCls:'icon-save',
            handler:function() {
                var rows = $(renderTo).datagrid('getChanges');
                var datas = [];
                var len = rows.length;
                var jsonData = '({"total":' + len + ',"data":[';

                for (var i = 0; i < len; i++) {
                    var industryCode = rows[i].industryName.length > 3 ? rows[i].industryCode : rows[i].industryName;
                    jsonData += '{"majorIndustryCode":"' + rows[i].majorIndustryCode + '",';
                    jsonData += '"industryCode":"' + industryCode + '",';
                    jsonData += '"flag":"' + rows[i].flag + '"}';
                    if (i + 2 <= len) {
                        jsonData += ',';
                    }
                }
                jsonData += ']})';
                var json = eval(jsonData);
                $.ajax({
                    url:'sysmgr/majorIndustry_modify.action',
                    method:'post',
                    data:{rows:json},
                    success:function(data) {
                        if (data == 'succ') {
                            $(renderTo).datagrid('acceptChanges');
                            Ext.Msg.alert('提示', '修改成功!', function(e) {
                                $(renderTo).datagrid('reload')
                            });
                        } else {
                            Ext.Msg.alert('提示', '修改失败,请与管理员联系!', function(e) {
                                $(renderTo).datagrid('rejectChanges')
                            });

                        }
                    }
                });
            }
        },'-',{
            text:'取消',
            iconCls:'icon-undo',
            handler:function() {
                $(renderTo).datagrid('rejectChanges');
            }
        }],
        onBeforeLoad
                :
                function() {
                    $(this).datagrid('rejectChanges');
                }

        ,
        onClickRow:function(rowIndex) {
            if (lastIndex != rowIndex) {
                $(renderTo).datagrid('endEdit', lastIndex);
                $(renderTo).datagrid('beginEdit', rowIndex);
            }
            lastIndex = rowIndex;
        }
    });

    /*注册点击查询按钮的事件*/
    $("#doQuery").click(function() {
        var csName = $('#query_name').val();
        var queryParams = new Object();
        if (csName != null && csName != '') {
            queryParams.csName = csName;
        }
        $(renderTo).datagrid('options').queryParams = queryParams;
        $(renderTo).datagrid('reload');
    });
});





