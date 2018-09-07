package com.cw.web.wechat.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;

@Controller
public class JcaptchaController {
    Random random = new Random();

    /**
     * 生成二维码
     * @param request
     * @param response
     */
    @RequestMapping("/common/codeGenerate")
    public void generateImg(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                    GeetestConfig.isnewfailback());

            String resStr = "{}";

            String userid = "0478fdf20c0c08fccfa502559f1c1794";

            //自定义参数,可选择添加
            HashMap<String, String> param = new HashMap<String, String>();
            param.put("user_id", userid); //网站用户id
            param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
            param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

            //进行验证预处理
            int gtServerStatus = gtSdk.preProcess(param);

            //将服务器状态设置到session中
            request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
            //将userid设置到session中
            request.getSession().setAttribute("userid", userid);

            resStr = gtSdk.getResponseStr();

            PrintWriter out = response.getWriter();
            out.println(resStr);
        }catch (Exception e){

        }
    }

    @RequestMapping("/common/validate")
    public void validate(HttpServletRequest request,
                          HttpServletResponse response) {
        try {
            GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                    GeetestConfig.isnewfailback());

            String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
            String validate = request.getParameter(GeetestLib.fn_geetest_validate);
            String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);

            //从session中获取gt-server状态
            int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);

            //从session中获取userid
            String userid = (String) request.getSession().getAttribute("userid");

            //自定义参数,可选择添加
            HashMap<String, String> param = new HashMap<String, String>();
            param.put("user_id", userid); //网站用户id
            param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
            param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

            int gtResult = 0;

            if (gt_server_status_code == 1) {
                //gt-server正常，向gt-server进行二次验证

                gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
                System.out.println(gtResult);
            } else {
                // gt-server非正常情况下，进行failback模式验证

                System.out.println("failback:use your own server captcha validate");
                gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
                System.out.println(gtResult);
            }


            if (gtResult == 1) {
                // 验证成功
                PrintWriter out = response.getWriter();
                JSONObject data = new JSONObject();
                try {
                    data.put("status", "success");
                    data.put("version", gtSdk.getVersionInfo());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                out.println(data.toString());
            } else {
                // 验证失败
                JSONObject data = new JSONObject();
                try {
                    data.put("status", "fail");
                    data.put("version", gtSdk.getVersionInfo());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                PrintWriter out = response.getWriter();
                out.println(data.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
