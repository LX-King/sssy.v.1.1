//===========================================
//  Func：   利润表 图标链接
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
        title:"负债表图表链接",
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
            $.messager.alert("错误","年份选择有误，请重新选择!");
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
    var graphicType = "line";
    //新负债表or旧负债表
    var type = "";

    /*这里需要改*/
    var url = 'assetGraphic';

    url += "?type=" + type;
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
