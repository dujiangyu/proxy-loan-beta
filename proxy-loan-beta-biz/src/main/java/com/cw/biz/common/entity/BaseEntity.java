package com.cw.biz.common.entity;

import com.cw.biz.CPContext;
import com.cw.biz.CwException;
import com.cw.biz.common.dto.Pages;
import com.cw.core.common.ApplicationContextHolder;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zds.common.lang.beans.Copier;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 *
 */
@MappedSuperclass
public class BaseEntity implements Serializable {


    public static final Long NULL_MERCHANT_ID = 1L;

    private static volatile AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = null;
    private static boolean inited = false;

    public BaseEntity() {
        Long tmp = CPContext.getContext().getMerchantId();
        if (tmp != null) {
            this.merchantId = tmp;
            if(this.rawVersion==1&&!StringUtils.isEmpty(CPContext.getContext().getSeUserInfo().getDisplayName())){
                this.rawCreator=CPContext.getContext().getSeUserInfo().getDisplayName();
            }
            if(this.rawVersion>1){
                this.rawModifier=CPContext.getContext().getSeUserInfo().getDisplayName();
            }
        }
        if (inited) {
            autowire();
        }
    }

    /**
     * 注入Entity中的@Autowired
     */
    private void autowire() {
        if (autowiredAnnotationBeanPostProcessor == null) {
            synchronized (AggEntity.class) {
                if (autowiredAnnotationBeanPostProcessor == null) {
                    List<BeanPostProcessor>
                            beanPostProcessors = ((AbstractBeanFactory) (((AbstractApplicationContext) ApplicationContextHolder.get()).getBeanFactory())).getBeanPostProcessors();
                    for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
                        if (beanPostProcessor instanceof AutowiredAnnotationBeanPostProcessor && beanPostProcessor.getClass().getName().contains("AutowiredAnnotationBeanPostProcessor")) {
                            autowiredAnnotationBeanPostProcessor = (AutowiredAnnotationBeanPostProcessor) beanPostProcessor;
                        }
                    }
                }
            }
        }
        autowiredAnnotationBeanPostProcessor.postProcessPropertyValues(null, null, this, getClass().getName());
    }

    public static void inited() {
        inited = true;
    }

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键'")
    protected Long id;

    @Column(name = "merchant_id", columnDefinition = "int(11) NOT NULL DEFAULT '1' comment '商户id'")
    protected Long merchantId = NULL_MERCHANT_ID;

    @Column(name = "raw_add_time", insertable = false, updatable = false,
            columnDefinition = " timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    protected Date rawAddTime = null;

    @Column(name = "raw_update_time", insertable = false, updatable = false,
            columnDefinition = "datetime  COMMENT '修改时间'")
    protected Date rawUpdateTime = new Date();

    @Column(name = "raw_creator", columnDefinition = "varchar(20) not null  comment '创建人'")
    protected String rawCreator = "1";

    @Column(name = "raw_modifier", columnDefinition = "varchar(20)  null  comment '修改人'")
    protected String rawModifier = "1";

    @Version
    @Column(name = "raw_version", columnDefinition = "smallint(6) unsigned NOT NULL DEFAULT '1' COMMENT '版本号'")
    protected Short rawVersion = 1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRawAddTime() {
        return this.rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }

    public Date getRawUpdateTime() {
        return this.rawUpdateTime;
    }

    public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
    }

    public String getRawCreator() {
        return rawCreator;
    }

    public void setRawCreator(String rawCreator) {
        this.rawCreator = rawCreator;
    }

    public String getRawModifier() {
        return rawModifier;
    }

    public void setRawModifier(String rawModifier) {
        this.rawModifier = rawModifier;
    }

    public Short getRawVersion() {
        return rawVersion;
    }

    public void setRawVersion(Short rawVersion) {
        this.rawVersion = rawVersion;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * 从DTO拷贝属性到领域对象
     * <p>
     * 拷贝策略为：1.忽略DTO中的null 2. 忽略DTO属性不兼容
     *
     * @param dto 传输对象
     */
    public void from(Object dto) {
       Copier.copy(dto, this, Copier.CopyStrategy.IGNORE_NULL,Copier.NoMatchingRule.IGNORE);
    }

    /**
     * 转换领域对象为目标类型
     *
     * @param clazz 目标类型
     */
    public <T> T to(Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            Copier.copy(this, t, Copier.CopyStrategy.IGNORE_NULL,Copier.NoMatchingRule.IGNORE);
            return t;
        } catch (Exception e) {
            CwException.throwIt(e);
            return null;
        }
    }

    /**
     * 把实体对象list转换为目标对象List
     */
    public static <T, S extends BaseEntity> List<T> map(List<S> list, Class<T> clazz) {
        if (list == null || list.isEmpty()) {
            return  Lists.newArrayList();
        }
        List<T> ts = new ArrayList<>(list.size());
        for (S s : list) {
            ts.add(s.to(clazz));
        }
        return ts;
    }

    /**
     * 转换Page对象中的集合类型S为T
     */
    public static <S extends BaseEntity, T> Page<T> map(Page<S> page, Class<T> clazz) {
        return Pages.map(page, clazz);
    }

    /**
     * 把实体对象Set转换为目标对象Set
     */
    public static <T, S extends BaseEntity> Set<T> map(Set<S> set, Class<T> clazz) {
        if (set == null || set.isEmpty()) {
            return Sets.newHashSet();
        }
        Set<T> ts = new HashSet<>(set.size());
        for (S s : set) {
            ts.add(s.to(clazz));
        }
        return ts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;

        BaseEntity that = (BaseEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
