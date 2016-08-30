/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.administrator.designdemo.R;
import com.example.administrator.designdemo.bean.GankEntity;
import com.example.administrator.designdemo.bean.HttpResult;
import com.example.administrator.designdemo.uitle.DialogUtils;
import com.example.administrator.designdemo.uitle.MyCallBack;
import com.example.administrator.designdemo.uitle.MyOkHttpUtils;
import com.example.administrator.designdemo.uitle.MyRecyclerCommonAdapter;
import com.example.administrator.designdemo.uitle.ScreenUtils;
import com.example.administrator.designdemo.view.MyLoadMoreRecyclerView;
import com.flyco.tablayout.SlidingTabLayout;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/24.
 */
public class RecommendedFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static RecommendedFragment reFragment;
    @Bind(R.id.rv)
    MyLoadMoreRecyclerView rv;
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private SlidingTabLayout sliding_tabs;
    private int screenWidth;
    private MyRecyclerCommonAdapter<GankEntity> adapter;

    public static RecommendedFragment newInstance() {
        if (reFragment != null) {
            return reFragment;
        }
        synchronized (RecommendedFragment.class) {
            if (reFragment == null) {
                return new RecommendedFragment();
            }
        }
        return reFragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_demo;
    }

    List<String> mDatas = new ArrayList<String>();
    private List<GankEntity> entity;
    private int startIndex = 2;
    @Override
    public void finishCreateView(Bundle state) {
        for (int i = 0; i < 30; i++) {

            mDatas.add("推荐" + i);
        }

        screenWidth = ScreenUtils.getScreenWidthPX(getActivity());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(staggeredGridLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setOnLoadDataListener(new MyLoadMoreRecyclerView.LoadData() {

            @Override
            public void onLoadMore() {
                getPicFromNet(startIndex);
            }
        });
        swipeLayout.setColorSchemeResources(R.color.colorPrimary, R.color.swipecolor);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.post(new Runnable() {

            @Override
            public void run() {
                swipeLayout.setRefreshing(true);

            }
        });
        onRefresh();
    }

    private void getPicFromNet(final int i) {
        Log.i("wjx", "i:" + i);
        if (i!=1){

            DialogUtils.showProgressDialog(getActivity(),"努力加载ing...");
        }
        MyOkHttpUtils.getInstance(getActivity(), new MyCallBack() {

            @Override
            public void httpCallBackSucc(int tag, Object src) {
                HttpResult result = (HttpResult) src;
                if (adapter != null && entity.size() > 0) {

                    if (swipeLayout.isRefreshing()) {
                        swipeLayout.setRefreshing(false);
                    }
                    if (i == 1) {
                        startIndex=2;
                        entity.clear();
                        entity = result.getResults();
                        adapter.setDatas(entity);
                        DialogUtils.closeProgressDialog();
                        adapter.notifyDataSetChanged();
                        return;
                    }
                    if (result.getResults().size() == 0) {
                        DialogUtils.closeProgressDialog();
                        DialogUtils.showToast(getActivity(), "没有更多数据了！", 0);
                        return;
                    }
                    entity.addAll(result.getResults());
                    startIndex++;
                    adapter.setDatas(entity);
                    DialogUtils.closeProgressDialog();
                    adapter.notifyDataSetChanged();
                    return;
                }
                entity = result.getResults();
                adapter = new MyRecyclerCommonAdapter<GankEntity>(getActivity(), R.layout.item_welfare_staggered, entity) {
                    @Override
                    protected void convert(final ViewHolder holder, GankEntity s, int position) {
                        ((TextView) holder.getView(R.id.tvShowTime)).setText(s.getPublishedAt().substring(0, s.getPublishedAt().indexOf("T")));
                        Glide.with(getActivity())
                                .load(s.getUrl())
                                .asBitmap()
                                .placeholder(R.drawable.pic_gray_bg)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(new SimpleTarget<Bitmap>(screenWidth / 2, screenWidth / 2) {
                                          @Override
                                          public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                              int width = resource.getWidth();
                                              int height = resource.getHeight();
                                              //计算高宽比
                                              int finalHeight = (screenWidth / 2) * height / width;
                                              ViewGroup.LayoutParams layoutParams = holder.getView(R.id.rl_root).getLayoutParams();
                                              layoutParams.height = finalHeight;
                                              ((ImageView) holder.getView(R.id.image)).setImageBitmap(resource);
                                          }
                                      }

                                );

                    }
                };
                if (swipeLayout.isRefreshing()) {
                    swipeLayout.setRefreshing(false);
                }
                rv.setAdapter(adapter);
            }

            @Override
            public void httpCallBackErr(int tag, Object src) {
                if (swipeLayout.isRefreshing()) {
                    swipeLayout.setRefreshing(false);
                }
                DialogUtils.closeProgressDialog();
            }
        }).getHttp("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/" + i, "MainActivity", HttpResult.class);
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

    @Override
    public void onRefresh() {
        getPicFromNet(1);
    }
}
