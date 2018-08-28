package com.cw.biz.user.domain.service;

import com.cw.biz.user.app.dto.YxUserInfoDto;
import com.cw.biz.user.domain.entity.YxUserInfo;
import com.cw.biz.user.domain.repository.SeUserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息服务
 * Created by dujy on 2018-05-20.
 */
@Service
public class YxUserInfoDomainService {

    @Autowired
    private SeUserInfoRepository repository;
    /**
     * 新增客户信息
     * @param cwUserInfoDto
     * @return
     */
    private YxUserInfo create(YxUserInfoDto cwUserInfoDto){
        YxUserInfo cwUserInfo = new YxUserInfo();
        cwUserInfo.from(cwUserInfoDto);
        return repository.save(cwUserInfo);
    }

    /**
     * 修改客户信息
     * @param cwUserInfoDto
     * @return
     */
    public YxUserInfo update(YxUserInfoDto cwUserInfoDto){
        //借款起始金额
        YxUserInfo cwUserInfo = repository.findByUserId(cwUserInfoDto.getUserId());
        if(cwUserInfo == null)
        {
            cwUserInfo = create(cwUserInfoDto);
        }else{
            cwUserInfo.from(cwUserInfoDto);
        }
        return cwUserInfo;
    }

    /**
     * 查询客户信息
     * @param id
     * @return
     */
    public YxUserInfo findById(Long id){
        return repository.findByUserId(id);
    }

}
