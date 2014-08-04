/**
 * Func:纳税申报情况 条件选择
 * User: LiuXiang
 * Date: 14-2-22
 * Time: 下午1:42
 *
 */
//=================================
//  表单提交检查
//
//  @param
//=================================
function submitForm() {
    var fxqq = document.uiForm.fxqq.value;
    var fxqz = document.uiForm.fxqz.value;

    //申报日期不为空判断
    if (fxqq == null || fxqq == "" || fxqz == null || fxqz == "") {
        Ext.Msg.alert("错误", "请选择申报日期!");
        return;
    }
    document.uiForm.submit();
}

//=================================
//  重置表单
//
//  @param
//=================================
function resetForm() {
    document.uiForm.reset();
}

//注册select 的change事件
function registerOnSelectChange() {
    var swjgDm = "";
    var hsjgDm = "";
    var url;
    var ajaxEvent = "ajaxReq";
    var form = "uiForm";

    /*选择核算机关触发的事件*/
    $("select[name='hsjg']").change(function () {
        hsjgDm = $(this).val();
        url = "taxCollectionAndManager_queryTaxUnitAndTaxManager.action?swjgDm=" + swjgDm + "&hsjgDm=" + hsjgDm;
        $(this).trigger(ajaxEvent)
    });

    $("select[name='swjg']").change(function () {
        var swjgDm = $(this).val();
        url = "taxCollectionAndManager_ajaxQueryTaxUnitAndTaxManager.action?swjgDm=" + swjgDm + "&hsjgDm=" + hsjgDm;
        $(this).trigger(ajaxEvent)
    });

    $("form[name='"+form+"']").delegate("select", ajaxEvent, function () {
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

    //初始化纳税人信息的Suggestion
    initStore(jQuery);
}


//===========================================
//  Func：   注册Input chang事件处理函数
//  Author： 刘翔
//  Date：   2014-5-29
//  Email：  LXiang.tyut@gmail.com
//===========================================
function registerOnInputChange(exports) {
    var input = $("input[name='taxPayerId']");
    input.keyup(function (event) {
        var e = window.event || event;
        var ul = exports.viewer.nsrxxViewer.ul;
        if (e.keyCode == 13) {//匹配回车键
            if (ul.hoverItem != -1) input.val($.trim(ul.liItems[ul.hoverItem].attr("code")));
            exports.viewer.nsrxxViewer.hide();
        } else if (e.keyCode == 27) {//匹配ESC
            exports.viewer.nsrxxViewer.hide();
        } else if (e.keyCode == 40) {//按下Down方向键
            if (ul.hoverItem != -1) {
                ul.liItems[ul.hoverItem].removeClass("liHover");
                ul.liItems[ul.hoverItem].next.addClass("liHover");
                ul.hoverItem = ul.liItems[ul.hoverItem].next.index;
            } else {
                ul.hoverItem = 0;
                ul.liItems[0].addClass("liHover");
            }
        } else if (e.keyCode == 38) {//按下Up方向键
            if (ul.hoverItem != -1) {
                ul.liItems[ul.hoverItem].removeClass("liHover");
                ul.liItems[ul.hoverItem].prev.addClass("liHover");
                ul.hoverItem = ul.liItems[ul.hoverItem].prev.index;
            } else {
                ul.hoverItem = 0;
                ul.liItems[0].addClass("liHover");
            }
        } else
            inputChangeCallBack($(this), exports);
    });
}

//=================================
//  初始化纳税人信息 用于Suggestion
//
//  @param
//=================================
function initStore(exports) {
    var swjgDm = $("select[name='swjg']").val();
    var url = "taxCollectionAndManager_ajaxQueryTaxPayerDetail.action?swjgDm=" + swjgDm;

    if (exports["store"] != null) delete exports["store"];
    exports.store = {};
    exports.store.nsrxx = [];
    if (exports["viewer"] != null)
        delete exports["viewer"];

    exports.viewer = {};
    exports.viewer.nsrxxViewer = $("<div class='nsrxx_suggestion'></div> ");
    var ul = $("<ul></ul>");
    ul.liItems = [];
    ul.hoverItem = -1;
    exports.viewer.nsrxxViewer.append(ul);
    exports.viewer.nsrxxViewer.ul = ul;

    //纳税人信息提示条 显示
    exports.viewer.nsrxxViewer.show = function () {
        var input = $("input[name='taxPayerId']");
        $(input.parent("span")).append(exports.viewer.nsrxxViewer);
        exports.viewer.nsrxxViewer.css("display", "");
    };

    //纳税人信息提示条清空
    exports.viewer.nsrxxViewer.empty = function () {
        if (exports.viewer.nsrxxViewer.children("ul").size() > 0) {
            exports.viewer.nsrxxViewer.ul.empty();
        }
    };

    //纳税人信息提示条 隐藏
    exports.viewer.nsrxxViewer.hide = function () {
        exports.viewer.nsrxxViewer.empty();
        exports.viewer.nsrxxViewer.css("display", "none");
    };

    $.ajax({
        url:url,
        async:false,
        success:function (response) {
            if (response.totalRecords == 0) exports.flag = "NULL";
            else {
                for (var i = 0; i < response.records.length; i++) {
                    exports.store.nsrxx.push(response.records[i]);
                }
                exports.flag = "SUCCESS";
            }
        }
    });
}


//=================================
//  纳税人识别号 按键事件响应函数
//
//  @param
//=================================
function inputChangeCallBack(input, exports) {
    var inputChar = input.val();
    var regNum = /^\d+$/;
    var regChinese = /[\d+][W+]/g;
    var suggestion = [];


    if (exports.flag == "SUCCESS") {

        exports.viewer.nsrxxViewer.hide();

        if (inputChar.match(regNum)) {
            for (var i = 0; i < exports.store.nsrxx.length; i++) {
                if (exports.store.nsrxx[i].nsrsbh.substring(0, inputChar.length) == inputChar) suggestion.push(exports.store.nsrxx[i]);
            }
        } else if (!inputChar.match(regChinese)) {
            for (var n = 0; n < exports.store.nsrxx.length; n++) {
                if (exports.store.nsrxx[n].nsrmc.indexOf(inputChar) > 0) suggestion.push(exports.store.nsrxx[n]);
            }
        }
        var ul = exports.viewer.nsrxxViewer.ul;

        if (suggestion.length > 0) {
            for (var j = 0; j < suggestion.length; j++) {
                ul.liItems[j] = $("<li index='" + j + "' code='" + $.trim(suggestion[j].nsrsbh) + "' >识别号:" + $.trim(suggestion[j].nsrsbh) + "    名称:" + $.trim(suggestion[j].nsrmc) + "</li>");
                (ul.liItems[j]).index = j;
                if (j > 0 && j < suggestion.length - 1) {
                    ul.liItems[j - 1].next = ul.liItems[j];
                    ul.liItems[j].prev = ul.liItems[j - 1];
                }
                if (j == suggestion.length - 1) {
                    ul.liItems[j].next = ul.liItems[0];
                    ul.liItems[0].prev = ul.liItems[j];
                }
                ul.append(ul.liItems[j]);
            }

            if (!ul.hoverItem)ul.hoverItem = -1;

            exports.viewer.nsrxxViewer.show();

            ul.delegate("li", "hover", function () {
                var index = $(this).attr("index");
                if (ul.hoverItem == -1) ul.hoverItem = index;
                ul.liItems[ul.hoverItem].removeClass("liHover");
                ul.hoverItem = index;
                ul.liItems[index].addClass("liHover");
            });

            /*注册li click事件*/
            ul.delegate("li", "click", function () {
                input.val($.trim($(this).attr("code")));
                ul.hoverItem = $(this).attr("index");
                exports.viewer.nsrxxViewer.hide();
            });

        }
    }

}

//=================================
//  关闭表单Enter自动提交
//
//  @param
//=================================
function registerAutoSubmitOff() {
    document.onkeydown = function (event) {
        var target, code, tag;
        if (!event) {
            event = window.event; //针对ie浏览器
            target = event.srcElement;
            code = event.keyCode;
            if (code == 13) {
                tag = target.tagName;
                return tag == "TEXTAREA";
            }
        }
        else {
            target = event.target; //针对遵循w3c标准的浏览器，如Firefox
            code = event.keyCode;
            if (code == 13) {
                tag = target.tagName;
                return tag != "INPUT";
            }
        }
    };
}


$(document).ready(function () {

    //注册关闭表单自动提交事件
    registerAutoSubmitOff();
    //注册Select Change事件
    registerOnSelectChange();
    //注册Input keypress事件
    initStore(jQuery);
    registerOnInputChange(jQuery);

    /*IE下利用jQueryPlugin实现placeholder*/
    $("input[name='taxPayerId']").placeholder({isUseSpan:true, onInput:false});
});