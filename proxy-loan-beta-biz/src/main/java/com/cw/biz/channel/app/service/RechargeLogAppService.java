package com.cw.biz.channel.app.service;

import com.cw.biz.channel.app.dto.RechargeLogDto;
import com.cw.biz.channel.domain.service.RechargeLogDomainService;
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
public class RechargeLogAppService {

    @Autowired
    private RechargeLogDomainService domainService;

    /**
     * 新增充值日志
     * @param rechargeLogDto
     * @return
     */
    public Long create(RechargeLogDto rechargeLogDto){
        return domainService.create(rechargeLogDto).getId();
    }
    /**
     * 查询充值日志详情
     * @param id
     * @return
     */
    public RechargeLogDto findById(Long id){
        return domainService.findById(id).to(RechargeLogDto.class);
    }

    /**
     * 按条件查询充值日志
     * @param dto
     * @return
     */
    public Page<RechargeLogDto> findByCondition(RechargeLogDto dto){
        return Pages.map(domainService.findByCondition(dto),RechargeLogDto.class);
    }
}
