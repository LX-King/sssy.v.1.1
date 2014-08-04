/**
 * Func:税务申报JS文件
 * User: LiuXiang
 * Date: 14-2-22
 * Time: 下午1:42
 *
 */


$(document).ready(function () {
    //=================================
    //  增加显示详情
    //=================================
    $("table[class*='lx-tm'] td").delegate("a", "click", function (event) {
        var e = event || window.event;
        if (e.stopPropagation())e.stopPropagation();
        e.cancelBubble = true;
        if (e.preventDefault) e.preventDefault();
        e.returnValue = false;

        var title = "";
        var detail = new Detail();
        detail.show(title, "taxCollectionAndManager_ajaxQueryTaxDeclareDetail.action?" + $(this).attr("queryBy"), null);
    });
});