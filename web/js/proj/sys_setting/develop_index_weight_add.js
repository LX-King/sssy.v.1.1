/*
 *  FileName:
 *  Function:
 *  Author:LiuXiang
 *  Date:2012-6-17:0:18:56
 *  Mail:LXiang.tyut@gmail.com
 *  Company:山西省太原市和信至诚科技有限公司
 *
 */

$(document).ready(function() {

    $("#modify-submit").click(function() {
        var fields = $("form input:last");
        var flag = true;
        fields.each(function(i) {
            if (this.value == null) {
                $.messager.alert('提示', '表单填写错误！', 'warning');
                flag = false;
                return flag;
            }

            if (!isNum(this.value)) {
                $.messager.alert('提示', '请填写数字！', 'warning');
                flag = false;
                return flag;
            }

        });

        if (flag) {
            var option = {
                clearForm:false,
                forceSync:true
            }
            $("#modify").ajaxForm(option);
            $("#modify").ajaxSubmit(showResponse);
        }
    });

    $("#modify-close").click(function() {
        parent.modifyDiaologClose();
    });
});


function showResponse(responseText, statusText, xhr, $form) {
    $('#response').text(responseText);
}

function isNum(obj) {
    var reg = /^(\d+)(\.*)(\d*)$/;
    if (!reg.test(obj)) {
        return false;
    }
    return true;
}



