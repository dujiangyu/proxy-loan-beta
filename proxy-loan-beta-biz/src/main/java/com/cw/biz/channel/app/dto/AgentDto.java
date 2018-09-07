package com.cw.biz.channel.app.dto;

import com.cw.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 代理商
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class AgentDto extends PageDto {

    private Long id;

    private String code;

    private String name;

    private String password;

    private String linkPerson;

    private String linkPhone;

    private BigDecimal balance;

    private BigDecimal interfaceFee;

    private Boolean isValid=Boolean.TRUE;

    private Long userId;

    public void prepareSave(){
    }
}
