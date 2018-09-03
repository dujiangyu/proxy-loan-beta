package com.cw.biz.channel.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.CwException;
import com.cw.biz.channel.app.dto.ChannelDto;
import com.cw.biz.channel.domain.entity.Channel;
import com.cw.biz.channel.domain.repository.ChannelRepository;
import com.cw.biz.user.domain.entity.SeUser;
import com.cw.biz.user.domain.service.SeUserService;
import com.cw.core.common.util.Utils;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 渠道服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class ChannelDomainService {

    @Autowired
    private SeUserService seUserService;

    @Autowired
    private ChannelRepository repository;
    /**
     * 新增渠道
     * @param channelDto
     * @return
     */
    public Channel create(ChannelDto channelDto){
        Channel channel = new Channel();
        channel.from(channelDto);
        channel.setBackLoginUrl("http://www.youxinjk.com/bgLogin.html");
        channel.setUserId(CPContext.getContext().getSeUserInfo().getId());
        String token= Utils.genarateUuid();
        channel.setCode(token);
        channel.setAccountNo(channelDto.getName());
        channel.setTgUrl("http://www.youxinjk.com/wechat/register.html?channelNo="+token);
        channel.prepareSave();
        return repository.save(channel);
    }

    /**
     * 修改渠道
     * @param channelDto
     * @return
     */
    public Channel update(ChannelDto channelDto){
        Channel channel=null;
        if(channelDto.getId()==null){
            channel = create(channelDto);
            //保存登录用户信息
            SeUser seUser = new SeUser();
            seUser.setType("user");
            seUser.setUsername(channelDto.getName());
            seUser.setDisplayName(channelDto.getName());
            seUser.setPassword(channelDto.getPassword());
            seUser.setrId(1L);
            SeUser seUser1 = seUserService.createUser(seUser);
            channel.setChannelUserId(seUser1.getId());
        }else {
           channel = repository.findOne(channelDto.getId());
            //修改渠道密码
           SeUser seUser =  seUserService.findByUserNameAndMerchantId(channelDto.getName(),0L);
           if(channelDto.getPassword()!=null && !"".equals(channelDto.getPassword())) {
               seUser.setPassword(channelDto.getPassword());
           }
           seUser.setUsername(channelDto.getName());
           seUserService.updateUser(seUser,Boolean.TRUE);


            channel.from(channelDto);
            channel = repository.save(channel);

        }
        return channel;
    }


    /**
     * 渠道停用
     * @param channelDto
     * @return
     */
    public Channel enable(ChannelDto channelDto)
    {
        Channel channel = repository.findOne(channelDto.getId());
        if(channel == null)
        {
            CwException.throwIt("渠道不存在");
        }
        if(channel.getIsValid()) {
            channel.setIsValid(Boolean.FALSE);
        }else{
            channel.setIsValid(Boolean.TRUE);
        }
        return channel;
    }

    /**
     * 查询渠道详情
     * @param id
     * @return
     */
    public Channel findById(Long id){
        return repository.findOne(id);
    }

    public Channel findByChannelUserId(Long id){
       return repository.findByChannelUserId(id);
    }

    /**
     * 按条件查询渠道列表
     * @param channelDto
     * @return
     */
    public Page<Channel> findByCondition(ChannelDto channelDto){
        String[] fields = {"rawAddTime"};
        channelDto.setSortFields(fields);
        channelDto.setSortDirection(Sort.Direction.DESC);
        Specification<Channel> supplierSpecification = (Root<Channel> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);

            predicates.add(cb.equal(root.get("isValid"),Boolean.TRUE));
            if(!"admin".equals(CPContext.getContext().getSeUserInfo().getUsername())) {
                predicates.add(cb.equal(root.get("userId"), CPContext.getContext().getSeUserInfo().getId()));
            }
            if(!StringUtils.isEmpty(channelDto.getName())) {
                predicates.add(cb.like(root.get("name"), "%"+channelDto.getName()+"%"));
            }
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, channelDto.toPage());
    }

    /**
     * 查询所有渠道
     * @return
     */
    public List<Channel> findAll(){
        return repository.findAll();
    }

}
