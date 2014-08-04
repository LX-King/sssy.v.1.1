//===========================================
//  Func：   税源质量变动情况 图标链接
//  Date：   2012-7-9
//  Author： 刘翔
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
        title:"税源质量变动情况图表链接",
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

//=================================
//  查询事件
//
//  @param  无
//=================================
function query() {
    /*这里需要改*/
        var url = 'taxResQuChange.action?method=line';
        var chartUtil = null;
        var bbar = new Ext.Toolbar({
            items:[
                "->", {
                    text:"折线图",
                    handler:function () {
                        url = "graphic/taxResQuChange.action?method=line";
                        chartUtil.update("line", url);
                    }
                }, "-",
                {
                    text:"柱状图",
                    handler:function () {
                        url = "graphic/taxResQuChange.action?method=column";
                        chartUtil.update("column", url);
                    }
                }
            ]
        });


        /*bbar.add(graphicType);*/
        chartUtil = new ChartUtil(bbar);
        chartUtil.showChartByType("line", url);

}

//=================================
//  关闭图表链接窗口
//
//  @param 无
//=================================
function closeGraphicWnd() {
    $("#graphicWin").dialog("close");
}
