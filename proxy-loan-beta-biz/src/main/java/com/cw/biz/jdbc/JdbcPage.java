package com.cw.biz.jdbc;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yy at 2016/11/21 14:25
 */
public class JdbcPage<T> implements Serializable{

    //一页显示的记录数
    private int pageSize;
    //记录总数
    private int totalCount;
    //总页数
    private int totalPageCount;
    //当前页码
    private int currentPageNo;
    //起始行数
    @JSONField(serialize = false)
    private int startIndex;
    //结束行数
     @JSONField(serialize = false)
    private int lastIndex;

    private  List<T> result;

    private Object extra;//扩展参数

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    /**
     * 分页构造函数
     * @param sql         包含筛选条件的sql，但不包含分页相关约束，如mysql的limit
     * @param currentPageNo 当前页
     * @param pageSize  每页记录数
     * @param jTemplate   JdbcTemplate实例
     * @param tClass 实体class类
     */
    public JdbcPage(String sql, int currentPageNo, int pageSize, JdbcTemplate jdbcTemplate, Class<T> tClass) {
        if (jdbcTemplate == null) {
            throw new IllegalArgumentException("Page.jTemplate is null");
        } else if (sql == null || sql.equals("")) {
            throw new IllegalArgumentException("Page.sql is empty");
        }
        //设置每页显示记录数
        setPageSize(pageSize);
        //设置要显示的页数
        setCurrentPageNo(currentPageNo);
        //计算总记录数
        StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
        totalSQL.append(sql);
        totalSQL.append(" ) totalTable ");
        //总记录数
        setTotalCount(jdbcTemplate.queryForObject(totalSQL.toString(), Integer.class));
        //计算总页数
        setTotalPageCount();
        //计算起始行数
        setStartIndex();
        //计算结束行数
        setLastIndex();
        StringBuffer paginationSQL = new StringBuffer();
        paginationSQL.append(sql);
        paginationSQL.append(" limit " + startIndex + "," + "  " + pageSize);
        //装入结果集
        setResult(jdbcTemplate.query(paginationSQL.toString(), new BeanPropertyRowMapper(tClass)));
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }
    public int getTotalPageCount() {
        return totalPageCount;
    }

    //计算总页数
    public void setTotalPageCount() {
        if (totalCount % pageSize == 0) {
            this.totalPageCount = totalCount / pageSize;
        } else {
            this.totalPageCount = (totalCount / pageSize) + 1;
        }
    }


    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex() {
        this.startIndex = (currentPageNo - 1) * pageSize;
    }

    public int getLastIndex() {
        return lastIndex;
    }


    //计算结束时候的索引
    public void setLastIndex() {
        if (totalCount < pageSize) {
            this.lastIndex = totalCount;
        } else if ((totalCount % pageSize == 0) || (totalCount % pageSize != 0 && currentPageNo < totalPageCount)) {
            this.lastIndex = currentPageNo * pageSize;
        } else if (totalCount % pageSize != 0 && currentPageNo == totalPageCount) {//最后一页
            this.lastIndex = totalCount;
        }
    }
}