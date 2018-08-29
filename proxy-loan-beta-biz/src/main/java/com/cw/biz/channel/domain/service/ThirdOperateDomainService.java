package com.cw.biz.channel.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.CwException;
import com.cw.biz.channel.app.dto.ThirdOperateDto;
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
    private ThirdOperateRepository repository;
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

    /**
     * 查询渠道详情
     * @param id
     * @return
     */
    public ThirdOperate findById(Long id)
    {
        return repository.findOne(id);
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
                predicates.add(cb.equal(root.get("userId"), CPContext.getContext().getSeUserInfo().getId()));
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
