package com.cw.biz.channel.app.service;

import com.cw.biz.channel.app.dto.ChannelDto;
import com.cw.biz.channel.domain.dao.ChannelSettleDao;
import com.cw.biz.channel.domain.entity.Channel;
import com.cw.biz.channel.domain.service.ChannelDomainService;
import com.cw.biz.common.dto.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 渠道服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class ChannelAppService {

    @Autowired
    private ChannelDomainService channelDomainService;
    /**
     * 新增渠道
     * @param channelDto
     * @return
     */
    public Long create(ChannelDto channelDto)
    {
        return channelDomainService.create(channelDto).getId();
    }

    /**
     * 修改渠道
     * @param channelDto
     * @return
     */
    public Long update(ChannelDto channelDto)
    {
        return channelDomainService.update(channelDto).getId();
    }

    /**
     * 停用启用渠道
     * @param channelDto
     * @return
     */
    public void enable(ChannelDto channelDto)
    {
        channelDomainService.enable(channelDto);
    }

    /**
     * 查询渠道详情
     * @param id
     * @return
     */
    public ChannelDto findById(Long id)
    {
        return channelDomainService.findById(id).to(ChannelDto.class);
    }

    /**
     * 按条件查询渠道
     * @param dto
     * @return
     */
    public Page<ChannelDto> findByCondition(ChannelDto dto)
    {
        return Pages.map(channelDomainService.findByCondition(dto),ChannelDto.class);
    }

    /**
     * 查询所有渠道
     * @return
     */
    public List<ChannelDto> findAll()
    {
        List<ChannelDto> channelDtoList = new ArrayList<ChannelDto>();
        List<Channel> channelList = channelDomainService.findAll();
        for(Channel channel : channelList){
            channelDtoList.add(channel.to(ChannelDto.class));
        }
        return channelDtoList;
    }

}
