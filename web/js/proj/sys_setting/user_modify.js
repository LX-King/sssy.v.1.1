/*
 *  FileName:user_mgr
 *  Function:菜单管理
 *  Author:LiuXiang
 *  Date:2012-4-26:9:18:44
 *  Mail:LXiang.tyut@gmail.com
 *  Company:山西省太原市和信至诚科技有限公司
 *
 */
$(document).ready(function() {

    $("#user-modify-submit").click(function() {
        var code = $("#code").val();
        var name = $("#name").val();
        /*var password = $('#password').val();*/
        var roleCode = $("#roleCode").combogrid('getValue');
        var isExpired = $('#isExpired').combogrid('getValue');

        var flag = code == null || code == '' || name == null || name == ''  || isExpired == '0';
        if (flag) {
            $.messager.alert('提示', '表单填写错误！', 'warning');
            return;
        }
        var option = {
            clearForm:false,
            forceSync:true
        }
        $("#user-modify-form").ajaxForm(option);
        $("#user-modify-form").ajaxSubmit(showResponse);
    });

    $("#user-modify-close").click(function() {
        parent.userModifyDiaologClose();
    });
});
function showResponse(responseText, statusText, xhr, $form) {
    $('#response').text(responseText);
}





