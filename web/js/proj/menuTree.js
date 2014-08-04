/*
 *  FileName:menuTree
 *  Function:生成系统左边树形菜单
 *  Author:LiuXiang
 *  Date:2012-3-21:11:12:29
 *  Mail:LXiang.tyut@gmail.com
 *  Company:山西省太原市和信至诚科技有限公司
 */

Ext.ns('com.sssy');

com.sssy.LeftTree = function(config) {
    com.sssy.LeftTree.superclass.constructor.call(this, config);
}


Ext.extend(com.sssy.LeftTree, Ext.tree.TreePanel, {
    initComponent:function() {
        this.root = new Ext.tree.AsyncTreeNode({
            text:'功能菜单',
            expanded:true ,
            leaf:false,
            id:'0',
            icon:'images/tree/root.gif',
            singleClickExpand: true
        });
        this.tbar = new Ext.Toolbar({
            items:[new Ext.BoxComponent({
                columnWidth: 0.6,
                el: 'username'
            }), '->', '-', {
                iconCls: 'icon-expand-all',
                tooltip: '全部展开',
                listeners: {
                    'click': function() {
                        this.expandAll();
                    },scope:this
                }
            }, '-', {
                iconCls: 'icon-collapse-all',
                tooltip: '全部关闭',
                listeners: {
                    'click': function() {
                        this.collapseAll();
                        this.getRootNode().expand();
                    },scope:this
                }
            }]
        });
        var config = {
            id:'leftMenu',
            region : 'west',
            title : '操作菜单',
            width: 230,
            minSize: 150,
            maxSize: 250,
            collapsible: true,
            margins: '0 0 0 5',
            split:true,

            //autoHeight:true,
            autoScroll: true,
            // frame : true,// 美化界面
            enableDD:true,
            border:true,
            useArrows: true,
            root:this.root,
            tbar:this.tbar,
            loader:new Ext.tree.TreeLoader({})
        };

        Ext.apply(this ,config);
        com.sssy.LeftTree.superclass.initComponent.apply(this, arguments);
    } ,
    listeners:{
        beforeload:function(node) {
            this.loader.dataUrl = 'user/user_getJSONTree.action?parentId=' + node.id;
        }
    }
});






