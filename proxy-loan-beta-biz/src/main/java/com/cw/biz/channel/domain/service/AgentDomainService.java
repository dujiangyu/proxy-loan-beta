package com.cw.biz.channel.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.channel.app.dto.AgentDto;
import com.cw.biz.channel.domain.entity.Agent;
import com.cw.biz.channel.domain.repository.AgentRepository;
import com.cw.biz.user.domain.entity.SeUser;
import com.cw.biz.user.domain.service.SeUserService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 代理商服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class AgentDomainService {

    @Autowired
    private SeUserService seUserService;

    @Autowired
    private AgentRepository repository;
    /**
     * 新增代理商
     * @param agentDto
     * @return
     */
    public Agent create(AgentDto agentDto){
        Agent agent = new Agent();
        agent.from(agentDto);
        agent.prepareSave();
        return repository.save(agent);
    }

    /**
     * 修改渠道
     * @param agentDto
     * @return
     */
    public Agent update(AgentDto agentDto){
        Agent agent=null;
        if(agentDto.getId()==null){
            agent = create(agentDto);
            //保存登录用户信息
            SeUser seUser = new SeUser();
            seUser.setUsername(agentDto.getName());
            seUser.setPassword(agentDto.getPassword());
            seUser.setrId(1L);
            seUser.setType("manager");
            seUser.setDisplayName(agentDto.getName());
            seUserService.createUser(seUser);
        }else {
            agent = repository.findOne(agentDto.getId());
            //修改渠道密码
            if(!StringUtils.isEmpty(agentDto.getName())) {
                SeUser seUser = seUserService.findByUserNameAndMerchantId(agent.getName(), 0L);
                seUser.setUsername(agentDto.getName());
                if (agentDto.getPassword() != null && !"".equals(agentDto.getPassword())) {
                    seUser.setPassword(agentDto.getPassword());
                }
                seUserService.updateUser(seUser, Boolean.TRUE);
            }
            agentDto.setBalance(agent.getBalance().add(agentDto.getBalance()));
            agent.from(agentDto);
            agent = repository.save(agent);
            //TODO 记录充值明细

        }
        return agent;
    }


    /**
     * 渠道停用
     * @param agentDto
     * @return
     */
    public Agent enable(AgentDto agentDto){
        Agent channel = repository.findOne(agentDto.getId());
        if(channel == null){
            CwException.throwIt("渠道不存在");
        }
        if(channel.getIsValid()) {
            channel.setIsValid(Boolean.FALSE);
        }else{
            channel.setIsValid(Boolean.TRUE);
        }
        return channel;
    }

    /**
     * 查询渠道详情
     * @param id
     * @return
     */
    public Agent findById(Long id)
    {
        return repository.findOne(id);
    }

    /**
     * 按条件查询渠道列表
     * @param agentDto
     * @return
     */
    public Page<Agent> findByCondition(AgentDto agentDto){
        String[] fields = {"rawAddTime"};
        agentDto.setSortFields(fields);
        agentDto.setSortDirection(Sort.Direction.DESC);
        Specification<Agent> supplierSpecification = (Root<Agent> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            if(!StringUtils.isEmpty(agentDto.getName())) {
                predicates.add(cb.like(root.get("name"), "%"+agentDto.getName()+"%"));
            }
            predicates.add(cb.equal(root.get("isValid"),Boolean.TRUE));
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, agentDto.toPage());
    }

    /**
     * 查询所有渠道
     * @return
     */
    public List<Agent> findAll(){
        return repository.findAll();
    }

}
