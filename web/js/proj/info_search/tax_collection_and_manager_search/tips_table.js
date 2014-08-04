//===========================================
//  Func：   TIPS_TABLE的JS文件
//  Author： 刘翔
//  Date：   2014.7.6
//  Email：  LXiang.tyut@gmail.com
//===========================================
$(document).ready(function () {

    var dItems = $("div[name='d']");//选择查看的标签页
    dItems.delegate("button", "click", function () {
        var clickBtn = $(this);
        var name = clickBtn.attr("name");
        var title = clickBtn.text() + "详情";
        var detail = new Detail();
        detail.show(title, "taxCollectionAndManager_ajaxQueryTipsDetail.action?" + $(this).attr("queryBy"), null);
    });
});