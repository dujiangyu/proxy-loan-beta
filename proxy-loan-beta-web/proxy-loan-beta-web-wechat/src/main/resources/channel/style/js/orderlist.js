$(function () {
    var a = [{field: "name", title: "姓名", width: 3, align: "center"},
            {field: "phone", title: "手机号码", width: 6, align: "center",formatter:
                function(value, row, index){
                    if (value==null) {
                        return '';
                    }else {
                        return value.substr(0,3)+"****"+value.substr(7,4);
                    }
                }},
            {field: "registerDate", title: "申请时间", width: 3, align: "center"}
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

    //退出系统
    $("#logout").click(function () {
        $.ajax("/common/logout.json", null);
    })
});