package com.cw.biz.channel.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.CwException;
import com.cw.biz.channel.app.dto.AgentDto;
import com.cw.biz.channel.app.dto.RechargeLogDto;
import com.cw.biz.channel.app.dto.ThirdOperateDto;
import com.cw.biz.channel.app.service.AgentAppService;
import com.cw.biz.channel.domain.entity.ThirdOperate;
import com.cw.biz.channel.domain.repository.ThirdOperateRepository;
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
import java.math.BigDecimal;
import java.util.List;

/**
 * 代理商下级
 * Created by dujy on 2017-05-20.
 */
@Service
public class ThirdOperateDomainService {

    @Autowired
    private SeUserService seUserService;

    @Autowired
    private AgentAppService appService;

    @Autowired
    private ThirdOperateRepository repository;

    @Autowired
    private RechargeLogDomainService rechargeLogDomainService;
    /**
     * 新增代理商下级
     * @param thirdOperateDto
     * @return
     */
    public ThirdOperate create(ThirdOperateDto thirdOperateDto){
        ThirdOperate thirdOperate = new ThirdOperate();
        thirdOperate.from(thirdOperateDto);
        thirdOperate.prepareSave();
        thirdOperate.setAccountNo(thirdOperateDto.getName());
        thirdOperate.setUserId(CPContext.getContext().getSeUserInfo().getId());
        return repository.save(thirdOperate);
    }

    /**
     * 修改渠道
     * @param thirdOperateDto
     * @return
     */
    public ThirdOperate update(ThirdOperateDto thirdOperateDto){
        ThirdOperate thirdOperate=null;
        if(thirdOperateDto.getId()==null){
            thirdOperate = create(thirdOperateDto);
            //保存登录用户信息
            SeUser seUser = new SeUser();
            seUser.setUsername(thirdOperateDto.getName());
            seUser.setrId(1L);
            seUser.setType("manager");
            seUser.setRoleIdsStr(",20");
            seUser.setPassword(thirdOperateDto.getPassword());
            seUser.setDisplayName(thirdOperateDto.getName());
            seUserService.createUser(seUser);
        }else {
            thirdOperate = repository.findOne(thirdOperateDto.getId());
            thirdOperate.from(thirdOperateDto);
            thirdOperate = repository.save(thirdOperate);
            //修改渠道密码
            SeUser seUser =  seUserService.findByUserNameAndMerchantId(thirdOperateDto.getName(),0L);
            if(thirdOperateDto.getPassword()!=null && !"".equals(thirdOperateDto.getPassword())) {
                seUser.setPassword(thirdOperateDto.getPassword());
            }
            seUserService.updateUser(seUser,Boolean.TRUE);

        }
        return thirdOperate;
    }

    public ThirdOperate recharge(ThirdOperateDto thirdOperateDto){
        ThirdOperate thirdOperate = repository.findOne(thirdOperateDto.getId());
        if(thirdOperate==null){
            CwException.throwIt("代理商不存在");
        }
        //验证代理商金额是否够充值
        AgentDto dto = appService.findByUserId(CPContext.getContext().getSeUserInfo().getId());
        if(dto.getBalance().compareTo(thirdOperateDto.getBalance())<0){
            CwException.throwIt("代理商金额不足，请联系客服充值");
        }
        BigDecimal currBalance = thirdOperate.getBalance();
        if(thirdOperate.getBalance()==null){
            thirdOperate.setBalance(BigDecimal.ZERO);
        }
        thirdOperate.setBalance(thirdOperate.getBalance().add(thirdOperateDto.getBalance()));
        //减掉代理商充值的金额
        AgentDto agentDto = new AgentDto();
        agentDto.setBalance(dto.getBalance().subtract(thirdOperateDto.getBalance()));
        agentDto.setId(dto.getId());
        appService.update(agentDto);
        //记录充值日志
        RechargeLogDto rechargeLogDto = new RechargeLogDto();
        rechargeLogDto.setRechargeObjectId(thirdOperateDto.getId());
        rechargeLogDto.setRechargeOperateId(CPContext.getContext().getSeUserInfo().getId());
        rechargeLogDto.setRechargeFee(thirdOperateDto.getBalance());
        rechargeLogDto.setRechargeBalance(thirdOperateDto.getBalance().add(currBalance==null?BigDecimal.ZERO:currBalance));
        rechargeLogDomainService.create(rechargeLogDto);

        return thirdOperate;
    }

    /**
     * 渠道停用
     * @param thirdOperateDto
     * @return
     */
    public ThirdOperate enable(ThirdOperateDto thirdOperateDto){
          ThirdOperate thirdOperate = repository.findOne(thirdOperateDto.getId());
          if(thirdOperate == null){
              CwException.throwIt("渠道不存在");
          }
          if(thirdOperate.getIsValid()) {
              thirdOperate.setIsValid(Boolean.FALSE);
          }else{
              thirdOperate.setIsValid(Boolean.TRUE);
          }
        return thirdOperate;
    }

    //扣取相关费用
    public ThirdOperate queryInterfaceFee(ThirdOperateDto thirdOperateDto){
        ThirdOperate thirdOperate = repository.findOne(thirdOperateDto.getId());
        if(thirdOperate == null){
            CwException.throwIt("渠道不存在");
        }
        if(thirdOperate.getBalance().compareTo(thirdOperateDto.getInterfaceFee())<0){
            CwException.throwIt("金额不足，请联系客服充值后再查询！");
        }
        thirdOperate.setBalance(thirdOperate.getBalance().subtract(thirdOperateDto.getInterfaceFee()));
       return thirdOperate;
   }

    /**
     * 查询渠道详情
     * @param id
     * @return
     */
    public ThirdOperate findById(Long id){
        return repository.findOne(id);
    }

    public ThirdOperate findByUserId(Long id){
        return repository.findByUserId(id);
    }
    /**
     * 按条件查询渠道列表
     * @param thirdOperateDto
     * @return
     */
    public Page<ThirdOperate> findByCondition(ThirdOperateDto thirdOperateDto){
        String[] fields = {"rawAddTime"};
        thirdOperateDto.setSortFields(fields);
        thirdOperateDto.setSortDirection(Sort.Direction.DESC);
        Specification<ThirdOperate> supplierSpecification = (Root<ThirdOperate> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);

            if(!"admin".equals(CPContext.getContext().getSeUserInfo().getUsername())) {
                predicates.add(cb.equal(root.get("agentId"), CPContext.getContext().getSeUserInfo().getId()));
            }
            predicates.add(cb.equal(root.get("isValid"),Boolean.TRUE));
            if(!StringUtils.isEmpty(thirdOperateDto.getName())) {
                predicates.add(cb.like(root.get("name"), "%"+thirdOperateDto.getName()+"%"));
            }
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, thirdOperateDto.toPage());
    }

    /**
     * 查询所有渠道
     * @return
     */
    public List<ThirdOperate> findAll(){
        return repository.findAll();
    }

}
