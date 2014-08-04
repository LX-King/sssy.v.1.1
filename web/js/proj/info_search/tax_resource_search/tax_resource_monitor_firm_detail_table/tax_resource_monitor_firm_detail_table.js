//===========================================
//  Func：   重点税源监控企业明细
//  Date：   2012-7-9
//  Author： 刘翔
//  Email：  LXiang.tyut@gmail.com
//===========================================



//====================================
//查询 企业基础信息查询
//
//@param  taxPayerCode 纳税人编码
//@date 2012-7-9
//====================================
function showFirmDetail(taxPayerCode, event) {
    //hack IE FireFox
    var e = window.event || event;
    //阻止
    if (document.all)
        e.cancelBubble = true;
    else
        e.stopPropagation();

    var url = "firmCounterMonitor.action";
    url += "?taxPayerCode=" + taxPayerCode;

    createWin(url);

    return false;
}

//====================================
//创建 Ext Window 载入企业基础信息
//
//@param url
//@date 2012-7-9
//====================================
function createWin(url) {
    var win;
    // 如果窗口存在，显示
    win = Ext.getCmp("win");
    if (win) {
        win.show();
        win.load({
            url:url
//            scripts:true
        });
    } else {
        win = new Ext.Window({
            title:"企业基础信息",
            id:'win',
            width:700,
            height:500,
            modal:true,
            autoScroll:true,
            maximizable:true,
            maximized:false,
            resizable:true,
            closable:true,
            closeAction:"close",
            bodyStyle:"background-color:white",
            html:'<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src=' + url + '></iframe>'
        });

        win.show();
    }
}