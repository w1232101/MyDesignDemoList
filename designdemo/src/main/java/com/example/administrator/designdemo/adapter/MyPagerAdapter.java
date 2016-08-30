/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.designdemo.fragment.DiscoverFragment;
import com.example.administrator.designdemo.fragment.FanJuFragment;
import com.example.administrator.designdemo.fragment.FenQuFragment;
import com.example.administrator.designdemo.fragment.RecommendedFragment;
import com.example.administrator.designdemo.fragment.ZhiBoFragment;

/**
 * Created by Administrator on 2016/8/24.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private final  String[] titles = {"推荐","番剧","直播","分区","发现"};
    private Fragment[] fragments ;
    Context mContext;
    public MyPagerAdapter(FragmentManager fm,Context context) {

        super(fm);
        fragments = new Fragment[titles.length];
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null)
        {
            switch (position)
            {
                case 0:
                    fragments[position] = RecommendedFragment.newInstance();
                    break;
                case 1:
                    fragments[position] = FanJuFragment.newInstance();
                    break;
                case 2:
                    fragments[position] = ZhiBoFragment.newInstance();
                    break;
                case 3:
                    fragments[position] = FenQuFragment.newInstance();
                    break;
                case 4:
                    fragments[position] = DiscoverFragment.newInstance();
                    break;
                default:
                    break;
            }
        }
        return  fragments[position] ;
    }

    @Override
    public int getCount() {

        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles[position];
    }
}
