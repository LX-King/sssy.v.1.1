//===========================================
//  Func：   年度计划添加
//  Date：   2012-10-28
//  Author： 刘翔
//  Company: 和信至诚科技有限公司
//  Email：  LXiang.tyut@gmail.com
//===========================================

$(document).ready(function () {

    $("#add-submit").click(function () {
        var nd = $('#nd').val();
        var flMx = $("#flMx").val();
        var ndjh = $("#ndjh").val();
        var zjh = $("#zjh").val();
        var fl = $('#fl').combobox('getValue');
        var swjgDm = $("#swjgDm").combogrid('getValue');

        var flag = nd == null || nd == '' || flMx == null || flMx == '' || ndjh == '' || ndjh == null || fl == '' || fl == null;
        if (flag) {
            $.messager.alert('提示', '表单填写错误！', 'warning');
            return;
        }

        $.post("sysmgr/annualPlan.action?method=addJudge", {swjgDm:swjgDm, zjh:zjh, ndjh:ndjh}, function (response) {
            if (response == "allowed") {
                var option = {
                    clearForm:false,
                    forceSync:true
                };
                $("#add-form").ajaxForm(option);
                $("#add-form").ajaxSubmit(showResponse);
            } else if (response == "beyond") {
                Ext.MessageBox.confirm("提示", "您设置的该年度计划已经超出了该县市年度计划总和，您确认要这样设置么?", function (btn, text) {
                    if (btn == "yes") {
                        var option = {
                            clearForm:false,
                            forceSync:true
                        };
                        $("#add-form").ajaxForm(option);
                        $("#add-form").ajaxSubmit(showResponse);
                    }
                }, this);
            } else if (response.indexOf("different") > 0) {
                Ext.Msg.alert('提示', "您设置的该县市总年度计划不一致，请重新设置!(原计划为：" + response.substr(response.indexOf(",") + 1));
                return false;
            }
        });


    });
    $("#add-close").click(function () {
        parent.modifyDiaologClose();
    });
});

function showResponse(responseText) {
    $('#response').text(responseText);
}