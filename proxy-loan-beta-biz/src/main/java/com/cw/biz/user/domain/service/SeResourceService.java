package com.cw.biz.user.domain.service;


import com.cw.biz.user.domain.dao.SeResourceDao;
import com.cw.biz.user.domain.dao.SeRoleDao;
import com.cw.biz.user.domain.dao.SeUserDao;
import com.cw.biz.user.domain.entity.SeResource;
import com.cw.biz.user.domain.entity.SeRole;
import com.cw.biz.user.domain.entity.SeUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class SeResourceService {

    @Autowired
    private SeResourceDao seResourceDao;

    @Autowired
    private SeUserDao seUserDaoDao;

    @Autowired
    private SeRoleDao seRoleDao;
//    @Autowired
//    private RedisTemplate<String, SeResource> redisTemplate;

    @Deprecated
    public SeResource createResource(SeResource resource) {
        return seResourceDao.createResource(resource);
    }
    @Deprecated
    public SeResource updateResource(SeResource resource) {
//        redisTemplate.delete("SeResource." + resource.getId());
        return seResourceDao.updateResource(resource);
    }
    @Deprecated
    public void deleteResource(Long resourceId) {
//        redisTemplate.delete("SeResource." + resourceId);
        seResourceDao.deleteResource(resourceId);
    }

    public SeResource findOne(Long resourceId) {
        SeResource seResource = null;//redisTemplate.opsForValue().get("SeResource." + resourceId);
        if (seResource == null) {
            seResource = seResourceDao.findOne(resourceId);
            if (seResource != null) {
//                redisTemplate.opsForValue().set("SeResource." + resourceId, seResource);
            }
        }
        return seResource;
    }


    public List<SeResource> findByBusinessType(String type) {
        return seResourceDao.findByBusinessType(type);
    }

    public  List<SeResource> findByUser(SeUser seUser){
        Set<Long> resourceIds = new HashSet<Long>();
        for (Long roleId : seUser.getRoleIds()) {
            SeRole role =seRoleDao.findOne(roleId);
            if (role != null) {
                resourceIds.addAll(role.getResourceIds());
            }
        }
        return seResourceDao.findByIdIn(resourceIds);
    }

    public  List<SeResource> findByUser(Long userId){
        SeUser seUser = seUserDaoDao.findOne(userId);
        return findByUser(seUser);
    }

    public Set<SeResource> findPermissions(Set<Long> resourceIds) {
        Set<SeResource> permissions = new TreeSet<>();
        for (Long resourceId : resourceIds) {
            SeResource resource = findOne(resourceId);
            if (resource != null && !StringUtils.isEmpty(resource.getPermission())) {
                permissions.add(resource);
            }
        }
        return permissions;
    }

    public List<SeResource> findByUserAndType(Long userId, SeResource.ResourceType resourceType){
        List<SeResource> seResources=findByUser(userId);
        List result = new ArrayList<>();
        for(SeResource resource : seResources){
            if(resource.getType()==resourceType){
                result.add(resource);
            }
        }
        return result;

    }
    public List<SeResource> findAll(){
        return seResourceDao.findAll();
    }


}
