/**
 * Func:纳税网报JS文件
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
        var title = $(this).text().substring(0, 6) + "税务申报详情";
        var detail = new Detail();
        detail.show(title, "XX");
    });
});