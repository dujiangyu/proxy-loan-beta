package com.cw.biz.channel.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.channel.app.dto.ConsumeLogDto;
import com.cw.biz.channel.domain.entity.ConsumeLog;
import com.cw.biz.channel.domain.repository.ConsumeLogRepository;
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
import java.util.List;

/**
 * 接口金额消耗记录日志
 * Created by dujy on 2017-05-20.
 */
@Service
public class ConsumeLogDomainService {

    @Autowired
    private ConsumeLogRepository repository;

    /**
     * 新增充值日志
     * @param consumeLogDto
     * @return
     */
    public ConsumeLog create(ConsumeLogDto consumeLogDto){
        ConsumeLog consumeLog = new ConsumeLog();
        consumeLog.from(consumeLogDto);
        consumeLog.prepareSave();
        return repository.save(consumeLog);
    }

    /**
     * 查询渠道详情
     * @param id
     * @return
     */
    public ConsumeLog findById(Long id){
        return repository.findOne(id);
    }

    /**
     * 按条件查询渠道列表
     * @param consumeLogDto
     * @return
     */
    public Page<ConsumeLog> findByCondition(ConsumeLogDto consumeLogDto){
        String[] fields = {"rawAddTime"};
        consumeLogDto.setSortFields(fields);
        consumeLogDto.setSortDirection(Sort.Direction.DESC);
        Specification<ConsumeLog> supplierSpecification = (Root<ConsumeLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);

            predicates.add(cb.equal(root.get("operateUserId"), CPContext.getContext().getSeUserInfo().getId()));

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, consumeLogDto.toPage());
    }

}
