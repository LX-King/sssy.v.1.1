//===========================================
//  Func：   税收类分指标图表链接
//  Date：   2012-7-10
//  Author： 刘翔
//  Company: CHINA CITIC BANK
//  Email：  LXiang.tyut@gmail.com
//===========================================

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
        title:"税收类分指标图表链接",
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
    url += "?method="+method;
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