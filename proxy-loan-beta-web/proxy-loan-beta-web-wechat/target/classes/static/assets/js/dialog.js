/**
 * Created by david on 2016/9/30.
 */
;(function($){
    var Dialog=function(config){
        var $this=this;
        //默认配置参数
        $this.config={
            //对话框宽、高
            width:'auto',
            height:'auto',
            //对话框的提示信息
            message:null,
            //对话框的类型
            type:'warning',
            //按钮配置
            buttons:null,
            //弹出框延迟多久关闭
            delay:null,
            //延时关闭的回调函数
            delayCallback:null,
            //对话框遮罩透明度
            maskOpacity:null,
            //指定遮罩层点击是否可以关闭
            maskClose:null,
            //是否启用动画
            effect:false,
        }
        //默认参数扩展
        if(config && $.isPlainObject(config)){
            $.extend($this.config,config);
        }else{
            $this.isConfig=true;
        }
        //  console.log(this.config);

        /*  <div class="g-dialog-container">
         <div class="dialog-window">
         <div class="dialog-header">
         <i class="iconfont icon warning"></i>
         </div>
         <div class="dialog-content">
         你是否要删除
         </div>
         <div class="dialog-footer">
         <button class="btn btn-primary radius">按钮</button>
         </div>
         </div>
         </div>*/
        //创建基本的DOM
        $this.body=$("body");
        //创建遮罩层
        $this.mask=$('<div class="g-dialog-container">');
        //创建弹出框
        $this.win=$('<div class="dialog-window">');
        //创建头部分
        $this.winHeader=$('<div class="dialog-header">');
        //创建头部提示图标
        $this.winHeaderIcon=$('<i class="iconfont icon"></i>');
        //创建头部loading
        $this.winHeaderLoading=$('<div class="loader"><div class="loading-3"><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i></div></div>');
        //创建提示文本信息
        $this.winContent=$('<div class="dialog-content">');
        //创建弹出框按钮组
        $this.winFooter=$('<div class="dialog-footer">');
        //渲染DOM
        $this.create();
    }
    //记录弹窗的层级
    Dialog.zIndex=10000;
    Dialog.prototype={
        //动画函数
        animate:function(){
            var $this=this;
            $this.win.css({"-webkit-transform":"scale(0,0)"});
            setTimeout(function(){
                $this.win.css({"-webkit-transform":"scale(1,1)"});
            },100)
        },
        create:function(){
            var $this=this,
                config=$this.config,
                mask=$this.mask,
                win=$this.win,
                header=$this.winHeader,
                headerIcon=$this.winHeaderIcon,
                headerLoading=$this.winHeaderLoading,
                content=$this.winContent,
                footer=$this.winFooter,
                body=$this.body;
            //增加弹窗的层级
            Dialog.zIndex++;
            mask.css({"zIndex":Dialog.zIndex});

            //如果没有传递任何配置参数,就弹出一个等待的图标形式的弹框
            if($this.isConfig){
                header.append(headerLoading);
                win.append(header);
                mask.append(win);
                body.append(mask);
                if(config.effect){
                    this.animate();
                }
            }else{
                //根据配置参数创建相应的弹框
                if(config.type==null || config.type=="" || config.type=="text"){
                }else{
                    header.append(headerIcon.addClass(config.type));
                    win.append(header);
                }

                //如果传了文本信息
                if(config.message){
                    win.append(content.html(config.message));
                }
                //按钮组
                if(config.buttons){
                    //创建按钮组
                    $this.createButtons(footer,config.buttons);
                    win.append(footer);
                }
                mask.append(win);
                body.append(mask);
                //设置对话框的宽高
                if(config.width!="auto"){
                    win.width(config.width);
                }
                //设置对话框的高
                if(config.height!="auto"){
                    win.height(config.height);
                }
                //弹出框延迟多久关闭
                if(config.delay && config.delay!=0){
                    setTimeout(function(){
                        $this.close();
                        //执行延时的回调函数
                        config.delayCallback && config.delayCallback();
                    },config.delay)
                }
                //对话框遮罩透明度
                if(config.maskOpacity){
                    mask.css({"backgroundColor":"rgba(0,0,0,"+config.maskOpacity+")"});
                }

                if(config.effect){
                    this.animate();
                }

                //指定遮罩层点击是否可以关闭
                if(config.maskClose){
                    mask.tap(function(){
                        $this.close();
                    });
                }
            }
        },
        //根据配置参数，创建按钮列表
        createButtons:function(footer,buttons){
            var $this=this;
            $(buttons).each(function(i){
                var type=this.type? " class='radius "+this.type+"'":"";
                var btnText=this.text?this.text:"按钮"+i;
                var callback=this.callback?this.callback:null;
                var button=$("<button"+type+">"+btnText+"</button>");
                if(callback){
                    button.tap(function(e){
                       var isClose=callback();
                        //阻止事件冒泡
                        //事件处理过程中，阻止了事件冒泡，但不会阻击默认行为（它就执行了超链接的跳转）
                        e.stopPropagation();
                        //事件处理过程中，阻止了事件冒泡，也阻止了默认行为（比如刚才它就没有执行超链接的跳转）
                        //return false;

                        //它的作用是：事件处理过程中，不阻击事件冒泡，但阻击默认行为（它只执行所有弹框，却没有执行超链接跳转）
                       //e.preventDefault();

                        //判断 回调函数 没给return false 时，就自动关闭。
                        if(isClose != false){
                            $this.close();
                        }
                    });
                }else{//没传回调函数时，自动 关闭
                    button.tap(function(e){
                        //阻止事件冒泡
                        e.stopPropagation();

                        $this.close();
                    });
                }
                footer.append(button);
            });
        },
        close:function(){
            this.mask.remove();
        }
    }
    window.Dialog=Dialog;
    dialog=function(config){
        return new Dialog(config);
    }
})(Zepto);
