package com.cw.web.common.controller;

import com.cw.biz.sms.app.service.SmsComponent;
import com.cw.web.common.component.LoginComponent;
import com.cw.web.common.component.UploadFileComponent;
import com.cw.web.common.dto.CPViewResultInfo;
import com.cw.web.common.model.LoginModel;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *通用请求
 */
@RestController
@RequestMapping("/common")
public class CommonController extends AbstractController {

    private final LoginComponent loginComponent;

    private final UploadFileComponent uploadFileComponent;

    private final SmsComponent smsComponent;

    @Autowired
    public CommonController(LoginComponent loginComponent, UploadFileComponent uploadFileComponent,SmsComponent smsComponent) {
        this.loginComponent = loginComponent;
        this.uploadFileComponent = uploadFileComponent;
        this.smsComponent=smsComponent;
    }

    /**
     * 用户登录
     * @param httpServletRequest
     * @param loginModel
     * @return
     */
    @PostMapping("passwordLogin.json")
    public CPViewResultInfo passwordLogin(HttpServletRequest httpServletRequest, @RequestBody LoginModel loginModel) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        String openid = (String) httpServletRequest.getSession().getAttribute("openid");
        loginComponent.wechatBinding(httpServletRequest, loginModel, openid, "passwordLogin");

        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("登录成功");
        return cpViewResultInfo;
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("upload.json")
    public CPViewResultInfo upload(MultipartFile file) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        String filePath = uploadFileComponent.upload(file);
        cpViewResultInfo.setData(filePath);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("上传成功");
        return cpViewResultInfo;
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("logout.json")
    public CPViewResultInfo logout() {
        CPViewResultInfo resultInfo = new CPViewResultInfo();
        SecurityUtils.getSubject().logout();
        resultInfo.setSuccess(true);
        return resultInfo;
    }
    /**
     * 判断移动号段
     * @param phone
     * @return
     */
    private Boolean isMobilePhone(String phone)
    {
        String PHONE_PATTERN="^((13[0-9])|(19[0-9])|(16[6|7])|(14[1|5|6|7|8])|(15([0-3]|[5-9]))|(17([0,1,3,6,7,8,]))|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(PHONE_PATTERN);
        Matcher m = p.matcher(phone);
        return m.find();
    }

    @PostMapping("/sendValidateCode.json")
    public CPViewResultInfo sendValidateCode(CPViewResultInfo info,@RequestBody LoginModel loginModel){
        info.newSuccess(this.smsComponent.sendValidateCode("basic",loginModel.getPhoneNum()));
        return info;
    }

}
