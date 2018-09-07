package com.cw.web.backend.controller.parameter;

import com.cw.biz.parameter.app.dto.IndexParameterDto;
import com.cw.biz.parameter.app.dto.ParameterDto;
import com.cw.biz.parameter.app.service.ParameterAppService;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 参数配置
 * Created by dujy on 2017-05-20.
 */
@RestController
public class ParameterController extends AbstractBackendController {

    @Autowired
    private ParameterAppService parameterAppService;

    /**
     * 查询所有参数
     * @return
     */
    @GetMapping("/parameter/findParameter.json")
    public CPViewResultInfo findAll()
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        IndexParameterDto parameterDtos = parameterAppService.findAll();
        cpViewResultInfo.setData(parameterDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /** 查询接口请求费用
     *&lt;功能简述&gt;
     *&lt;功能详细描述&gt;
     * ${tags} [参数说明]
     *
     * @return ${return_type} [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @GetMapping("/parameter/findByCode.json")
    public CPViewResultInfo findByCode(String code){
       CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
       ParameterDto parameterDtos = parameterAppService.findByCode(code);
       cpViewResultInfo.setData(parameterDtos);
       cpViewResultInfo.setSuccess(true);
       cpViewResultInfo.setMessage("成功");
       return cpViewResultInfo;
   }

    /**
     * 修改参数
     * @param parameterDto
     * @return
     */
    @PostMapping("/parameter/update.json")
    @ResponseBody
    public CPViewResultInfo updateParameter(@RequestBody IndexParameterDto parameterDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long parameterId = parameterAppService.update(parameterDto);
        cpViewResultInfo.setData(parameterId);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("参数保存成功");
        return cpViewResultInfo;
    }
}
