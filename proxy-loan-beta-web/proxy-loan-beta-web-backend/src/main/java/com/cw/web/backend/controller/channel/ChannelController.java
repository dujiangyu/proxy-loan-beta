package com.cw.web.backend.controller.channel;

import com.cw.biz.channel.app.dto.ChannelDto;
import com.cw.biz.channel.app.service.ChannelAppService;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 渠道服务
 * Created by dujy on 2017-07-13.
 */
@RestController
public class ChannelController extends AbstractBackendController {

    @Autowired
    private ChannelAppService channelAppService;

    /**
     * 新增渠道
     * @param channelDto
     * @return
     */
    @PostMapping("/channel/create.json")
    @ResponseBody
    public CPViewResultInfo create(@RequestBody ChannelDto channelDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long cardId = channelAppService.create(channelDto);
        cpViewResultInfo.setData(cardId);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");

        return cpViewResultInfo;
    }

    /**
     * 修改渠道信息
     * @param channelDto
     * @return
     */
    @PostMapping("/channel/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody ChannelDto channelDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = channelAppService.update(channelDto);
        cpViewResultInfo.setData(id);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");

        return cpViewResultInfo;
    }

    /**
     * 查询渠道详情
     * @param id
     * @return
     */
    @GetMapping("/channel/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        ChannelDto channelDto = channelAppService.findById(id);
        cpViewResultInfo.setData(channelDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 查询渠道
     * @param channelDto
     * @return
     */
    @PostMapping("/channel/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody ChannelDto channelDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        channelDto.setPageNo(channelDto.getPageNumber());
        Page<ChannelDto> creditCardDtos = channelAppService.findByCondition(channelDto);
        cpViewResultInfo.setData(creditCardDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 启用停用渠道
     * @param channelDto
     * @return
     */
    @PostMapping("/channel/enable.json")
    @ResponseBody
    public CPViewResultInfo enable(@RequestBody ChannelDto channelDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        channelAppService.enable(channelDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

}
