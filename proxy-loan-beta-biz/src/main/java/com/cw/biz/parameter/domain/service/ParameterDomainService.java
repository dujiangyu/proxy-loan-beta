package com.cw.biz.parameter.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.parameter.app.ParameterEnum;
import com.cw.biz.parameter.app.dto.IndexParameterDto;
import com.cw.biz.parameter.app.dto.ParameterDto;
import com.cw.biz.parameter.domain.entity.Parameter;
import com.cw.biz.parameter.domain.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参数管理
 * Created by dujy on 2017-05-20.
 */
@Service
public class ParameterDomainService {

    @Autowired
    private ParameterRepository repository;
    /**
     * 新增参数
     * @param parameterDto
     * @return
     */
    public Parameter create(ParameterDto parameterDto){
        Parameter parameter = new Parameter();
        parameter.from(parameterDto);
        return repository.save(parameter);
    }

    /**
     * 修改参数
     * @param indexParameterDto
     * @return
     */
    public Parameter update(IndexParameterDto indexParameterDto){
        //借款起始金额
        Parameter parameter = repository.findByParameterCode(ParameterEnum.profitPlat.getKey());
        if(parameter == null)
        {
            ParameterDto parameterDto = new ParameterDto();
            parameterDto.setParameterCode(ParameterEnum.profitPlat.getKey());
            parameterDto.setParameterName(ParameterEnum.profitPlat.getValue());
            parameterDto.setParameterName(ParameterEnum.profitPlat.getValue());
            parameterDto.setParameterValue(indexParameterDto.getProfitPlat());
            create(parameterDto);
        }else{
            parameter.setParameterValue(indexParameterDto.getProfitPlat());
        }
        //借款截止金额
        Parameter parameter1 = repository.findByParameterCode(ParameterEnum.profitChannel.getKey());
        if(parameter1 == null)
        {
            ParameterDto parameterDto = new ParameterDto();
            parameterDto.setParameterCode(ParameterEnum.profitChannel.getKey());
            parameterDto.setParameterName(ParameterEnum.profitChannel.getValue());
            parameterDto.setParameterName(ParameterEnum.profitChannel.getValue());
            parameterDto.setParameterValue(indexParameterDto.getProfitChannel());
            create(parameterDto);
        }else{
            parameter1.setParameterValue(indexParameterDto.getProfitChannel());
        }

        return parameter;
    }

    /**
     * 产品停用、启用
     * @param productDto
     * @return
     */
    public Parameter enable(ParameterDto productDto){
        Parameter parameter = repository.findOne(productDto.getId());
        if(parameter == null){
            CwException.throwIt("参数不存在");
        }
        if(parameter.getIsValid()) {
            parameter.setIsValid(Boolean.FALSE);
        }else{
            parameter.setIsValid(Boolean.TRUE);
        }
        return parameter;
    }

    /**
     * 查询单个参数
     * @param id
     * @return
     */
    public Parameter findById(Long id)
    {
        return repository.findOne(id);
    }

    /**
     * 根据参数编码查询审核配置
     * @param code
     * @return
     */
    public Parameter findByCode(String code)
    {
        return repository.findByParameterCode(code);
    }

    /**
     * 查询所有参数
     * @return
     */
    public List<Parameter> findAll() {
        return repository.findAll();
    }

}
