/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: EventTypesInfo.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-11-4
 *
 */
package net.doyouhike.app.bbs.biz.entity;

/**
 * @author ZouWenJie
 * 活动的类型数据封装类
 */
public class EventTypesInfo {
	private String id;
	private String sort_num;
	private String type_name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSort_num() {
		return sort_num;
	}
	public void setSort_num(String sort_num) {
		this.sort_num = sort_num;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	
}
