// var httpUrl = "http://120.79.255.186";
var httpUrl = "http://192.168.2.105:9527";
// var httpUrl = "http://192.168.1.105:9999";
var sendSmsUrl = httpUrl + "/common/sendValidateCode.json";
var loginUrl = httpUrl + "/common/passwordLogin.json";
var findByIdUrl = httpUrl +"/front/userInfo/findById.json";
var findParameterUrl = httpUrl +"/front/parameter/findParameter.json";
var updateUrl = httpUrl +"/front/userInfo/update.json";
var productListUrl = httpUrl + "/front/product/findByCondition.json";
var findProductByIdUrl = httpUrl +"/front/product/findById.json";
var applyLoanUrl = httpUrl +"/front/product/applyLoan.json";
/**
 * 是否是手机号码
 * @param phone
 * @returns {boolean}
 */
var sendTime =60;
var appName="MSYQ";
 function isMobile(Obj) {
    var val = Obj.val();
    var myreg = /^[1][3,4,5,7,8,9][0-9]{9}$/;
    if(!myreg.test(val)){
        return false;
    }
    return true;
}
function leftTimer() {
    sendTime--;
    if (sendTime <= 0) {
        window.clearInterval(InterValObj);
        $("#sending").css("display","none")
        $("#sendSms").css("display","")
    }else{
        $("#sending").text(""+sendTime+"s")
    }
}
function getArgsFromHref(sHref, sArgName)
{   var args    = sHref.split("?");
    var retval = "CEFMGL3DP";
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
function loginPost(url,json) {
    var returnData;
    $.ajax({
        url: url,
        contentType: "application/json;charset=utf-8",
        data: json,
        method:"post",
        dataType: "json",
        async: false,
        success: function (data) {
            returnData = data;
        },
        error: function () {
            alert("服务请求出错");
        }
    });
    return returnData;
}
function showMessage(message) {
    dialog({
        type: null,
        message: message,
        delay: 1000,
        maskOpacity: 0.01
    });
    return false;
}
var serializeToJson= function (formObj) {
    var serializeObj={};
    var array=formObj.serializeArray();
    $(array).each(function(){
        if(serializeObj[this.name]){
            if($.isArray(serializeObj[this.name])){
                serializeObj[this.name].push(this.value);
            }else{
                serializeObj[this.name]=[serializeObj[this.name],this.value];
            }
        }else{
            serializeObj[this.name]=this.value;
        }
    });

    return JSON.stringify(serializeObj);
}

/**
 * 保存用户信息
 */
function saveUserInfo() {
    var certNo = $("#certNo").val();
    if(certNo!=undefined){
       var isPass =  IdentityCodeValid(certNo);
       if(!isPass){
           return false;
       }
    }
    var jsonStr= serializeToJson($("#userForm"));
    var result =post(updateUrl,jsonStr);
    if(result.success){
        window.location=result.data;
    }else{
        showMessage(result.message);
        window.location.href="/register.html";
    }
}
function findById() {
    var jsonStr=$("#userForm").serialize();
    var result =get(findByIdUrl,jsonStr);
    if(result.success){
        $("#loanPurpose").val(result.data.loanPurpose);
        $("#loanPurposeText").text(result.data.loanPurpose==null?"请选择":result.data.loanPurpose);
        $("#certNo").val(result.data.certNo);
        $("#city").val(result.data.city);
        $("#wechatNumber").val(result.data.wechatNumber);
        $("#wechatAmount").val(result.data.wechatAmount);
        $("#creditCardAmount").val(result.data.creditCardAmount);
        $("#loanAmount").val(result.data.loanAmount);
        $("#income").val(result.data.income);
        $("input:radio[name=isFund][value="+result.data.isFund+"]").prop("checked",true);
        $("input:radio[name=isSocial][value="+result.data.isSocial+"]").prop("checked",true);
        $("input:radio[name=isWechatAmount][value="+result.data.isWechatAmount+"]").prop("checked",true);
        $("input:radio[name=isHouseLoan][value="+result.data.isHouseLoan+"]").prop("checked",true);
        $("input:radio[name=isCarLoan][value="+result.data.isCarLoan+"]").prop("checked",true);
        $("input:radio[name=isBorrow][value="+result.data.isBorrow+"]").prop("checked",true);

    }else{
        showMessage(result.message);
        window.location.href="/register.html";
    }
}
function findBindWechat() {
    var jsonStr=$("#userForm").serialize();
    var result =get(findParameterUrl,jsonStr);
    if(result.success){
        $("#wechatId").val(result.data.wechatId);
        $("#wechatShowId").text(result.data.wechatId);
    }else{
        showMessage(result.message);
    }
}
function post(url,data) {
    var returnData;
    $.ajax({
        type:"POST",
        url: url,
        contentType: "application/json;charset=utf-8",
        data: data,
        method:"post",
        dataType: "json",
        async: false,
        success: function (data) {
            returnData = data;
        },
        error: function () {
            alert("服务请求出错");
        }
    });
    return returnData;
}

function get(url,data) {
    var returnData;
    $.ajax({
        url: url,
        contentType: "application/json;charset=utf-8",
        data: data,
        method:"get",
        dataType: "json",
        async: false,
        success: function (data) {
            returnData = data;
        },
        error: function () {
            alert("服务请求出错");
        }
    });
    return returnData;
}
var initProductList=function (pageNo) {
    if(pageNo==1){
        $("#prolist").empty();
    }
    var jsonStr= serializeToJson($("#proListForm"));
    var result =post(productListUrl,jsonStr);
    var resultData = result.data.content;
    if(result.success){
        var sHtml = "";
        $.each(resultData, function (j) {
            var sLiHtml = "";
            sLiHtml += "<li onclick='viewDetail("+resultData[j].id+")' class=\"clearfix\">";
            sLiHtml += "    <span class=\"name\"><img src=\""+resultData[j].img+"\">"+resultData[j].name+"</span>";
            sLiHtml += "    <span class=\"detail\">";
            sLiHtml += "    <p><span class=\"th\">最高额度"+resultData[j].endAmount+"元</span></p>";
            sLiHtml += "    <p><span class=\"td\">"+resultData[j].productFlag+"</span><span class=\"td\">"+resultData[j].productLabel+"</span></p>";
            sLiHtml += "</span>";
            sLiHtml += "</li>";

            sHtml += sLiHtml;
        });
        if(result.data.totalElements==0){
            $("#prolist").empty();
            sHtml = "<div class=\"main\">";
            sHtml += "<div class=\"invest-buy\"><img src=\"/borrow/assets/images/nodate@3x.png\" alt=\"\"/></div>";
            sHtml += "<p class=\"tc c-gray-666\">暂无数据！</p>";
            sHtml += "</div>";
        }
        $("#prolist").append(sHtml);
    }else{
        showMessage(result.message);
    }
}
var viewDetail = function (id) {
    window.location.href="prodetail.html?id="+id;
}

var findProductById=function (id) {
    var jsonStr="id="+id;
    var result =get(findProductByIdUrl,jsonStr);
    if(result.success){
        $("#id").val(result.data.id);
        $("#url").val(result.data.url);
        $("#img").attr("src",result.data.img);
        $("#name").text(result.data.name);
        $("#endAmount").text(result.data.endAmount);
        $("#endPeriod").text(result.data.endPeriod);
        $("#applyCondition").text(result.data.applyCondition);
    }else{
        showMessage(result.message);
        window.location.href="/register.html";
    }
}
function applyLoan() {
    var param = {};
    param["productId"] = $("#id").val();
    param["applyArea"] = "H5";
    param["appName"] = appName;
    param["channelNo"] = localStorage.getItem("channelNo");
    param["type"] = "APPLY_LOAN";
    var jsonStr = JSON.stringify(param);
    var result = post(applyLoanUrl, jsonStr);
    if (result.success) {
        window.location.href = $("#url").val();
    } else {
        showMessage(result.message);
        window.location.href="/register.html";
    }
}

    /**
     * 身份号码认证
     * @param code
     * @returns {boolean}
     * @constructor
     */
function IdentityCodeValid(code) {
    var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
    var tip = "";
    var pass= true;

    if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
        tip = "身份证号格式错误";
        pass = false;
    }

    else if(!city[code.substr(0,2)]){
        tip = "地址编码错误";
        pass = false;
    }
    else{
        //18位身份证需要验证最后一位校验位
        if(code.length == 18){
            code = code.split('');
            //∑(ai×Wi)(mod 11)
            //加权因子
            var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
            //校验位
            var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
            var sum = 0;
            var ai = 0;
            var wi = 0;
            for (var i = 0; i < 17; i++)
            {
                ai = code[i];
                wi = factor[i];
                sum += ai * wi;
            }
            var last = parity[sum % 11];
            if(parity[sum % 11] != code[17]){
                tip = "校验位错误";
                pass =false;
            }
        }
    }
    if(!pass) showMessage(tip);
    return pass;
}

