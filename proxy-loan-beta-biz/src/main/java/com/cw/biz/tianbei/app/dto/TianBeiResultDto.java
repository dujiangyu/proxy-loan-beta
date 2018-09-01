package com.cw.biz.tianbei.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Data;

/**
 * @Title: TianBeiResultDto
 * @Description: 天贝接口请求结果
 * @Author: Away
 * @Date: 2018/9/1 15:15
 */
@Data
public class TianBeiResultDto extends BaseDto{

    /**身份证号**/
    private String idCard;

    /**查询类型**/
    private String queryType;

    /**查询结果**/
    private String queryResult;

}
