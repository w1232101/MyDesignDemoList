package com.example.administrator.designdemo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.designdemo.uitle.ActivityCollector;
import com.example.administrator.designdemo.uitle.MyThemeManager;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/24.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        //设置主题 皮肤
        MyThemeManager.onActivityCreateSetSkin(this);

        super.onCreate(savedInstanceState);
        //设置布局内容

        setContentView(getLayoutId());
        //为换肤做准备！！！
        ActivityCollector.addActivity(this);
        //初始化黄油刀控件绑定框架
        ButterKnife.bind(this);
        //适配4.4系统状态栏 4.4要在toolbar之前设置状态栏 5.0及以后都是在toolbar后设置
        setStatusBarColor();
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
        //设置全局状态栏颜色
        setStatusBarColor();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        ButterKnife.unbind(this);
    }

    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void initToolBar();
    public abstract void setStatusBarColor();

}
