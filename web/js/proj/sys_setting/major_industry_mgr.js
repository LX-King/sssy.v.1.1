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
    var flags = [{flag:'y',flagText:'有效'},{flag:'n',flagText:'无效'}];
    var renderTo = '#datagrid';
    var columns = [[
        {field:'majorIndustryName',title:'行业大类名称',width:200 ,sortable:true ,align:'center' , editor:'numberbox'},
        {field:'industryName',title:'所属产业名称',width:100 , align:'center',editor:'numberbox'},
        {field:'flag',title:'是否有效',width:100,align:'center',editor:{type:'combobox', options:{valueField:'flag',textField:'flagText',data:flags,required:true}},formatter:function(val, rec) {
         if (val == 'Y')
         return '有效';
         else
         return '无效';
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
            text:'添加',
            iconCls:'icon-add',
            handler:function() {
                $('#openShowModify').html('');
                $('#openShowModify')[0].src = 'sysmgr/majorIndustry_showAddUI.action';
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
                    idArrs.push(rows[i].majorIndustryCode);
                }
                var textArr = "";
                for (i = 0; i < rows.length; i++) {
                    textArr += rows[i].majorIndustryName + ' ';
                }
                Ext.Msg.confirm('提示', '确定要删除[' + textArr + ']？', function(btn) {
                    if (btn == 'yes') {
                        $.post('sysmgr/majorIndustry_del.action?idArrs=' + idArrs, function(res) {
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
                    $('#openShowModify').html('');
                    var id = rows[0].majorIndustryCode;
                    $('#openShowCompanyModify')[0].src = 'sysmgr/majorIndustry_showModify.action?code=' + id;
                    $('#companyscale-modify').dialog('open');
                }
            }
        }];

    var url = 'sysmgr/majorIndustry_query.action';
    var myDataGrid = new MyDataGrid(columns, url, 'majorIndustryCode', renderTo);
    myDataGrid.option.toolbar = toolbar;
    myDataGrid.option.singleSelect = true;
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
        var name = $('#query_name').val();
        var queryParams = new Object();
        if (name != null && name != '') {
            queryParams.name = name;
        }
        $(myDataGrid.renderTo).datagrid('options').queryParams = queryParams;
        $(myDataGrid.renderTo).datagrid('reload');
    });
});




