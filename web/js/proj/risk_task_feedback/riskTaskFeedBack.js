/**
 * User: LiuXiang
 * Date: 13-7-13
 * Time: 上午7:53
 * Company:CiticBank
 */

(function($,exports){
    var mod = {} ;
    mod.creat = function(includes){
        var result = function(){ this.init.apply(this,arguments)};
        result.fn = result.prototype;
        result.extend = function(ob){$.extend(result,ob)};
        result.include = function(ob){$.extend(result.fn,ob)};
        result.proxy = function(func){return $.proxy(func,this);};
        result.fn.proxy = result.proxy;
        if(includes)result.include(includes);
        return result;
    };
    exports.Controler = mod;
})(jQuery,window);


(function($){
    Controler.FeedBackWin = Controler.create({
        init:function(report){

        }
    });
})(jQuery);
