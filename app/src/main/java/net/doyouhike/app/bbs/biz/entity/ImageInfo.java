/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ImageInfo.java
 * Author: wu-yoline(伍建鹏)
 * Version: 1.0
 * Create: 2015-10-10
 *
 * Changes (from 2015-10-10)
 * -----------------------------------------------------------------
 * 2015-10-10 : 创建 本类 ，添加三个属性(wu-yoline);
 * -----------------------------------------------------------------
 * 2015-10-29 : 根据http接口的变化，添加多个个属性(wu-yoline);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.biz.entity;

import net.doyouhike.app.bbs.util.StrUtils;

/**
 * 图片信息实体类
 * 
 * @author wu-yoline
 *
 */
public class ImageInfo {

	private String real_file;
	private String small_file;
	private String middle_file;
	private String desc;
	private String PhotoID;
	private String Path;
	private String Ext;
	private String height = "0";
	private String width = "0";

	/**
	 * 图片信息实体类
	 */
	public ImageInfo() {
	}

	/**
	 * 图片信息实体类
	 * 
	 * @param realFile
	 *            原图片url地址
	 * @param smallFile
	 *            小图片url地址
	 * @param desc
	 *            图片描述
	 */
	public ImageInfo(String realFile, String smallFile, String desc) {
		this.real_file = realFile;
		this.small_file = smallFile;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getReal_file() {
		return real_file;
	}

	public void setReal_file(String real_file) {
		this.real_file = real_file;
	}

	public String getSmall_file() {
		return small_file;
	}

	public void setSmall_file(String small_file) {
		this.small_file = small_file;
	}

	public String getPhotoID() {
		return PhotoID;
	}

	public void setPhotoID(String photoID) {
		PhotoID = photoID;
	}

	public String getPath() {
		return Path;
	}

	public void setPath(String path) {
		Path = path;
	}

	public String getExt() {
		return Ext;
	}

	public void setExt(String ext) {
		Ext = ext;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getMiddle_file() {
		if (middle_file == null) {
			middle_file = getMiddleUrl(getReal_file());
		}
		return middle_file;
	}

	public void setMiddle_file(String middle_file) {
		this.middle_file = middle_file;
	}

	private String getMiddleUrl(String real) {
		if (StrUtils.hasContent(real)) {
			int index = real.lastIndexOf('.');
			return real.substring(0, index) + "_m"
					+ real.substring(index, real.length());
		} else {
			return real;
		}
	}
	
	public float getImgHeight(){
		float imgHeight = 0;
		try {
			imgHeight = Float.parseFloat(height);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return imgHeight;
	}
	
	public float getImgWidth(){
		float imgWidth = 0;
		try {
			imgWidth = Float.parseFloat(width);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return imgWidth;
	}

}
