package com.cw.biz.notice.app.dto;

import com.cw.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 系统公告
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class NoticeDto extends PageDto {

    private Long id;

    private String title;

    private String content;

    private String effDate;

    private String expDate;

    private String viewStaff;

    private Boolean isValid=Boolean.TRUE;

    private String type;

    public void prepareSave(){

    }
}
