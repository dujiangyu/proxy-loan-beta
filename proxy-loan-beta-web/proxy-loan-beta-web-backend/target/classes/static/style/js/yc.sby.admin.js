ajaxBeforeSend = function () {
    $("#loadingLayer").show()
};
ajaxComplete = function () {
    setTimeout(function () {
        $("#loadingLayer").animate({opacity: 0}, 300, function () {
            $(this).css({opacity: ""}).hide()
        })
    }, 300)
};
var handleAjaxResult = function (a, c, b) {
    if (a.result) {
        toastr.options.positionClass = "toast-top-right";
        toastr.options.showDuration = 300;
        toastr.options.hideDuration = 300;
        toastr.options.timeOut = 2500;
        toastr.success(a.message);
        setTimeout(function () {
            c && c()
        }, 1500)
    } else {
        alert(a.message, b)
    }
};
var convertSelect2Items = function (c, b, d) {
    var a = [];
    if (c.data) {
        $.each(c.data, function (f, e) {
            a.push({id: e[b ? b : "id"].toString(), text: e[d ? d : "name"]})
        })
    }
    return a
};
$("#btnCloseNote").click(function () {
    $("#subviewLayer").slideUp(200)
});
var showNote = function (a) {
    $("#subviewLayer .subview-content").html(a);
    $("#subviewLayer").slideDown(200)
};
var showStatisticalFieldDescription = function (b) {
    var a = "";
    $.each(b, function (d, c) {
        a += "<h3>" + d + "</h3>";
        var f = "";
        for (var e = 0, g = c.length; e < g; ++e) {
            f += "<li>" + c[e] + "</li>"
        }
        a += "<ul>" + f + "</ul>"
    });
    showNote(a)
};
alert = function (c, d, b) {
    var a = initModal("提示", "<div>" + c + "</div>", {
        size: b && b.size ? b.size : "small",
        btns: ['<button type="button" class="btn btn-primary" data-dismiss="modal" data-btntype="ok">确定</button>']
    });
    a.modal({backdrop: "static", show: true}).on("hidden.bs.modal", function (f) {
        d && d()
    });
    return a
};
confirm = function (c, d, b) {
    var a = initModal("请选择", "<div>" + c + "</div>", {
        size: b && b.size ? b.size : "small",
        btns: ['<button type="button" class="btn btn-primary" data-dismiss="modal" data-btntype="ok" onclick="this.dataset.marked=true">确定</button>', '<button type="button" class="btn btn-default" data-dismiss="modal" data-btntype="cancel" onclick="this.dataset.marked=true">取消</button>']
    });
    a.modal({backdrop: "static", show: true}).on("hidden.bs.modal", function (g) {
        if (d) {
            var f = a.find("button[data-marked=true]");
            if (!f || !f.length || f.data("btntype") === "cancel") {
                d(false)
            } else {
                if (f.data("btntype") === "ok") {
                    d(true)
                }
            }
        }
    });
    return a
};
var loadUrl = function (f, d, e, a, b) {
    var c;
    $.appAjax({
        url: d, type: "GET", dataType: "text", onSuccess: function (h, i, g) {
            c = initModal(f, h, {size: e && e.size ? e.size : "middle", close: true});
            c.appBindValidator();
            c.appBindView();
            c.modal({backdrop: "static", show: true}).on("shown.bs.modal", function (j) {
                a && a(true, c)
            }).on("hidden.bs.modal", function (j) {
                b && b(true, c)
            })
        }, onError: function (g, i, j) {
            var h = initModal(f, "<p>" + i + ": " + j + "</p>", {size: e && e.size ? e.size : "middle", close: true});
            h.modal({backdrop: "static", show: true}).on("shown.bs.modal", function (k) {
                a && a(false, h)
            }).on("hidden.bs.modal", function (k) {
                b && b(false, h)
            })
        }
    });
    return c
};
var showModal = function (f, e, d, a, b) {
    var c = initModal(f, "<div>" + e + "</div>", {
        size: d && d.size ? d.size : "middle",
        close: d && d.close != undefined ? d.close : true,
    });
    c.appBindValidator();
    c.modal({backdrop: "static", show: true}).on("shown.bs.modal", function (g) {
        a && a(true, c)
    }).on("hidden.bs.modal", function (g) {
        b && b(true, c)
    });
    return c
};
var initModal = function (g, d, c) {
    var f = undefined, b = undefined;
    if (c) {
        if (c.size === "large") {
            f = " modal-lg"
        } else {
            if (c.size === "middle") {
                f = ""
            } else {
                if (c.size === "small") {
                    f = " modal-sm"
                } else {
                    f = ""
                }
            }
        }
        if (c.btns) {
            b = c.btns.join("")
        }
    }
    var a = g !== undefined && g !== null && g !== "", g = a ? g : "&nbsp;",
        e = c && (c.close === true || c.close === "true");
    return $('<div class="modal fade"><div class="modal-dialog ' + f + '"><div class="modal-content">' + (a || e ? '<div class="modal-header"><button type="button" class="close" data-dismiss="modal"><span>&times;</span></button><h4 class="modal-title">' + g + "</h4></div>" : "") + '<div class="modal-body">' + d + "</div>" + (b ? '<div class="modal-footer">' + b + "</div>" : "") + "</div></div></div>")
};
var hasPrivilege = function (a) {
    return PRIVILEGELIST.contains(a)
};
var encodeParameters = function () {
    var c = [];
    for (var a = 0, b = arguments.length; a < b; ++a) {
        c.push(arguments[a])
    }
    return JSON.stringify(c).base64Encrypt()
};
var generateRandomPassword = function (g) {
    var b = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"];
    var f = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"];
    var h = parseInt(Math.random() * (g - 1)) + 1;
    var d = g - h;
    var c = "";
    for (var e = 0, a = b.length; e < h; ++e) {
        c += b[parseInt(Math.random() * a)]
    }
    for (var e = 0, a = f.length; e < d; ++e) {
        c += f[parseInt(Math.random() * a)]
    }
    return c
};
$.fn.extend({
    appLoading: function () {
        var c = $(this);
        var b = $('<div class="form-loading"></div>');
        for (var a = 0; a < 8; ++a) {
            b.append("<div></div>")
        }
        c.addClass("loading-layer").append(b);
        return c
    }, appBindView: function () {
        var a = $(this);
        $("form[disabled]").each(function (d, c) {
            var b = $(c);
            b.find('button,input[type="button"],input[type="submit"]').remove();
            b.find('input[type="hidden"]').remove();
            b.find(".field-validation-valid").remove();
            b.find(".input-group-btn").remove();
            b.find('input[type="radio"],input[type="checkbox"]').each(function () {
                var g = $(this);
                var f = g.attr("name");
                if (f.indexOf("[") >= 0) {
                    f = f.substr(0, f.indexOf("["))
                }
                var h = b.find('[name^="' + f + '"]');
                if (h && h.length) {
                    var e = [];
                    h.each(function () {
                        var i = $(this);
                        if (i.is(":checked")) {
                            e.push(i.next().html())
                        }
                    });
                    $('<div class="form-disabled">' + (e.length ? e.join() : "-") + "</div>").insertAfter(g.parent())
                }
                h.parent().remove()
            });
            b.find("input,textarea").each(function () {
                var f = $(this);
                var e = f.val();
                $('<div class="form-disabled">' + (e === undefined || e === "" ? "-" : e) + "</div>").insertAfter(f);
                f.remove()
            });
            b.find("select").each(function () {
                var f = $(this);
                var e = f.find("option[selected]").html();
                $('<div class="form-disabled">' + (e === undefined || e === "" ? "-" : e) + "</div>").insertAfter(f);
                f.remove()
            })
        });
        a.find('select[data-select2!="custom"]').each(function () {
            var b = $(this);
            $(this).appSelect2({search: $(this).data("search") === "True"})
        }).on({
            change: function () {
                $(this).valid()
            }
        });
        a.find('input[type="date"]').attr({type: "text", "data-toggle": "date"});
        a.find('input[type="datetime"]').attr({type: "text", "data-toggle": "datetime"});
        a.find('input[data-toggle^="date"]').each(function (c, b) {
            var d = $(b);
            d.appDateTimePicker({
                format: d.data("format"),
                min: d.data("min"),
                max: d.data("max"),
                start: d.data("start"),
                startName: d.data("startname"),
                end: d.data("end"),
                endName: d.data("endname"),
                close: d.data("close").toUpperCase() === "TRUE"
            })
        });
        a.find('[data-toggle="tooltip"]').tooltip();
        a.find('[data-toggle="popover"]').popover();
        return a
    }, appStatistics: function (e, d) {
        var f = $(this);
        f.html("");
        var a = undefined;
        var c = d ? $(d).height() : 0;
        for (var b = 0; b < e.length; b++) {
            f.append($("<li>").attr({"class": e[b].css || "sum"}).append($("<div>").attr({"class": "value-sm-block"}).text((e[b].value && !isNaN(e[b].value) ? e[b].value : "0") + (e[b].describe || ""))).append($("<div>").attr({"class": "sub-title-block"}).text(e[b].name)));
            if (d && $(d).height() > c) {
                f.prepend($("<li>").css({
                    cursor: "pointer",
                    height: c
                }).append($("<div>").attr({"class": "pt20"}).append($("<span>").attr({"class": "fa fa-ellipsis-h"}))).mouseover(function () {
                    if (!a) {
                        a = $("<div>").attr({"class": "statistics-modal"}).css({
                            top: ($(this).offset().top + 5) + "px",
                            left: ($(this).offset().left + 40) + "px",
                            width: (parseInt(f.parents(".row").width()) - 120) + "px",
                        });
                        var h = "";
                        for (var g = 0; g < e.length; g++) {
                            if (g % 4 == 0) {
                                h += '<div class="form-group">'
                            }
                            h += ' <div class="col-sm-6 text-center" style="color:' + (e[g].css == "avg" ? "#337ab7" : "#3c763d") + ';">    <span style="display:block"><b>' + (e[g].value && !isNaN(e[g].value) ? e[g].value : "0") + (e[g].describe || "") + "</b></span>   <span>" + e[g].name + "</span></div>";
                            if ((g + 1) % 4 == 0 || g == e.length - 1) {
                                h += "</div>"
                            }
                        }
                        a.html(h);
                        $("body").prepend(a)
                    }
                    a.show();
                    a.mouseover(function () {
                        $(this).show()
                    }).mouseout(function () {
                        $(this).hide()
                    })
                }).mouseout(function () {
                    a.hide()
                }));
                while (d && $(d).height() > c) {
                    f.find("li:last").remove()
                }
                break
            }
        }
        return f
    }
});
$(function () {
    Main.init();
    $("#loadingLayer").appLoading();
    try {
        var a = parseDatetime(cookie.get("AUTH.EXPIRED").base64Decrypt());
        var c = setInterval(function () {
            if (new Date() > a) {
                clearInterval(c);
                alert("登录授权已过期，请重新登录。", function () {
                    location.href = "/auth/logout"
                })
            }
        }, 60 * 1000)
    } catch (b) {
        console.log(b)
    }
    $("form *[data-val-remote]").each(function (e, d) {
        if ($(d).val() !== "") {
            $(d).valid()
        }
    });
    if (document.body.clientWidth <= 767) {
        $(".datagrid-search").each(function (j, h) {
            var g = $(h);
            var f = g.children().remove();
            var k = $('<div id="datagrid-conditions" class="collapse"></div>').append(f);
            k.on({
                "show.bs.collapse": function () {
                    e.animate({opacity: 0, height: 0}, 100, function () {
                        e.hide().css({height: ""})
                    })
                }, "hide.bs.collapse": function () {
                    e.show(150).animate({opacity: 1}, 100)
                }
            });
            var e = $('<div class="form-group">    <div class="col-xs-24 text-right">        <a href="javascript:void(0);">            展开搜索条件            <span class="fa fa-angle-double-down"></span>        </a>    </div></div>');
            e.find("a").on({
                click: function () {
                    k.collapse("show")
                }
            });
            var d = $('<div class="pull-left mt5">    <a href="javascript:void(0);">        收起搜索条件        <span class="fa fa-angle-double-up"></span>    </a></div>');
            d.find("a").on({
                click: function () {
                    k.collapse("hide")
                }
            });
            k.find(".datalist-action").prepend(d);
            g.append(k).append(e)
        })
    }
    $("html").appBindView();
    $("#RefreshFinance").click(function () {
        $.appAjax({
            url: "/admin/merchant/refreshfinance", onSuccess: function (d) {
                handleAjaxResult(d, function () {
                    location.reload()
                })
            }
        })
    })
});