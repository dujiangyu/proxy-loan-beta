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
        //全景雷达
        Parameter parameter = repository.findByParameterCode(ParameterEnum.LEIDAI.getKey());
        if(parameter == null)
        {
            ParameterDto parameterDto = new ParameterDto();
            parameterDto.setParameterCode(ParameterEnum.LEIDAI.getKey());
            parameterDto.setParameterName(ParameterEnum.LEIDAI.getValue());
            parameterDto.setParameterName(ParameterEnum.LEIDAI.getValue());
            parameterDto.setParameterValue(indexParameterDto.getLeida());
            create(parameterDto);
        }else{
            parameter.setParameterValue(indexParameterDto.getLeida());
        }
        //运营商报告
        Parameter parameter1 = repository.findByParameterCode(ParameterEnum.YUNYINGSHANG.getKey());
        if(parameter1 == null)
        {
            ParameterDto parameterDto = new ParameterDto();
            parameterDto.setParameterCode(ParameterEnum.YUNYINGSHANG.getKey());
            parameterDto.setParameterName(ParameterEnum.YUNYINGSHANG.getValue());
            parameterDto.setParameterName(ParameterEnum.YUNYINGSHANG.getValue());
            parameterDto.setParameterValue(indexParameterDto.getYunyingshang());
            create(parameterDto);
        }else{
            parameter1.setParameterValue(indexParameterDto.getYunyingshang());
        }

        //天贝报告
        Parameter parameter2 = repository.findByParameterCode(ParameterEnum.REPORT.getKey());
        if(parameter2 == null){
            ParameterDto parameterDto = new ParameterDto();
            parameterDto.setParameterCode(ParameterEnum.REPORT.getKey());
            parameterDto.setParameterName(ParameterEnum.REPORT.getValue());
            parameterDto.setParameterName(ParameterEnum.REPORT.getValue());
            parameterDto.setParameterValue(indexParameterDto.getReport());
            create(parameterDto);
        }else{
            parameter2.setParameterValue(indexParameterDto.getReport());
        }

        //天贝借条逾期
       Parameter parameter3 = repository.findByParameterCode(ParameterEnum.OVERDUEFILE.getKey());
       if(parameter3 == null){
           ParameterDto parameterDto = new ParameterDto();
           parameterDto.setParameterCode(ParameterEnum.OVERDUEFILE.getKey());
           parameterDto.setParameterName(ParameterEnum.OVERDUEFILE.getValue());
           parameterDto.setParameterName(ParameterEnum.OVERDUEFILE.getValue());
           parameterDto.setParameterValue(indexParameterDto.getOverdue());
           create(parameterDto);
       }else{
           parameter3.setParameterValue(indexParameterDto.getReport());
       }


        //天贝黑名单检测
       Parameter parameter4 = repository.findByParameterCode(ParameterEnum.BLACKLIST.getKey());
       if(parameter4 == null){
           ParameterDto parameterDto = new ParameterDto();
           parameterDto.setParameterCode(ParameterEnum.BLACKLIST.getKey());
           parameterDto.setParameterName(ParameterEnum.BLACKLIST.getValue());
           parameterDto.setParameterName(ParameterEnum.BLACKLIST.getValue());
           parameterDto.setParameterValue(indexParameterDto.getBlackList());
           create(parameterDto);
       }else{
           parameter4.setParameterValue(indexParameterDto.getReport());
       }


        //新颜实名认证
       Parameter parameter5 = repository.findByParameterCode(ParameterEnum.INFOAUTH.getKey());
       if(parameter5 == null){
           ParameterDto parameterDto = new ParameterDto();
           parameterDto.setParameterCode(ParameterEnum.INFOAUTH.getKey());
           parameterDto.setParameterName(ParameterEnum.INFOAUTH.getValue());
           parameterDto.setParameterName(ParameterEnum.INFOAUTH.getValue());
           parameterDto.setParameterValue(indexParameterDto.getInfoAuth());
           create(parameterDto);
       }else{
           parameter5.setParameterValue(indexParameterDto.getReport());
       }

        //新颜逾期档案
       Parameter parameter6 = repository.findByParameterCode(ParameterEnum.OVERDUEFILE.getKey());
       if(parameter6 == null){
           ParameterDto parameterDto = new ParameterDto();
           parameterDto.setParameterCode(ParameterEnum.OVERDUEFILE.getKey());
           parameterDto.setParameterName(ParameterEnum.OVERDUEFILE.getValue());
           parameterDto.setParameterName(ParameterEnum.OVERDUEFILE.getValue());
           parameterDto.setParameterValue(indexParameterDto.getOverdueFile());
           create(parameterDto);
       }else{
           parameter6.setParameterValue(indexParameterDto.getReport());
       }

        //天贝报告
       Parameter parameter7 = repository.findByParameterCode(ParameterEnum.ZMF.getKey());
       if(parameter7 == null){
           ParameterDto parameterDto = new ParameterDto();
           parameterDto.setParameterCode(ParameterEnum.ZMF.getKey());
           parameterDto.setParameterName(ParameterEnum.ZMF.getValue());
           parameterDto.setParameterName(ParameterEnum.ZMF.getValue());
           parameterDto.setParameterValue(indexParameterDto.getZmf());
           create(parameterDto);
       }else{
           parameter7.setParameterValue(indexParameterDto.getReport());
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
