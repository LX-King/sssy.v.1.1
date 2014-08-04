// 重点企业实缴税收变化表
// -- 打开图表链接

$(document).ready(function () {

    if ($("input[name='graphicPeriod']:checked").val() == 'year') {
        $("input[name='monthSpan']").hide();
        $("input[name='yearSpanYear']").show();
    } else {
        $("input[name='monthSpan']").show();
        $("input[name='yearSpanYear']").hide();
    }


    var graphicPeriod = $("input[name='graphicPeriod']");
    graphicPeriod.change(function () {
        var checkedPeriod = $("input[name='graphicPeriod']:checked").val();
        if (checkedPeriod == 'year') {
            $("input[name='monthSpan']").hide();
            $("input[name='yearSpanYear']").show();
        } else {
            $("input[name='monthSpan']").show();
            $("input[name='yearSpanYear']").hide();
        }
    });


    $("#graphicWin").dialog({
        modal:true,
        closed:true,
        title:"税收完成情况图表查询",
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

function query() {

    var swjgDm = $("select[name='swjgDm']").val();
    var graphicType = "line";
    var displayItem = $("select[name='displayItem']").val();
    var reportPeriod = '';
    if ($("input[name='graphicPeriod']:checked").val() == 'year') {
        reportPeriod = $("input[name='yearSpanYear']").val();
    } else {
        reportPeriod = $("input[name='monthSpan']").val();
    }

    var url = 'graphic/taxChangeForBigFirm.action';
    url += "?swjgDm=" + swjgDm;
    url += "&reportPeriod=" + reportPeriod;
    url += "&displayItem=" + displayItem;
    //欠缴税金余额
    url += "&dataType=" + "qjsjye";
    url += "graphicType=" + graphicType;

    var chartUtil = new ChartUtil();
    chartUtil.showChartByType(graphicType, url);
}

function closeGraphicWnd() {
    $("#graphicWin").dialog("close");
}


