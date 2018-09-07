package com.cw.biz.user.domain.dao;

import com.cw.biz.user.domain.entity.SeResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Repository
public class SeResourceDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public SeResource createResource(final SeResource resource) {
        final String sql = "insert into pf_se_resource(name, type, url, permission, parent_id, parent_ids, available,business_type) values(?,?,?,?,?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setString(count++, resource.getName());
                psst.setString(count++, resource.getType().name());
                psst.setString(count++, resource.getUrl());
                psst.setString(count++, resource.getPermission());
                psst.setLong(count++, resource.getParentId());
                psst.setString(count++, resource.getParentIds());
                psst.setBoolean(count++, resource.getAvailable());
                psst.setString(count++,resource.getBusinessType());
                return psst;
            }
        }, keyHolder);
        resource.setId(keyHolder.getKey().longValue());
        return resource;
    }

    public SeResource updateResource(SeResource resource) {
        final String sql = "update pf_se_resource set name=?, type=?, url=?, permission=?, parent_id=?, parent_ids=?, available=?,business_type=? where id=?";
        jdbcTemplate.update(
                sql,
                resource.getName(), resource.getType().name(), resource.getUrl(), resource.getPermission(), resource.getParentId(), resource.getParentIds(), resource.getAvailable(),resource.getBusinessType(), resource.getId());
        return resource;
    }

    public void deleteResource(Long resourceId) {
        SeResource resource = findOne(resourceId);
        final String deleteSelfSql = "delete from pf_se_resource where id=?";
        jdbcTemplate.update(deleteSelfSql, resourceId);
        final String deleteDescendantsSql = "delete from pf_se_resource where parent_ids like ?";
        jdbcTemplate.update(deleteDescendantsSql, resource.makeSelfAsParentIds() + "%");
    }


    public SeResource findOne(Long resourceId) {
        final String sql = "select id, name, type, url, permission, parent_id, parent_ids, available,business_type from pf_se_resource where id=? order by id";
        List<SeResource> resourceList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SeResource.class), resourceId);
        if (resourceList.size() == 0) {
            return null;
        }
        return resourceList.get(0);
    }

    public List<SeResource> findByIdIn(Set<Long> resourceIds) {
        if (resourceIds.size() == 0) {
            return new ArrayList<>();
        }
        Long idArray[] = resourceIds.toArray(new Long[0]);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < idArray.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(idArray[i].toString());
        }
        final String sql = "select id, name, type, url, permission, parent_id, parent_ids, available,business_type from pf_se_resource where id in ("+sb.toString()+")";
        List<SeResource> resourceList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SeResource.class));
        if (resourceList.size() == 0) {
            return new ArrayList<>();
        }
        return resourceList;
    }

    public List<SeResource> findByBusinessType(String type) {
        String conType = "'common','"+type+"'";
        final String sql = "select id, name, type, url, permission, parent_id, parent_ids, available,business_type from pf_se_resource where business_type in("+conType+") order by concat(parent_ids, id) desc";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(SeResource.class));
    }
    public List<SeResource> findAll() {
        final String sql = "select id, name, type, url, permission, parent_id, parent_ids, available,business_type from pf_se_resource order by concat(parent_ids, id) asc";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(SeResource.class));
    }
}
