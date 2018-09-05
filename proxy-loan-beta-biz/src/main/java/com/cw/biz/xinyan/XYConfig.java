package com.cw.biz.xinyan;

/**
 * @Title: XYConfig
 * @Description: 新颜配置
 * @Author: Away
 * @Date: 2018/9/5 16:42
 */
public class XYConfig {

    /**
     * 测试环境
     */
    public static final String BASE_URL = "https://test.xinyan.com";

    /**
     * 生产环境
     */
//    public static final String BASE_URL="https://api.xinyan.com";
    /**
     * 数据类型
     */
    public static final String DATA_TYPE = "json";

    /**
     * 编码类型
     */
    public static final String CHAR_SET = "utf-8";

    /**
     * 私玥文件名
     */
    public static final String PFX_NAME = "8000013189_pri.pfx";

    /**
     * 私玥密码
     */
    public static final String PFX_PWD = "217526";

    /**
     * 公玥文件名
     */
    public static final String CER_NAME = "bfkey_8000013189.cer";

    /**
     * 商户ID
     */
    public static final String MEMBER_ID = "8000013189";//测试号

    /**
     * 终端ID
     */
    public static final String TERMINAL_ID = "8000013189";//测试号

    /**
     * 公司所处行业
     */
    public static final String INDUSTRY_TYPE="A";

    /**
     * 淘宝芝麻分H5授权成功回调页面（不是回调接口）
     */
    public static final String TAOBAO_AUTH_SUCCESS_URL="";

    /**
     * 淘宝芝麻分H5授权页面
     */
    public static final String TAOBAO_URL="https://test.xinyan.com/spider-h5/#/taobao?";//测试环境

//    /**
//     * 淘宝芝麻分H5页面
//     */
//    public static final String TAOBAO_URL="https://api.xinyan.com/spider-h5/#/taobao?";//正式环境

    /**
     * 授权回调接口
     */
    public static final String NOTIFY_URL="http://karl-leo.imwork.net:57701/common/receiveXinYanNotify.json";

    /**
     * 实名认证接口
     */
    public static final String REAL_NAME_AUTH_URL = BASE_URL + "/idcard/v2/auth";

    /**
     * 逾期档案
     */
    public static final String OVERDUE_URL = BASE_URL + "/product/archive/v3/overdue";

    /**
     * 生成订单链接
     */
    public static final String BUILD_ORDER_URL = BASE_URL + "/gateway-data/data/v2/preOrderRsa";

    /**
     * 用户淘宝全部信息
     */
    public static final String USER_TAOBAO_ALL_INFO_URL = BASE_URL + "/data/taobao/v3/userdata/";
}
