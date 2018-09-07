package com.cw.biz.user.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.user.domain.dao.SeRoleDao;
import com.cw.biz.user.domain.dao.SeUserDao;
import com.cw.biz.user.domain.entity.SeResource;
import com.cw.biz.user.domain.entity.SeRole;
import com.cw.biz.user.domain.entity.SeUser;
import com.cw.biz.common.dto.ListParamDto;
import com.cw.biz.jdbc.JdbcPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class SeRoleService {

    @Autowired
    private SeRoleDao seRoleDao;
    @Autowired
    private SeResourceService resourceService;
    @Autowired
    private SeUserDao seUserDao;

//    @Autowired
//    private RedisTemplate<String, SeRole> redisTemplate;
    public SeRole createRole(SeRole role) {
        if(!"admin".equals(CPContext.getContext().getSeUserInfo().getUsername())){
            role.setMerchantId(CPContext.getContext().getSeUserInfo().getId());
        }else {
            role.setMerchantId(0L);
        }
        return seRoleDao.createRole(role);
    }

    public SeRole updateRole(SeRole role) {
//        redisTemplate.delete("SeRole." + role.getId());
        return seRoleDao.updateRole(role);
    }

    public void deleteRole(Long roleId) {
        List<SeUser> userList=seUserDao.findAllContainRole(roleId);
        for(SeUser seUser:userList){
            List<Long> roIdList=seUser.getRoleIds();
            roIdList.remove(roleId);
            seUserDao.updateUser(seUser);
        }
//        redisTemplate.delete("SeRole." + roleId);
        seRoleDao.deleteRole(roleId);
    }

    public void enable(Long roleId, Long merchantId) {
//        redisTemplate.delete("SeRole." + roleId);
        seRoleDao.enable(roleId, merchantId);
    }

    public void enable(Long roleId) {
        SeRole seRole = seRoleDao.findOne(roleId);
        Assert.notNull(seRole);
        enable(seRole.getId(), seRole.getMerchantId());
    }

    public void enable(List<Long> roleIds) {
        for(Long id : roleIds) {
            enable(id);
        }
    }

    public void disable(Long roleId, Long merchantId) {
//        redisTemplate.delete("SeRole." + roleId);
        seRoleDao.disable(roleId, merchantId);
    }

    public void disable(Long roleId) {
        SeRole seRole = seRoleDao.findOne(roleId);
        Assert.notNull(seRole);
        disable(seRole.getId(), seRole.getMerchantId());
    }

    public void disable(List<Long> roleIds) {
        for(Long id : roleIds) {
            disable(id);
        }
    }

    public SeRole findOne(Long roleId) {
        SeRole seRole = null;//redisTemplate.opsForValue().get("SeRole." + roleId);
        if (seRole == null) {
            seRole = seRoleDao.findOne(roleId);
            if (seRole != null) {
//                redisTemplate.opsForValue().set("SeRole." + roleId, seRole);
            }
        }
        return seRole;
    }

    public List<SeRole> findByMerchantIdAndType(Long merchantId,String type) {
        return seRoleDao.findByMerchantIdAndType(merchantId,type);
    }

    public Set<String> findRoles(Long... roleIds) {
        Set<String> roles = new HashSet<String>();
        for (Long roleId : roleIds) {
            SeRole role = findOne(roleId);
            if (role != null) {
                roles.add(role.getRole());
            }
        }
        return roles;
    }
    public List<SeRole> findRoleByRoleIdsAndMerchantIdAndType(String roleIds,Long merchantId,String type) {
        return seRoleDao.findByRoleIds(roleIds,merchantId,type);
    }

    public Set<SeResource> findPermissions(Long[] roleIds) {
        return resourceService.findPermissions(findResourceIds(roleIds));
    }

    public Set<Long> findResourceIds(Long[] roleIds) {
        Set<Long> resourceIds = new HashSet<Long>();
        for (Long roleId : roleIds) {
            SeRole role = findOne(roleId);
            if (role != null) {
                resourceIds.addAll(role.getResourceIds());
            }
        }
        return resourceIds;
    }

    public JdbcPage findByPage(ListParamDto dto) {
        if(!"admin".equals(CPContext.getContext().getSeUserInfo().getUsername())){
            dto.setMerchantId(CPContext.getContext().getSeUserInfo().getId());
        }else{
            dto.setMerchantId(0L);
        }
        return seRoleDao.findByPage(dto);
    }
}
