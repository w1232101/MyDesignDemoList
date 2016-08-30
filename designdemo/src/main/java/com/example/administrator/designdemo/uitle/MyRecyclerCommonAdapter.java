package com.example.administrator.designdemo.uitle;

import android.content.Context;
import android.view.LayoutInflater;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public abstract class MyRecyclerCommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public MyRecyclerCommonAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                MyRecyclerCommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    public void setDatas(List<T> datas) {
        super.mDatas = datas;
        mDatas = datas;
    }

    protected abstract void convert(ViewHolder holder, T t, int position);

}

