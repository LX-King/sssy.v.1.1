//===========================================
//  Func：   年度计划维护主页JS脚本
//  Date：   2012-10-27
//  Author： 刘翔
//  Company: 和信至诚科技有限公司
//  Email：  LXiang.tyut@gmail.com
//===========================================

//=================================
//  初始化页面
//
//  @param
//=================================
$(document).ready(function () {

    var renderTo = '#datagrid';
    var frozenColumns = [
        [
            {field:'id', title:'ID', width:50, align:'center' },
            {field:'swjgDm', title:'税务机关', width:150, align:'center', formatter:function (val, rec) {
                var mc = '';
                $.ajax({
                    type:'post',
                    url:'sysmgr/annualPlan.action?method=getTaxUnit',
                    data:{swjgDm:val},
                    async:false,
                    success:function(res) {
                        mc = res;
                    }
                });
                return mc;
                }
            },{
                field:"zjh",title:"总年度计划",width:100,align:"center"
            }
        ]
    ];
    var columns = [
        [
            {field:'id', title:'ID', width:50, align:'center' },
            {field:'nd', title:'年度', width:130, align:'center' },
            {field:'fl', title:'分类', width:150, align:'center', formatter:function (val, rec) {
                if (val == '05')
                    return "税种";
                else if (val == '04')
                    return "预算级次";
                else
                    return "管理机关";
            }
            },
            {field:'flMx', title:'分类明细', width:200, align:'center'},
            {field:'ndjh', title:'年度计划', width:130, align:'center'}

        ]
    ];

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
            text:"添加",
            iconCls:"icon-add",
            handler:function () {
                $('#openShowModify').html('');
                $('#openShowModify')[0].src = 'sysmgr/annualPlan.action?method=showAdd';
                $('#modify').dialog('setTitle', '添加').dialog('open');
            }
        },
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
                    var id = rows[0].id;
                    $("#openShowModify").html("");
                    $('#openShowModify')[0].src = "sysmgr/annualPlan.action?method=showModify&id=" + id;
                    $('#modify').dialog('setTitle', '修改').dialog('open');
                }
            }
        },
        {
            text:"删除",
            iconCls:"icon-remove",
            handler:function () {
                var rows = $(renderTo).datagrid('getSelections');
                if (rows.length == 0) {
                    Ext.Msg.alert('Warning', '请选择一条记录！');
                }
                if (rows.length > 1)
                    Ext.Msg.alert('Warning', '一次只能删除一条记录！');
                else {
                    var idArrs = [];
                    idArrs.push(rows[0].id);
                    var textArr = "";
                    textArr += rows[0].id + ' ';

                    Ext.Msg.confirm('提示', '确定要删除年度计划[' + textArr + ']？', function (btn) {
                        if (btn == 'yes') {
                            $.post('sysmgr/annualPlan.action?method=del&id=' + idArrs, function (res) {
                                Ext.Msg.alert('info', res);
                                $(renderTo).datagrid('load');
                            });
                        }
                    });
                }
            }

        }
    ];

    /*var url = 'sysmgr/annualPlan.action?method=query';*/


    var myDataGrid = $('#datagrid').datagrid({
        iconCls:'icon-save',
        width:'auto',
        height:'auto',
        nowrap:false,
        striped:true,
        collapsible:true,
        url:'sysmgr/annualPlan.action?method=query',
        sortName:'code',
        sortOrder:'desc',
        remoteSort:false,
        columns:columns,
        frozenColumns:frozenColumns,
        pagination:true,
        toolbar:toolbar});

    /*注册点击查询按钮的事件*/
    $("#doQuery").click(function () {
        var nd = $('#query_nd').val();
        var fl = $('#query_fl').combobox('getValue');
        alert(fl);
        var swjgDm = $("#swjgDm").combobox("getValue");

        var queryParams = new Object();

        if (fl != null && fl != '') {
            queryParams.fl = fl;
        }

        if (nd != null && nd != '')
            queryParams.nd = nd;

        if (swjgDm != null && swjgDm != '')
            queryParams.swjgDm = swjgDm;


        myDataGrid.datagrid('options').queryParams = queryParams;
        myDataGrid.datagrid('reload');
    });
});


function addDiaologClose() {
    $('#add').dialog('close');
    $('#datagrid').datagrid('reload');
}
function modifyDiaologClose() {
    $('#modify').dialog('close');
    $('#datagrid').datagrid('reload');
}