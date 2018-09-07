package com.cw.biz.sms.domain.entity;

import com.cw.biz.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Title: SmsMould
 * @Description: 短信模板
 * @Author: Away
 * @Date: 2018/9/1 15:15
 */
@Entity
@Table(name="yx_sms_mould")
@org.hibernate.annotations.Table(appliesTo = "yx_sms_mould",comment = "短信模板")
@Data
public class SmsMould extends BaseEntity{

    @Column(name="mould_code",unique = true,columnDefinition="varchar(30) not null comment '模板唯一编号'")
    private String mouldCode;

    @Column(name="mould_content",columnDefinition="varchar(200) not null comment '模板内容'")
    private String mouldContent;

}
