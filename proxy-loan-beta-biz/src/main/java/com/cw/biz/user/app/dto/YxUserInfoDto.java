package com.cw.biz.user.app.dto;

import com.cw.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 客服信息
 * Created by dujy on 2017/7/31.
 */
@Getter
@Setter
public class YxUserInfoDto extends PageDto{

    private Long id;

    private Long userId;

    private String name;

    private String certNo;

    private String phone;

    private Integer status=0;

    private String bankAccountNo;

    private String address;

    private Integer sesameScore;

    private String sourceChannel;

    private Integer channelUserId;

    private Date registerDate;

    private String startDate;

    private String endDate;

    private String servicePwd;

    private String customerType;

    private String openId;

    private String captcha;

    private String showPageContent;

    private String tradeNo;

    private BigDecimal loanAmount;

    private String loanPurpose;
}
