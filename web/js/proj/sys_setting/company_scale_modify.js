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


   /* *//*end*//*
    *//*实现动态加载查询条件中的父亲节点*//*
    $('#menuParentId').combotree({
        url:'sysmgr/menu_getJSONTree.action?parentId=-1' ,
        value:'功能菜单',
        onBeforeExpand:function(node) {
            $('#menuParentId').combotree("tree").tree("options").url = "sysmgr/menu_getJSONTree.action?parentId=" + node.id;
        }
    });*/
    $("#modify-submit").click(function() {
        var csName = $('#csName').val();
        var minLimit = $('#minLimit').val();
        var maxLimit = $('#maxLimit').val();

        var flag = csName == null || csName == '' || minLimit == null || minLimit == '' || maxLimit == '' || maxLimit == null;
        if (flag) {
            $.messager.alert('提示', '表单填写错误！', 'warning');
            return;
        }

        var option = {
            clearForm:false,
            forceSync:true
        }
        $("#modify-form").ajaxForm(option);
        $("#modify-form").ajaxSubmit(showResponse);


    });
    $("#modify-close").click(function() {
        parent.modifyDiaologClose();
    });


});

function showResponse(responseText, statusText, xhr, $form) {
    $('#response').text(responseText);
}



