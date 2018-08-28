package com.cw.biz.user.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户信息完善类
 * Created by dujy on 2017/7/31.
 */
@Getter
@Setter
public class YxUserInfoDto extends BaseDto{

    private Long id;

    private Long userId;

    private String name;

    private String certNo;

    private String phone;

    private String bankAccountNo;

    private String address;

    private Integer sesameScore;

}
