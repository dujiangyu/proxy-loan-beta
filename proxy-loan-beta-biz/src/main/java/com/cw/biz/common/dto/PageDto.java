package com.cw.biz.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.CriteriaBuilder;

/**
 *
 */
public class PageDto extends BaseDto {

	/**
	 * 页码，从0开始
	 */
	@JSONField(serialize = false)
	private Integer pageNo;

	@JSONField(serialize = false)
	private int sizePerPage = Pages.DEFAULT_PAGE_SIZE;

	@JSONField(serialize = false)
	private Sort.Direction sortDirection = Sort.Direction.ASC;

	@JSONField(serialize = false)
	private String[] sortFields;

	private String sort;

	private String order;

	private Integer pageNumber=1;

	private Boolean isPager=Boolean.FALSE;

	public PageRequest toPage() {
		if (sortFields == null) {
			return Pages.newPageRequest(pageNo, sizePerPage);
		} else {
			return Pages.newPageRequest(pageNo, sizePerPage, sortDirection, sortFields);
		}
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo - 1;
	}

	public int getSizePerPage() {
		return sizePerPage;
	}

	public void setSizePerPage(int sizePerPage) {
		this.sizePerPage = sizePerPage;
	}

	public Sort.Direction getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(Sort.Direction sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String[] getSortFields() {
		return sortFields;
	}

	public void setSortFields(String[] sortFields) {
		this.sortFields = sortFields;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Boolean getIsPager() {
		return isPager;
	}

	public void setIsPager(Boolean pager) {
		isPager = pager;
	}
}
