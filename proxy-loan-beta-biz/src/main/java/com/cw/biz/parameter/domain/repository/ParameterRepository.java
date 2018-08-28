package com.cw.biz.parameter.domain.repository;

import com.cw.biz.parameter.domain.entity.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 参数表
 * Created by dujy on 2017-05-20.
 */
public interface ParameterRepository extends JpaRepository<Parameter,Long>{
    Parameter findByParameterCode(String parameterCode);
}
