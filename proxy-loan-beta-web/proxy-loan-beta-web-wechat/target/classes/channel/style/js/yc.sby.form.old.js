var IsSubmiting = false, TrimLeftReg = /^(\s|\u00A0)+/, TrimRightReg = /(\s|\u00A0)+$/,
    DataRegs = [/^(\d{4})?(?:-|\/|年)?(\d{1,2})?(?:-|\/|月)?(\d{1,2})?(?:日)?(?: )?(\d{1,2})?(?::)?(\d{1,2})?(?::)?(\d{1,2})?(?:,|\.|')?(\d{3})?$/, /^(\d{4})-(\d{1,2})-(\d{1,2})T(\d{1,2}):(\d{1,2}):(\d{1,2}).(\d{3})/, /^(?:[A-Za-z]{3}) ([A-Za-z]{3}) (\d{1,2}) (\d{4}) (\d{1,2}):(\d{1,2}):(\d{1,2})/, /^(?:[A-Za-z]{3},) (\d{1,2}) ([A-Za-z]{3}) (\d{4}) (\d{1,2}):(\d{1,2}):(\d{1,2})/, /^(?:\/?Date\(?)?\d+\)?\/?/],
    EnumValueReg = /^-?\d+$/, FileTypeRegs = {
        All: {Reg: undefined, Desc: "请选择上传文件"},
        Text: {Reg: /(\.|\/)(txt)$/i, Desc: "请选择文本文件"},
        Image: {Reg: /(\.|\/)(jpg|jpeg|png|gif|bmp)$/i, Desc: "请选择图片文件"},
        Word: {Reg: /(\.|\/)(doc|docx|docm|dotx|dotm)$/i, Desc: "请选择Word文件"},
        Excel: {Reg: /(\.|\/)(xls|xlsx|xlsm|xltx|xltm|xlsb|xlam)$/i, Desc: "请选择Excel文件"},
        PowerPoint: {Reg: /(\.|\/)(ppt|pptx|pptm|ppsx|ppsm|potx|potm|ppam)$/i, Desc: "请选择PowerPoint文件"},
        Sound: {Reg: /(\.|\/)(mp3|wav|wma|act|amr|ava|cd|ogg|asf|midi|rm)$/i, Desc: "请选择声音文件"},
        Vedio: {Reg: /(\.|\/)(avi|wma|rmvb|rm|flash|mp4|mid|3gp|mpeg|mpg|dat)$/i, Desc: "请选择视频文件"}
    }, EscapeCharMappings = [{original: '"', escape: "&quot;"}, {original: "&", escape: "&amp;"}, {
        original: "<",
        escape: "&lt;"
    }, {original: ">", escape: "&gt;"}, {original: " ", escape: "&nbsp;"}, {
        original: "©",
        escape: "&copy;"
    }, {original: "®", escape: "&reg;"}, {original: "‘", escape: "&lsquo;"}, {
        original: "’",
        escape: "&rsquo;"
    }, {original: "“", escape: "&ldquo;"}, {original: "”", escape: "&rdquo;"}, {
        original: "′",
        escape: "&prime;"
    }, {original: "″", escape: "&Prime;"}, {original: "‹", escape: "&lsaquo;"}, {
        original: "›",
        escape: "&rsaquo;"
    }, {original: "ˆ", escape: "&circ;"}, {original: "˜", escape: "&tilde;"}, {
        original: "…",
        escape: "&hellip;"
    }, {original: "–", escape: "&ndash;"}, {original: "—", escape: "&mdash;"}, {original: "•", escape: "&bull;"}],
    ajaxBeforeSend, ajaxComplete;
String.prototype.replaceAll = function (b, a) {
    var c = new RegExp(b, "g");
    return this.toString().replace(c, a)
};
String.prototype.contains = function (a) {
    if (this.toString().indexOf(a) >= 0) {
        return true
    } else {
        return false
    }
};
String.prototype.trimLeft = function (a) {
    if (a === undefined || a === null || a === "") {
        return this.toString().replace(TrimLeftReg, "")
    } else {
        var b = new RegExp("^(" + a + ")+");
        return this.toString().replace(b, "")
    }
};
String.prototype.trimRight = function (a) {
    if (a === undefined || a === null || a === "") {
        return this.toString().replace(TrimRightReg, "")
    } else {
        var b = new RegExp("(" + a + ")+$");
        return this.toString().replace(b, "")
    }
};
String.prototype.trim = function (a) {
    return this.toString().trimLeft(a).trimRight(a)
};
String.prototype.padLeft = function (a, e) {
    if (this.length >= a) {
        return this.toString()
    }
    var b = "";
    for (var c = 0, d = a - this.length; c < d; ++c) {
        b += e
    }
    return b + this.toString()
};
String.prototype.padRight = function (a, e) {
    if (this.length >= a) {
        return this.toString()
    }
    var b = "";
    for (var c = 0, d = a - this.length; c < d; ++c) {
        b += e
    }
    return this.toString() + b
};
String.prototype.passwordEncrypt = function () {
    var a = "ghijklmnopqrstuvwxyz".split(""), j = this.base64Encrypt(), h = "", f = "", k, n = "";
    for (var g = 0, c = j.length; g < c; ++g) {
        h += j.charCodeAt(g).toString().padLeft(3, "0")
    }
    if (h.length % 2 === 1) {
        h = "0" + h
    }
    k = h.sha1Encrypt().toLowerCase();
    for (var g = 0, c = h.length / 2; g < c; ++g) {
        var b = parseInt(Math.random() * 100) % 10;
        if (b % 2 === 0) {
            f += (b + h.charAt(g * 2) + h.charAt(g * 2 + 1))
        } else {
            f += (b + h.charAt(g * 2 + 1) + h.charAt(g * 2))
        }
    }
    var b = 0;
    for (var g = 0, m = f.length, e = k.length, l = Math.max(m, e); g < l; ++g) {
        var d = parseInt(Math.random() * 20);
        if (b % 2 === 0) {
            n += (g < m ? f.charAt(g) : a[d]) + (g < e ? k.charAt(g) : a[d])
        } else {
            n += (g < e ? k.charAt(g) : a[d]) + (g < m ? f.charAt(g) : a[d])
        }
        b = g < m ? parseInt(f.charAt(g)) : d
    }
    return n
};
String.prototype.md5Encrypt = function () {
    return CryptoJS.MD5(this.toString()).toString().toUpperCase()
};
String.prototype.md5Encrypt_16 = function () {
    return this.md5Encrypt().substr(8, 16)
};
String.prototype.sha1Encrypt = function () {
    return CryptoJS.SHA1(this.toString()).toString().toUpperCase()
};
String.prototype.sha256Encrypt = function () {
    return CryptoJS.SHA256(this.toString()).toString().toUpperCase()
};
String.prototype.sha384Encrypt = function () {
    return CryptoJS.SHA384(this.toString()).toString().toUpperCase()
};
String.prototype.sha512Encrypt = function () {
    return CryptoJS.SHA512(this.toString()).toString().toUpperCase()
};
String.prototype.hexEncrypt = function () {
    return CryptoJS.enc.Utf8.parse(this.toString()).toString().toUpperCase()
};
String.prototype.base64Encrypt = function () {
    return CryptoJS.enc.Base64.stringify(CryptoJS.enc.Utf8.parse(this.toString()))
};
String.prototype.base64Decrypt = function () {
    return CryptoJS.enc.Base64.parse(this.toString()).toString(CryptoJS.enc.Utf8)
};
String.prototype.desEncrypt = function (a) {
    return CryptoJS.DES.encrypt(this.toString(), CryptoJS.enc.Utf8.parse(a), {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    }).toString()
};
String.prototype.desDecrypt = function (a) {
    return CryptoJS.DES.decrypt({ciphertext: CryptoJS.enc.Base64.parse(this.toString())}, CryptoJS.enc.Utf8.parse(a), {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    }).toString(CryptoJS.enc.Utf8)
};
String.prototype.tripleDESEncrypt = function (a) {
    return CryptoJS.TripleDES.encrypt(this.toString(), CryptoJS.enc.Utf8.parse(a), {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    }).toString()
};
String.prototype.tripleDESDecrypt = function (a) {
    return CryptoJS.TripleDES.decrypt({ciphertext: CryptoJS.enc.Base64.parse(this.toString())}, CryptoJS.enc.Utf8.parse(a), {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    }).toString(CryptoJS.enc.Utf8)
};
String.prototype.aesEncrypt = function (a) {
    return CryptoJS.AES.encrypt(this.toString(), CryptoJS.enc.Utf8.parse(a), {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    }).toString()
};
String.prototype.aesDecrypt = function (a) {
    return CryptoJS.AES.decrypt({ciphertext: CryptoJS.enc.Base64.parse(this.toString())}, CryptoJS.enc.Utf8.parse(a), {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    }).toString(CryptoJS.enc.Utf8)
};
String.prototype.xorEncrypt = function (c) {
    var d = this.length;
    while (c.length < d) {
        c += c
    }
    var a = "";
    for (var b = 0; b < d; ++b) {
        a += String.fromCharCode(this.charCodeAt(b) ^ c.charCodeAt(b))
    }
    return a
};
String.prototype.xorDecrypt = function (a) {
    return this.xorEncrypt(a)
};
String.prototype.escapeCharEncrypt = function () {
    var a = this;
    $.each(EscapeCharMappings, function (b, c) {
        a = c.replaceAll(c.original, c.escape)
    });
    return a
};
String.prototype.escapeCharDecrypt = function () {
    var a = this;
    $.each(EscapeCharMappings, function (b, c) {
        a = a.replaceAll(c.escape, c.original)
    });
    return a
};
var encodeParameters = function () {
    var c = [];
    for (var a = 0, b = arguments.length; a < b; ++a) {
        c.push(arguments[a])
    }
    return JSON.stringify(c).base64Encrypt()
};
var generateRandomPassword = function (b) {
    var j = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"],
        e = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"];
    var g = "";
    for (var c = 0, k = 0, a = 0, f = j.length, d = e.length; c < b; ++c) {
        var h = parseInt(Math.random() * 10000 % 2) === 0;
        if (h) {
            k === b - 1 && (h = false)
        } else {
            a === b - 1 && (h = true)
        }
        if (h) {
            g += j[parseInt(Math.random() * f)];
            ++k
        } else {
            g += e[parseInt(Math.random() * d)];
            ++a
        }
    }
    return g
};
Array.prototype.contains = function (a) {
    var b = false;
    $.each(this, function (c, d) {
        if (a === d) {
            b = true
        }
    });
    return b
};
var millisecondsTime = 1, secondsTime = millisecondsTime * 1000, minutesTime = secondsTime * 60,
    hoursTime = minutesTime * 60, daysTime = hoursTime * 24;
var today = function () {
    var a = new Date();
    return new Date(a.getFullYear(), a.getMonth(), a.getDate())
}();
Date.prototype.addYears = function (a) {
    this.setFullYear(this.getFullYear() + a);
    return this
};
Date.prototype.addMonths = function (a) {
    this.setMonth(this.getMonth() + a);
    return this
};
Date.prototype.addDays = function (a) {
    this.setDate(this.getDate() + a);
    return this
};
Date.prototype.addHours = function (a) {
    this.setHours(this.getHours() + a);
    return this
};
Date.prototype.addMinutes = function (a) {
    this.setMinutes(this.getMinutes() + a);
    return this
};
Date.prototype.addSeconds = function (a) {
    this.setSeconds(this.getSeconds() + a);
    return this
};
Date.prototype.addMilliseconds = function (a) {
    this.setMilliseconds(this.getMilliseconds() + a);
    return this
};
Date.prototype.toFormat = function (c) {
    var d = {
        "y+": this.getFullYear(),
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours() === 12 ? 12 : this.getHours() % 12,
        "H+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        fff: this.getMilliseconds()
    };
    for (var b in d) {
        if (new RegExp("(" + b + ")").test(c)) {
            if (RegExp.$1.length === 1) {
                c = c.replace(RegExp.$1, d[b])
            } else {
                var a = "000" + d[b];
                c = c.replace(RegExp.$1, a.substr(a.length - RegExp.$1.length))
            }
        }
    }
    return c
};
Date.prototype.toWeekDay = function () {
    var a = this.getDay();
    switch (a) {
        case 0:
            return "周日";
        case 1:
            return "周一";
        case 2:
            return "周二";
        case 3:
            return "周三";
        case 4:
            return "周四";
        case 5:
            return "周五";
        case 6:
            return "周六"
    }
    return null
};
var parseDatetime = function (c) {
    var a = DataRegs[0].exec(c);
    if (a !== null) {
        var e = a[2] === undefined ? 0 : (a[2] - 1);
        return new Date(a[1], e, a[3] || 1, a[4] || 0, a[5] || 0, a[6] || 0, a[7] || 0)
    }
    var a = DataRegs[1].exec(c);
    if (a !== null) {
        var e = a[2] === undefined ? 0 : (a[2] - 1);
        var d = new Date(a[1], e, a[3] || 1, (a[4] || 0), a[5] || 0, a[6] || 0, a[7] || 0);
        return d.addHours(-(new Date().getTimezoneOffset() / 60))
    }
    var b = function (f) {
        switch (f) {
            case"Jan":
                return 0;
            case"Feb":
                return 1;
            case"Mar":
                return 2;
            case"Apr":
                return 3;
            case"May":
                return 4;
            case"Jun":
                return 5;
            case"Jul":
                return 6;
            case"Aug":
                return 7;
            case"Sep":
                return 8;
            case"Oct":
                return 9;
            case"Nov":
                return 10;
            case"Dec":
                return 11
        }
    };
    a = DataRegs[2].exec(c);
    if (a !== null) {
        var e = b(a[1]);
        return new Date(a[3], e, a[2] || 1, a[4] || 0, a[5] || 0, a[6] || 0)
    }
    a = DataRegs[3].exec(c);
    if (a !== null) {
        var e = b(a[2]);
        var d = new Date(a[3], e, a[1] || 1, (a[4] || 0), a[5] || 0, a[6] || 0);
        return d.addHours(-(new Date().getTimezoneOffset() / 60))
    }
    a = DataRegs[4].exec(c);
    if (a !== null) {
        return new Date(parseInt(a[1]))
    }
    return new Date(NaN)
};
var cookie = function () {
    return {
        set: function (b, e, d, c) {
            var a = undefined;
            if (d) {
                a = new Date();
                a.setTime(a.getTime() + (d * 60 * 1000))
            }
            document.cookie = b + "=" + encodeURI(e) + (a ? ("; expires=" + a.toUTCString()) : "") + "; path=/" + (c ? ("; domain=" + c) : "")
        }, get: function (a) {
            var e = document.cookie.split("; ");
            for (var b = 0, d = e.length; b < d; ++b) {
                var f = e[b];
                if (f.indexOf(a + "=") === 0) {
                    return decodeURI(f.substring(a.length + 1, f.length))
                }
            }
            return undefined
        }, remove: function (a) {
            cookie.set(a, undefined, -1)
        }, clear: function () {
            var d = document.cookie.split("; ");
            for (var b = 0, c = d.length; b < c; ++b) {
                var a = d[b].split("=")[0];
                cookie.remove(a)
            }
        }
    }
}();
var isNumber = function (b) {
    var a = parseFloat(b);
    return !isNaN(a) && isFinite(a)
};
var digital = function () {
    var a = {
        numbers: ["零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"],
        units: ["", "拾", "佰", "仟"],
        levels: ["元", "万", "亿", "万", "亿亿"]
    };
    return {
        convertCN: function (e) {
            var h = parseFloat(e).toString(), g = h.indexOf("."), c = g > 0 ? h.substr(0, g) : h,
                b = g > 0 ? h.substr(g + 1, 2) : undefined;
            var j = "";
            for (var d = 0, f = c.length; d < f; ++d) {
                if (d % 4 === 0) {
                    j = a.levels[d / 4] + j
                }
                j = a.numbers[parseInt(c[f - d - 1])] + a.units[d % 4] + j
            }
            j = j.replace(/零(拾|佰|仟)/g, "零").replace(/零+/g, "零").replace(/零(元|万|亿|亿亿)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").trimRight("零");
            if (b !== undefined) {
                if (parseInt(b[0])) {
                    j += a.numbers[parseInt(b[0])] + "角"
                }
                if (parseInt(b[1])) {
                    j += a.numbers[parseInt(b[1])] + "分"
                }
            } else {
                j = j === "元" ? "零元整" : j + "整"
            }
            return j
        }
    }
}();
var guid = function () {
    var a = new Date().getTime();
    return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, function (d) {
        var b = (a + Math.random() * 16) % 16 | 0;
        a = Math.floor(a / 16);
        return (d === "x" ? b : (b & 3 | 8)).toString(16)
    })
};
var AreaInitPath = undefined;
var areaUtility = function () {
    var b, a = false, c = [];
    return {
        execute: function (d) {
            if (!b) {
                c.push(d);
                if (!a && (a = true)) {
                    if (!AreaInitPath) {
                        throw new Error("请先指定数据 api 路径（AreaInitPath）.")
                    }
                    $.appAjax({
                        url: AreaInitPath,
                        cache: true,
                        executeBeforeSendFunc: false,
                        executeCompleteFunc: false,
                        onSuccess: function (f) {
                            b = f;
                            while (c.length) {
                                var e = c.shift();
                                e && e()
                            }
                        }
                    })
                }
            } else {
                if (c.length) {
                    c.push(d)
                } else {
                    d && d()
                }
            }
        }, data: function () {
            return b
        }
    }
}();
$.extend({
    appAjax: function (a) {
        a = $.extend({
            url: undefined,
            type: "POST",
            data: undefined,
            dataType: "json",
            contentType: "application/json",
            async: true,
            cache: false,
            executeBeforeSendFunc: true,
            executeCompleteFunc: true,
            onBeforeSend: undefined,
            onSuccess: undefined,
            onError: undefined,
            onComplete: undefined
        }, a);
        if ((a.onBeforeSend && a.onBeforeSend(a.data)) !== false) {
            $.ajax({
                url: a.url,
                type: a.type,
                data: a.data,
                dataType: a.dataType,
                contentType: a.contentType,
                async: a.async,
                cache: a.cache,
                beforeSend: function (c, b) {
                    a.executeBeforeSendFunc && ajaxBeforeSend && ajaxBeforeSend()
                },
                success: function (c, d, b) {
                    if(c.code!='000000'){
                        location.href="/channel/login.html";
                    }else{
                        a.onSuccess && a.onSuccess(c, d, b);
                    }
                },
                error: function (b, c, d) {
                    console.log("response: " + JSON.stringify(b));
                    console.log("errorString: " + c);
                    console.log("statusText: " + d);
                    a.onError && a.onError(b, c, d)
                    location.href="/channel/login.html";
                },
                complete: function (c, b) {
                    a.executeCompleteFunc && ajaxComplete && ajaxComplete();
                    a.onComplete && a.onComplete(c, b)
                }
            })
        }
    }, appBlankPost: function (b, a) {
        var c = $("<form>").attr({action: b, target: "_blank", method: "post"});
        $.each(a, function (e, d) {
            c.append('<input type="hidden" name="' + e + '" value="' + d + '">')
        });
        $("body").append(c);
        c.submit().remove()
    }
});
$.fn.extend({
    appSerialize: function () {
        var e = {}, b = [], f = [];
        var c = [];
        var d = [];
        $.each(this.serializeArray(), function (l, h) {
            var g = Object.prototype.toString.call($('[name="' + h.name + '"]').val()) === "[object Array]";
            if (!c.contains(h.name) || g) {
                if (g) {
                    if ($('[name="' + h.name + '"]').length > 1) {
                        throw new Error("存在多个相同 name 控件.")
                    }
                    var k = d[h.name] || 0, j = "", n = h.name.lastIndexOf(".");
                    if (n > 0) {
                        j = h.name.substr(0, n) + "[" + k + "]" + h.name.substr(n, h.name.length - n)
                    } else {
                        j = h.name + "[" + k + "]"
                    }
                    var m = {name: j, value: h.value};
                    if (!c.contains(j)) {
                        c.push(j);
                        b.push(m);
                        d[h.name] = ++k
                    }
                } else {
                    b.push(h);
                    c.push(h.name)
                }
            }
        });
        b = b.sort(function (h, g) {
            return h.name.localeCompare(g.name)
        });
        $.each(b, function (h, g) {
            var j = g.name.split("."), k = [];
            $.each(j, function (m, l) {
                if (l.contains("[")) {
                    k.push(l.substring(0, l.indexOf("[")));
                    k.push(l.substring(l.indexOf("["), l.indexOf("]") + 1))
                } else {
                    k.push(l)
                }
            });
            f.push({names: k, value: g.value || ""})
        });
        if (f && f.length) {
            var a = function (j, g) {
                var h = [], i = undefined;
                $.each(j, function (m, k) {
                    if (g < k.names.length) {
                        var l = k.names[g];
                        if (i && i.name === l) {
                            i.value.push(k)
                        } else {
                            i = {name: l, value: [k]};
                            h.push(i)
                        }
                    }
                });
                if (h && h.length) {
                    $.each(h, function (l, k) {
                        if (k.name.contains("[")) {
                            $.each(k.value, function (n, m) {
                                k.value[n].names[g] = "[" + l + "]"
                            })
                        }
                        a(k.value, g + 1)
                    })
                }
            };
            a(f, 0);
            $.each(f, function (j, g) {
                var h = g.names.join(".").replaceAll("\\.\\[", "[");
                if (e[h] === undefined) {
                    e[h] = g.value
                }
            })
        }
        return e
    }, appSubmit: function (a) {
        a = $.extend({onBeforeSend: undefined, onSuccess: undefined, onError: undefined, onComplete: undefined}, a);
        var b = $(this);
        return b.on({
            submit: function (c) {
                IsSubmiting = true;
                c.preventDefault();
                if (b.valid()) {
                    $.appAjax({
                        url: b.attr("action"), data: b.appSerialize(), onBeforeSend: function (d) {
                            b.find("[data-val-password]").each(function (g, e) {
                                var f = $(e).attr("name"), h = d[f];
                                if (h !== undefined && (typeof h).toUpperCase() === "STRING" && h !== "") {
                                    d[f] = h.passwordEncrypt()
                                }
                            });
                            return a.onBeforeSend && a.onBeforeSend(d)
                        }, onSuccess: a.onSuccess, onError: a.onError, onComplete: function (e, d) {
                            IsSubmiting = false;
                            a.onComplete && a.onComplete(e, d)
                        }
                    })
                } else {
                    IsSubmiting = false
                }
            }
        })
    }, appAutoSubmit: function () {
        var a = $(this);
        a.find("input,select,textarea").on({
            change: function () {
                a.submit()
            }
        });
        return a
    }, appBindValidator: function () {
        var a = this.parents().addBack().filter("form").add(this.find("form")).has("[data-val=true]");
        if (a && a.length) {
            a.removeData("validator");
            $.validator && $.validator.unobtrusive.parse(a)
        }
        return $(this)
    }, appDatagrid: function (q) {
        q = $.extend({
            url: undefined,
            data: undefined,
            dataList: undefined,
            minWidth: 1000,
            showHeader: true,
            showFooter: true,
            showCheckboxColumn: false,
            toolbar: undefined,
            columns: undefined,
            page: 1,
            pageList: [15, 20, 30, 50, 100],
            pageSize: 15,
            rowTemplate: undefined,
            emptyCellValue: "-",
            onAfterResponse: undefined,
            onBeforeRenderRow: undefined,
            onAfterRender: undefined
        }, q);
        var i = function () {
            var F = q.responseData;
            var E = q.onAfterResponse && q.onAfterResponse(F);
            if (E !== undefined) {
                F = E
            }
            if (F.data && F.data.totalElements) {
                var D = m.find("tbody");
                $.each(F.data.content, function (J, H) {
                    var G = q.onBeforeRenderRow && q.onBeforeRenderRow(H, J);
                    if (G !== undefined) {
                        H = G
                    }
                    var K = h;
                    $.each(q.columns, function (M, L) {
                        var N = L.formatter ? L.formatter(H[L.field], H, J) : H[L.field];
                        K = K.replaceAll("{{" + L.field + "}}", N === undefined ? q.emptyCellValue : N)
                    });
                    var I = $(K);
                    D.append(I);
                    if (q.showCheckboxColumn) {
                        I.find('input[data-column="rowcheckbox"]').attr({"data-index": J}).on({
                            change: function () {
                                m.find('input[data-column="headercheckbox"]').prop({checked: m.find('input[data-column="rowcheckbox"]:checked').length === F.data.length})
                            }
                        })
                    }
                })
            } else {
                m.find("tbody").append('<tr><td class="text-center" colspan="' + B + '">暂无数据</td></tr>')
            }
            if (q.showFooter) {
                if (q.page === 1) {
                    l.addClass("text-muted");
                    r.addClass("text-muted")
                } else {
                    l.removeClass("text-muted");
                    r.removeClass("text-muted")
                }
                if (q.page === F.data.totalElements) {
                    t.addClass("text-muted");
                    j.addClass("text-muted")
                } else {
                    t.removeClass("text-muted");
                    j.removeClass("text-muted")
                }
                c.val(q.page).data("current", q.page).data("max", F.data.totalPages);
                y.html(F.data.totalPages);
                p.html(F.data.totalElements || 0);
                g.html(F.data.totalElements || 0)
            }
            q.onAfterRender && q.onAfterRender(F)
        };
        if (!q.data) {
            q.data = {}
        }
        var e = 0, s = {};
        if (q.columns && q.columns.length) {
            var b = [];
            if (isNumber(q.firstSortIndex)) {
                b.push(q.columns[q.firstSortIndex])
            }
            $.each(q.columns, function (E, D) {
                if (q.firstSortIndex !== E) {
                    b.push(q.columns[E])
                }
            });
            $.each(b, function (E, D) {
                if (D.width) {
                    e += D.width
                }
                if (D.sortable) {
                    if (D.order !== "asc") {
                        D.order = "desc"
                    }
                    s[D.field] = D.order
                } else {
                    D.sortable = false;
                    D.order = undefined
                }
            })
        }
        q.data.sorts = s;
        q.data.page = q.page;
        q.data.size = q.pageSize;
        var B = q.columns.length + (q.showCheckboxColumn ? 1 : 0);
        var v = $(this);
        v.addClass("datalist").empty();
        var m = $('<table id="dategrid" class="table table-striped table-hover"' + (q.minWidth ? ' style="min-width: ' + q.minWidth + 'px;"' : "") + "><thead></thead><tbody></tbody></table>");
        v.append($('<div class="datalist-body table-responsive"></div>').append(m));
        if (q.showHeader && q.toolbar && q.toolbar.length) {
            var n = $('<tr id="dategrid_toolbar"><th id="dategrid_toolbar_container" colspan="' + B + '"></th></tr>');
            m.find("thead").append(n);
            var w = n.find("#dategrid_toolbar_container");
            $.each(q.toolbar, function (E, D) {
                w.append($('<a href="javascript:void(0);" data-index="' + E + '">' + (D.iconCls ? '<span class="mr5 ' + D.iconCls + '"></span>' : "") + D.text + "</a>").on({
                    click: function () {
                        var F = q.toolbar[$(this).data("index")];
                        F && F.handler && F.handler()
                    }
                }))
            })
        }
        var A = $('<tr id="dategrid_title"></tr>');
        if (q.showHeader) {
            m.find("thead").append(A)
        }
        var h = "";
        if (q.showCheckboxColumn) {
            var f = $('<th><label class="checkbox"><input type="checkbox" data-column="headercheckbox" /><span>&nbsp;</span></label></th>');
            A.append(f);
            f.find('input[data-column="headercheckbox"]').on({
                change: function () {
                    m.find('input[data-column="rowcheckbox"]').prop({checked: $(this).is(":checked")})
                }
            });
            h += '<td><label class="checkbox"><input type="checkbox" data-column="rowcheckbox" /><span>&nbsp;</span></label></td>'
        }
        var x = q.columns.length - 1, d = 100;
        $.each(q.columns, function (F, D) {
            var H = $('<th data-index="' + F + '"></th>').text(D.title);
            A.append(H);
            if (D.width) {
                var G = 0;
                if (F === x) {
                    G = d
                } else {
                    G = parseFloat((D.width / e * 100).toFixed(2));
                    d -= G
                }
                H.css({width: G + "%"})
            }
            var E = "";
            if (D.align === "center") {
                E = "text-center";
                H.addClass("text-center")
            } else {
                if (D.align === "right") {
                    E = "text-right";
                    H.addClass("text-right")
                }
            }
            if (D.sortable) {
                H.addClass("sorted").attr({title: "点击更改排序方式"}).append($('<span class="fa fa-angle-' + (D.order === "desc" ? "down" : "up") + ' text-bold ml10"></span>')).on({
                    click: function () {
                        var I = q.columns[$(this).data("index")];
                        q.firstSortIndex = $(this).data("index");
                        console.log(I);
                        if (I.sortable) {
                            I.order = I.order === "desc" ? "asc" : "desc";
                            v.appDatagrid(q)
                        }
                    }
                })
            }
            h += '<td class="' + E + '">{{' + D.field + "}}</td>"
        });
        if (q.rowTemplate === undefined) {
            h = "<tr>" + h + "</tr>"
        } else {
            h = '<tr><td colspan="' + B + '">' + q.rowTemplate + "</td></tr>"
        }
        if (q.showFooter) {
            var o = {
                checkPage: function () {
                    var E = parseInt(c.val());
                    var F = c.data("current");
                    var D = c.data("max");
                    if (isNaN(E)) {
                        c.val(F)
                    } else {
                        if (E < 1) {
                            c.val(1)
                        } else {
                            if (E > D) {
                                c.val(D)
                            } else {
                                c.val(E)
                            }
                        }
                    }
                    return {newPage: E, current: F, max: D}
                }, go: function () {
                    q.page = parseInt(c.val());
                    v.appDatagrid(q)
                }
            };
            var C = $('<div id="dategrid_pagination" class="datalist-footer row"></div>');
            v.append(C);
            var a = $('<div id="dategrid_pagination_info" class="col-md-16 pagination-info"><ul class="pagination pagination-noborder pagination-sm"><li class="pagination-info-rows"><span class="text-gray">每页显示</span><select id="dategrid_pagination_info_rows" class="form-control input-sm pull-left" style="width: 75px;"></select><span class="text-gray">条记录，共</span><span id="dategrid_pagination_info_totalpage" class="text-gray pl0 pr0">1</span><span class="text-gray hidden-xs hidden-sm">页</span><span class="text-gray visible-xs pr0 visible-sm">页（</span><span id="dategrid_pagination_info_totalrecord" class="text-gray pl0 pr0 visible-xs visible-sm">0</span><span class="text-gray visible-xs visible-sm">条数据）</span></li><li><span class="text-muted hidden-xs">|</span></li><li class="pagination-info-pages"><a id="dategrid_pagination_info_first" class="text-muted" href="javascript:void(0);" title="跳转到首页">首页</span></a><a id="dategrid_pagination_info_prev" class="text-muted" href="javascript:void(0);" title="跳转到上一页"><span class="fa fa-chevron-left"></span></a><span class="text-gray">当前第</span><input id="dategrid_pagination_info_current" class="form-control input-sm pull-left" style="width: 75px;" type="text" value="1" title="跳转到指定页" /><a id="dategrid_pagination_info_next" class="text-muted" href="javascript:void(0);" title="跳转到下一页"><span class="fa fa-chevron-right"></span></a><a id="dategrid_pagination_info_last" class="text-muted" href="javascript:void(0);" title="跳转到尾页">尾页</span></a></li><li><span class="text-muted">|</span></li><li class="pagination-info-refresh"><a id="dategrid_pagination_info_refresh" href="javascript:void(0);" title="刷新数据"><span class="hidden-xs">刷新数据</span><span class="fa fa-refresh"></span></a></li></ul></div>');
            C.append(a);
            var k = a.find("#dategrid_pagination_info_rows").on({
                change: function () {
                    q.pageSize = parseInt($(this).val());
                    v.appDatagrid(q)
                }
            });
            $.each(q.pageList, function (E, D) {
                k.append("<option" + (D === q.pageSize ? ' selected="selected"' : "") + ' value="' + D + '">' + D + "</option>")
            });
            var y = a.find("#dategrid_pagination_info_totalpage");
            var p = a.find("#dategrid_pagination_info_totalrecord");
            var l = a.find("#dategrid_pagination_info_first").on({
                click: function () {
                    var D = c.data("current");
                    if (D > 1) {
                        q.page = 1;
                        v.appDatagrid(q)
                    }
                }
            });
            var r = a.find("#dategrid_pagination_info_prev").on({
                click: function () {
                    var D = c.data("current");
                    if (D > 1) {
                        q.page = D - 1;
                        v.appDatagrid(q)
                    }
                }
            });
            var c = a.find("#dategrid_pagination_info_current").on({
                keyup: function (D) {
                    if (D.keyCode === 13) {
                        var E = o.checkPage();
                        if (E.newPage !== E.current) {
                            o.go()
                        }
                    } else {
                        o.checkPage()
                    }
                }, blur: function () {
                    var D = o.checkPage();
                    if (D.newPage !== D.current) {
                        o.go()
                    }
                }
            }).data("current", 1).data("max", 1);
            var t = a.find("#dategrid_pagination_info_next").on({
                click: function () {
                    var E = c.data("current");
                    var D = c.data("max");
                    if (E < D) {
                        q.page = E + 1;
                        v.appDatagrid(q)
                    }
                }
            });
            var j = a.find("#dategrid_pagination_info_last").on({
                click: function () {
                    var E = c.data("current");
                    var D = c.data("max");
                    if (E < D) {
                        q.page = D;
                        v.appDatagrid(q)
                    }
                }
            });
            var u = a.find("#dategrid_pagination_info_refresh").on({
                click: function () {
                    v.appDatagrid(q)
                }
            });
            var z = $('<div id="dategrid_pagination_record" class="col-md-8 pagination-record hidden-xs hidden-sm"><ul class="pagination pagination-noborder pagination-sm"><li class="pagination-record-total"><span class="text-gray">共</span><span id="dategrid_pagination_record_total" class="text-gray pl0 pr0">0</span><span class="text-gray">条记录</span></li></ul></div>');
            C.append(z);
            var g = z.find("#dategrid_pagination_record_total")
        }
        if (q.url) {
            $.appAjax({
                url: q.url, data: JSON.stringify(q.data), onSuccess: function (D) {
                    q.responseData = D;
                    i()
                }
            })
        } else {
            if (!q.dataList) {
                q.dataList = []
            }
            q.responseData = {
                size: q.pageSize,
                data: q.dataList,
                totalCount: q.dataList.length,
                pageCount: Math.ceil(q.dataList.length / q.pageSize)
            };
            i()
        }
        v.data("opts", q);
        return v
    }, appDatagridReload: function () {
        var a = $(this);
        return a.appDatagrid(a.data("opts"))
    }, appDatagridSelectedRows: function () {
        var c = $(this);
        var b = c.data("opts").responseData.data;
        var a = [];
        c.find('input[data-column="rowcheckbox"]:checked').each(function (e, d) {
            a.push(b[$(this).data("index")])
        });
        return a
    }, appUploadFile: function (b) {
        var c = $(this);
        if (c.fileupload === undefined) {
            throw new Error("需要 jquery.fileupload 插件.")
        }
        b = $.extend({
            url: undefined,
            postName: "fileData",
            multiple: false,
            autoUpload: true,
            fileListId: "files",
            fileType: "All",
            maxFileSize: 9 * 1024 * 1024,
            onFileAdd: undefined,
            onBeforeSubmit: undefined,
            onSuccess: undefined,
            onError: undefined,
            onComplete: undefined,
            onProgressing: undefined
        }, b);
        var e = FileTypeRegs[b.fileType];
        if (e === undefined) {
            e = FileTypeRegs.All
        }
        var a = $("#" + b.fileListId);
        var d = function (f) {
            return {id: f.id, type: f.type, name: f.name, size: f.size}
        };
        c.attr({name: b.postName, multiple: b.multiple}).each(function (h, g) {
            var f = $(g);
            f.fileupload({
                url: b.url, type: "POST", dataType: "json", autoUpload: false, change: function (j, i) {
                    a.empty()
                }, add: function (j, i) {
                    $.each(i.files, function (l, k) {
                        if (e.Reg !== undefined && !e.Reg.test(k.type) && !e.Reg.test(k.name)) {
                            alert(e.Desc + "(" + k.name + ").");
                            return false
                        }
                        if (k.size > b.maxFileSize) {
                            alert("文件(" + k.name + ")超过大小限制(" + b.maxFileSize + "B).");
                            return false
                        }
                        k.id = guid();
                        a.append('<div id="' + k.id + '" class="files-item alert alert-default">   <div class="progress">       <div class="progress-bar progress-bar-success"></div>   </div>   <div class="text-ellipsis">' + k.name + "</div></div>");
                        $(b.onFileAdd && b.onFileAdd(d(k), f)).on({
                            click: function () {
                                i.submit()
                            }
                        });
                        if (b.autoUpload) {
                            i.submit()
                        }
                    })
                }, submit: function (k, j) {
                    var i = true;
                    $.each(j.files, function (n, m) {
                        var o = {id: m.id};
                        var l = (b.onBeforeSubmit && b.onBeforeSubmit(d(m), o, f));
                        if (l === false) {
                            i = false
                        } else {
                            o.id = m.id;
                            j.formData = o
                        }
                    });
                    if (!i) {
                        return false
                    }
                }, done: function (k, j) {
                    var i = false;
                    if (j.result.result && j.result.code === "200") {
                        $.each(j.files, function (m, l) {
                            if (j.result.id === l.id) {
                                setTimeout(function () {
                                    var n = a.find("#" + l.id);
                                    n.removeClass("alert-default").addClass("alert-success").append($('<div class="result-layer"><span class="fa fa-check mr5"></span>上传成功</div>').css({opacity: 0}).animate({opacity: 1}, 250));
                                    setTimeout(function () {
                                        n.animate({opacity: 0}, 1000, function () {
                                            n.remove()
                                        })
                                    }, 1000)
                                }, 250);
                                b.onSuccess && b.onSuccess($.extend(d(l), {data: j.result.data}), f);
                                b.onComplete && b.onComplete($.extend(d(l), {data: j.result.data}), "success", f);
                                i = true
                            }
                        })
                    }
                    if (!i) {
                        k.type = "fileuploadfail";
                        j.fail(k, j)
                    }
                }, fail: function (j, i) {
                    $.each(i.files, function (l, k) {
                        setTimeout(function () {
                            var m = a.find("#" + k.id);
                            m.removeClass("alert-default").addClass("alert-danger").append($('<div class="result-layer">   <span class="fa fa-exclamation-circle mr5"></span>' + (i.result && (i.result.message || "上传失败")) + '   <a href="javascript:void(0);" class="fileupload-close">&times;</a></div>').css({opacity: 0}).animate({opacity: 1}, 250)).find(".progress .progress-bar").removeClass("progress-bar-success").addClass("progress-bar-danger");
                            m.find(".fileupload-close").on({
                                click: function () {
                                    m.animate({opacity: 0}, 500, function () {
                                        m.remove()
                                    })
                                }
                            })
                        }, 250);
                        b.onError && b.onError($.extend(d(k), {result: i.result}), f);
                        b.onComplete && b.onComplete($.extend(d(k), {result: i.result}), "error", f)
                    })
                }, progress: function (j, i) {
                    $.each(i.files, function (m, l) {
                        var k = parseInt(i.loaded / i.total * 100, 10);
                        a.find("#" + l.id + " .progress .progress-bar").css({width: k + "%"});
                        b.onProgressing && b.onProgressing(d(l), k, f)
                    })
                }
            })
        });
        c.data("opts", b);
        return c
    }, appChart: function (b) {
        var d = $(this);
        if (d.highcharts === undefined) {
            throw new Error("需要 highcharts 插件.")
        }
        b = $.extend({
            url: undefined,
            data: undefined,
            dataList: undefined,
            minWidth: 400,
            height: 400,
            title: undefined,
            yAxisTitle: undefined,
            copyright: "",
            xAxisCategories: undefined,
            xAxisTickInterval: 1,
            tooltipValueSuffix: "",
            plotOptions: undefined,
            onAfterResponse: undefined,
            onAfterRender: undefined,
            onAfterClick: undefined
        }, b);
        var c = function (k) {
            var f = b.onAfterResponse && b.onAfterResponse(k);
            if (f !== undefined) {
                k = f
            }
            var g = [];
            var j = [];
            b.renderData = [];
            b.selectedList = [];
            $.each(k, function (m, l) {
                if (!g.contains(l.type)) {
                    g.push(l.type)
                }
                if (l.type === "pie") {
                    l.size = "100%"
                }
                if (m === 0) {
                    $.each(l.data, function (o, n) {
                        j.push(n.xAxis)
                    })
                }
                b.renderData.push(l)
            });
            if (g.contains("pie") && g.length > 1) {
                throw new Error("pie 图表不能与其他类型图表一同渲染.")
            }
            var i = g.contains("pie");
            var h = g.contains("spline") && g.length === 1;
            if (b.chart) {
                b.chart.destroy()
            }
            b.plotOptions = $.extend({
                pie: {
                    showInLegend: true, dataLabels: {
                        enabled: true, formatter: function () {
                            return '<span style="color: ' + this.point.color + ';">' + this.point.name + "：" + this.y + "（" + this.percentage.toFixed(2) + "%）</span>"
                        }
                    }
                }
            }, b.plotOptions);
            b.plotOptions.series = {
                allowPointSelect: !!b.onAfterClick,
                cursor: b.onAfterClick ? "pointer" : "default",
                events: {
                    click: function (p) {
                        if (b.onAfterClick) {
                            var o = this.index, n = p.point.x, m = o + "-" + n;
                            if (p.ctrlKey === true || p.shiftKey === true) {
                                if (b.selectedList.contains(m)) {
                                    b.selectedList.splice($.inArray(m, b.selectedList), 1)
                                } else {
                                    b.selectedList.push(m)
                                }
                            } else {
                                if (b.selectedList.contains(m)) {
                                    b.selectedList = []
                                } else {
                                    b.selectedList = [m]
                                }
                            }
                            var l = [];
                            var q = b.renderData;
                            $.each(b.selectedList, function (t, s) {
                                var r = s.split("-");
                                l.push({itemIndex: parseInt(r[0]), dataIndex: parseInt(r[1]), data: q[o].data[n]})
                            });
                            b.onAfterClick(l, q, b.chart)
                        }
                    }
                }
            };
            b.chart = new Highcharts.Chart({
                chart: {renderTo: e, zoomType: "x", marginTop: 45},
                colors: a,
                exporting: {
                    buttons: {
                        contextButton: {
                            menuItems: [{
                                text: "导出PNG", onclick: function () {
                                    this.exportChart({type: "image/png"})
                                }
                            }, {
                                text: "导出JPEG", onclick: function () {
                                    this.exportChart({type: "image/jpeg"})
                                }
                            }, {
                                text: "导出PDF", onclick: function () {
                                    this.exportChart({type: "application/pdf"})
                                }
                            }, {
                                text: "导出SVG", onclick: function () {
                                    this.exportChart({type: "image/svg+xml"})
                                }
                            }]
                        }
                    }
                },
                title: {text: b.title},
                legend: {layout: "horizontal",},
                credits: {text: b.copyright},
                xAxis: {
                    categories: b.xAxisCategories && b.xAxisCategories.length ? b.xAxisCategories : j,
                    tickmarkPlacement: "on",
                    tickInterval: b.xAxisTickInterval,
                },
                yAxis: {title: {text: b.yAxisTitle}},
                tooltip: {
                    shared: i ? false : true,
                    useHTML: true,
                    headerFormat: "<h5>{" + (i ? "series.name" : "point.key") + "}</h5><table>",
                    pointFormat: '<tr><td><span style="color: {' + (i ? "point" : "series") + '.color}; padding-right: 5px;">●</span>{' + (i ? "point" : "series") + '.name}</td><td style="padding-left: 10px; text-align: right;"><b>{point.y} ' + b.tooltipValueSuffix + (i ? "（{point.percentage:.2f}%）" : "") + "</b></td></tr>",
                    footerFormat: "</table>",
                    crosshairs: [h ? {width: 1, color: "#dedede"} : true]
                },
                plotOptions: b.plotOptions,
                series: b.renderData
            });
            b.onAfterRender && b.onAfterRender(k, b.chart)
        };
        if (!b.height) {
            b.height = 400
        }
        var a = ["#99CCFF", "#999999", "#FFCC33", "#CCCCFF", "#6699CC", "#FFCCCC", "#E57F99", "#99CCCC", "#D98C40", "#FFACD6", "#ACD659", "#8383AC"];
        d.css({
            width: "100%",
            minWidth: b.minWidth ? (b.minWidth + "px") : "auto",
            height: b.height ? (b.height + "px") : "auto"
        }).empty();
        var e = d.attr("id");
        if (e === undefined) {
            e = guid();
            d.attr("id", e)
        }
        if (b.url) {
            $.appAjax({
                url: b.url, data: b.data, onSuccess: function (f) {
                    c(f)
                }
            })
        } else {
            c(b.dataList)
        }
        d.data("opts", b);
        return d
    }, appSelect2: function (b) {
        var d = $(this);
        if (d.select2 === undefined) {
            throw new Error("需要 select2 插件.")
        }
        b = $.extend({
            placeholder: undefined,
            disabled: undefined,
            multiple: undefined,
            maxSelectLength: 9999,
            search: undefined,
            keywordName: "keyword",
            createNewItem: false,
            dataList: undefined,
            url: undefined,
            data: undefined,
            size: 10,
            convertItems: undefined,
            onOpen: undefined,
            onClose: undefined,
            onSelect: undefined,
            onUnSelect: undefined
        }, b);
        d.attr({disabled: b.disabled});
        d.attr({multiple: b.multiple});
        var a = undefined;
        if (b.dataList) {
            a = [];
            $.each(b.dataList, function (f, e) {
                if (e.id !== undefined) {
                    a.push({id: e.id.toString(), text: e.text})
                }
            });
            d.empty()
        }
        var c = undefined;
        if (b.url) {
            b.search === undefined && (b.search = true);
            c = {
                delay: 250, url: b.url, type: "POST", dataType: "json", data: function (f) {
                    var e = $.extend({page: f.page || 1, size: b.size}, b.data);
                    e[b.keywordName ? b.keywordName : "keyword"] = f.term;
                    return e
                }, processResults: function (f, g) {
                    g.page = g.page || 1;
                    var e = b.convertItems && b.convertItems(f, g.page, b.size);
                    return {results: e || f, pagination: {more: (g.page * b.size) < f.totalCount}}
                }
            }
        }
        d.each(function (j, f) {
            var g = $(f);
            var e = !!g.attr("multiple");
            var h = false;
            var k = b.placeholder || g.attr("placeholder");
            g.find("option").each(function (m, l) {
                var n = $(l);
                if (n.val() === undefined || n.val() === "") {
                    h = true;
                    if ((k === undefined || k === "") && (n.html() !== undefined && n.html() !== "")) {
                        k = n.html();
                        d.attr({placeholder: k});
                        e && n.remove()
                    }
                }
            });
            g.select2({
                language: "zh-CN",
                placeholder: k,
                minimumResultsForSearch: !e && b.search ? undefined : Infinity,
                allowClear: e ? false : h,
                maximumSelectionLength: b.maxSelectLength,
                tags: b.createNewItem,
                data: a,
                ajax: c
            }).on({
                "select2:open": function (i) {
                    b.onOpen && b.onOpen(i, g)
                }, "select2:close": function (i) {
                    b.onClose && b.onClose(i, g)
                }, "select2:select": function (i) {
                    b.onSelect && b.onSelect(i, g)
                }, "select2:unselect": function (i) {
                    b.onUnSelect && b.onUnSelect(i, g)
                }
            })
        });
        d.data("opts", b);
        return d
    }, appSelect2SetValue: function (a) {
        return $(this).val(a).trigger("change")
    }, appDateTimePicker: function (a) {
        var b = $(this);
        if (b.daterangepicker === undefined) {
            throw new Error("需要 bootstrap.daterangepicker 插件.")
        }
        a = $.extend({
            format: undefined,
            min: undefined,
            max: undefined,
            start: undefined,
            startName: "start",
            end: undefined,
            endName: "end",
            close: true,
            minutesIncrement: 1,
            secondsSelector: false,
            separator: "~",
            days: undefined,
            dateRange: undefined,
            drops: undefined,
            opens: undefined,
            onChange: undefined
        }, a);
        a.separator = a.separator ? a.separator.trim() : "~";
        b.each(function (s, p) {
            var o = $(p), q = o.attr("id"), u = o.attr("name"), f = o.data("toggle"), n = f.indexOf("time") >= 0,
                r = f.indexOf("range") >= 0, k = a.start || new Date(), g = r && a.end ? a.end : k,
                l = a.startName || "start", t = r ? (a.endName || "end") : undefined;
            var e = function (v) {
                var y = o.val().split(" " + a.separator + " ");
                var z = y.length >= 1 && y[0] ? y[0] : "";
                var i = r && y.length >= 2 ? y[1] : "";
                var x = $('input[type="hidden"][data-for="' + q + '"][name="' + l + '"]');
                var w = $('input[type="hidden"][data-for="' + q + '"][name="' + t + '"]');
                x.val(z);
                w.val(i);
                v && a.onChange && a.onChange(r ? [z, i] : [z], r ? [x, w] : [x], o)
            };
            if (u === l || u === t) {
                o.removeAttr("name");
                o.data("name", u)
            }
            var m = o.data("daterangepicker");
            if (m) {
                m.container.remove()
            }
            var j = o.parent(".input-icon");
            if (j || j.length) {
                o.insertAfter(j);
                j.remove()
            }
            j = $('<div class="input-icon datetimepicker"></div>').insertAfter(o);
            var c = $('<span class="fa fa-' + (n ? "clock-o" : "calendar") + '"></span>').appendTo(j).on({
                click: function () {
                    o.click()
                }
            });
            var d = $('<span class="fa fa-caret-down"></span>').appendTo(j).on({
                click: function () {
                    o.click()
                }
            });
            var h = $('<span class="fa fa-clear" title="清空">&times;</span>').appendTo(j).on({
                click: function () {
                    o.data("daterangepicker").setStartDate(new Date());
                    o.data("daterangepicker").setEndDate(new Date());
                    o.val("");
                    e(true);
                    h.hide()
                }
            });
            o.addClass("form-datetimepicker").remove().attr({readonly: true}).insertAfter(c).daterangepicker({
                autoApply: true,
                locale: {
                    format: a.format || (n ? (a.secondsSelector ? "YYYY-MM-DD HH:mm:ss" : "YYYY-MM-DD HH:mm") : "YYYY-MM-DD"),
                    separator: " " + a.separator + " ",
                    applyLabel: "确定",
                    cancelLabel: "取消",
                    customRangeLabel: "自定义",
                    daysOfWeek: ["日", "一", "二", "三", "四", "五", "六"],
                    monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"]
                },
                drops: a.drops,
                opens: a.opens,
                applyClass: "hide",
                cancelClass: "hide",
                singleDatePicker: !r,
                timePicker: n,
                timePicker24Hour: true,
                timePickerIncrement: a.minutesIncrement,
                timePickerSeconds: a.secondsSelector,
                alwaysShowCalendars: true,
                minDate: a.min || "1990-01-01",
                maxDate: a.max || "9999-12-31",
                startDate: k,
                endDate: g,
                dateLimit: r && a.days ? {days: a.days} : undefined,
                ranges: r && a.dateRange ? a.dateRange : undefined
            }).on({
                "show.daterangepicker": function () {
                    d.removeClass("fa-caret-down").addClass("fa-caret-up");
                    if (!o.val()) {
                        o.data("daterangepicker").setStartDate(new Date());
                        o.data("daterangepicker").setEndDate(new Date())
                    }
                }, "hide.daterangepicker": function () {
                    d.removeClass("fa-caret-up").addClass("fa-caret-down");
                    a.close && h.show();
                    e(true)
                }
            });
            if (!a.start) {
                o.data("daterangepicker").setStartDate(new Date());
                o.data("daterangepicker").setEndDate(new Date());
                o.val("");
                h.hide()
            }
            e(false);
            !a.close && h.hide()
        });
        b.data("opts", a);
        return b
    }, appDateTimePickerSetValue: function (a, c) {
        var d = $(this);
        var b = d.data("opts");
        if ((typeof a).toUpperCase() === "OBJECT") {
            $.each(a, function (f, e) {
                b[f] = e
            })
        } else {
            b[a] = c
        }
        return d.appDateTimePicker(b)
    }, appMobileCalendarPicker: function (a) {
        a = $.extend({
            format: "yyyy-MM-dd",
            min: undefined,
            max: undefined,
            closeFor: undefined,
            onChange: undefined,
            onOpen: undefined,
            onClose: undefined,
        }, a);
        var b = $(this);
        b.each(function (e, d) {
            var c = $(d), f = b.val();
            c.data("calendar") !== undefined && c.calendar("destroy");
            c.calendar({
                value: f ? [parseDatetime(f).toFormat("yyyy-MM-dd")] : [],
                minDate: a.min,
                maxDate: a.max,
                formatValue: function (i, g, h) {
                    return g.length ? new Date(g[0]).toFormat(a.format) : ""
                },
                onChange: function (i, g, h) {
                    a.onChange && a.onChange(c, g[0])
                },
                onOpen: function (g) {
                    a.onOpen && a.onOpen(c)
                },
                onClose: function (g) {
                    a.onClose && a.onClose(c);
                    c.val() && a.closeFor && $(a.closeFor.indexOf("#") === 0 ? a.closeFor : ("#" + a.closeFor)).click()
                }
            })
        });
        return b
    }, appMobileTimePicker: function (a) {
        a = $.extend({
            format: "HH:mm",
            view: "minute",
            hours: undefined,
            minutes: undefined,
            seconds: undefined,
            onChange: undefined,
            onOpen: undefined,
            onClose: undefined,
        }, a);
        var b = $(this);
        b.each(function (e, d) {
            var c = $(d), g = c.val(), f = function (m, j, n) {
                var k = [];
                for (var l = m; l < j; l += n) {
                    k.push(l.toString().padLeft(2, "0"))
                }
                return k
            }, h = [{textAlign: "center", values: a.hours ? a.hours : f(0, 24, 1)}, {divider: true, content: "时"}];
            if (a.view) {
                a.view = a.view.toUpperCase();
                if (a.view === "MINUTE" || a.view === "SECOND") {
                    h.push({textAlign: "center", values: a.minutes ? a.minutes : f(0, 60, 1)});
                    h.push({divider: true, content: "分"})
                }
                if (a.view === "SECOND") {
                    h.push({textAlign: "center", values: a.seconds ? a.seconds : f(0, 60, 1)});
                    h.push({divider: true, content: "秒"})
                }
            }
            c.data("picker") !== undefined && c.picker("destroy");
            c.picker({
                value: g ? g.split(":") : undefined, cols: h, formatValue: function (l, i, k) {
                    if (i.length) {
                        var j = a.format.replace("HH", i[0]).replace("hh", i[0]);
                        if (i.length >= 2) {
                            j = j.replace("MM", i[1]).replace("mm", i[1])
                        }
                        if (i.length >= 3) {
                            j = j.replace("SS", i[2]).replace("ss", i[2])
                        }
                        return j
                    } else {
                        return ""
                    }
                }, onChange: function (k, i, j) {
                    a.onChange && a.onChange(c, i)
                }, onOpen: function (i) {
                    a.onOpen && a.onOpen(c)
                }, onClose: function (i) {
                    a.onClose && a.onClose(c)
                }
            })
        });
        return b
    }, appMobileAreas: function (a) {
        a = $.extend({view: "district", onChange: undefined, onOpen: undefined, onClose: undefined,}, a);
        var b = $(this);
        areaUtility.execute(function () {
            var c = areaUtility.data();
            b.each(function (g, p) {
                var o = $(p), j = function () {
                    var A = o.val();
                    if (!A) {
                        return []
                    }
                    var w = A.split(","), v = w[0], x = w[1], u = w[2], t = [];
                    if (v) {
                        for (var y = 0, z = c.provinces.length; y < z; ++y) {
                            if (c.provinces[y].nameCN === v) {
                                t.push(c.provinces[y].provinceId);
                                break
                            }
                        }
                        if (t[0] && x) {
                            for (var y = 0, z = c.cities.length; y < z; ++y) {
                                if (c.cities[y].provinceId === t[0] && c.cities[y].nameCN === x) {
                                    t.push(c.cities[y].cityId);
                                    break
                                }
                            }
                            if (t[1] && u) {
                                for (var y = 0, z = c.districts.length; y < z; ++y) {
                                    if (c.districts[y].cityId === t[1] && c.districts[y].nameCN === u) {
                                        t.push(c.districts[y].districtId);
                                        break
                                    }
                                }
                            }
                        }
                    }
                    return t
                }, h = function () {
                    var i = {ids: [], names: []};
                    $.each(c.provinces, function (u, t) {
                        i.ids.push(t.provinceId);
                        i.names.push(t.nameCN)
                    });
                    return i
                }, d = function (t) {
                    var i = {ids: [], names: []};
                    $.each(c.cities, function (w, u) {
                        if (u.provinceId === t) {
                            i.ids.push(u.cityId);
                            i.names.push(u.nameCN)
                        }
                    });
                    return i
                }, r = function (t) {
                    var i = {ids: [], names: []};
                    $.each(c.districts, function (w, u) {
                        if (u.cityId === t) {
                            i.ids.push(u.districtId);
                            i.names.push(u.nameCN)
                        }
                    });
                    return i
                };
                var s = j();
                var q = h(), l = s[0] ? s[0] : q.ids[0], m = d(l), k = s[1] ? s[1] : m.ids[0], f = r(k),
                    e = s[2] ? s[2] : f.ids[0];
                var n = [{textAlign: "center", values: q.ids, displayValues: q.names, cssClass: "col-provinces"}];
                if (a.view) {
                    a.view = a.view.toUpperCase();
                    if (a.view === "CITY" || a.view === "DISTRICT") {
                        n.push({textAlign: "center", values: m.ids, displayValues: m.names, cssClass: "col-cities"})
                    }
                    if (a.view === "DISTRICT") {
                        n.push({textAlign: "center", values: f.ids, displayValues: f.names, cssClass: "col-districts"})
                    }
                }
                o.data("picker") !== undefined && o.picker("destroy");
                o.picker({
                    title: o.attr("placeholder"), cols: n, value: [l, k, e], formatValue: function (u, i, t) {
                        return t.join(",")
                    }, onChange: function (y, t, x) {
                        var w = parseInt(t[0]), i = parseInt(t[1]), v = parseInt(t[2]);
                        if (w !== l && !isNaN(i)) {
                            m = d(w);
                            i = m.ids[0];
                            y.cols[1].replaceValues(m.ids, m.names);
                            if (!isNaN(v)) {
                                f = r(i);
                                v = f.ids[0];
                                y.cols[2].replaceValues(f.ids, f.names)
                            }
                            l = w;
                            k = i;
                            e = v;
                            y.updateValue();
                            return
                        } else {
                            if (i !== k && !isNaN(v)) {
                                f = r(i);
                                v = f.ids[0];
                                y.cols[2].replaceValues(f.ids, f.names);
                                l = w;
                                k = i;
                                e = v;
                                y.updateValue();
                                return
                            }
                        }
                        var u = [w];
                        !isNaN(i) && u.push(i);
                        !isNaN(i) && !isNaN(v) && u.push(v);
                        o.data("ids", u);
                        a.onChange && a.onChange(o, u)
                    }, onOpen: function (i) {
                        a.onOpen && a.onOpen(o)
                    }, onClose: function (i) {
                        a.onClose && a.onClose(o)
                    }
                })
            })
        });
        return b
    }, appSuspensionBlock: function (b) {
        var h = $(this);
        b = $.extend({scrollTop: h.offset().top, action: undefined, top: 0, shadow: true}, b);
        var e = h.offset().left, d = parseFloat(h.css("width")), i = parseFloat(h.css("height")),
            c = parseFloat(h.css("margin-top")), f = parseFloat(h.css("margin-bottom")), g;
        var a = function () {
            var j = $(document.body).scrollTop();
            if (j > b.scrollTop) {
                if (b.action === "fade") {
                    h.fadeIn("fast")
                } else {
                    if (b.action === "suspension") {
                        h.addClass("suspension").css({top: b.top, left: e, width: d});
                        if (b.shadow) {
                            h.addClass("suspension-shadow")
                        }
                        if (!g) {
                            g = $("<div>").addClass("suspension-placeholder").css({
                                display: "inline-block",
                                width: d,
                                height: i,
                                "margin-top": c,
                                "margin-bottom": f
                            }).insertAfter(h)
                        }
                    }
                }
            } else {
                if (b.action === "fade") {
                    h.fadeOut("fast")
                } else {
                    if (b.action === "suspension") {
                        h.removeClass("suspension").removeClass("suspension-shadow").css({
                            top: "",
                            left: "",
                            width: "",
                            "margin-top": "",
                            "margin-bottom": ""
                        });
                        if (g) {
                            g.remove();
                            g = undefined
                        }
                    }
                }
            }
        };
        $(window).on({scroll: a});
        a();
        h.data("opts", b);
        return h
    }, appScrollMonitor: function (c) {
        c = $.extend({offset: 0}, c);
        var d = $(this), f = parseFloat(d.data("offset")), e = d.find('a[href^="#"]'), b = [];
        e.on({
            click: function () {
                $.each(b, function (h, g) {
                    g.link.parent().removeClass("active")
                });
                $(this).parent().addClass("active")
            }
        });
        e.each(function (j, h) {
            var g = $(h);
            b.push({link: g, target: $(g.attr("href"))})
        });
        var a = function () {
            var j = $(document.body).scrollTop(), g = 0;
            for (var h = 0, k = b.length; h < k; ++h) {
                if (b[h].target.offset().top - j > c.offset) {
                    break
                } else {
                    g = h
                }
            }
            $.each(b, function (m, l) {
                if (m === g) {
                    l.link.parent().addClass("active")
                } else {
                    l.link.parent().removeClass("active")
                }
            })
        };
        if (b && b.length) {
            $(window).on({scroll: a});
            a()
        }
        d.data("opts", c);
        return d
    }
});
var getEnumValueName = function (enumName, value) {
    if (EnumValueReg.test(value)) {
        try {
            var val = parseInt(value), enumObject = eval(enumName);
            for (var i in enumObject) {
                if (enumObject[i] === val) {
                    return i
                }
            }
        } catch (e) {
            return undefined
        }
    }
    return undefined
};
var getEnumMessage = function (enumName, value) {
    var valueName = EnumValueReg.test(value) ? getEnumValueName(enumName, value) : value;
    try {
        return eval(enumName + ".message." + valueName)
    } catch (e) {
        return undefined
    }
};
var browser = {
    versions: function () {
        var b = navigator.userAgent, a = navigator.appVersion;
        return {
            versionCode: a,
            trident: b.contains("Trident"),
            presto: b.contains("Presto"),
            webKit: b.contains("AppleWebKit"),
            gecko: b.contains("Gecko") && b.contains("KHTML"),
            mobile: !!b.match(/AppleWebKit.*Mobile.*/),
            ios: !!b.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
            android: b.contains("Android") || b.contains("Linux"),
            iPhone: b.contains("iPhone") || b.contains("Mac"),
            iPad: b.contains("iPad"),
            webApp: !b.contains("Safari"),
            google: b.indexOf("Chrome") > -1,
            isWechat: !!b.match(/MicroMessenger/i)
        }
    }(), language: (navigator.browserLanguage || navigator.language).toLowerCase()
};
$(function () {
    if ($("body").highcharts) {
        Highcharts.setOptions({
            lang: {
                decimalPoint: ".",
                thousandsSep: ",",
                numericSymbols: "k,M,G,T,P,E",
                loading: "加载中",
                noData: "暂无数据",
                contextButtonTitle: "导出图表",
                downloadJPEG: "导出JPEG",
                downloadPDF: "导出PDF",
                downloadPNG: "导出PNG",
                downloadSVG: "导出SVG",
                printChart: "打印图表",
                months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                shortMonths: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
                weekdays: ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"],
                drillUpText: "返回",
                resetZoom: "恢复缩放",
                resetZoomTitle: "恢复图表"
            }
        })
    }
});