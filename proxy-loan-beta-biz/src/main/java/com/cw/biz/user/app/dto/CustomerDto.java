package com.cw.biz.user.app.dto;

import com.cw.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 客服信息
 * Created by dujy on 2017/7/31.
 */
@Getter
@Setter
public class CustomerDto extends PageDto{

    private Long id;

    private String name;

    private String phone;

    private Date registerDate;

    public String getPhone() {
        return phone.substring(0,3)+"****"+phone.substring(7,11);
    }
}
