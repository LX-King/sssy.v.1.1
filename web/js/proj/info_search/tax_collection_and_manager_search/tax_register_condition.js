//注册select 的change事件
function registerOnSelectChange() {
    var swjgDm = "";
    var hsjgDm = "";
    var url;
    var ajaxEvent = "ajaxReq";

    /*选择核算机关触发的事件*/
    $("select[name='hsjg']").change(function () {
        hsjgDm = $(this).val();
        url = "taxCollectionAndManager_ajaxQueryTaxUnitAndTaxManager.action?swjgDm=" + swjgDm + "&hsjgDm=" + hsjgDm;
        $(this).trigger(ajaxEvent)
    });

    $("select[name='swjg']").change(function () {
        var swjgDm = $(this).val();
        url = "taxCollectionAndManager_ajaxQueryTaxUnitAndTaxManager.action?swjgDm=" + swjgDm + "&hsjgDm=" + hsjgDm;
        $(this).trigger(ajaxEvent)
    });

    $("#taxRegisterForm").delegate("select", ajaxEvent, function () {
        var curSelect = $(this);
        $.ajax({
            url:url,
            success:function (response) {
                selectChangeCallBack(response);
            }});
    });
}

/**
 * 根据Select change事件做相应的处理
 * @param response
 */
function selectChangeCallBack(response) {
    var optionStr = "";
    var optionStr2 = "";
    if (0 != response.totalUnits) {
        var swjgSelect = $("select[name='swjg']");
        swjgSelect.empty();
        for (var i = 0; i < response.units.length; i++) {
            optionStr += "<option value='" + response.units[i].dm + "'>" + response.units[i].mc + "</option>";
        }
        swjgSelect.append($(optionStr));
    }

    if (0 != response.totalManagers) {
        var managerSelect = $("select[name='zgy']");
        managerSelect.empty();
        for (var j = 0; j < response.managers.length; j++) {
            optionStr2 += "<option value='" + response.managers[j].zgyDm + "'>" + response.managers[j].mc + "</option>";
        }
        managerSelect.append($(optionStr2));
    }
}





//===========================================
//  Func：   税务登记查询 条件选择界面的初始化JS
//  Date：   2014-4-21
//  Author： 刘翔
//  Email：  LXiang.tyut@gmail.com
//===========================================
$(document).ready(function () {

    var curYear = new Date().getFullYear(); //当前年份
    var curMonth = new Date().getMonth() + 1; //当前月份
    var yearSpan = [-10, 40]; //可选择年度区间

    /*初始化选择年份*/
    $("select[type='nd']").each(function () {
        for (var i = yearSpan[0]; i < yearSpan[1]; i++) {
            if (i != 0)
                $(this).append($("<option value='" + (curYear + i) + "'>" + (curYear + i) + " </option>"));
            else {
                $(this).append($("<option value='" + (curYear + i) + "' selected >" + (curYear + i) + "</option>"));
            }
        }
    });

    /*初始化月份选择*/
    $("select[type='monthPeriod']").each(function () {
        for (var j = 1; j <= 12; j++) {

            if (j < 10){
                if(j == curMonth)
                    $(this).append($("<option value='0" + (j) + "' selected >" + j + " 月</option>"));
                else
                    $(this).append($("<option value='0" + (j) + "' >" + j + " 月</option>"));
            }
            else {
                if(j == curMonth)
                    $(this).append($("<option value='" + (j) + "' selected >" + j + " 月</option>"));
                else
                    $(this).append($("<option value='" + (j) + "'>" + j + " 月</option>"));
            }


        }
    });

    registerOnSelectChange();

    /*注册检查表格事件*/
    $("#taxRegisterForm").submit(function () {

        var czq = $("select[name='czq_nd'] option:selected").val() + $("select[name='czq_yf'] option:selected").val(); //参照期
        var fxq = $("select[name='fxq_nd'] option:selected").val() + $("select[name='fxq_yf'] option:selected").val(); //分析期
        var taxUnit = $("select[name='hsjg'] option:selected").val(); //核算机关
        var gljg = $("select[name='swjg'] option:selected").val(); //核算机关
        var hydl = $("select[name='hydlDm'] option:selected").val();//行业大类
        var zclx = $("select[name='qyzclx'] option:selected").val();//注册类型
        var hsxs = $("select[name='hsxs'] option:selected").val();//核算形式
        var zgy = $("select[name='zgy'] option:selected").val();//征管员

        var flag = true;
        /*
         *检查参照期和分析期 参照期不能比分析期大
         */
        if (czq >= fxq) {
            Ext.Msg.alert("错误", "参照期区间不应该大于分析期的区间，请您重新选择!");
            flag = false;
        }
        /*检查核算机关*/
        else if (taxUnit == null || taxUnit == '' || taxUnit == 0) {
            Ext.Msg.alert("错误", "请选择核算机关!");
            flag = false;
        }
        /*检查管理机关*/
        else if (gljg == null || gljg == '' || gljg == 0) {
            Ext.Msg.alert("错误", "请选择管理机关!");
            flag = false;
        }
        /*检查管理员*/
        else if (zgy == null || zgy == '') {
            Ext.Msg.alert("错误", "请选择管理员!");
            flag = false;
        }
        return flag;
    });
});

function checkForm() {
    //激发事件
    $("#taxRegisterForm").trigger("submit");
}
function resetForm() {
    $("#taxRegisterForm").get(0).reset();
}