/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: DesItemData.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-11-11
 *
 */
package net.doyouhike.app.bbs.biz.entity;

public class DesItemData {
	public DesItemData(int type, String title) {
		this.type = type;
		this.name = title;
	}

	public String name;
	public int type; // 0 标签 1 目的地
}