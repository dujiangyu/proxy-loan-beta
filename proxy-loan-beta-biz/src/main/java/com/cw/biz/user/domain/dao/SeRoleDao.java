package com.cw.biz.user.domain.dao;

import com.cw.biz.user.domain.entity.SeRole;
import com.cw.biz.common.dto.ListParamDto;
import com.cw.biz.CwException;
import com.cw.biz.jdbc.JdbcPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


@Repository
public class SeRoleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final static String QUERY_SQL = "select id, role, description, resource_ids as resourceIdsStr, available,type from pf_se_role";

    public SeRole createRole(final SeRole role) {
        final String sql = "insert into pf_se_role(role, merchant_id,description, resource_ids, available,type) values(?,?,?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setString(count++, role.getRole());
                psst.setLong(count++, role.getMerchantId());
                psst.setString(count++, role.getDescription());
                psst.setString(count++, role.getResourceIdsStr());
                psst.setBoolean(count++, role.getAvailable());
                psst.setString(count++,role.getType());
                return psst;
            }
        }, keyHolder);
        }catch (org.springframework.dao.DuplicateKeyException e){
            CwException.throwIt("角色编码已存在");
        }
        role.setId(keyHolder.getKey().longValue());
        return role;
    }

    public SeRole updateRole(SeRole role) {
        final String sql = "update pf_se_role set role=?, description=?, resource_ids=?, available=?,type=? where id=?";
        try {
             jdbcTemplate.update(
                sql,
                role.getRole(), role.getDescription(), role.getResourceIdsStr(), role.getAvailable(),role.getType(), role.getId());
        }catch (org.springframework.dao.DuplicateKeyException e){
            CwException.throwIt("角色编码已存在");
        }
        return role;
    }

    public void deleteRole(Long roleId) {
        final String sql = "delete from pf_se_role where id=?";
        jdbcTemplate.update(sql, roleId);
    }


    
    public SeRole findOne(Long roleId) {
        final String sql = "select id, role, merchant_id,description, resource_ids as resourceIdsStr, available,type from pf_se_role where id=?";
        List<SeRole> roleList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SeRole.class), roleId);
        if (roleList.size() == 0) {
            return null;
        }
        return roleList.get(0);
    }

    public List<SeRole> findByMerchantIdAndType(Long merchantId,String type) {
        final String sql = "select id, role,merchant_id, description, resource_ids as resourceIdsStr, available,type from pf_se_role where role != 'admin' and merchant_id =" +merchantId+" and type='"+type+"'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(SeRole.class));
    }
    public List<SeRole> findByRoleIds(String roleIds,Long merchantId,String type) {
        final String sql = "select id, role,merchant_id, description, resource_ids as resourceIdsStr, available,type from pf_se_role where id in ("+roleIds+")"+ " and merchant_id="+merchantId+" and type='"+type+"'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(SeRole.class));
    }

    public JdbcPage findByPage(ListParamDto dto) {
        StringBuilder sb = new StringBuilder(QUERY_SQL);
        sb.append(" where merchant_id = ").append(dto.getMerchantId());
        sb.append(" and role != ").append("'admin'");
        if(StringUtils.isNotBlank(dto.getNameOrCode())){
            sb.append(" and (role like '%").append(dto.getNameOrCode()).append("%' or description like '%").append(dto.getNameOrCode()).append("%')");
        }
        return new JdbcPage(sb.toString(),dto.getPageNo()+1,dto.getSizePerPage(),jdbcTemplate,SeRole.class);
    }

    public void disable(Long roleId, Long merchantId) {
        String sql = "update pf_se_role set available = ? where id = ? and merchant_id = ? ";
        jdbcTemplate.update(
                sql,
                Boolean.FALSE, roleId, merchantId);
    }

    public void enable(Long roleId, Long merchantId) {
        String sql = "update pf_se_role set available = ? where id = ? and merchant_id = ? ";
        jdbcTemplate.update(
                sql,
                Boolean.TRUE, roleId, merchantId);
    }
}
