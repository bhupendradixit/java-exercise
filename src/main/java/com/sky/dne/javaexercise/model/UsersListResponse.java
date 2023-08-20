package com.sky.dne.javaexercise.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsersListResponse {

	private Integer page;

	@JsonProperty("per_page")
	private Integer perPage;

	@JsonProperty("totalrecord")
	private Integer totalRecord;

	@JsonProperty("total_pages")
	private Integer totalPages;

	List<UserDetails> data = new ArrayList<>();

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPerPage() {
		return perPage;
	}

	public void setPerPage(Integer perPage) {
		this.perPage = perPage;
	}

	public Integer getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public List<UserDetails> getData() {
		return data;
	}

	public void setData(List<UserDetails> data) {
		this.data = data;
	}

}
