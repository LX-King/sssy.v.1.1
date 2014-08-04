/**
 * Func:机打发票JS文件
 * User: LiuXiang
 * Date: 14-2-22
 * Time: 下午1:42
 *
 */



$(document).ready(function () {


    //=================================
    //  增加显示详情
    //=================================
    $("table[class*='lx-tm'] td").delegate("a", "click", function () {
        event.preventDefault();
        var title = $(this).text();
        var detail = new Detail();
        detail.show(title, "XX");
    });
});