
package com.cw.biz.common.dto;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 通过实体name或者code做前缀查询
 *
 *
 */
public final class ListParamDto extends PageDto {

    private String nameOrCode;

    private Long merchantId;

    private Boolean rawIsValid;

    private String userType;

    private String showType;

    private String phone;

    private String displayName;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getNameOrCode() {
        return nameOrCode;
    }

    public void setNameOrCode(String nameOrCode) {
        this.nameOrCode = nameOrCode;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Boolean getRawIsValid() {
        return rawIsValid;
    }

    public void setRawIsValid(Boolean rawIsValid) {
        this.rawIsValid = rawIsValid;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public Specification<?> createSpecification() {
        if (StringUtils.isBlank(nameOrCode) && Objects.isNull(merchantId) && Objects.isNull(rawIsValid)) {
            return null;
        }

        Specification<?> typeSpecification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//Lists.newArrayListWithCapacity(4);
            String nameOrCode = this.getNameOrCode();
            if (StringUtils.isNotBlank(nameOrCode)) {
                Path namePath = null;
                Path codePath = null;
                try {
                    namePath = root.get("name");
                } catch (Exception ignored) {
                }
                try {
                    codePath = root.get("code");
                } catch (Exception ignored) {
                }
                if (namePath != null && codePath != null) {
                    predicates.add(cb.or(cb.like(namePath, "%" + nameOrCode + "%"), cb.like(codePath, "%" + nameOrCode + "%")));
                } else if (codePath != null) {
                    predicates.add(cb.like(codePath, "%" + nameOrCode + "%"));
                } else if (namePath != null) {
                    predicates.add(cb.like(namePath, "%" + nameOrCode + "%"));
                }
            }
            if (!Objects.isNull(this.getRawIsValid())) {
                Path rawIsValidPath = null;
                try {
                    rawIsValidPath = root.get("rawIsValid");
                } catch (Exception ignored) {
                }
                if (rawIsValidPath != null) {
                    predicates.add(cb.equal(rawIsValidPath, this.getRawIsValid()));
                }
            }
            if (!Objects.isNull(this.getMerchantId())) {
                predicates.add(cb.equal(root.get("merchantId"), this.getMerchantId()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return typeSpecification;
    }
}
