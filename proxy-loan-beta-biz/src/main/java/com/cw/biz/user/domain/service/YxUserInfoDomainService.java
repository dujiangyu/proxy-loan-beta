package com.cw.biz.user.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.channel.app.dto.ChannelDto;
import com.cw.biz.channel.app.service.ChannelAppService;
import com.cw.biz.user.app.dto.YxUserInfoDto;
import com.cw.biz.user.domain.entity.YxUserInfo;
import com.cw.biz.user.domain.repository.SeUserInfoRepository;
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
 * 用户信息服务
 * Created by dujy on 2018-05-20.
 */
@Service
public class YxUserInfoDomainService {

    @Autowired
    private SeUserInfoRepository repository;

    @Autowired
    private ChannelAppService appService;
    /**
     * 新增客户信息
     * @param cwUserInfoDto
     * @return
     */
    private YxUserInfo create(YxUserInfoDto cwUserInfoDto){
        YxUserInfo cwUserInfo = new YxUserInfo();
        if(cwUserInfoDto.getSourceChannel()!=null) {
            ChannelDto dto = appService.findByCode(cwUserInfoDto.getSourceChannel());
            cwUserInfoDto.setChannelUserId(dto.getChannelUserId().intValue());
            cwUserInfoDto.setUserId(dto.getUserId());
        }
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
        YxUserInfo cwUserInfo = repository.findByPhone(CPContext.getContext().getSeUserInfo().getPhone());
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
        return repository.findOne(id);
    }

    public YxUserInfo findByPhone(String phone){
          return repository.findByPhone(phone);
      }


    /**
    * 按条件查询渠道列表
    * @param yxUserInfoDto
    * @return
    */
   public Page<YxUserInfo> findByCondition(YxUserInfoDto yxUserInfoDto){
       String[] fields = {"rawAddTime"};
       yxUserInfoDto.setSortFields(fields);
       yxUserInfoDto.setSortDirection(Sort.Direction.DESC);
       Specification<YxUserInfo> supplierSpecification = (Root<YxUserInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
           List<Predicate> predicates = Lists.newArrayListWithCapacity(20);

           if(!"admin".equals(CPContext.getContext().getSeUserInfo().getUsername())) {
               predicates.add(cb.equal(root.get("userId"), CPContext.getContext().getSeUserInfo().getId()));
           }
           if("audit".equals(yxUserInfoDto.getCustomerType())){
               predicates.add(root.get("name").isNotNull());
               predicates.add(root.get("certNo").isNotNull());
           }
           if("query".equals(yxUserInfoDto.getCustomerType())){
              predicates.add(root.get("name").isNull());
              predicates.add(root.get("certNo").isNull());
           }
           if(yxUserInfoDto.getStatus()!=9){
               predicates.add(cb.equal(root.get("status"), yxUserInfoDto.getStatus()));
           }
           if(!StringUtils.isEmpty(yxUserInfoDto.getName())) {
               predicates.add(cb.like(root.get("name"), "%"+yxUserInfoDto.getName()+"%"));
           }
           if(!StringUtils.isEmpty(yxUserInfoDto.getPhone())) {
                  predicates.add(cb.like(root.get("phone"), "%"+yxUserInfoDto.getPhone()+"%"));
              }
           query.where(cb.and(predicates.toArray(new Predicate[0])));
           return query.getRestriction();
       };
       return repository.findAll(supplierSpecification, yxUserInfoDto.toPage());
   }


    /**
    * 按条件查询渠道列表
    * @param yxUserInfoDto
    * @return
    */
   public Page<YxUserInfo> findChannelByCondition(YxUserInfoDto yxUserInfoDto){
       String[] fields = {"rawAddTime"};
       yxUserInfoDto.setSortFields(fields);
       yxUserInfoDto.setSortDirection(Sort.Direction.DESC);
       Specification<YxUserInfo> supplierSpecification = (Root<YxUserInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
           List<Predicate> predicates = Lists.newArrayListWithCapacity(20);

           predicates.add(cb.equal(root.get("sourceChannel"), yxUserInfoDto.getSourceChannel()));

           if(!StringUtils.isEmpty(yxUserInfoDto.getStartDate())){
               predicates.add(cb.greaterThanOrEqualTo(root.get("registerDate").as(String.class), yxUserInfoDto.getStartDate()));
           }
           if(!StringUtils.isEmpty(yxUserInfoDto.getEndDate())){
              predicates.add(cb.lessThanOrEqualTo(root.get("registerDate").as(String.class), yxUserInfoDto.getEndDate()));
           }

           query.where(cb.and(predicates.toArray(new Predicate[0])));
           return query.getRestriction();
       };
       return repository.findAll(supplierSpecification, yxUserInfoDto.toPage());
   }

}
