/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: TagInfo.java
 * Author: 伍建鹏(wu-yoline)
 * Version: 1.0
 * Create: 2015-10-21
 *
 * Changes (from 2015-10-21)
 * -----------------------------------------------------------------
 * 2015-10-21 : 1、创建本类，添加id和desc属性(wu-yoline)
 * -----------------------------------------------------------------
 * 2015-11-06 : 1、重写equals方法;(wu-yoline)
 * 				2、添加构造函数;(wu-yoline)
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.biz.entity;


/**
 * 标签的实体类
 * 
 * @author wu-yoline
 *
 */
public class TagInfo {
	private String id;
	private String desc;

	public TagInfo() {
	}

	public TagInfo(String id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TagInfo other = (TagInfo) obj;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
