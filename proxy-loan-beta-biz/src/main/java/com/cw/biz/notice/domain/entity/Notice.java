package com.cw.biz.notice.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统公告
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="yx_sys_notice")
@Getter
@Setter
public class Notice extends AggEntity {

    @Column(name="title",columnDefinition="varchar(100) not null comment '公告标题'")
    private String title;

    @Column(name="content",columnDefinition="varchar(500) comment '公告内容'")
    private String content;

    @Column(name="eff_date",columnDefinition="datetime comment '生效时间'")
    private Date effDate;

    @Column(name="exp_date",columnDefinition="datetime comment '失效时间'")
    private Date expDate;

    @Column(name="view_staff",columnDefinition="varchar(50) comment '查看人员角色'")
    private String viewStaff;

    @Column(name="is_valid",columnDefinition="tinyint(1) comment '是否有效'")
    private Boolean isValid=Boolean.TRUE;

    public void prepareSave(){

    }
}
