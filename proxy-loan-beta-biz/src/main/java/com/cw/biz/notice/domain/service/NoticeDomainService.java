package com.cw.biz.notice.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.notice.app.dto.NoticeDto;
import com.cw.biz.notice.domain.entity.Notice;
import com.cw.biz.notice.domain.repository.NoticeRepository;
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
import java.util.Date;
import java.util.List;

/**
 * 公告服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class NoticeDomainService {

    @Autowired
    private NoticeRepository repository;

    /**
     * 新增公告
     * @param noticeDto
     * @return
     */
    public Notice create(NoticeDto noticeDto){
        Notice notice = new Notice();
        notice.from(noticeDto);
        notice.prepareSave();
        return repository.save(notice);
    }

    /**
     * 修改公告
     * @param noticeDto
     * @return
     */
    public Notice update(NoticeDto noticeDto){
        Notice notice=null;
        if(noticeDto.getId()==null){
            notice = create(noticeDto);
        }else {

             notice = repository.findOne(noticeDto.getId());
            if (notice == null) {
                notice = create(noticeDto);
            } else {
                notice.from(noticeDto);
                notice = repository.save(notice);
            }
        }
        return notice;
    }

    /**
     * 公告删除
     * @param noticeDto
     * @return
     */
    public Notice enable(NoticeDto noticeDto){
        Notice notice = repository.findOne(noticeDto.getId());
        if(notice == null){
            CwException.throwIt("公告不存在");
        }
        if(notice.getIsValid()) {
            notice.setIsValid(Boolean.FALSE);
        }else{
            notice.setIsValid(Boolean.TRUE);
        }
        return notice;
    }

    /**
     * 查询渠道详情
     * @param id
     * @return
     */
    public Notice findById(Long id){
        return repository.findOne(id);
    }

    /**
     * 按条件查询渠道列表
     * @param noticeDto
     * @return
     */
    public Page<Notice> findByCondition(NoticeDto noticeDto){
        String[] fields = {"rawAddTime"};
        noticeDto.setSortFields(fields);
        noticeDto.setSortDirection(Sort.Direction.DESC);
        Specification<Notice> supplierSpecification = (Root<Notice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            if(!StringUtils.isEmpty(noticeDto.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%"+noticeDto.getTitle()+"%"));
            }
            //展示公告
            if("notice".equals(noticeDto.getType())) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("effDate"),new Date()));
                predicates.add(cb.lessThanOrEqualTo(root.get("expDate"),new Date()));
            }

            predicates.add(cb.equal(root.get("isValid"),Boolean.TRUE));
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, noticeDto.toPage());
    }

}
