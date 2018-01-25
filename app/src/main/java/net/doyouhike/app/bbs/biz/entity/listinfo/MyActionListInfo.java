/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: MyActionListInfo.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-11-03
 *
 * Changes (from 2015-11-03)
 * -----------------------------------------------------------------
 * 2015-11-03 创建MyActionListInfo.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.biz.entity.listinfo;

import net.doyouhike.app.bbs.biz.entity.MyActionInfo;

import java.util.ArrayList;
import java.util.List;

public class MyActionListInfo {
	
	private List<MyActionInfo> myActions = new ArrayList<MyActionInfo>();

	public List<MyActionInfo> getMyActions() {
		return myActions;
	}

	public void setMyActions(List<MyActionInfo> myActions) {
		this.myActions = myActions;
	}
	
}
