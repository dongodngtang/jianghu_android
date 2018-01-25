/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: PageFragmentAdapter.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-9-25
 *
 * Changes (from 2015-9-25)
 * -----------------------------------------------------------------
 * 2015-9-25 创建MainFragmentAdapter.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 * 
 * Changes (from 2015-10-2)
 * -----------------------------------------------------------------
 * 2015-10-2 改名为PageFragmentAdapter (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.adapter.live;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class PageFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> listFragment;

    public PageFragmentAdapter(FragmentManager fragmentManager,
                               List<Fragment> listFragment) {
        super(fragmentManager);
        this.listFragment = listFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if (listFragment != null && position < listFragment.size()) {
            return listFragment.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (listFragment != null && listFragment.size() > 0) {
            return listFragment.size();
        }
        return 0;
    }


}
