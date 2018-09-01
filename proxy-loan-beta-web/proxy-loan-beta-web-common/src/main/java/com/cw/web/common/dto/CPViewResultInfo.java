package com.cw.web.common.dto;

import com.alibaba.fastjson.JSON;
import com.zds.common.lang.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * Created by dujy on 2017-05-21.
 */
@Slf4j
public class CPViewResultInfo implements Serializable{

    private Boolean success =Boolean.TRUE;
    private String code = "000000";
    private String message;
    private Object data;
    private Long total;
    private Object rows;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public String convert(Object object)
    {
        CPViewResultInfo message = new CPViewResultInfo();
        message.setData(object);
        message.setSuccess(Boolean.TRUE);
        message.setMessage("成功");
        return JSON.toJSONString(message);
    }
    public void newSuccess(Object data){
        this.data=data;
        this.success=true;
        this.message="成功";
    }

    public void newFalse(Exception e,String falseCode){
        this.success=false;
        this.code=falseCode;
        this.message=e.getMessage();
        log.error(e.getMessage(),e);
    }
    public void newFalse(Exception e){
        if(e.getClass().getName().equalsIgnoreCase(BusinessException.class.getName()))this.code=((BusinessException) e).getCode();
        this.success=false;
        this.message=e.getMessage();
        log.error(e.getMessage(),e);
    }

}
