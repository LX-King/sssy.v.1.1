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
    $('#companyscale-add').dialog('close');
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
    var lastIndex ;
    var isAdd = false;
    var addIndex = [];

    /*企业规模重置对话框初始化*/
    var dialog = $('#dialog').dialog({
        title:'选择年份',
        width:330,
        height:200,
        closed:true,
        modal:true,
        buttons:[{
            text:'重置',
            iconCls:'icon-ok',
            handler:function() {
                var year = $('#year').val();
                var season = $('#season').val();
                $.ajax({
                    url:'sysmgr/companyScale_reset.action',
                    data:{year:year , season:season},
                    success:function(data) {
                        if (data == 'succ') {
                            Ext.Msg.alert('提示', '重置成功!' , function(e){
                                dialog.dialog('close');
                            });
                        } else {
                            Ext.Msg.alert('提示', '重置失败,请与管理员联系!' , function(e){
                                dialog.dialog('close');
                            });
                        }
                    }
                });
            }
        },{
            text:'取消',
            handler:function() {
                dialog.dialog('close');
            }
        }]
    });

    $(renderTo).datagrid({
        pagination:true,
        width : '100%',
        height :'auto',
        fitColumns: true,
        striped :true,
        rownumbers:true,
       /* frozenColumns:[[
            {field:'csCode',checkbox:true},
        ]],*/
        toolbar:[
            {
                text:'添加',
                iconCls:'icon-add',
                handler:function() {
                    isAdd = true;

                    $(renderTo).datagrid('endEdit', lastIndex);
                    $(renderTo).datagrid('appendRow', {
                        csName:'',
                        minLimit:'',
                        maxLimit:''
                    });
                    lastIndex = $(renderTo).datagrid('getRows').length - 1;
                    $(renderTo).datagrid('selectRow', lastIndex);
                    $(renderTo).datagrid('beginEdit', lastIndex);
                    addIndex.push(lastIndex);
                }
            },'-',{
                text:'删除',
                iconCls:'icon-remove',
                handler:function() {
                    var rows = $(renderTo).datagrid('getSelections');
                    if (rows) {
                        Ext.Msg.alert("警告", "您确定要删除这[" + rows.length + "]条数据?", function(e) {
                            var idArrs = "";
                            for (var i = 0; i < rows.length; i++) {
                                idArrs += rows[i].csCode;
                                if (i + 2 < rows.length) {
                                    idArrs += ',';
                                }
                            }
                            $.ajax({
                                method:'post',
                                url:"sysmgr/companyScale_del.action",
                                data:{idArrs:idArrs},
                                success:function(data) {
                                    Ext.Msg.alert('提示', '删除成功!', function() {
                                        $(renderTo).datagrid('reload');
                                    });
                                }
                            });
                        })

                    }
                }
            },'-',{
                text:'保存',
                iconCls:'icon-save',
                handler:function() {
                    var addedRecordslen = addIndex.length;
                    for (var j = 0; j < addedRecordslen; j++) {
                        $(renderTo).datagrid('endEdit', addIndex[j]);
                    }
                    if (isAdd) {
                        var addRecords = $(renderTo).datagrid('getChanges', 'inserted') ;
                        var jsonData = '({"total":' + addRecords.length + ',"data":[';
                        for (var i = 0; i < addRecords.length; i++) {
                            jsonData += '{"csName":"' + addRecords[i].csName + '",';
                            jsonData += '"minLimit":"' + addRecords[i].minLimit + '",';
                            jsonData += '"maxLimit":"' + addRecords[i].maxLimit + '"}';
                            if (i < addRecords.length - 1) {
                                jsonData += ',';
                            }
                        }
                        jsonData += ']})';
                        var json = eval(jsonData);
                        $.ajax({
                            url:'sysmgr/companyScale_add.action',
                            method:'post',
                            data:{rows:json},
                            success:function(data) {
                                if (data == 'succ') {
                                    $(renderTo).datagrid('acceptChanges');
                                    Ext.Msg.alert('提示', '添加成功!', function(e) {
                                        $(renderTo).datagrid('reload')
                                    });
                                } else {
                                    Ext.Msg.alert('提示', '添加失败,请与管理员联系!', function(e) {
                                        $(renderTo).datagrid('rejectChanges')
                                    });

                                }
                                isAdd = false;
                            }
                        });

                    } else {
                        var rows = $(renderTo).datagrid('getChanges');
                        var len = rows.length;
                        var jsonData = '({"total":' + len + ',"data":[';
                        for (var i = 0; i < len; i++) {
                            jsonData += '{"csCode":"' + rows[i].csCode + '",';
                            jsonData += '"csName":"' + rows[i].csName + '",';
                            jsonData += '"minLimit":"' + rows[i].minLimit + '",';
                            jsonData += '"maxLimit":"' + rows[i].maxLimit + '"}';
                            if (i + 2 <= len) {
                                jsonData += ',';
                            }
                        }
                        jsonData += ']})';
                        var json = eval(jsonData);
                        $.ajax({
                            url:'sysmgr/companyScale_modify.action',
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
                }
            },'-',{
                text:'企业规模重置',
                iconCls:'icon-edit',
                handler:function() {
                    dialog.dialog('open');
                }

            },'-',{
                text:'取消',
                iconCls:'icon-undo',
                handler:function() {
                    $(renderTo).datagrid('rejectChanges');
                }
            }],
        onBeforeLoad:
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
        var name = $('#query_csName').val();
        var queryParams = new Object();
        if (name != null && name != '') {
            queryParams.name = name;
        }
        $(renderTo).datagrid('options').queryParams = queryParams;
        $(renderTo).datagrid('reload');
    });
});




