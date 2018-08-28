package com.cw.biz.channel.domain.dao;
/*
 * 描       述:  &lt;描述&gt;
 * 修  改   人:  ${user}
 * 修  改 时 间:  ${date}
 * &lt;修改描述:&gt;
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ChannelSettleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

}
