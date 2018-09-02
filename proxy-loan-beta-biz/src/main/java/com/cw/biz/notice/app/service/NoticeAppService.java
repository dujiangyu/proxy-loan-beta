package com.cw.biz.notice.app.service;

import com.cw.biz.common.dto.Pages;
import com.cw.biz.notice.app.dto.NoticeDto;
import com.cw.biz.notice.domain.service.NoticeDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 代理商服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class NoticeAppService {

    @Autowired
    private NoticeDomainService domainService;

    /**
     * 修改代理商
     * @param noticeDto
     * @return
     */
    public Long update(NoticeDto noticeDto){
        return domainService.update(noticeDto).getId();
    }

    /** 发布公告
     *&lt;功能简述&gt;
     *&lt;功能详细描述&gt;
     * ${tags} [参数说明]
     *
     * @return ${return_type} [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Long create(NoticeDto noticeDto){
        return domainService.create(noticeDto).getId();
    }

    /**
     * 停用启用公告
     * @param noticeDto
     * @return
     */
    public void enable(NoticeDto noticeDto){
        domainService.enable(noticeDto);
    }

    /**
     * 查询公告详情
     * @param id
     * @return
     */
    public NoticeDto findById(Long id){
        return domainService.findById(id).to(NoticeDto.class);
    }

    /**
     * 按条件查询公告
     * @param dto
     * @return
     */
    public Page<NoticeDto> findByCondition(NoticeDto dto){
        return Pages.map(domainService.findByCondition(dto),NoticeDto.class);
    }

}
