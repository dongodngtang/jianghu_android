/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: PostInfo.java
 * Author: wu-yoline(伍建鹏)
 * Version: 1.0
 * Create: 2015-10-2
 *
 * Changes (from 2015-10-2)
 * -----------------------------------------------------------------
 * 2015-10-2 : 创建 本类 ，添加mTitle和mContent属性及其get、set方法(wu-yoline);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.biz.entity;

/**
 * 贴子信息实体类
 * 
 * @author wu-yoline
 *
 */
public class PostInfo {

	private String title;
	private String content;
	private String icon;
	private String url;

	/**
	 * 贴子信息实体类
	 */
	public PostInfo() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
