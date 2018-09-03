
package com.cw.biz.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@Data
public class BaseDto implements Serializable {

    private Long id;

    /**商户id**/
    protected Long merchantId;

    /**创建时间**/
    protected Date rawAddTime;

    /**修改时间**/
    protected Date rawUpdateTime;

    /**创建人**/
    protected String rawCreator;

    /**修改人**/
    protected String rawModifier;

}