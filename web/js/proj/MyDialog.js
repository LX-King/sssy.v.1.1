/*
 *  FileName:
 *  Function:
 *  Author:LiuXiang
 *  Date:2012-5-20:11:50:13
 *  Mail:LXiang.tyut@gmail.com
 *  Company:山西省太原市和信至诚科技有限公司
 *
 */

function MyDialog(paras) {
    this.title = paras.title;
    this.width = paras.width == null ? null : paras.width ;
    this.height = paras.height == null ? null : paras.height ;
    this.content = paras.content == null? null :'';
    this.button = paras.button == null ? [ {value:'确定'}] : paras.button;
}

MyDialog.prototype.createDialog = function() {
    this.dialog = $.dialog({
        id:'msgDialog',
        title:this.title,
        lock:true,
        focus:true,
        fixed:true,
        width:this.width == null ? 300 : this.width,
        height:this.height == null ? 50 : this.height,
        drag:true,
        esc:true,
        content:this.content,
        button:this.button

    });
}
MyDialog.prototype.createIFrameDialog = function(url ,params){
    var defaultParams = {};
    defaultParams.title = this.title;
    defaultParams.width = params.width == null ? 500 : params.width;
    defaultParams.height = params.height == null ? 400 : params.height;
    defaultParams.esc = true;
    defaultParams.drag = true;
    defaultParams.fixed = true;
    defaultParams.lock = true;
    defaultParams.id='iframeDialog';
    /*defaultParams.button = params.button == null ? [{value:'提交',
        callback:function(){
            
        }
    }]:params.button;*/
    this.dialog = $.dialog.open(url , defaultParams);
}



