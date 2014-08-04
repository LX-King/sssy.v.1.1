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

    /*tree*/
    var rootNode = new Ext.tree.AsyncTreeNode({
        text:'',
        leaf:false,
        id:'-1',
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
        rootVisible:false,
        name:'category.parentId',
        root:rootNode,
        listeners:{
            beforeload:function(node) {
                this.loader.dataUrl = 'sysmgr/menu_getJSONTreeForExt.action?parentId=' + node.id + '&&method=modify&&modifyId=' + $('#menuId').val();
            }
        }
    });
    tree.render(Ext.get('menuParentId'))
    tree.expandAll();
    tree.on('checkchange', function(n, c) {
        var pnode = n.parentNode ;
        if (c) {
            pnode.cascade(function(cnode) {
                if (n.id != cnode.id) {
                    cnode.getUI().toggleCheck(false);
                }
            });
        }
    });

    $("#menu-modify-submit").click(function() {
        var parentId = tree.getChecked()[0].id;
        $('#_menuParentId').val(parentId);
        var menuType = $('#menuType').combogrid('getValue');
        var menuName = $('#menuName').val();

        var flag = parentId == null || parentId == '' || menuType == null || menuType == '' || menuName == '' || menuName == null;
        if (flag) {
            Ext.Msg.alert('提示', '表单填写错误！');
            return;
        }

        var option = {
            clearForm:false,
            forceSync:true
        }
        $("#menu-modify-form").ajaxForm(option);
        $("#menu-modify-form").ajaxSubmit(showResponse);
    });
    $("#menu-modify-close").click(function() {
        parent.menuModifyDiaologClose();
    });

});

function showResponse(responseText, statusText, xhr, $form) {
    $('#response').text(responseText);
}




