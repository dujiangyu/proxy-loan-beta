package com.cw.biz.parameter.app.service;

import com.cw.biz.parameter.app.ParameterEnum;
import com.cw.biz.parameter.app.dto.IndexParameterDto;
import com.cw.biz.parameter.app.dto.ParameterDto;
import com.cw.biz.parameter.domain.entity.Parameter;
import com.cw.biz.parameter.domain.service.ParameterDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 系统参数服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class ParameterAppService {

    @Autowired
    private ParameterDomainService parameterDomainService;

    /**
     * 修改参数
     * @param parameterDto
     * @return
     */
    public Long update(IndexParameterDto parameterDto)
    {
        return parameterDomainService.update(parameterDto).getId();
    }

    /**
     * 根据编码查询配置
     * @param code
     * @return
     */
    public ParameterDto findByCode(String code){
        return parameterDomainService.findByCode(code).to(ParameterDto.class);
    }

    /**
     * 查询全部参数
     * @return
     */
    public IndexParameterDto findAll(){
        IndexParameterDto indexParameterDto = new IndexParameterDto();
        List<Parameter> productList = parameterDomainService.findAll();
        for(Parameter parameter : productList){
            if(ParameterEnum.LEIDAI.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setLeida(parameter.getParameterValue());
            }
            if(ParameterEnum.YUNYINGSHANG.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setYunyingshang(parameter.getParameterValue());
            }
            if(ParameterEnum.REPORT.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setReport(parameter.getParameterValue());
            }
            if(ParameterEnum.BLACKLIST.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setBlackList(parameter.getParameterValue());
            }
            if(ParameterEnum.OVERDUE.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setOverdue(parameter.getParameterValue());
            }
            if(ParameterEnum.OVERDUEFILE.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setOverdueFile(parameter.getParameterValue());
            }
            if(ParameterEnum.INFOAUTH.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setInfoAuth(parameter.getParameterValue());
            }
            if(ParameterEnum.ZMF.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setZmf(parameter.getParameterValue());
            }

        }
        return indexParameterDto;
    }

}
