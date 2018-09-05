package com.cw.biz.user.app.dto;

import com.cw.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 客户审核接收参数对象
 * Created by dujy on 2017/7/31.
 */
@Getter
@Setter
public class CustomerAuditDto extends PageDto{

    private String name;

    private String phone;

    private String idCard;

    private String servicePwd;
}