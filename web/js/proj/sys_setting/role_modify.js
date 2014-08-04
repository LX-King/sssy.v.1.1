/*
 *  FileName:menu_mgr
 *  Function:菜单管理
 *  Author:LiuXiang
 *  Date:2012-4-26:9:18:44
 *  Mail:LXiang.tyut@gmail.com
 *  Company:山西省太原市和信至诚科技有限公司
 *
 */

$(document).ready(function() {

    /*实现动态加载查询条件中的父亲节点*/
    var rootNode = new Ext.tree.AsyncTreeNode({
            text:'功能菜单',
            expanded:true ,
            leaf:false,
            id:'0',
            checked:true,
            singleClickExpand: true
        });
    var tree = new Ext.tree.TreePanel({
        checked:true,
        split: true,
        border:false,
        autoWidth: true,
        height:320,
        autoScroll:true,
        /*bodyStyle :{},*/
        margins: '0 0 0 5',
        loader: new Ext.tree.TreeLoader(),
        rootVisible:true,
        root:rootNode,
        listeners:{
            beforeload:function(node) {
                this.loader.dataUrl = 'sysmgr/role_getJSONTreeForExt.action?parentId=' + node.id + '&&method=modify&&modifyId='+$('#roleCode').val();
            }
        }
    });
    tree.render(Ext.get('categorys'));
    
    tree.expandAll();
    $("#role-modify-submit").click(function() {
        var checkedNodes = tree.getChecked();
        var roleCategorys = '';
        for(var i = 0;i<checkedNodes.length;i++){
            roleCategorys += checkedNodes[i].id;
            if(i < checkedNodes.length-1){
                roleCategorys+=','; 
            }
        }
        $('#_categorys').val(roleCategorys);
        var roleName = $('#roleName').val();

        var flag = roleCategorys == null || roleCategorys == '' || roleName == null || roleName == '';
        if (flag) {
            Ext.Msg.alert('提示', '表单填写错误！');
            return;
        }

        var option = {
            clearForm:false,
            forceSync:true
        };
        $("#role-modify-form").ajaxForm(option);
        $("#role-modify-form").ajaxSubmit(showResponse);
    });
    $("#role-modify-close").click(function() {
        parent.roleModifyDiaologClose();
    });

    /*注册点击查询按钮的事件*/
    $("#doMenuAdd").click(function() {
        var roleCategorys = $("#roleCategorys").combotree('getValue');
        var roleName = $('#roleName').val();
        var roleCode = $('#roleCode').val();


        var queryParams = new Object();
        if (roleCategorys != null && roleCategorys != '') {
            queryParams.roleCategorys = roleCategorys;
        }
        if (roleName != null && roleName != '')
            queryParams.roleName = roleName;

        if (roleCode != null && roleCode != '')
            queryParams.roleCode = roleCode;

        $(myDataGrid.renderTo).datagrid('options').queryParams = queryParams;
        $(myDataGrid.renderTo).datagrid('reload');
    });
});

function showResponse(responseText, statusText, xhr, $form) {
    $('#response').text(responseText);
}




