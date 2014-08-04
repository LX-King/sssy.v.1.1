/**
 * Func:税务登记JS文件
 * User: LiuXiang
 * Date: 14-2-02
 * Time: 下午1:42
 *
 */
$(document).ready(function () {
    //=================================
    //  增加显示详情
    //=================================
    $("table[class*='lx-tm'] td").delegate("a", "click", function (event) {
        var e =   event || window.event;
        if(e.stopPropagation())e.stopPropagation();
        e.cancelBubble = true;
        if(e.preventDefault) e.preventDefault();
        e.returnValue = false;

        var title = $.trim($(this).attr("djzt")) +"户--"+ $(this).attr("title").substring(2,4)+"税务登记详情";
        var detail = new Detail();
        detail.show(title, "taxCollectionAndManager_ajaxQueryRegisterDetail.action?"+$(this).attr("queryBy"),null);
    });
});