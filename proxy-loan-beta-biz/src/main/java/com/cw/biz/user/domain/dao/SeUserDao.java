package com.cw.biz.user.domain.dao;

import com.cw.biz.user.domain.entity.SeUser;
import com.cw.biz.common.dto.ListParamDto;
import com.cw.biz.CwException;
import com.cw.biz.jdbc.JdbcPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.util.List;


@Repository
public class SeUserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final static String QUERY_SQL  = "select id, merchant_id, username, salt, role_ids as roleIdsStr, locked,type,display_name,rid,wechat_id,phone,channel from pf_se_user";
    public SeUser createUser(final SeUser user) {
        final String sql = "insert into pf_se_user(merchant_id, username, password, salt, role_ids, locked,type,display_name,rid,wechat_id,phone,register_date,channel) values(?,?,?,?,?,?,?,?,?,?,?,now(),?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setLong(count++, user.getMerchantId());
                psst.setString(count++, user.getUsername());
                psst.setString(count++, user.getPassword());
                psst.setString(count++, user.getSalt());
                psst.setString(count++, user.getRoleIdsStr());
                psst.setBoolean(count++, user.getLocked());
                psst.setString(count++, user.getType());
                psst.setString(count++, user.getDisplayName());
                psst.setLong(count++, user.getrId());
                psst.setString(count++, user.getWechatId());
                psst.setString(count++,user.getPhone());
                psst.setString(count++,user.getChannel());
                return psst;
            }, keyHolder);
        } catch (DuplicateKeyException e) {
            CwException.throwIt("手机号码或姓名已被使用");
        }
        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    public SeUser updateUser(SeUser user) {
        String sql = "update pf_se_user set merchant_id=?,username=?, password=?, salt=?, role_ids=?, locked=? ,type=? ,display_name=?,rid=?,wechat_id=?,phone=?,channel=? where id=?";
        try {
            jdbcTemplate.update(
                    sql,
                    user.getMerchantId(), user.getUsername(), user.getPassword(), user.getSalt(), user.getRoleIdsStr(), user.getLocked(), user.getType(), user.getDisplayName(), user.getrId(), user.getWechatId(),user.getPhone(),user.getChannel(), user.getId());
        } catch (DuplicateKeyException e) {
            CwException.throwIt("该账号被其他客户使用！");
        }
        return user;
    }

    public void deleteUser(Long userId) {
        String sql = "delete from pf_se_user where id=?";
        jdbcTemplate.update(sql, userId);
    }

    public SeUser findOne(Long userId) {
        String sql = "select id, merchant_id, username, password, salt, role_ids as roleIdsStr, locked,type,display_name ,rid,wechat_id ,phone,channel from pf_se_user where id=?";
        List<SeUser> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SeUser.class), userId);
        if (userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    public List<SeUser> findAll() {
        String sql = "select id, merchant_id, username, password, salt, role_ids as roleIdsStr, locked,type,display_name,rid,wechat_id,phone,channel from pf_se_user";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(SeUser.class));
    }
    public JdbcPage<SeUser> findByPage(ListParamDto dto){

        StringBuilder sb = new StringBuilder(QUERY_SQL);
        sb.append(" where 1=1 and rid!=0 and merchant_id = ").append(dto.getMerchantId());
        if (StringUtils.isNotBlank(dto.getUserType())) {
            sb.append(" and type = '").append(dto.getUserType()).append("'");
        }
        if("1".equals(dto.getShowType())){
            sb.append(" and role_ids like '%2%' and locked=0 ");
        }

        if(StringUtils.isNotBlank(dto.getDisplayName())){
            sb.append(" and display_name like '%"+dto.getDisplayName()+"%' ");
        }

        if(StringUtils.isNotBlank(dto.getPhone())){
            sb.append(" and phone like '%"+dto.getPhone()+"%' ");
        }
        sb.append(" and username != 'admin'");//admin不显示出来
        if(StringUtils.isNotBlank(dto.getNameOrCode())){
            sb.append(" and (username like '%").append(dto.getNameOrCode()).append("%' or display_name like '%").append(dto.getNameOrCode()).append("%')");
        }
        JdbcPage<SeUser> page=new JdbcPage(sb.toString(),dto.getPageNo()+1,dto.getSizePerPage(),jdbcTemplate,SeUser.class);
        return  page;
    }


    public SeUser findByUsernameAndMerchantId(String username, Long merchantId) {
        String sql = "select id, merchant_id, username, password, salt, role_ids as roleIdsStr, locked,type,display_name,rid,wechat_id,phone,channel from pf_se_user where  1=1 and (username=? or phone=?)";
        List<SeUser> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SeUser.class), username,username);
        if (userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    public SeUser findByWechatIdAndMerchantId(String wechatId, Long merchantId) {
        String sql = "select id, merchant_id, username, password, salt, role_ids as roleIdsStr, locked,type,display_name,rid,wechat_id,phone,channel from pf_se_user where merchant_id=? and wechat_id=?";
        List<SeUser> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SeUser.class), merchantId, wechatId);
        if (userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    public List<SeUser> findAllContainRole(Long roleId) {
        String sql = "select id, merchant_id, username, password, salt, role_ids as roleIdsStr, locked,type,display_name,wechat_id ,phone,channel from pf_se_user where role_ids like ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(SeUser.class), "%," + roleId + ",%");
    }

    public void lock(String username, Long merchantId) {
        String sql = "update pf_se_user set locked=? where username=? and merchant_id=?";
        jdbcTemplate.update(
                sql,
                Boolean.TRUE, username, merchantId);
    }

    public void unlock(String username, Long merchantId) {
        String sql = "update pf_se_user set locked=? where username=? and merchant_id=?";
        jdbcTemplate.update(
                sql,
                Boolean.FALSE, username, merchantId);
    }

}
