package com.cw.biz.notice.domain.repository;

import com.cw.biz.notice.domain.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 系统公告管理
 * Created by Administrator on 2017/7/13.
 */
public interface NoticeRepository extends JpaRepository<Notice,Long>,JpaSpecificationExecutor<Notice> {

}
