var http="http://127.0.0.1:9527/";
// var http = "http://120.79.255.186:8080/";
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

var customerListUrl = http+"/backend/customer/findByCondition.json";
var updatePwdUrl=http+"/backend/user/updatePassword.json";
var perimissonUrl=http+"/backend/user/getUserPermission.json";
var userListUrl=http+"/backend/user/findByCondition.json";
var saveUserUrl=http+"/backend/user/update.json";
var lockUserUrl=http+"/backend/user/lock.json";
var roleListUrl =http+ "/backend/role/findAllRole.json";
var roleAllListUrl=http+"/backend/role/findByCondition.json";
var saveRoleUrl=http+"/backend/role/update.json";
var allRoleIdsUrl=http+"/backend/role/findAllResource.json";
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
function enableLinkSettle(){
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
                    data: JSON.stringify({"id": row.linkId}),
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
function getCookie(name)
{
 var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

 if(arr=document.cookie.match(reg))

  return (arr[2]);
 else
  return null;
}