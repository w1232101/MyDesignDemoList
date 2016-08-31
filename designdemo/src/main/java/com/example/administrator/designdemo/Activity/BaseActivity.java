package com.example.administrator.designdemo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.designdemo.uitle.StatusBarCompat;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/24.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        //设置布局内容
        setContentView(getLayoutId());
        //初始化黄油刀控件绑定框架
        ButterKnife.bind(this);
        //适配4.4系统状态栏
        StatusBarCompat.compat(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
        //设置全局状态栏颜色
        StatusBarCompat.compat(this);
    }

    @Override
    protected void onDestroy()
    {

        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void initToolBar();

}
