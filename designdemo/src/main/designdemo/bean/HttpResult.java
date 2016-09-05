/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Created by maning on 16/6/2.
 * 接口返回总Bean
 */
public class HttpResult implements Serializable{

    private static final long serialVersionUID = 2369780845535121572L;
    private boolean error;
    private List<GankEntity> results;
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public List<GankEntity> getResults() {
		return results;
	}
	public void setResults(List<GankEntity> results) {
		this.results = results;
	}
	@Override
	public String toString() {
		return "HttpResult [error=" + error + ", results=" + results + "]";
	}

}
