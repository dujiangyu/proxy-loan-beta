$(function () {
    var a = [{field: "phone", title: "手机号码", width: 3, align: "center"},
        {field: "name", title: "姓名", width: 6, align: "center",formatter:
            function(value, row, index){
                if (value==null) {
                    return '';
                }else {
                    return value;
                }
            }},
        {field: "registerDate", title: "注册时间", width: 3, align: "center"},
        {field: "sesameCredit", title: "芝麻分", width: 3, align: "center"},
        {field: "city", title: "区域", width: 3, align: "center",formatter:
            function(value, row, index){
                if (value==null) {
                    return '';
                }else {
                    return value;
                }
            }},
        {field: "wechatAmount", title: "微粒贷", width: 4, align: "center"},
        {field: "isCreditCard", title: "是否信用卡", width: 4, align: "center",formatter:
            function(value, row, index){
                    if (value) {
                        return '有';
                    }else {
                        return '无';
                    }
                }},
        {field: "isFund", title: "是否有公积金", width: 4, align: "center",formatter:
            function(value, row, index){
                if (value) {
                    return '有';
                }else {
                    return '无';
                }
            }},
        {field: "isSocial", title: "是否有社保", width: 4, align: "center",formatter:
            function(value, row, index){
                if (value) {
                    return '有';
                }else {
                    return '无';
                }
            }}
        ];
    //查询数据
    $("#frmList").on({
        submit: function () {
            $("#tbData").appDatagrid({
                url: $("#frmList").attr("doAction"),
                data: $("#frmList").appSerialize(),

                minWidth: 1200,
                columns: a
            });
            return false
        }
    }).submit();
    //导出数据
    $("#btnExport").click(function () {
        $.appBlankPost("/wechat/userInfo/exportData.json", $("#frmList").appSerialize())
    })
    //退出系统
    $("#logout").click(function () {

        $.ajax("/common/logout.json", null);
    })
});