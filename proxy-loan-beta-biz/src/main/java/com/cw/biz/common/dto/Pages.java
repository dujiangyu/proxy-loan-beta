
package com.cw.biz.common.dto;

import com.cw.biz.common.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
/**
 *
 */
public class Pages {

    public static final int DEFAULT_PAGE_SIZE = 30;

    public static PageRequest newPageRequest(Integer index, int sizePerPage, Sort.Direction direction, String... properties) {
        return newPageRequest(index, sizePerPage, new Sort(direction, properties));
    }

    public static PageRequest newPageRequest(Integer index, Sort.Direction direction, String... properties) {
        return newPageRequest(index, DEFAULT_PAGE_SIZE, direction, properties);
    }

    public static PageRequest newPageRequest(Integer index, int sizePerPage, Sort sort) {
        if (index == null) {
            index = 0;
        }
        return new PageRequest(index, sizePerPage, sort);
    }

    public static PageRequest newPageRequest(Integer index, Sort sort) {
        return newPageRequest(index, DEFAULT_PAGE_SIZE, sort);
    }

    public static PageRequest newPageRequest(Integer index, int sizePerPage) {
        return newPageRequest(index, sizePerPage, null);
    }

    public static PageRequest newPageRequest(Integer index) {
        return newPageRequest(index, DEFAULT_PAGE_SIZE);
    }

    public static <S extends BaseEntity, T> Page<T> map(Page<S> page, Class<T> clazz) {
        return page.map(source -> source.to(clazz));
    }
}
