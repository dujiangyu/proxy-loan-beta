package com.cw.biz.channel.app.service;

import com.cw.biz.channel.app.dto.AgentDto;
import com.cw.biz.channel.app.dto.ThirdOperateDto;
import com.cw.biz.channel.domain.entity.Agent;
import com.cw.biz.channel.domain.service.AgentDomainService;
import com.cw.biz.common.dto.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理商服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class AgentAppService {

    @Autowired
    private AgentDomainService domainService;

    /**
     * 修改代理商
     * @param agentDto
     * @return
     */
    public Long update(AgentDto agentDto){
        return domainService.update(agentDto).getId();
    }
    /** 充值
     *&lt;功能简述&gt;
     *&lt;功能详细描述&gt;
     * ${tags} [参数说明]
     *
     * @return ${return_type} [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Long recharge(AgentDto agentDto){
        return domainService.recharge(agentDto).getId();
    }

    /**
     * 停用启用代理商
     * @param channelDto
     * @return
     */
    public void enable(AgentDto channelDto){
        domainService.enable(channelDto);
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
   public void queryInterfaceFee(AgentDto agentDto){
      domainService.queryInterfaceFee(agentDto);
   }
    /**
     * 查询代理商详情
     * @param id
     * @return
     */
    public AgentDto findById(Long id){
        return domainService.findById(id).to(AgentDto.class);
    }

    public AgentDto findByUserId(Long id){
        return domainService.findByUserId(id).to(AgentDto.class);
    }

    /**
     * 按条件查询代理商
     * @param dto
     * @return
     */
    public Page<AgentDto> findByCondition(AgentDto dto){
        return Pages.map(domainService.findByCondition(dto),AgentDto.class);
    }

    /**
     * 查询所有代理商
     * @return
     */
    public List<AgentDto> findAll(){
        List<AgentDto> agentDtoList = new ArrayList<AgentDto>();
        List<Agent> channelList = domainService.findAll();
        for(Agent agent : channelList){
            agentDtoList.add(agent.to(AgentDto.class));
        }
        return agentDtoList;
    }

}
