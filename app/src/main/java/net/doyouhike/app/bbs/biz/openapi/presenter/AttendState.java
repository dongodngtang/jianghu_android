/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: AttendState.java
 * Author: wu-yoline(伍建鹏)
 * Version: 1.0
 * Create: 2015-9-25
 *
 * Changes (from 2015-11-03)
 * -----------------------------------------------------------------
 * 2015-9-25 : 创建 AttendState.java (wu-yoline)，添加三个常量ATTENDING、NOT_ATTEND、ATTENTION_EACH_OTHER;(wu-yoline)
 * -----------------------------------------------------------------
 * 2015-9-25 : 添加三个常量ATTENDING、NOT_ATTEND、ATTENTION_EACH_OTHER的注释;(wu-yoline)
 * -----------------------------------------------------------------
 * 2015-11-03 : 添加三个常量ATTENTION_TO_INVITE的注释;(wu-yoline)
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.biz.openapi.presenter;

import net.doyouhike.app.bbs.util.StringUtil;

/**
 * 记录关注状态的几个常量
 * 
 * @author wu-yoline
 * 
 */
public class AttendState {

	/**
	 * 正在关注
	 */
	public final static int ATTENDING = 1;
	/**
	 * 没有关注
	 */
	public final static int NOT_ATTEND = 0;
	/**
	 * 互相关注
	 */
	public final static int ATTENTION_EACH_OTHER = 2;

	/**
	 * 邀请
	 */
	public final static int ATTENTION_TO_INVITE = 3;

	//  # follow/已关注   not_follow/未关注   follow_each/相互关注

	public final static String SOCIAL_FOLLOW = "follow";

	public final static String SOCIAL_NOT_FOLLOW = "not_follow";

	public final static String SOCIAL_FOLLOW_EACH = "follow_each";


}
