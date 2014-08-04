/*
 *  FileName:税收征管状况查看详细工具类
 *  Function:查看详细
 *  Author:LiuXiang
 *  Date:14-2-22:下午12:51
 *  Mail:LXiang.tyut@gmail.com
 *
 */
(function (exports, $) {
    var detail = function () {
    };
    detail.id = "showDetail";
    detail.fn = detail.prototype;
    detail.fn.init = function (url,data) {
        var tmplate = detail.htmlTmplate = $("<div id='" + detail.id + "'></div>");
        tmplate.title = $("<div style='width:400px;margin: 20px auto 40px;font-size: 20px; font-family: 楷体_GB2312; font-weight: bold;'></div>");
        tmplate.reportDate = $("<div style='font-size:10px;width: 90%;text-align: right;margin-bottom:30px;'><span>制表期:</span><span style='text-decoration: underline;'></span></div>");


        tmplate.body = $("<div id='detailBody'></div>");
        tmplate.body.table = $("<table width='900' class='search_table lx-tm'></table>");

        /*AJAX访问获取信息*/
        $.ajax({url:url, method:"post", data:data, dataType:"json",async:false,
            success:function (response) {
                tmplate.reportDate.find("span").last().text(response.reportDate);
                tmplate.title.text(response.title);
                var thTR = $("<tr></tr>");
                for(var i = 0 ; i<response.th.length;i++){
                    var th =  $("<th>"+response.th[i].fieldName+"</th>");
                    thTR.append(th);
                }
                tmplate.body.table.append(thTR);
                if(response.totalRecords > 0 )
                    for(var j = 0 ; j<response.records.length;j++){
                        var record = $("<tr class='lx-tm'></tr>");
                        for(var field in response.records[j]){
                            record.append($("<td>"+field+"</td>"))
                        }
                        tmplate.body.table.append(record);
                    }
            }
        });
        tmplate.body.append(tmplate.body.table);
        tmplate.append(tmplate.title);
        tmplate.append(tmplate.reportDate);
        tmplate.append(tmplate.body);
    };
    detail.fn.show = function (title, url,data) {
        /*初始化*/
        this.init(url,data);

        var curDetail = this;
        this.title = title;
        this.url = url;
        detail.wrapper = new Ext.Window({
            title:curDetail.title,
            id:'win',
            width:800,
            height:500,
            modal:true,
            autoScroll:true,
            maximizable:true,
            maximized:false,
            resizable:true,
            closable:true,
            bodyStyle:"background-color:white",
            url:curDetail.url,
            html:"<div id='detailWrapper' style='width:95%;margin: 20px;text-align: center;'></div>",
            closeAction:"close"
        });
        detail.wrapper.show();
        $("#detailWrapper").append(detail.htmlTmplate);

    };
    exports.Detail = detail;
})(window, jQuery);


