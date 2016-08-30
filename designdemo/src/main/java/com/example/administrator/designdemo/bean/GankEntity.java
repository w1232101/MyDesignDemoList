/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.bean;

import java.io.Serializable;

/**
 * Created by maning on 16/6/2.
 */
public class GankEntity implements Serializable{

    private static final long serialVersionUID = -2600330151554512031L;

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getPublishedAt() {
		return publishedAt;
	}
	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	@Override
	public String toString() {
		return "GankEntity [_id=" + _id + ", createdAt=" + createdAt + ", desc=" + desc + ", publishedAt=" + publishedAt
				+ ", source=" + source + ", type=" + type + ", url=" + url + ", used=" + used + ", who=" + who + "]";
	}


}
