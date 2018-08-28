package com.cw.web.backend.controller.channel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cw.biz.channel.app.dto.ChannelDto;
import com.cw.biz.channel.app.service.ChannelAppService;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public CPViewResultInfo create(@RequestBody ChannelDto channelDto)
    {
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
    public CPViewResultInfo enable(@RequestBody ChannelDto channelDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        channelAppService.enable(channelDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**查询所有一级渠道
     *&lt;功能简述&gt;
     *&lt;功能详细描述&gt;
     * ${tags} [参数说明]
     *
     * @return ${return_type} [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @GetMapping("/channel/findAll.json")
    @ResponseBody
    public JSONArray findAll(String firstChannelName){
        List<ChannelDto> channelDtoList = channelAppService.findAll(firstChannelName);
        if(channelDtoList.size()>0){
            JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(channelDtoList));
            return jsonArray;
        }else{
            return null;
        }
    }
    /**
     * 查询所有产品数据
     * @return
     */
    @GetMapping("/channel/findAllChannel.json")
    @ResponseBody
    public String findAllChannel(){
        List<ChannelDto> channelDtoList = channelAppService.findAll(null);
        return JSONObject.toJSONString(channelDtoList);
    }
}
