/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: OnFromFileListener.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-11-07
 *
 * Changes (from 2015-11-07)
 * -----------------------------------------------------------------
 * 2015-11-07 创建OnFromFileListener.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.util.form;

/**
 * 上传图片响应监听
 */
public interface OnFromFileListener {
	public void onSuccess(int code, String msg,
			String dataJsonStr);
	public void onFailure(String errorMessage);
}
