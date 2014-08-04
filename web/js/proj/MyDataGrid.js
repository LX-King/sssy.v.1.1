/*
 *  FileName: MyDataGrid
 *  Function: MyDataGrid
 *  Author:LiuXiang
 *  Date:2012-4-26:14:23:02
 *  Mail:LXiang.tyut@gmail.com
 *  Company:山西省太原市和信至诚科技有限公司
 *
 */

function MyDataGrid(columns, url, id, renderTo) {

    //设置渲染DOM元素的CSS 默认是填充全局
    this.renderTo = renderTo;
    $(this.renderTo).css('margin', '0 0 0 0 ').css('height', '100%').css('width', '100%');

    //设置查询工具栏的CSS
    this.toolbarStyle = 'height:100%;background:#EFEFEF;padding: 1px 2px;border-bottom: 1px solid #CCC;overflow-y:hidden;';


    this.option = {};
    this.option.url = url;
    this.option.width = '100%';
    this.option.height = 'auto';
    this.option.fitColumns = true;
    this.option.striped = true;
    this.option.columns = columns;
    this.option.loadMsg = '数据正在加载中,请稍候...';
    this.option.pagination = true;
    this.option.idField = id;
    this.option.pageSize = 10;
    this.option.singleSelect = true;
    this.option.rownumbers = true;
    this.option.queryParams = {};
    /*this.option.onBeforeLoad = function() {
        var geturl ;
        geturl = $.ajax({
            type: "post",
            url: 'sysmgr/menu_showAdd.action',
            async:false,
            complete: function () {
                var response = geturl.getAllResponseHeaders();
                if (response && response.indexOf('sessionstatus') > 0 ) {
                    top.Ext.Msg.alert('提示', '登录时间过长，请重新登录!', function() {
                        window.location.href = "login.jsp";
                    });
                }
            }
        });
    }*/
}
MyDataGrid.prototype.createQueryBar = function(title, height, queryBar) {
    var queryBar = "<div class='panel' style='width:auto;height:" + height + "'>" +
                   "<div class= 'panel-header' style = 'width:100%;height:auto'> " +
                   "<div class='panel-title'>" + title + "</div>" +
                   "<div class='panel-tool'>" + "</div>" +
                   "</div>" +
                   "<div class='panel-body' style='height:100%;'>" +
                   "<div class = 'panel-toolbar' style='height:90%;'></div>" +
                   "</div>" +
                   "</div>";
    $(queryBar).insertBefore(this.renderTo);
};

MyDataGrid.prototype.createGataGrid = function() {
    $(this.renderTo).datagrid(this.option);
} ;


