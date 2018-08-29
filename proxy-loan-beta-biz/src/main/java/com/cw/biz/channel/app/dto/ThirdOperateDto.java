package com.cw.biz.channel.app.dto;

import com.cw.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 第三级操作员
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class ThirdOperateDto extends PageDto {

    private Long id;

    private String name;

    private String linkPerson;

    private String linkPhone;

    private String tgUrl;

    private String backLoginUrl;

    private String accountNo;

    private String password;

    private BigDecimal balance;

    private Long userId;

    private Boolean isValid=Boolean.TRUE;

}
