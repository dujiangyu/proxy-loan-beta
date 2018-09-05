var http="http://127.0.0.1:9527/";
// var http = "http://www.youxinjk.com/";
var allProductAllUrl = http + "/backend/product/findAllProduct.json";
var channelAllUrl=http+"/backend/channel/findAllChannel.json";
var uploadUrl = http + "/common/upload.json";
var agentListUrl=http+"/backend/agent/findByCondition.json";
var saveAgentUrl=http+"/backend/agent/update.json";
var saveAgentRechargeUrl=http+"/backend/agent/recharge.json";
var enableAgentUrl=http+"/backend/agent/enable.json";
//渠道
var channelListUrl=http+"/backend/channel/findByCondition.json";
var saveChannelUrl=http+"/backend/channel/update.json";
var enableChannelUrl=http+"/backend/channel/enable.json";

var providerListUrl=http+"/backend/provider/findByCondition.json";
var saveProviderUrl=http+"/backend/provider/update.json";
var enableProviderUrl=http+"/backend/provider/enable.json";
var saveProviderRechargeUrl=http+"/backend/provider/recharge.json";

var noticeListUrl=http+"/backend/notice/findByCondition.json";
var saveNoticeUrl=http+"/backend/notice/update.json";
var enableNoticeUrl=http+"/backend/notice/enable.json";
//参数
var findParameterUrl = http + "/backend/parameter/findParameter.json";
var saveParameterUrl = http + "/backend/parameter/update.json";
var findByCodeUrl = http + "/backend/parameter/findByCode.json";

var customerListUrl = http+"/backend/customer/findByCondition.json";
var findByIdUrl = http+"/backend/customer/findById.json";
var updatePwdUrl=http+"/backend/user/updatePassword.json";
var perimissonUrl=http+"/backend/user/getUserPermission.json";
var userListUrl=http+"/backend/user/findByCondition.json";
var saveUserUrl=http+"/backend/user/update.json";
var lockUserUrl=http+"/backend/user/lock.json";
var roleListUrl =http+ "/backend/role/findAllRole.json";
var roleAllListUrl=http+"/backend/role/findByCondition.json";
var saveRoleUrl=http+"/backend/role/update.json";
var allRoleIdsUrl=http+"/backend/role/findAllResource.json";

//三方接口
var queryReiDaUrl=http+"/backend/customer/queryTianbeiLeida.json";
var queryTianbeiUrl=http+"/backend/customer/queryTianbeiReport.json";
var queryYysUrl=http+"/backend/customer/queryTianbeiYys.json";
var blacklistCheckUrl = http+"/backend/customer/queryTianbeiBlackList.json";
var jtOverdueCheckUrl = http+"/backend/customer/queryTianbeiOverdue.json";
var xyInfoCheckUrl=http+"/backend/customer/queryXyInfoCheck.json";
var xyZmfUrl=http+"/backend/customer/queryXyZmf.json";
var xyOverdueFileUrl=http+"/backend/customer/queryXyOverdueFile.json";

