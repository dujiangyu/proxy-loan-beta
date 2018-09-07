package com.cw.biz.channel.app.service;

import com.cw.biz.channel.app.dto.AgentDto;
import com.cw.biz.channel.app.dto.ThirdOperateDto;
import com.cw.biz.channel.domain.entity.ThirdOperate;
import com.cw.biz.channel.domain.service.ThirdOperateDomainService;
import com.cw.biz.common.dto.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 渠道服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class ThirdOperateAppService {

    @Autowired
    private ThirdOperateDomainService domainService;

    /**
     * 修改渠道
     * @param thirdOperateDto
     * @return
     */
    public Long update(ThirdOperateDto thirdOperateDto){
        return domainService.update(thirdOperateDto).getId();
    }

    /**服务商充值
     *&lt;功能简述&gt;
     *&lt;功能详细描述&gt;
     * ${tags} [参数说明]
     *
     * @return ${return_type} [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */

    public Long recharge(ThirdOperateDto thirdOperateDto){
           return domainService.recharge(thirdOperateDto).getId();
       }
    /**
     * 停用启用渠道
     * @param thirdOperateDto
     * @return
     */
    public void enable(ThirdOperateDto thirdOperateDto){
        domainService.enable(thirdOperateDto);
    }

     /** 扣取费用
      *&lt;功能简述&gt;
      *&lt;功能详细描述&gt;
      * ${tags} [参数说明]
      *
      * @return ${return_type} [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
      */
    public void queryInterfaceFee(ThirdOperateDto thirdOperateDto){
       domainService.queryInterfaceFee(thirdOperateDto);
    }

    /**
     * 查询渠道详情
     * @param id
     * @return
     */
    public ThirdOperateDto findById(Long id)
    {
        return domainService.findById(id).to(ThirdOperateDto.class);
    }

    public ThirdOperateDto findByUserId(Long id){
        ThirdOperate thirdOperate = domainService.findByUserId(id);
        if(thirdOperate==null){
            return null;
        }
        return thirdOperate.to(ThirdOperateDto.class);
    }

    /**
     * 按条件查询渠道
     * @param dto
     * @return
     */
    public Page<ThirdOperateDto> findByCondition(ThirdOperateDto dto)
    {
        return Pages.map(domainService.findByCondition(dto),ThirdOperateDto.class);
    }

    /**
     * 查询所有渠道
     * @return
     */
    public List<ThirdOperateDto> findAll()
    {
        List<ThirdOperateDto> thirdOperateDtoList = new ArrayList<ThirdOperateDto>();
        List<ThirdOperate> thirdOperateList = domainService.findAll();
        for(ThirdOperate thirdOperate : thirdOperateList){
            thirdOperateDtoList.add(thirdOperate.to(ThirdOperateDto.class));
        }
        return thirdOperateDtoList;
    }

}
