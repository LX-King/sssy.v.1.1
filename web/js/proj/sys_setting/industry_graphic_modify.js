//===========================================
//  Func：   产业图表修改
//  Date：   2012-8-2
//  Author： 刘翔
//  Company:
//  Email：  LXiang.tyut@gmail.com
//===========================================
$(document).ready(function () {

    $("#modify-submit").click(function () {
        var fields = $("form input:gt(1)");
        var flag = true;
        fields.each(function (i) {
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
            };
            $("#modify").ajaxForm(option);
            $("#modify").ajaxSubmit(showResponse);
        }
    });

    $("#modify-close").click(function () {
        parent.dialogClose();
    });

    $("#industryName").combobox({
        onChange:function (newValue, oldValue) {
            var options = this;
            for (var i = 0; i < options.length; i++) {
                if (options[i].value == newValue) {
                    $("#industryCode").val($(options[i]).attr("realValue"));
                    break;
                }
            }
        }
    });
});

function showResponse(responseText, statusText, xhr, $form) {
    var msg = eval(responseText);
    alert(msg.msg);
    if (msg.msg == "succ")
        $('#response').text("修改成功!");
    else
        $('#response').text("修改失败!");
}

function isNum(obj) {
    var reg = /^(\d+)(\.*)(\d*)$/;
    if (!reg.test(obj)) {
        return false;
    }
    return true;
}



