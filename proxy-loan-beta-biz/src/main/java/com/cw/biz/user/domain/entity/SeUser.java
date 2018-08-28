/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-13 18:59 创建
 */
package com.cw.biz.user.domain.entity;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SeUser implements Serializable {
    private Long id; //编号
    private Long merchantId; //所属公司,平台用户编号为0
    private String username; //用户名
    private String displayName;
    private String password; //密码
    private String salt; //加密密码的盐
    private String type;//todo: 用户分类，不同分类的用户不能登录,比如送货员、管理后台用户
    private String wechatId;//微信openid
    private String phone;
    private String roleStr;
    private String channel;

    public String getRoleStr() {
        if(roleStr==null){
            roleStr="";
            for(Long roleId:roleIds){
                roleStr+=roleId+",";
            }
        }
        return roleStr;
    }

    public void setRoleStr(String roleStr) {
        this.roleStr = roleStr;
    }

    /**
     * 关联ID
     */
    private Long rId;
    private List<Long> roleIds; //拥有的角色列表
    private Boolean locked = Boolean.FALSE;

    private  String roleNameStr;

    public SeUser() {
    }

    public SeUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getRoleNameStr() {
        return roleNameStr;
    }

    public void setRoleNameStr(String roleNameStr) {
        this.roleNameStr = roleNameStr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCredentialsSalt() {
        return username + salt;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<Long> getRoleIds() {
        if (roleIds == null) {
            roleIds = new ArrayList<Long>();
        }
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }


    public String getRoleIdsStr() {
//        if (CollectionUtils.isEmpty(roleIds)) {
//            return "";
//        }
//        StringBuilder s = new StringBuilder(",");
//        for (Long roleId : roleIds) {
//            s.append(roleId);
//            s.append(",");
//        }
        return getRoleStr();
    }

    public void setRoleIdsStr(String roleIdsStr) {
        if (StringUtils.isEmpty(roleIdsStr)) {
            return;
        }
        String[] roleIdStrs = roleIdsStr.split(",");
        for (String roleIdStr : roleIdStrs) {
            if (StringUtils.isEmpty(roleIdStr)) {
                continue;
            }
            getRoleIds().add(Long.valueOf(roleIdStr));
        }
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeUser user = (SeUser) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", organizationId=" + merchantId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", roleIds=" + roleIds +
                ", locked=" + locked +
                '}';
    }
}
