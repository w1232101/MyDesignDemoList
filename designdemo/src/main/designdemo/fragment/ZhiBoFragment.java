/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.designdemo.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/24.
 */
public class ZhiBoFragment extends BaseFragment {

    private static ZhiBoFragment reFragment;

    public static ZhiBoFragment newInstance() {
        if (reFragment != null) {
            return reFragment;
        }
        synchronized (ZhiBoFragment.class) {
            if (reFragment == null) {
                return new ZhiBoFragment();
            }
        }
        return reFragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_demo;
    }

    @Override
    public void finishCreateView(Bundle state) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