function getQueryString(key){
    var reg = new RegExp("(^|&)"+key+"=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(reg);
    return result?decodeURIComponent(result[2]):null;
}
function initCommbox() {
    //加载产品下拉列表
    $('#productId').combobox({
        url: allProductAllUrl,
        method: 'GET',
        contentType:'application/json',
        valueField: 'id',//绑定字段ID
        textField: 'name',//绑定字段Name
        panelHeight: '250',//自适应
        multiple: false
    });
    $('#channelId').combobox({
        url: channelAllUrl,
        method: 'GET',
        contentType:'application/json',
        valueField: 'firstChannelName',//绑定字段ID
        textField: 'firstChannelName',//绑定字段Name
        panelHeight: 'auto',//自适应
        multiple: false
    });
}
function uploadFile(id){
    var result="";
    $.ajaxFileUpload({
        url:uploadUrl,//服务器端程序
        secureuri: false,
        fileElementId:id,//input框的ID
        method:"post",
        async: false,
        dataType: 'json',//返回数据类型
        success: function (data){//上传成功
            $("#"+id+"Img").attr("src",data.data);
            $("#"+id+"Hide").val(data.data);
        },
        error: function (data, status, e)//服务器响应失败处理函数
        {
            alert(e,false);
        }
    });
    return result;
}
function exportExcel(url) {
    $('#searchForm').attr('action',url);
    $('#searchForm').attr('target','_blank');
    $('#searchForm').submit();
}
//停用链接
function enableLink(){
var row = $("#productList").datagrid("getSelected");
if(row) {
    var message="";
    if(row.isValid){
        message="是否停用该链接？";
    }else{
        message="是否启用该链接？";
    }
    $.messager.confirm('温馨提示', message, function (r) {
        if (r) {
            $.ajax({
                url: deleteLinkUrl,
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({"id": row.id}),
                dataType: "json",
                success: function (datas) {
                    Search(1,30);
                }
            });
        }
    });
}else{
    $.messager.alert('温馨提示', "请选择需要操作的数据行", 'info');
}
}
function post(url,data) {
    var returnData;
    data["type"]="manager";
    var json = JSON.stringify(data);
    json = json.replace(/\r\n/g,"\n");
    $.ajax({
        url: url,
        contentType: "application/json;charset=utf-8",
        data: json,
        method:"post",
        dataType: "json",
        async: false,
        beforeSend:function () {
        },
        success: function (data) {
            returnData = data;
        },
        complete:function () {
        },
        error: function () {
            //showTipMessage("数据请求错误!",true);
        }

    });
    return returnData;
}

function httpGet(url,data) {
    var returnData;
    data = data;
    $.ajax({
        url: url,
        contentType: "application/json;charset=utf-8",
        data: data,
        method:"get",
        dataType: "json",
        async: false,
        beforeSend:function () {
        },
        success: function (data) {
            returnData = data;
        },
        complete:function () {
        },
        error: function () {
            //showTipMessage("数据请求错误!",true);
        }
    });
    return returnData;
}
function getArgsFromHref(sHref, sArgName)
{   var args    = sHref.split("?");
    var retval = 0;
    if(args[0] == sHref) /*参数为空*/
    {
        return retval; /*无需做任何处理*/
    }
    var str = args[1];
    args = str.split("&");
    for(var i = 0; i < args.length; i ++)
    {
        str = args[i];
        var arg = str.split("=");
        if(arg.length <= 1) continue;
        if(arg[0] == sArgName) retval = arg[1];
    }
    return retval;
}
function showTipMessage(message) {
    $.messager.alert("提示",message,"");
}
function getCookie(name)
{
 var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

 if(arr=document.cookie.match(reg))

  return (arr[2]);
 else
  return null;
}
function datetimeFormat_1(longTypeDate){
    var datetimeType = "";
    var date = new Date();
    date.setTime(longTypeDate);
    datetimeType+= date.getFullYear();   //年
    datetimeType+= "-" + getMonth(date); //月
    datetimeType += "-" + getDay(date);   //日
    datetimeType+= "&nbsp;&nbsp;" + getHours(date);   //时
    datetimeType+= ":" + getMinutes(date);      //分
    datetimeType+= ":" + getSeconds(date);      //分
    return datetimeType;
}
//返回 01-12 的月份值
function getMonth(date){
    var month = "";
    month = date.getMonth() + 1; //getMonth()得到的月份是0-11
    if(month<10){
        month = "0" + month;
    }
    return month;
}
//返回01-30的日期
function getDay(date){
    var day = "";
    day = date.getDate();
    if(day<10){
        day = "0" + day;
    }
    return day;
}
//返回小时
function getHours(date){
    var hours = "";
    hours = date.getHours();
    if(hours<10){
        hours = "0" + hours;
    }
    return hours;
}
//返回分
function getMinutes(date){
    var minute = "";
    minute = date.getMinutes();
    if(minute<10){
        minute = "0" + minute;
    }
    return minute;
}
//返回秒
function getSeconds(date){
    var second = "";
    second = date.getSeconds();
    if(second<10){
        second = "0" + second;
    }
    return second;
}