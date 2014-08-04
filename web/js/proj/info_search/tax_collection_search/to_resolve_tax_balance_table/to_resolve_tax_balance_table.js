// 税收收入待解税金余额表
// -- 打开图表链接

$(document).ready(function () {
    $("#option").hide();

    $("#type").change(function () {
        var selectedVal = $("#type option:selected").val();
        if (selectedVal == '1')
            $("#option").hide();
        if (selectedVal == '2')
            $("#option").hide();
        if (selectedVal == '3')
            $("#option").show();
    });

    if ($("input[name='graphicPeriod']:checked").val() == 'year') {
        $("input[name='monthSpan']").hide();
        $("input[name='yearSpanYear']").show();
    } else {
        $("input[name='monthSpan']").show();
        $("input[name='yearSpanYear']").hide();
    }
    $("input[name='show1']:eq(0)").attr("checked", "checked");
    $("input[name='show2']:eq(1)").attr("checked", "checked");

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

    $("input[name='show1']").change(function () {
        if ($("input[name='show1']:checked").val() == 'bqsr') {
            $("input[name='show2'][value='tbzzl']").attr("checked", "checked");
        } else {
            $("input[name='show2'][value='wcje']").attr("checked", "checked");
        }
    });

    $("input[name='show2']").change(function () {
        if ($("input[name='show2']:checked").val() == 'tbzzl') {
            $("input[name='show1'][value='bqsr']").attr("checked", "checked");
        } else {
            $("input[name='show1'][value='ljsr']").attr("checked", "checked");
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
    var url = 'graphic/taxResolveBalance.action';
    url += "?method=column";
    var chartUtil = null;
    var bbar = new Ext.Toolbar({
        items:[
            "->", {
                text:"折线图",
                handler:function () {
                    url = "graphic/taxResolveBalance.action?method=line";
                    chartUtil.update("line", url);
                }
            }, "-",
            {
                text:"柱状图",
                handler:function () {
                    url = "graphic/taxResolveBalance.action?method=column";
                    chartUtil.update("column", url);
                }
            }, "-",
            {
                text:"饼图",
                handler:function () {
                    url = "graphic/taxResolveBalance.action?method=pie";
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
