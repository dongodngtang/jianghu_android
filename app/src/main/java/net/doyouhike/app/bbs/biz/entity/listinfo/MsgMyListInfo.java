/*
* -----------------------------------------------------------------
* Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
* -----------------------------------------------------------------
*
* File: MsgMyListInfo.java
* Author: ChenWeiZhen
* Version: 1.0
* Create: 2015-11-05
*
* Changes (from 2015-11-05)
* -----------------------------------------------------------------
* 2015-11-05 创建MsgMyListInfo.java (ChenWeiZhen);
* -----------------------------------------------------------------
*/
package net.doyouhike.app.bbs.biz.entity.listinfo;

import java.util.List;

public abstract class MsgMyListInfo<T1> {

	protected List<T1> msgBean;
	protected int numNoRead=0; // "numNoRead": 27 未读的数量

	public List<T1> getMsgBean() {
		return msgBean;
	}

	public void setMsgBean(List<T1> msgBean) {
		this.msgBean = msgBean;
	}

	public int getNumNoRead() {
		return numNoRead;
	}

	public void setNumNoRead(int numNoRead) {
		this.numNoRead = numNoRead;
	}
}
