/*
 *  FileName:
 *  Function:
 *  Author:LiuXiang
 *  Date:2012-5-22:16:02:06
 *  Mail:LXiang.tyut@gmail.com
 *  Company:山西省太原市和信至诚科技有限公司
 *
 */

$(function() {
    //处理AJAX的session过期问题
    $.ajaxSetup({
        cache:false ,
        complete:function(XHR, TS) {
            var resText = XHR.responseText;
            if (resText != null && resText.indexOf("sessionstatus") > 0) {
                Ext.Msg.alert('提示', '登录时间过长，请重新登录!', function() {
                    top.window.location.href = "login.jsp";
                });
                //showMsg("您的登录已超时, 请重新登录!",'error');
            }
        }
    });
});

