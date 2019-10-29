package com.hj.domain;

import java.util.List;

public class PageDetails<T> {

	private List<T> details;
	// 当前页
	private int currentPage;
	// 当前页显示的条数
	private int currentCount;
	// 总条数
	private int totalCount;
	// 总页数
	private int totalPage;

	public List<T> getDetails() {
		return details;
	}

	public void setDetails(List<T> details) {
		this.details = details;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}
