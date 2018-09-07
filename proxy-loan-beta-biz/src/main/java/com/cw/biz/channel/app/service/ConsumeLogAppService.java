package com.cw.biz.channel.app.service;

import com.cw.biz.channel.app.dto.ConsumeLogDto;
import com.cw.biz.channel.domain.service.ConsumeLogDomainService;
import com.cw.biz.common.dto.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 充值日志服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class ConsumeLogAppService {

    @Autowired
    private ConsumeLogDomainService domainService;

    /**
     * 新增充值日志
     * @param consumeLogDto
     * @return
     */
    public Long create(ConsumeLogDto consumeLogDto){
        return domainService.create(consumeLogDto).getId();
    }
    /**
     * 查询充值日志详情
     * @param id
     * @return
     */
    public ConsumeLogDto findById(Long id){
        return domainService.findById(id).to(ConsumeLogDto.class);
    }

    /**
     * 按条件查询充值日志
     * @param dto
     * @return
     */
    public Page<ConsumeLogDto> findByCondition(ConsumeLogDto dto){
        return Pages.map(domainService.findByCondition(dto),ConsumeLogDto.class);
    }
}
