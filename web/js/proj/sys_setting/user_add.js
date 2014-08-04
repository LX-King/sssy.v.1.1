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

    $("#user-add-submit").click(function() {
        var code = $("#code").val();
        var name = $("#name").val();
        var password = $('#password').val();
        var password2 = $('#password2').val();
        var roleCode = $("#roleCode").combogrid('getValue');
        var isExpired = $('#isExpired').combogrid('getValue');

        var flag = code == null || code == '' || name == null || name == '' || password == '' || password == null || password2 == null || password2 == '' || isExpired == '0';
        if (flag) {
            $.messager.alert('提示', '表单填写错误！', 'warning');
            return;
        }
        if(password != password2){
            $.messager.alert('提示', '两次密码填写不一致！', 'warning');
            return;
        }
        var option = {
            clearForm:false,
            forceSync:true
        }
        $("#user-add-form").ajaxForm(option);
        $("#user-add-form").ajaxSubmit(showResponse);
    });
    
    $("#user-add-close").click(function() {
        parent.userModifyDiaologClose();
    });

});
function showResponse(responseText, statusText, xhr, $form) {
    $('#response').text(responseText);
}





