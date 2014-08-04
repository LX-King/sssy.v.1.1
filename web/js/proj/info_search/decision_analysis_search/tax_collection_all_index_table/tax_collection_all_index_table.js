// 税收类所有指标表
// -- 打开图表链接

$(document).ready(function () {
    if ($("input[name='graphicPeriod']:checked").val() == 'year') {
        $("div[id='season']").hide();
        $("div[id='year']").show();
    } else {
        $("div[id='season']").show();
        $("div[id='year']").hide();
    }

    var graphicPeriod = $("input[name='graphicPeriod']");
    graphicPeriod.change(function () {
        var checkedPeriod = $("input[name='graphicPeriod']:checked").val();
        if (checkedPeriod == 'year') {
            $("div[id='season']").hide();
            $("div[id='year']").show();
        } else {
            $("div[id='season']").show();
            $("div[id='year']").hide();
        }
    });


    $("#graphicWin").dialog({
        modal:true,
        closed:true,
        title:"税收类所有指标图表链接",
        buttons:[
            {
                text:'查询',
                iconCls:'icon-ok',
                handler:query
            },
            {
                text:'关闭',
                iconCls:'icon-cancel',
                handler:closeGraphicWnd
            }
        ],
        onClose:function () {
            if ($("#chartContainer") != null) {
                $("#chartContainer").remove();
                $("#graphicWin").dialog({
                    width:500,
                    closed:true,
                    modal:true
                });
            }
        }
    });
});

function showGraphicStatistics() {
    $("#graphicWin").dialog('open');
}

//=================================
//  查询事件
//
//  @param  无
//=================================
function query() {
    var reportPeriod = '';
    if ($("input[name='graphicPeriod']:checked").val() == 'year') {
        var yearEndValue = $("select[name='ndEnd'] option:selected").val();
        var yearBeginValue = $("select[name='ndBegin'] option:selected").val();
        if (yearEndValue <= yearBeginValue) {
//            Ext.Msg.alert("错误","年份选择有误，请重新选择!");
            $.messager.alert("错误", "年份选择有误，请重新选择!");
            return;

        }
        reportPeriod = yearBeginValue + "-" + yearEndValue;
    } else {
        var year = $("select[name='nd'] option:selected").val();
        var season = $("select[name='monthPeriod'] option:selected").val();
        reportPeriod = year + "-" + season;
    }

    //数据类型
    var dataType = $("input[name='dataType']:checked").val();
    //图表类型
    var graphicType = $("input[name='graphicType']:checked").val();

    //新利润表or旧利润表
    var type = "";
    var method = "graphic";
    /*这里需要改*/
    var url = 'taxAllIndexGraphic.action';
    url += "?method=" + method;
    url += "&type=" + type;
    url += "&reportPeriod=" + reportPeriod;
    url += "&graphicType=" + graphicType;

    var chartUtil = new ChartUtil();
    chartUtil.showChartByType(graphicType, url);
}

//=================================
//  关闭图表链接窗口
//
//  @param 无
//=================================
function closeGraphicWnd() {
    $("#graphicWin").dialog("close");
}


//====================================
//查询 税收类所有指标
//
//@param  indexCode 指标名称
//@date 2012-7-9
//====================================
function show(indexCode, event) {
    //hack IE FireFox
    var e = window.event || event;
    //阻止
    if (document.all)
        e.cancelBubble = true;
    else
        e.stopPropagation();
    var method = "detail";
    var url = "taxAllIndexGraphic.action";
    url += "?method=detail";
    url += "&indexCode=" + indexCode;

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
            url:url,
            scrips:true
        });
    } else {
       /* var panel = new Ext.Panel({
            bodyStyle:"background:#ffffff",
            html : '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src='+ url + '></iframe>',
            autoScroll:true
        });*/
        win = new Ext.Window({
            title:"税收类分指标查询",
            id:'win',
            width:700,
            height:500,
            modal:true,
            autoScroll:true,
            maximizable:true,
            maximized:false,
            resizable:true,
            closable:true,
            bodyStyle:"background-color:white",
            closeAction:"close",
            html : '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src='+ url + '></iframe>'
        });


        win.show();
        /* win.load({
         url:url,
         scripts:true
         });*/
    }
}