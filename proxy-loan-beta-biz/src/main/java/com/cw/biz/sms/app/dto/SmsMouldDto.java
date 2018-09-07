package com.cw.biz.sms.app.dto;

import com.cw.biz.common.dto.BaseDto;
import com.cw.biz.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Title: SmsMouldDto
 * @Description: 短信模板DTO
 * @Author: Away
 * @Date: 2018/9/1 15:15
 */
@Data
public class SmsMouldDto extends BaseDto{

    /**模板唯一编号**/
    private String mouldCode;

    /**模板内容**/
    private String mouldContent;

}
