$(document).ready(function () {

    $("input[name='lx']").change(function () {
        var lx = $("input[name='lx']:checked").val();
        var fxzbDm = $("input[type='hidden'][name='dm']").val();
        var url = "sysmgr/indexFeature_queryBy.action?lx=" + lx + "&fxzbDm=" + fxzbDm;
        $.ajax({
            method:"post",
            url:url,
            success:function (msg) {
                var obj = msg[0];
                $("textarea[name='tz']").val(obj.feature);
                $("textarea[name='yd']").val(obj.question);
                $("textarea[name='jy']").val(obj.option);
            }
        });
    });

    $("#modify-submit").click(function () {
        var fields = $("form textarea");
        var flag = true;
        fields.each(function (i) {
            if (this.value == null) {
                $.messager.alert('提示', '表单填写错误！', 'warning');
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