package com.example.administrator.designdemo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.designdemo.R;
import com.example.administrator.designdemo.bean.GankEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/31.
 */
public class ImageShowPagerAdapter extends PagerAdapter {


    Context mContext;
    List<View> mDatas;
    List<GankEntity> urls;

    public ImageShowPagerAdapter(Context mContext, List<View> datas,List<GankEntity> urls) {
        this.mContext = mContext;
        this.mDatas = datas;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView iv = (ImageView) mDatas.get(position).findViewById(R.id.iv_image_show);
        Glide.with(mContext).load(urls.get(position).getUrl()).into(iv);
        ((ViewPager) container).addView(mDatas.get(position), 0);
        return mDatas.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(mDatas.get(position));

    }

}

