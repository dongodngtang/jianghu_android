/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: EventCatsInfo.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-11-4
 *
 */
package net.doyouhike.app.bbs.biz.entity;

import java.util.List;

public class EventCatsInfo {
	public String id;
	public String title;
	public List<CatsData> cats;
	public class CatsData{
		public String cat_id;
		public String cat_name;
		public String fid;
	}
}
