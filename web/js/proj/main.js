/*function checkSessionStatus(conn, response, options) {
 if (response && response.getResponseHeader && response.getResponseHeader('sessionstatus')) {
 Ext.Msg.confirm('提示', '登录时间过长，请重新登录！', function(btn) {
 if (btn == 'yes') {
 window.location.href = "sysmgr/user_relogin.action";
 }
 });
 }
 }*/

Ext.onReady(function() {
    /*Ext.Ajax.on('requestcomplete', function checkSessionStatus(conn, response, options) {
     if (response && response.getResponseHeader && response.getResponseHeader('sessionstatus')) {
     sessionTimeout();
     }
     }, this);*/
    Ext.BLANK_IMAGE_URL = "js/ext/resources/images/default/s.gif";
    Ext.QuickTips.init();

    // 顶部信息
    var topPanel = new Ext.BoxComponent({
        region: 'north',
        height: 39,
        margins: '2 5 0 5',
        border: false,
        style: 'background:url(images/hd-bg.gif);',
        html: "<div style='text-align:center; color:white; font-size:20px; padding:5px; font-family:黑体'>税收税源分析决策管理系统（县局版）&nbsp;&nbsp;&nbsp;&nbsp;</div>"
    });

    // 左侧菜单，构造树形菜单
    var tree = new Ext.tree.TreePanel({
        region: 'west',
        title: '操作菜单',
        useArrows: true,
        tbar: [new Ext.BoxComponent({
            columnWidth: 0.6,
            el: 'username'
        }), '->', '-', {
            iconCls: 'icon-expand-all',
            tooltip: '全部展开',
            listeners: {
                'click': function() {
                    tree.expandAll();
                }
            }
        }, '-', {
            iconCls: 'icon-collapse-all',
            tooltip: '全部关闭',
            listeners: {
                'click': function() {
                    tree.collapseAll();
                    tree.getRootNode().expand();
                }
            }
        }
        ],
        split: true,
        width: 230,
        minSize: 150,
        maxSize: 250,
        collapsible: true,
        margins: '0 0 0 5',
        autoScroll: true,
        loader: new Ext.tree.TreeLoader(),
        root:new Ext.tree.AsyncTreeNode({
            text:'功能菜单',
            expanded:true ,
            leaf:false,
            id:'0',
            icon:'images/icon_monitor_pc.gif',
            singleClickExpand: true
        }),
        listeners:{
            beforeload:function(node) {
                this.loader.dataUrl = 'sysmgr/user_getJSONTree.action?parentId=' + node.id;
            }
        }
    });


    tree.on('click', function(node, e) {
        if (node.id == '8') {
            relogin();
        } else if (node.id == '9') {
            logout();
        } else if (!node.isLeaf() && node.id != '0') {
            if (!node.isExpanded()) {
                node.expand();
            } else {
                node.collapse();
            }
        } else {
            var tabPanel = Ext.getCmp('tabPanel');
            var tab = tabPanel.getItem(0);
            tab.setTitle(node.text);
        }
    });

    function testSessonStatus(node, e) {
        $.ajax({
            type: "post",
            url: 'sysmgr/menu_showAdd.action',
            async:false,
            complete: function (res) {
                var response = res.getAllResponseHeaders();
                if (response && response.indexOf('sessionstatus') > 0) {
                    top.Ext.Msg.alert('提示', '登录时间过长，请重新登录!', function() {
                        window.location.href = "login.jsp";
                    });
                } else {

                }
            }
        });
    }

    // 主面板
    var rightPanel = new Ext.TabPanel({
        region: 'center',
        height: '100%',
        id: 'tabPanel',
        activeTab: 0,
        margins: '0 5 0 0',
        enableTabScroll:true,
        frame:true,
        //html:'<iframe frameborder=0 scrolling="auto" name="mainFrame" id="mainFrame" width="100%" height="100%" src="home.jsp"></iframe>',
        html:'<iframe frameborder=0 scrolling="auto" name="mainFrame" id="mainFrame" width="100%" height="100%" src="home_showTaskNotification.action"></iframe>',
        items: [
            {
                title: '首页',
                xtype: "panel",
                layout:'fit'
            }]

    });

    // 底部信息
    var bottomPanel = new Ext.BoxComponent({
        region: 'south',
        height: 20,
        margins: '0 0 0 0',
        border: false,
        html: "<div style='text-align:center; color: #1C3B75; font-size:12px; padding:5px; font-family:黑体'>建议使用IE 7.0及以上版本的浏览器访问该系统</div>"
    });

    // 系统布局
    var viewport = new Ext.Viewport({
        layout: 'border',
        items: [topPanel, tree, rightPanel, bottomPanel]
    });

    // 点击菜单事件响应函数
    function nodeClick(node) {
        var tabPanel = Ext.getCmp('tabPanel');
        var tab = tabPanel.getItem(0);
        tab.setTitle(node.text);
    }


    function sessionTimeout() {
        Ext.Msg.alert('提示', '登录时间过长，请重新登录!', function() {
            window.location.href = "login.jsp";
        });
    }

    // 重新登录事件响应函数
    function relogin() {
        Ext.Msg.confirm('提示', '是否重新登录？', function(btn) {
            if (btn == 'yes') {
                window.location.href = "sysmgr/user_relogin.action";
            }
        });
    }

    // 退出系统事件响应函数
    function logout() {
        Ext.Msg.confirm('提示', '是否退出系统？', function(btn) {
            if (btn == 'yes') {
                window.location.href = "sysmgr/user_relogin.action";
                window.close();
            }
        });
    }

});

