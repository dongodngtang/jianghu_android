/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: PinyinComparator.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-10-23
 *
 */
package net.doyouhike.app.bbs.util;


import net.doyouhike.app.bbs.biz.entity.SelectCityModel;

import java.util.Comparator;

/**
 * @author ZouWenJie
 * 拼音比较器类
 */
public class PinyinComparator implements Comparator<SelectCityModel> {
	public int compare(SelectCityModel o1, SelectCityModel o2) {
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
