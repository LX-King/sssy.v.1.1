// 欠缴税金明细表
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
    query();
}

function query() {

    var url = 'graphic/taxOwn.action';
    url += "?method=column";
    var chartUtil = null;
    var bbar = new Ext.Toolbar({
        items:[
            "->", {
                text:"折线图",
                handler:function () {
                    url = "graphic/taxOwn.action?method=line";
                    chartUtil.update("line", url);
                }
            }, "-",
            {
                text:"柱状图",
                handler:function () {
                    url = "graphic/taxOwn.action?method=column";
                    chartUtil.update("column", url);
                }
            }, "-",
            {
                text:"饼图",
                handler:function () {
                    url = "graphic/taxOwn.action?method=pie";
                    chartUtil.update("pie", url);
                }
            }

        ]
    });


    /*bbar.add(graphicType);*/
    chartUtil = new ChartUtil(bbar);
    chartUtil.showChartByType("column", url);

}

function closeGraphicWnd() {
    $("#graphicWin").dialog("close");
}


