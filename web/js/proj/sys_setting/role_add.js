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
        checked:false,
        singleClickExpand: true
    });
    var tree = new Ext.tree.TreePanel({
        checked:true,
        split: true,
        border:false,
        autoWidth: true,
        height:320,
        autoScroll:true,
        margins: '0 0 0 5',
        loader: new Ext.tree.TreeLoader(),
        rootVisible:true,
        root:rootNode,
        listeners:{
            beforeload:function(node) {
                this.loader.dataUrl = 'sysmgr/role_getJSONTreeForExt.action?parentId=' + node.id + '&&method=add&&modifyId=' + $('#roleCode').val();
            }
        }
    });
    //根据当前节点选中父节点及其子节点
    tree.on('checkchange', function(node, flag) {
        //获取所有子节点
        node.cascade(function(node) {
            node.attributes.checked = flag;
            node.ui.checkbox.checked = flag;
            return true;
        });
        //获取所有父节点
        var pNode = node.parentNode;
        for (; pNode.id != "-1"; pNode = pNode.parentNode) {
            if (flag || tree.getChecked(id, node.parentNode) == "") {
                pNode.ui.checkbox.checked = flag;
                pNode.attributes.checked = flag;
            }
        }
    });

    tree.render(Ext.get('categorys'));
    tree.expandAll();


    $("#role-add-submit").click(function() {
        var checkedNodes = tree.getChecked();
        var categorys = '';
        for (var i = 0; i < checkedNodes.length; i++) {
            categorys += checkedNodes[i].id;
            if (i < checkedNodes.length - 1) {
                categorys += ',';
            }
        }
        $('#_categorys').val(categorys);
        var roleName = $('#roleName').val();

        var flag = categorys == null || roleName == '' || roleName == null;
        if (flag) {
            $.messager.alert('提示', '表单填写错误！', 'warning');
            return;
        }
        var option = {
            clearForm:false,
            forceSync:true
        }
        $("#role-add-form").ajaxForm(option);
        $("#role-add-form").ajaxSubmit(showResponse);
    });

    $("#role-add-close").click(function() {
        parent.roleModifyDiaologClose();
    });
});
function showResponse(responseText, statusText, xhr, $form) {
    $('#response').text(responseText);
}



