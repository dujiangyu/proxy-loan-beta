$(function () {
    var a = [{field: "channelNo", title: "渠道", width: 3, align: "center"},
            {field: "phone", title: "手机号码", width: 6, align: "center",formatter:
                function(value, row, index){
                    if (value==null) {
                        return '';
                    }else {
                        return value;//.substr(0,3)+"****"+value.substr(7,4);
                    }
                }},
            {field: "applyDate", title: "申请时间", width: 3, align: "center"},
            {field: "status", title: "状态", width: 4, align: "center",formatter:
                function(value, row, index){
                    if (value==2) {
                        return '<font color="red">审核拒绝</font>';
                    }else if (value==1) {
                        return '<font color="green">审核通过</font>';
                    }else{
                        return '待审核';
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

    //退出系统
    $("#logout").click(function () {
        $.ajax("/common/logout.json", null);
    })
});