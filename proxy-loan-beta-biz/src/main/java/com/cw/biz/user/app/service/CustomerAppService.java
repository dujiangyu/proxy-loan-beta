package com.cw.biz.user.app.service;

import com.cw.biz.common.dto.Pages;
import com.cw.biz.user.app.dto.CustomerDto;
import com.cw.biz.user.app.dto.YxUserInfoDto;
import com.cw.biz.user.domain.entity.YxUserInfo;
import com.cw.biz.user.domain.service.YxUserInfoDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by dujy on 2018-05-20.
 */
@Transactional
@Service
public class CustomerAppService {

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

    public YxUserInfoDto findByPhone(String phone){
       YxUserInfoDto cwUserInfoDto = new YxUserInfoDto();
       YxUserInfo cwUserInfo = yxUserInfoDomainService.findByPhone(phone);
       if(cwUserInfo != null){
           cwUserInfoDto = cwUserInfo.to(YxUserInfoDto.class);
       }
       return cwUserInfoDto;
   }
    /**
    * 按条件查询渠道
    * @param dto
    * @return
    */
   public Page<YxUserInfoDto> findByCondition(YxUserInfoDto dto){
       return Pages.map(yxUserInfoDomainService.findByCondition(dto),YxUserInfoDto.class);
   }

    /** 渠道用户登录后台查看发展客户
     *&lt;功能简述&gt;
     *&lt;功能详细描述&gt;
     * ${tags} [参数说明]
     *
     * @return ${return_type} [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
   public Page<CustomerDto> findChannelByCondition(YxUserInfoDto dto){
       return Pages.map(yxUserInfoDomainService.findChannelByCondition(dto),CustomerDto.class);
   }
}
