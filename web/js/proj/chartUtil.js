/*
 *  FileName:ChartUtil
 *  Function:用FushionChart画图工具类
 *  Author:LiuXiang
 *  Date:2012-5-27:10:03:20
 *  Mail:LXiang.tyut@gmail.com
 *  Company:山西省太原市和信至诚科技有限公司
 *
 */

//================================================
//     Fun:ChartUtil 构造函数
//     Author:刘翔
//     Email:LXiang.tyut@gmail.com
//     Date:2012-6-20
//================================================
function ChartUtil(bbar) {
    this.chartType = {};
    //饼图
    this.chartType.PIE = 'PIE';
    //折线图
    this.chartType.LINE = 'LINE';
    //柱状图
    this.chartType.COLUMN = 'COLUMN';
    // fushionChart
    this.fushionChart = null;
    //container ext window
    this.wrapper = new Ext.Window({
        title:"图表链接",
        id:'win',
        width:810,
        height:487,
        modal:true,
        autoScroll:true,
        maximizable:true,
        maximized:false,
        resizable:true,
        closable:true,
        bodyStyle:"background-color:white",
        html:"<div id='chartWrapper'></div>",
        closeAction:"close",
        bbar:bbar
    });
}
ChartUtil.prototype._getAppliactionName = function(){
  var contextPath = document.location.pathname; 
  var index =contextPath.substr(1).indexOf("/"); 
  contextPath = contextPath.substr(0,index+1); 
  delete index; 
  return contextPath; 
} ;

//================================================
//     Fun:根据所选参数和URL展示图表结果
//     Author:刘翔
//     Email:LXiang.tyut@gmail.com
//     Date:2012-6-20
//================================================
ChartUtil.prototype.showChartByType = function (type, url) {
    this.url = url;
    this.swfURL = '';
    var chartUtil = this;
	var applicationName = chartUtil._getAppliactionName();
    if (type == 'pie') {
        type = this.chartType.PIE;
    } else if (type == 'line') {
        type = this.chartType.LINE;
    } else {
        type = this.chartType.COLUMN;
    }
    if (type == this.chartType.PIE) {
        this.swfURL = applicationName+'/FusionCharts/Pie3D.swf';
    } else if (type == this.chartType.LINE) {
        this.swfURL = applicationName+'/FusionCharts/MSLine.swf';
    } else if (type == this.chartType.COLUMN) {
        this.swfURL = applicationName+'/FusionCharts/MSColumn3D.swf';
    }

    chartUtil._drawByType();
};



//================================================
//     Fun:私有方法,让showChartByType调用
//     Author:刘翔
//     Email:LXiang.tyut@gmail.com
//     Date:2012-6-20
//================================================
ChartUtil.prototype._drawByType = function () {
    var chartUtil = this;
    FusionCharts.debugMode.enabled(true);
    FusionCharts.debugMode.outputTo(function () {
        console.log(arguments);
    });

    if ($("#chartContainer").val() == null) {
        chartUtil.container = $('<div id="chartContainer" style="margin:0;padding:0;"></div>').hide();
    } else
        chartUtil.container = $("#chartContainer");
    this.wrapper.show();
    /*this.wrapper.appendTo("body");*/
    this.container.appendTo("#chartWrapper");
    $("#chartContainer").insertFusionCharts({
        swfUrl:chartUtil.swfURL,
        width:chartUtil.wrapper.getWidth() - 14,
        height:'417',
        scaleMode:"ExactFit",
        id:'myChartID',
        dataSource:chartUtil.url,
        dataFormat:'jsonurl'
    });
    this.container.show("slow");
    this.wrapper.on("resize", function () {
        $("#chartContainer").updateFusionCharts({width:chartUtil.wrapper.getWidth(), height:chartUtil.wrapper.getHeight() - 32});
    });
};


//================================================
//     Fun:查询功能
//     Author:刘翔
//     Email:LXiang.tyut@gmail.com
//     Date:2012-6-28
//================================================
ChartUtil.prototype.query = function () {
    $("#chartContainer").appendFusionCharts({
        swfUrl:this.swfURL,
        width:'500',
        height:'300',
        id:'myChart22',
        dataSource:this.url,
        dataFormat:'jsonurl'
    });

};

//================================================
//     Fun:图表更新
//     Author:刘翔
//     Email:LXiang.tyut@gmail.com
//     Date:2012-6-20
//================================================
ChartUtil.prototype.update = function (type, url) {
    if (type == 'pie') {
        type = this.chartType.PIE;
    } else if (type == 'line') {
        type = this.chartType.LINE;
    } else {
        type = this.chartType.COLUMN;
    }
    var chartUtil = this;
    var applicationName = chartUtil._getAppliactionName();
    if (type == this.chartType.PIE) {
        this.swfURL = applicationName+'/FusionCharts/Pie3D.swf';
    } else if (type == this.chartType.LINE) {
        this.swfURL = applicationName+'/FusionCharts/MSLine.swf';
    } else if (type == this.chartType.COLUMN) {
        this.swfURL = applicationName+'/FusionCharts/MSColumn3D.swf';
    }
    if (url == null || url == "")
        this.container.updateFusionCharts({swfUrl:this.swfURL});
    else
        this.container.updateFusionCharts({swfUrl:this.swfURL, dataSource:url, dataFormat:'jsonurl'});
};





