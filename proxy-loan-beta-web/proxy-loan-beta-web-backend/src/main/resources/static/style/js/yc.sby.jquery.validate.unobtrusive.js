(function (e) {
    var d = /[^\x00-\xff]/g, a = /^(\d{15}|\d{17}(\d|x|X))$/, g = {
            "11": "北京",
            "12": "天津",
            "13": "河北",
            "14": "山西",
            "15": "内蒙古",
            "21": "辽宁",
            "22": "吉林",
            "23": "黑龙江",
            "31": "上海",
            "32": "江苏",
            "33": "浙江",
            "34": "安徽",
            "35": "福建",
            "36": "江西",
            "37": "山东",
            "41": "河南",
            "42": "湖北",
            "43": "湖南",
            "44": "广东",
            "45": "广西",
            "46": "海南",
            "50": "重庆",
            "51": "四川",
            "52": "贵州",
            "53": "云南",
            "54": "西藏",
            "61": "陕西",
            "62": "甘肃",
            "63": "青海",
            "64": "宁夏",
            "65": "新疆",
            "71": "台湾",
            "81": "香港",
            "82": "澳门",
            "91": "国外"
        }, b = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2],
        c = ["1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2"];
    var f = function (h) {
        var j = e('[name="' + h + '"]');
        var i = j && j.length ? j.attr("type") : undefined;
        if (!i) {
            i = ""
        }
        if (i.toUpperCase() === "RADIO") {
            j = e(':radio[name="' + h + '"]:checked')
        } else {
            if (i.toUpperCase() === "CHECKBOX") {
                if (!j.is(":checked")) {
                    j = undefined
                }
            }
        }
        return j && j.length ? j.val() : ""
    };
    if (e && e.validator) {
        e.extend(e.validator, {
            messages: {
                required: "请先输入值",
                remote: "数据验证不通过",
                email: "格式错误",
                url: "格式错误",
                date: "格式错误",
                dateISO: "格式错误",
                number: "格式错误",
                digits: "格式错误",
                creditcard: "格式错误",
                equalTo: "输入的值不一致",
                maxlength: e.validator.format("至多输入{0}个字符"),
                minlength: e.validator.format("至少输入{0}个字符"),
                rangelength: e.validator.format("请输入{0}~{1}个字符"),
                range: e.validator.format("请输入{0}~{1}"),
                max: e.validator.format("最大不能超过{0}"),
                min: e.validator.format("最小不能小于{0}")
            },
        })
    }
    e.validator.addMethod("password", function (k, h, i) {
        if (k === undefined || k === "") {
            return true
        }
        var j = i.pattern;
        if (k && j) {
            return (new RegExp(j, "g")).test(k)
        } else {
            return true
        }
    });
    e.validator.unobtrusive.adapters.add("password", ["pattern"], function (h) {
        h.rules.password = {pattern: h.params.pattern};
        h.messages.password = h.message
    });
    e.validator.addMethod("equals", function (l, i, k) {
        if (l === undefined || l === "") {
            return true
        }
        var m = k.equalstype, h = k.equalstarget, j;
        if (m === "value") {
            j = h
        } else {
            if (m === "property") {
                j = f(h)
            }
        }
        return l === j
    });
    e.validator.unobtrusive.adapters.add("equals", ["equalstype", "equalstarget"], function (h) {
        h.rules.equals = {equalstype: h.params.equalstype, equalstarget: h.params.equalstarget};
        h.messages.equals = h.message
    });
    e.validator.addMethod("notequals", function (l, i, k) {
        if (l === undefined || l === "") {
            return true
        }
        var m = k.equalstype, h = k.equalstarget, j;
        if (m === "value") {
            j = h
        } else {
            if (m === "property") {
                j = f(h)
            }
        }
        return l !== j
    });
    e.validator.unobtrusive.adapters.add("notequals", ["equalstype", "equalstarget"], function (h) {
        h.rules.notequals = {equalstype: h.params.equalstype, equalstarget: h.params.equalstarget};
        h.messages.notequals = h.message
    });
    e.validator.addMethod("requiredifequals", function (n, i, m) {
        var j = m.propertyname, h = m.propertyvalues, l = f(j), k = h.split(",");
        if (k.contains(l)) {
            return e.validator.methods.required.call(this, n, i, m)
        } else {
            return true
        }
    });
    e.validator.unobtrusive.adapters.add("requiredifequals", ["propertyname", "propertyvalues"], function (h) {
        h.rules.requiredifequals = {propertyname: h.params.propertyname, propertyvalues: h.params.propertyvalues};
        h.messages.requiredifequals = h.message
    });
    e.validator.addMethod("requiredifnotequals", function (n, i, m) {
        var j = m.propertyname, h = m.propertyvalues, l = f(j), k = h.split(",");
        if (!k.contains(l)) {
            return e.validator.methods.required.call(this, n, i, m)
        } else {
            return true
        }
    });
    e.validator.unobtrusive.adapters.add("requiredifnotequals", ["propertyname", "propertyvalues"], function (h) {
        h.rules.requiredifnotequals = {propertyname: h.params.propertyname, propertyvalues: h.params.propertyvalues};
        h.messages.requiredifnotequals = h.message
    });
    e.validator.addMethod("requiredifempty", function (l, h, k) {
        var i = k.propertyname, j = f(i);
        if (j === undefined || j === null || j === "") {
            return e.validator.methods.required.call(this, l, h, k)
        } else {
            return true
        }
    });
    e.validator.unobtrusive.adapters.add("requiredifempty", ["propertyname"], function (h) {
        h.rules.requiredifempty = {propertyname: h.params.propertyname};
        h.messages.requiredifempty = h.message
    });
    e.validator.addMethod("requiredifnotempty", function (l, h, k) {
        var i = k.propertyname, j = f(i);
        if (j !== undefined && j !== null && j !== "") {
            return e.validator.methods.required.call(this, l, h, k)
        } else {
            return true
        }
    });
    e.validator.unobtrusive.adapters.add("requiredifnotempty", ["propertyname"], function (h) {
        h.rules.requiredifnotempty = {propertyname: h.params.propertyname};
        h.messages.requiredifnotempty = h.message
    });
    e.validator.addMethod("textlength", function (n, l, p) {
        var k = parseInt(p.maximumlength), q = parseInt(p.minimumlength), j = parseInt(p.doublebytelength),
            r = e(l).attr("name"), o = e('span[data-textlengthspan-for="' + r + '"]'), i = 0;
        if (n !== undefined && n !== "") {
            var h = n.match(d);
            var m = h ? h.length : 0;
            i = m * j;
            i += (n.length - m)
        }
        o.html(i + "/" + k);
        if (q <= i && i <= k) {
            o.removeClass("field-validation-error");
            return true
        } else {
            o.addClass("field-validation-error");
            return false
        }
    });
    e.validator.unobtrusive.adapters.add("textlength", ["maximumlength", "minimumlength", "doublebytelength"], function (h) {
        h.rules.textlength = {
            maximumlength: h.params.maximumlength,
            minimumlength: h.params.minimumlength,
            doublebytelength: h.params.doublebytelength
        };
        h.messages.textlength = h.message
    });
    e.validator.addMethod("collectionlength", function (m, j, o) {
        var h = parseInt(o.maximumlength), q = parseInt(o.minimumlength), i = e(j).data("originalname"), k = 0, p = 0;
        while (true) {
            var n = e('[name^="' + i + "[" + (k++) + ']"]');
            if (!n || !n.length) {
                break
            }
            var l = n ? n.attr("type") : undefined;
            if (!l) {
                l = ""
            }
            if (l.toUpperCase() === "RADIO" || l.toUpperCase() === "CHECKBOX") {
                n = e('[name^="' + i + "[" + (k - 1) + ']"]:checked');
                if (!n || !n.length) {
                    continue
                }
            }
            ++p
        }
        return q <= p && p <= h
    });
    e.validator.unobtrusive.adapters.add("collectionlength", ["maximumlength", "minimumlength"], function (h) {
        h.rules.collectionlength = {maximumlength: h.params.maximumlength, minimumlength: h.params.minimumlength};
        h.messages.collectionlength = h.message
    });
    e.validator.addMethod("identitycode", function (s, k, t) {
        if (s === undefined || s === "") {
            return true
        }
        if (a.test(s)) {
            var q = s.substr(0, 2);
            if (g[q]) {
                var o, n, r;
                if (s.length === 15) {
                    o = parseInt("19" + s.substr(6, 2));
                    n = parseInt(s.substr(8, 2));
                    r = parseInt(s.substr(10, 2))
                } else {
                    o = parseInt(s.substr(6, 4));
                    n = parseInt(s.substr(10, 2));
                    r = parseInt(s.substr(12, 2))
                }
                var h = new Date(o, n - 1, r);
                if (h.getFullYear() === o && h.getMonth() + 1 === n && h.getDate() === r) {
                    if (s.length === 15) {
                        return true
                    } else {
                        var p = s.split(""), l = 0;
                        for (var j = 0; j < 17; ++j) {
                            l += parseInt(p[j]) * b[j]
                        }
                        var m = c[l % 11];
                        return m === p[17].toLowerCase()
                    }
                }
            }
        }
        return false
    });
    e.validator.unobtrusive.adapters.add("identitycode", function (h) {
        h.rules.identitycode = {};
        h.messages.identitycode = h.message
    });
    e.validator.addMethod("digitalrange", function (p, l, q) {
        var k = parseFloat(p), n = parseFloat(q.minimum), i = parseFloat(q.maximum), m = q.minimumproperty,
            h = q.maximumproperty;
        if (isNaN(k)) {
            return true
        }
        if (m) {
            var j = parseFloat(f(m));
            !isNaN(j) && (n = j)
        }
        if (h) {
            var o = parseFloat(f(h));
            !isNaN(o) && (i = o)
        }
        return n <= k && k <= i
    });
    e.validator.unobtrusive.adapters.add("digitalrange", ["minimum", "maximum", "minimumproperty", "maximumproperty"], function (h) {
        h.rules.digitalrange = {
            minimum: h.params.minimum,
            maximum: h.params.maximum,
            minimumproperty: h.params.minimumproperty,
            maximumproperty: h.params.maximumproperty
        };
        h.messages.digitalrange = h.message
    });
    e.validator.addMethod("postremote", function (n, m, o) {
        if (n === undefined || n === "") {
            return true
        }
        var j = o.url, h = o.additionalfields, p = e(m), r = p.attr("name"), k = {};
        if (!IsSubmiting && p.is(":focus")) {
            return true
        }
        k[r.split(".")[0]] = n;
        if (h !== undefined && h !== null && h !== "") {
            e.each(h.split(","), function (t, s) {
                k[s.split(".")[0]] = f(s)
            })
        }
        var i = JSON.stringify({url: j, postData: k});
        var q = p.data("lastrequestdata");
        var l = i === q ? p.data("isvalid") : false;
        if (i !== q || l === undefined) {
            p.data("lastrequestdata", i);
            p.data("isvalid", false);
            p.attr({readonly: true});
            e.ajax({
                url: j,
                type: "POST",
                data: k,
                dataType: "json",
                contentType: "application/json",
                async: true,
                cache: false,
                success: function (t, u, s) {
                    if (t === true || t === false) {
                        l = t
                    }
                },
                complete: function (t, s) {
                    p.data("isvalid", l).valid();
                    p.attr({readonly: false})
                }
            })
        }
        return l
    });
    e.validator.unobtrusive.adapters.add("postremote", ["url", "additionalfields"], function (h) {
        h.rules.postremote = {url: h.params.url, additionalfields: h.params.additionalfields};
        h.messages.postremote = h.message
    })
}(jQuery));