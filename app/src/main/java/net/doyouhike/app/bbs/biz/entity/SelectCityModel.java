/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: SelectCityModel.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-10-23
 *
 */
package net.doyouhike.app.bbs.biz.entity;

import java.io.Serializable;

/**
 * @author ZouWenJie
 * 选择城市界面的数据实体类
 */
public class SelectCityModel implements Serializable {
	private String name; // 显示的数据
	private String sortLetters; // 显示数据拼音的首字母
    private String city_id;//城市的ID号
	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
