//===========================================
//  Func：   企业经营信息表
//  Date：   2012-7-9
//  Author： 刘翔
//  Email：  LXiang.tyut@gmail.com
//===========================================

//====================================
//查询 企业经营信息
//
//@param  event
//@date 2012-7-9
//====================================
function show(event) {
    //hack IE FireFox
    var e = window.event || event;
    //阻止
    if (document.all)
        e.cancelBubble = true;
    else
        e.stopPropagation();

    var queryInterestUrl = "firmRunInfo_queryInterest.action";
    var queryAssetUrl = "firmRunInfo_queryAsset.action";
    createWin({interestUrl:queryInterestUrl, assetUrl:queryAssetUrl});

    return false;
}

//====================================
//创建 Ext Window 载入企业财务信息
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
    } else {


        var interestPanel = new Ext.Panel({
            width:790,
            height:255,
            autoScroll:true,
            maximizable:false,
            resizable:false,
            bodyStyle:"background-color:white",
            layout:'fit',
            html:'<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src=' + url.interestUrl + '></iframe>'
        });
        var assetPanel = new Ext.Panel({
            width:790,
            height:255,
            autoScroll:true,
            maximizable:false,
            resizable:false,
            layout:'fit',
            bodyStyle:"background-color:white",
            html:'<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src=' + url.assetUrl + '></iframe>'
        });

        win = new Ext.Window({
            title:"企业财务信息",
            id:'win',
            width:800,
            height:550,
            modal:true,
            autoScroll:false,
            maximizable:false,
            maximized:false,
            resizable:true,
            closable:true,
            closeAction:"close",
            items:[
                interestPanel, new Ext.Spacer({height:5}), assetPanel, new Ext.Spacer({height:5})
            ]
        });
        /*  interestPanel.load({
         url:url.interestUrl
         });
         assetPanel.load({
         url:url.assetUrl
         });*/
        win.show();
    }
}