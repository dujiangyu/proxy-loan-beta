package com.cw.web.backend.controller.channel;

import com.cw.biz.channel.app.dto.AgentDto;
import com.cw.biz.channel.app.dto.ThirdOperateDto;
import com.cw.biz.channel.app.service.ThirdOperateAppService;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 代理商下级服务
 * Created by dujy on 2017-07-13.
 */
@RestController
public class ThirdOperateController extends AbstractBackendController {

    @Autowired
    private ThirdOperateAppService thirdOperateAppService;

    /**
     * 修改代理商下级信息
     * @param thirdOperateDto
     * @return
     */
    @PostMapping("/provider/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody ThirdOperateDto thirdOperateDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        thirdOperateAppService.update(thirdOperateDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");

        return cpViewResultInfo;
    }

    /**
       * 服务商充值
       * @param thirdOperateDto
       * @return
       */
      @PostMapping("/provider/recharge.json")
      @ResponseBody
      public CPViewResultInfo recharge(@RequestBody ThirdOperateDto thirdOperateDto){
          CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
          Long id = thirdOperateAppService.recharge(thirdOperateDto);
          cpViewResultInfo.setData(id);
          cpViewResultInfo.setSuccess(true);
          cpViewResultInfo.setMessage("充值成功");

          return cpViewResultInfo;
      }
    /**
     * 查询代理商下级详情
     * @param id
     * @return
     */
    @GetMapping("/provider/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        ThirdOperateDto thirdOperateDto = thirdOperateAppService.findById(id);
        cpViewResultInfo.setData(thirdOperateDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 查询代理商下级
     * @param thirdOperateDto
     * @return
     */
    @PostMapping("/provider/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody ThirdOperateDto thirdOperateDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        thirdOperateDto.setPageNo(thirdOperateDto.getPageNumber());
        Page<ThirdOperateDto> creditCardDtos = thirdOperateAppService.findByCondition(thirdOperateDto);
        cpViewResultInfo.setData(creditCardDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 启用停用代理商下级
     * @param thirdOperateDto
     * @return
     */
    @PostMapping("/provider/enable.json")
    @ResponseBody
    public CPViewResultInfo enable(@RequestBody ThirdOperateDto thirdOperateDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        thirdOperateAppService.enable(thirdOperateDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

}
