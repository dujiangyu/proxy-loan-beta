package com.cw.biz.channel.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.channel.app.dto.RechargeLogDto;
import com.cw.biz.channel.domain.entity.RechargeLog;
import com.cw.biz.channel.domain.repository.RechargeLogRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * 充值记录日志
 * Created by dujy on 2017-05-20.
 */
@Service
public class RechargeLogDomainService {

    @Autowired
    private RechargeLogRepository repository;

    /**
     * 新增充值日志
     * @param rechargeLogDto
     * @return
     */
    public RechargeLog create(RechargeLogDto rechargeLogDto){
        RechargeLog rechargeLog = new RechargeLog();
        rechargeLog.from(rechargeLogDto);
        rechargeLog.setRechargeDate(new Date());
        rechargeLog.prepareSave();
        return repository.save(rechargeLog);
    }

    /**
     * 查询渠道详情
     * @param id
     * @return
     */
    public RechargeLog findById(Long id){
        return repository.findOne(id);
    }

    /**
     * 按条件查询渠道列表
     * @param rechargeLogDto
     * @return
     */
    public Page<RechargeLog> findByCondition(RechargeLogDto rechargeLogDto){
        String[] fields = {"rawAddTime"};
        rechargeLogDto.setSortFields(fields);
        rechargeLogDto.setSortDirection(Sort.Direction.DESC);
        Specification<RechargeLog> supplierSpecification = (Root<RechargeLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);

            predicates.add(cb.equal(root.get("rechargeOperateId"), CPContext.getContext().getSeUserInfo().getId()));

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, rechargeLogDto.toPage());
    }

}
