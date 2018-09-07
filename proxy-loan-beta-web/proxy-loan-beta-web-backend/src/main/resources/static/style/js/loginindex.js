var Login = function () {
    var a = function () {
        var d = $(".box-login");
        if (b("box").length) {
            switch (b("box")) {
                case"register":
                    d = $(".box-register");
                    break;
                case"forgot":
                    d = $(".box-forgot");
                    break;
                default:
                    d = $(".box-login");
                    break
            }
        }
        d.show().addClass("animated flipInX").on("webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend", function () {
            $(this).removeClass("animated flipInX")
        })
    };
    var c = function () {
        $(".forgot").on("click", function () {
            $(".box-login").removeClass("animated flipInX").addClass("animated bounceOutRight").on("webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend", function () {
                $(this).hide().removeClass("animated bounceOutRight")
            });
            $(".box-forgot").show().addClass("animated bounceInLeft").on("webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend", function () {
                $(this).show().removeClass("animated bounceInLeft")
            })
        });
        $(".register").on("click", function () {
            $(".box-login").removeClass("animated flipInX").addClass("animated bounceOutRight").on("webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend", function () {
                $(this).hide().removeClass("animated bounceOutRight")
            });
            $(".box-register").show().addClass("animated bounceInLeft").on("webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend", function () {
                $(this).show().removeClass("animated bounceInLeft")
            })
        });
        $(".go-back").click(function () {
            var d;
            if ($(".box-register").is(":visible")) {
                d = $(".box-register")
            } else {
                d = $(".box-forgot")
            }
            d.addClass("animated bounceOutLeft").on("webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend", function () {
                d.hide().removeClass("animated bounceOutLeft")
            });
            $(".box-login").show().addClass("animated bounceInRight").on("webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend", function () {
                $(this).show().removeClass("animated bounceInRight")
            })
        })
    };
    var b = function (d) {
        d = d.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var f = new RegExp("[\\?&]" + d + "=([^&#]*)"), e = f.exec(location.search);
        return e == null ? "" : decodeURIComponent(e[1].replace(/\+/g, " "))
    };
    return {
        init: function () {
            a();
            c()
        }
    }
}();
$(function () {
    Main.init();
    Login.init();
    var c = $.query.get("_ts");
    if (!!c) {
        var a = parseInt(Date.parse(new Date())) / 1000;
        var f = parseInt(c);
        if (a - f <= 120) {
            alert("你的账号在其他设备登录，请确保账号密码没有泄露")
        }
    }
    var e = "USER.ACCOUNT", d = 7 * 24 * 60;
    var b = cookie.get(e);
    if (b) {
        $("#userName").val(b.base64Decrypt()).valid();
        $("#password").focus()
    } else {
        $("#username").focus()
    }
    $("#form_login").appSubmit({
        onBeforeSend: function (g) {
            $("#btnLogin").attr({disabled: true})
        }, onSuccess: function (g) {
            if (g.success) {
                if ($("#remember").is(":checked")) {
                    cookie.set(e, $("#userName").val().base64Encrypt(), d)
                } else {
                    cookie.remove(e);
                }
                location.href = "/frame.html";//g.data
            } else {
                alert(g.data && g.data.length ? g.data[0] : g.message);
                $("#btnLogin").removeAttr("disabled")
                $.message.info("info",g.message,"");
            }
        }, onError: function () {
            $("#btnLogin").removeAttr("disabled")
        }
    })
});