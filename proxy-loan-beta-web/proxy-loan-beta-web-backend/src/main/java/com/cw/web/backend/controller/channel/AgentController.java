package com.cw.web.backend.controller.channel;

import com.cw.biz.channel.app.dto.AgentDto;
import com.cw.biz.channel.app.service.AgentAppService;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 代理商
 * Created by dujy on 2017-07-13.
 */
@RestController
public class AgentController extends AbstractBackendController {

    @Autowired
    private AgentAppService agentAppService;

    /**
     * 修改代理商信息
     * @param agentDto
     * @return
     */
    @PostMapping("/agent/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody AgentDto agentDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = agentAppService.update(agentDto);
        cpViewResultInfo.setData(id);
        cpViewResultInfo.setMessage("成功");
        cpViewResultInfo.setSuccess(true);

        return cpViewResultInfo;
    }

    /**
        * 代理商充值
        * @param agentDto
        * @return
        */
       @PostMapping("/agent/recharge.json")
       @ResponseBody
       public CPViewResultInfo recharge(@RequestBody AgentDto agentDto){
           CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
           Long id = agentAppService.recharge(agentDto);
           cpViewResultInfo.setData(id);
           cpViewResultInfo.setSuccess(true);
           cpViewResultInfo.setMessage("成功");

           return cpViewResultInfo;
       }


    /**
     * 查询代理商详情
     * @param id
     * @return
     */
    @GetMapping("/agent/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        AgentDto agentDto = agentAppService.findById(id);
        cpViewResultInfo.setData(agentDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 查询代理商
     * @param agentDto
     * @return
     */
    @PostMapping("/agent/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody AgentDto agentDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        agentDto.setPageNo(agentDto.getPageNumber());
        Page<AgentDto> creditCardDtos = agentAppService.findByCondition(agentDto);
        cpViewResultInfo.setData(creditCardDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 启用停用代理商
     * @param agentDto
     * @return
     */
    @PostMapping("/agent/enable.json")
    @ResponseBody
    public CPViewResultInfo enable(@RequestBody AgentDto agentDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        agentAppService.enable(agentDto);
        cpViewResultInfo.setMessage("成功");
        cpViewResultInfo.setSuccess(true);
        return cpViewResultInfo;
    }

}
