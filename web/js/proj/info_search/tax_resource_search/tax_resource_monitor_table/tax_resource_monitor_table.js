// 重点税源监测户数统计表
// -- 打开图表链接


//====================================
//查询 重点税源监控企业明细
//
//@param  firmRegType 企业注册类型
//@date 2012-7-9
//====================================
function showFirmDetail(firmRegType, event) {
    /*//hack IE FireFox
     var e = window.event || event;
     //阻止
     if (document.all)
     e.cancelBubble = true;
     else
     e.stopPropagation();*/

    var url = "firmCounterMonitor.action";
    url += "?firmRegType=" + firmRegType;

    createWin(url);

    return false;
}

//====================================
//创建 Ext Window 来载入重点税源监控企业明细结果页面
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
        });
    } else {
        win = new Ext.Window({
            title:"重点税源监控企业明细",
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
        /*win.load({
            url:url
            *//*scripts:true*//*
        });*/
    }
}