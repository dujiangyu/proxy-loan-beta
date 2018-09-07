package com.cw.biz.xinyan.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @Title: XinYanResultDto
 * @Description:
 * @Author: Away
 * @Date: 2018/9/5 21:31
 */
@Getter
@Setter
public class XinYanResultDto extends BaseDto{

    /**身份证号**/
    private String idCard;

    /**查询类型**/
    private String queryType;

    /**查询结果**/
    private String queryResult;

    /**查询人**/
    private String queryUser;
}
