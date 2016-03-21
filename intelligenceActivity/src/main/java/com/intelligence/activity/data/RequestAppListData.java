package com.intelligence.activity.data;

import java.io.Serializable;
import java.util.List;

public class RequestAppListData {
	private String total; 
	private String page;
	private String pagesize;
	private List<Machine> data;
	
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPagesize() {
		return pagesize;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	public List<Machine> getData() {
		return data;
	}

	public void setData(List<Machine> data) {
		this.data = data;
	}

}
