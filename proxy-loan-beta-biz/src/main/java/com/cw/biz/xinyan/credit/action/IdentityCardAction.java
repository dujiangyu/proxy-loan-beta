package com.cw.biz.xinyan.credit.action;

import com.cw.biz.xinyan.credit.Config;
import com.cw.biz.xinyan.credit.rsa.RsaCodingUtil;
import com.cw.biz.xinyan.credit.util.HttpUtils;
import com.cw.biz.xinyan.credit.util.JXMConvertUtil;
import com.cw.biz.xinyan.credit.util.MapToXMLString;
import com.cw.biz.xinyan.credit.util.SecurityUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 淘气
 * @createtime 2016年9月26日下午8:58:24
 */
public class IdentityCardAction extends HttpServlet {
	
	private static final long serialVersionUID = 4865269178052033560L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id_card = req.getParameter("id_card").trim();
		String id_holder = req.getParameter("id_holder").trim();
		String is_photo = req.getParameter("is_photo");
		
		log("=====需要验证的身份证(id_card):"+id_card+",姓名(id_holder)："+id_holder);
		/** 1、 商户号 **/
		String member_id= Config.getConstants().get("member.id");
		/**2、终端号 **/
		String terminal_id=Config.getConstants().get("terminal.id");
		/** 3、 加密数据类型 **/
		String data_type=Config.getConstants().get("data.type");
		/** 4、加密数据 **/
	    
		String trans_id=""+System.currentTimeMillis();// 商户订单号
		String trade_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 订单日期
		
		
		/** 组装参数  **/
		Map<Object, Object> ArrayData = new HashMap<Object, Object>();
		ArrayData.put("member_id", member_id);
		ArrayData.put("terminal_id", terminal_id);
		ArrayData.put("trans_id", trans_id);
		ArrayData.put("trade_date", trade_date);
		ArrayData.put("id_card", id_card);
		ArrayData.put("id_holder", id_holder);
		ArrayData.put("industry_type", "A1");//根据自己的业务类型传入参数
		ArrayData.put("is_photo", is_photo);
		
		Map<Object, Object> ArrayData1 = new HashMap<Object, Object>();
		String XmlOrJson = "";
		if (data_type.equals("xml")) {
			ArrayData1.putAll(ArrayData);
			XmlOrJson = MapToXMLString.converter(ArrayData1, "data_content");
		} else {
			JSONObject jsonObjectFromMap = JSONObject.fromObject(ArrayData);
			XmlOrJson = jsonObjectFromMap.toString();
		}
		log("====请求明文:" + XmlOrJson);
		
		/** base64 编码 **/
		String base64str = SecurityUtil.Base64Encode(XmlOrJson);
		log("====base64 编码:"+base64str);
		
		/** rsa加密  **/
		String pfxpath = Config.getWebRoot() + "key\\" + Config.getConstants().get("pfx.name");// 商户私钥
		File pfxfile = new File(pfxpath);
		if (!pfxfile.exists()) {
			log("私钥文件不存在！");
			throw new RuntimeException("私钥文件不存在！");
		}
		String pfxpwd =Config.getConstants().get("pfx.pwd");// 私钥密码
		
		String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);//加密数据
		log("====加密串:"+data_content);
		
		/**============== http 请求==================== **/
		 Map<String, String> headers =new HashMap<>();
		 String url = Config.getConstants().get("idCardAuthUrl");
		 Map<String, Object> params =new HashMap<String,Object>();
		 params.put("member_id", member_id);
		 params.put("terminal_id", terminal_id);
		 params.put("data_type", "json");
		 params.put("data_content", data_content);
        long startTime = System.currentTimeMillis();
        log("开始时间："+startTime);
	    String PostString = HttpUtils.doPostByForm(url, headers,params);
		long endTime = System.currentTimeMillis();
		log("结束时间："+endTime);
		log("消耗时间："+(endTime-startTime));
		log("请求返回："+ PostString);
		/** ================处理返回============= **/
		if(PostString.isEmpty()){//判断参数是否为空
			log("=====返回数据为空");
			throw new RuntimeException("返回数据为空");
		}
		
		if(data_type.equals("xml")){
			PostString = JXMConvertUtil.XmlConvertJson(PostString);
			log("=====返回结果转JSON:"+PostString);
		}
		Map<String,Object> ArrayDataString = JXMConvertUtil.JsonConvertHashMap(PostString);//将JSON转化为Map对象。
		req.setAttribute("result", PostString);
		req.setAttribute("picture", ArrayDataString.get("photo"));
		if("noPhoto".equals(is_photo)){
			req.getRequestDispatcher("/show.jsp").forward(req,resp);
		}else {
			req.getRequestDispatcher("/pictureShow.jsp").forward(req,resp);
			
		}
		
		
		
		
	
	}
	public void log(String msg) {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\t: " + msg);
	}
}