function getLocation(){
    if (navigator.geolocation){
        navigator.geolocation.getCurrentPosition(showPosition,showError);
    }else{
        showMessage("浏览器不支持地理定位。");
    }
}

function showPosition(position){
    $("#latlon").html("纬度:"+position.coords.latitude +'，经度:'+ position.coords.longitude);
    var latlon = position.coords.latitude+','+position.coords.longitude;

    //baidu
    var url = "http://api.map.baidu.com/geocoder/v2/?ak=C93b5178d7a8ebdb830b9b557abce78b&callback=renderReverse&location="+latlon+"&output=json&pois=0";
    $.ajax({
        type: "GET",
        dataType: "jsonp",
        url: url,
        beforeSend: function(){
            $("#city").html('正在定位...');
        },
        success: function (json) {
            if(json.status==0){
                $("#city").html(json.result.formatted_address);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $("#city").html(latlon+"地址位置获取失败");
        }
    });

    // //google
    // var url = 'http://maps.google.cn/maps/api/geocode/json?latlng='+latlon+'&language=CN';
    // $.ajax({
    //     type: "GET",
    //     url: url,
    //     beforeSend: function(){
    //         $("#city").html('正在定位...');
    //     },
    //     success: function (json) {
    //         if(json.status=='OK'){
    //             var results = json.results;
    //             $.each(results,function(index,array){
    //                 if(index==0){
    //                     $("#city").html(array['formatted_address']);
    //                 }
    //             });
    //         }
    //     },
    //     error: function (XMLHttpRequest, textStatus, errorThrown) {
    //         $("#city").html(latlon+"地址位置获取失败");
    //     }
    // });
}

function showError(error){
    switch(error.code) {
        case error.PERMISSION_DENIED:
            showMessage("定位失败,用户拒绝请求地理定位");
            break;
        case error.POSITION_UNAVAILABLE:
            showMessage("定位失败,位置信息是不可用");
            break;
        case error.TIMEOUT:
            showMessage("定位失败,请求获取用户位置超时");
            break;
        case error.UNKNOWN_ERROR:
            showMessage("定位失败,定位系统失效");
            break;
    }
}

/**
 * 复制微信号
 */
function copyWechatId(){
    var jsonStr=$("#userForm").serialize();
    var result =get(findParameterUrl,jsonStr);
    if(result.success){
        var oInput = document.createElement('input');
        oInput.value = result.data.wechatId;
        document.body.appendChild(oInput);
        oInput.select(); // 选择对象
        document.execCommand("Copy"); // 执行浏览器复制命令
        oInput.className = 'oInput';
        oInput.style.display='none';
        showMessage('复制成功');
    }else{
        showMessage(result.message);
    }

}
function showRegisterPro(proHtml) {
    var title="";
    if(proHtml=="privacyProtocol.html"){
        title="用户隐私条款";
    }else if(proHtml=="registerProtocol.html"){
        title="用户注册协议";
    }else{
        title="信息授权服务协议"
    }
    var index=layer.ready(function(){
        layer.open({
            type: 2,
            title: title,
            maxmin: false,
            area: ['90%', '80%'],
            content: proHtml,
            end: function(){
            }
        });
    });
    layer.full(index);
}
function validateData() {
    if(!isMobile($("#phoneNum"))){
        showMessage("手机号码格式不正确");
        $("#phoneNum").select();
        $("#phoneNum").focus();
        return false;
    }
    if($("#name").val()==""){
        showMessage("请填写姓名");
        $("#name").select();
        $("#name").focus();
        return false;
    }
    if($("#sesame").val()==""){
        showMessage("请填写芝麻分");
        $("#sesame").select();
        $("#sesame").focus();
        return false;
    }
    //验证芝麻分是否在合理范围之内
    if($("#sesame").val()!=""&&$("#sesame").val()!=undefined){
        if($("#sesame").val()>1000||$("#sesame").val()<500){
            showMessage("芝麻分填写范围是1000之内");
            $("#sesame").select();
            $("#sesame").focus();
            return false;
        }
    }
    return true;
}