/*
* -----------------------------------------------------------------
* Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
* -----------------------------------------------------------------
*
* File: GoToHotEvent.java
* Author: ChenWeiZhen
* Version: 1.0
* Create: 2015-11-17
*
* Changes (from 2015-11-17)
* -----------------------------------------------------------------
* 2015-11-17 创建GoToHotEvent.java (ChenWeiZhen);
* -----------------------------------------------------------------
*/
package net.doyouhike.app.bbs.biz.event;

/**
 * 登出时通过EventBus发送这个，让MainActivity显示热门
 */
public class GoToLiveListEvent {
	
	private boolean isToHot = true;

	public boolean isToHot() {
		return isToHot;
	}

	public void setToHot(boolean isToHot) {
		this.isToHot = isToHot;
	}
	
}
