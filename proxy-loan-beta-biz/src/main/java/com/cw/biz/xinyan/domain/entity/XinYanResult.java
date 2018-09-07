package com.cw.biz.xinyan.domain.entity;

import com.cw.biz.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Title: XinYanResult
 * @Description: 新颜接口请求结果
 * @Author: Away
 * @Date: 2018/9/1 15:15
 */
@Entity
@Table(name="yx_xinyan_result")
@org.hibernate.annotations.Table(appliesTo = "yx_xinyan_result",comment = "新颜接口请求结果")
@Data
public class XinYanResult extends BaseEntity{

    @Column(name="id_card",columnDefinition="varchar(18) not null comment '身份证号'")
    private String idCard;

    @Column(name="query_type",columnDefinition="varchar(36) not null comment '查询类型'")
    private String queryType;

    @Column(name="query_result",columnDefinition="longtext  comment '查询结果'")
    private String queryResult;

    @Column(name="query_user",columnDefinition="varchar(255)  comment '查询人'")
    private String queryUser;

}
