/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.designdemo.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/24.
 */
public class FanJuFragment extends BaseFragment {

    private static FanJuFragment reFragment;
    @Bind(R.id.rv)
    RecyclerView rv;

    public static FanJuFragment newInstance() {
        if (reFragment != null) {
            return reFragment;
        }
        synchronized (FanJuFragment.class) {
            if (reFragment == null) {
                return new FanJuFragment();
            }
        }
        return reFragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_demo;
    }

    List<String> mDatas = new ArrayList<String>();

    @Override
    public void finishCreateView(Bundle state) {
        for (int i = 0; i < 30; i++) {

            mDatas.add("推荐" + i);
        }
//        lv.setAdapter(new CommonAdapter<String>(getActivity(), mDatas, R.layout.demo_item) {
//            @Override
//            public void convert(ViewHolder helper, String item) {
//                helper.setText(R.id.tv_item, item);
//            }
//        });
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.demo_item, mDatas)
        {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_item, s);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
