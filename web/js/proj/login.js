/*
 *  FileName: Login.js
 *  Function: 首页登录
 *  Author:LiuXiang
 *  Date:2012-3-16:20:51:40
 *  Mail:LXiang.tyut@gmail.com
 *
 */

function login() {
    Ext.BLANK_IMAGE_URL = 'images/blank.gif';

    //设置请求的默认编码格式
    Ext.lib.Ajax.defaultPostHeader += ";charset=utf-8";
    Ext.form.Field.prototype.msgTarget = 'side';
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    var form = new Ext.form.FormPanel({
        url:'sysmgr/user_login.action',
        method:'post',
        defaultType:'textfield',
        title:'欢迎登录',
        width:300,
        autoHeight:true,
        frame:true,
        labelAlign:'right',
        labelWidth:60,
        bodyStyle:'text-align:center;',
        buttonAlign:'center',
        defaults:{
            border:false,
            allowBlank:false,
            msgTarget:'side',
            blankText:'该字段不允许为空'
        },
        waitMsgTarget:true,
        items:[
            {
                fieldLabel:'登录帐号',
                name:'user.code'
            },
            {
                fieldLabel:'登录密码',
                name:'user.password',
                inputType:'password'
                //regex:/^.{4,}$/,
                //regexText:'长度不能少于4位'
            }
        ],
        buttons:[
            {
                text:'登陆',
                handler:function () {
                    form.getForm().submit({
                        success:function (f, a) {
                            //OpenFullWin('main.jsp');
                            window.location = 'main.jsp';
                        },
                        failure:function (f, a) {
                            //                       		alert(a.result.msg);
                            Ext.Msg.alert('提示', a.result.msg);
                        },
                        waitMsg:'正在提交，请稍等...'
                    });
                }
            },
            {
                text:'取消',
                handler:function () {
                    form.getForm().reset();
                }
            }
        ]
    });
    form.render('login-panel');
    Ext.get('login-panel').setStyle('position', 'absolute')
        .center(Ext.getBody());

}
//是否安装 FlashPlayer
function hasPlugin(name) {
    name = name.toLowerCase();
    for (var i = 0; i < navigator.plugins.length; i++) {
        if (navigator.plugins[i].name.toLowerCase().indexOf(name) > -1) {
            return true;
        }
    }
    return false;
}
//是否安装FlashPlayer IE
function hasIEPlugin(name) {
    try {
        new ActiveXObject(name);
        return true;
    } catch (ex) {
        return false;
    }
}

//检测所有浏览器中的Flash
function hasFlash() {
    var result = hasPlugin("Flash");
    if (!result) {
        result = hasIEPlugin("ShockwaveFlash.ShockwaveFlash");
    }
    return result;
}

function testIE(){
	if($.browser.msie){
		if($.browser.version == '6.0'){
			 Ext.MessageBox.confirm("提示","为了正常使用本系统，您需要安装最新的IE浏览器.", function (btn,text) {
	                if(btn == "yes"){
	                	downloadIE();
	                }
	            }, this);
		}
	}
}
//检测FlashPlayer版本
function getFlashVer() {//获得flashplayer的版本 google
    var f = "", n = navigator;
    if (n.plugins && n.plugins.length) {
        for (var i = 0; i < n.plugins.length; i++) {
            if (n.plugins[i].name.indexOf('Shockwave Flash') != -1) {
                f = n.plugins[i].description.split('Shockwave Flash ')[1];
                break;
            }
        }
    } else if (window.ActiveXObject) {
        for (var j = 10; j >= 2; j--) {
            try {
                var fl = eval("new ActiveXObject('ShockwaveFlash.ShockwaveFlash." + j + "');");
                if (fl) {
                    f = j + ".0";
                    break;
                }
            }
            catch (e) {
            }
        }
    }
    
    return parseInt(f.substr(0, f.indexOf(".")));
}
function dowloadFP() {
    var iframe = document.createElement("iframe");
    iframe.style.display = "none";
    if (document.all) {
        iframe.src = "sysmgr/user_downLoadFlashPlayer.action?agentBrowser=ie";
    } else {
        iframe.src = "sysmgr/user_downLoadFlashPlayer.action?agentBrowser=other";
    }
    document.body.appendChild(iframe);
}
function downloadIE(){
	 var iframe = document.createElement("iframe");
	    iframe.style.display = "none";
	    iframe.src = "sysmgr/user_downLoadIE.action";
	    document.body.appendChild(iframe);
}

Ext.onReady(function () {

    if (hasFlash()) {
        var vFlash = getFlashVer();
        var allowedFP = 9;
        if (vFlash <= allowedFP) {
            Ext.MessageBox.confirm("提示","为了正常使用本系统，您的浏览器需要安装FlashPlayer.", function (btn,text) {
                if(btn == "yes"){
                    dowloadFP();
                }
            }, this);

        }
    }else{
    	Ext.MessageBox.confirm("提示","为了正常使用本系统，您的浏览器需要安装FlashPlayer.", function (btn,text) {
                if(btn == "yes"){
                    dowloadFP();
                }
            }, this);
    }
    
    testIE();
});




