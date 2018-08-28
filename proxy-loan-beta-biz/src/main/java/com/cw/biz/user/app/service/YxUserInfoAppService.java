package com.cw.biz.user.app.service;

import com.cw.biz.user.app.dto.YxUserInfoDto;
import com.cw.biz.user.domain.entity.YxUserInfo;
import com.cw.biz.user.domain.service.YxUserInfoDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by dujy on 2018-05-20.
 */
@Transactional
@Service
public class YxUserInfoAppService {

    @Autowired
    private YxUserInfoDomainService yxUserInfoDomainService;

    /**
     * 修改客户信息
     * @param cwUserInfoDto
     * @return
     */
    public Long update(YxUserInfoDto cwUserInfoDto){
        return yxUserInfoDomainService.update(cwUserInfoDto).getId();
    }

    /**
     * 查询用户详情
     * @param id
     * @return
     */
    public YxUserInfoDto findById(Long id){
        YxUserInfoDto cwUserInfoDto = new YxUserInfoDto();
        YxUserInfo cwUserInfo = yxUserInfoDomainService.findById(id);
        if(cwUserInfo != null){
            cwUserInfoDto = cwUserInfo.to(YxUserInfoDto.class);
        }
        return cwUserInfoDto;
    }

}
