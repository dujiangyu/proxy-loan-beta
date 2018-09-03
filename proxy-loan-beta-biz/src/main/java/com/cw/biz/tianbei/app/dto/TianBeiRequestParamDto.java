package com.cw.biz.tianbei.app.dto;

import lombok.Data;

/**
 * @Title: TianBeiRequestParamDto
 * @Description: 天贝请求参数DTO
 * @Author: Away
 * @Date: 2018/9/1 15:15
 */
@Data
public class TianBeiRequestParamDto{

    /**身份证号**/
    private String idCard;

    /**姓名**/
    private String name;

    /**电话号**/
    private String phone;

    /**电话服务密码**/
    private String servicePwd;

    /**查询类型**/
    private String queryType;

    /**查询结果**/
    private String queryResult;

}
