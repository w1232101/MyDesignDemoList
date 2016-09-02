/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.administrator.designdemo.R;
import com.example.administrator.designdemo.adapter.MyPagerAdapter;
import com.example.administrator.designdemo.receiver.SkinBroadCastReceiver;
import com.example.administrator.designdemo.uitle.ActivityCollector;
import com.example.administrator.designdemo.uitle.DialogUtils;
import com.example.administrator.designdemo.uitle.MyThemeManager;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyw.myapplication.Constant;
import com.gyw.myapplication.RollHeaderView;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static final String MY_SKIN_ACTION = "MY_SKIN_ACTION";
    public static final String SKIN_KEY = "SkinKey";
    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.sliding_tabs)
    public SlidingTabLayout slidingTabs;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.rollHeadr)
    RollHeaderView rollHeadr;
    private ActionBarDrawerToggle mDrawerToggle;
    private int index = 0;
    private long exitTime = 0;
    private List<String> linkUrl;
    private SkinBroadCastReceiver skinReceiver;

    public boolean isDay = true;
    private SharedPreferences sp;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        sp = this.getSharedPreferences("config", Context.MODE_PRIVATE);
        int theme = sp.getInt(SKIN_KEY,0);
        View view = navigationView.getHeaderView(0).findViewById(R.id.iv_head_switch_mode);
        ((ImageView)view).setImageResource(theme==1?R.drawable.ic_switch_daily:R.drawable.ic_switch_night);
        List<String> imgUrlList = Arrays.asList(Constant.imgUrls);
        linkUrl = Arrays.asList(Constant.linkUrls);

        rollHeadr.setImgUrlData(imgUrlList);
        rollHeadr.setOnHeaderViewClickListener(new RollHeaderView.HeaderViewClickListener() {
            @Override
            public void HeaderViewClick(int position) {

                BrowserActivity.launch(MainActivity.this, linkUrl.get(position), " ");

            }
        });


        //去掉Navigation的scrollbar
        disableNavigationViewScrollbars(navigationView);
        drawerLayout.addDrawerListener(new DrawerListener());

        navigationView.setNavigationItemSelectedListener(this);

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), this));
        viewPager.setOffscreenPageLimit(4);
        slidingTabs.setViewPager(viewPager);

        //注册换肤的广播接收者
        registerBroadReceiver();
        navigationView.getHeaderView(0).findViewById(R.id.iv_head_switch_mode).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int theme = sp.getInt(SKIN_KEY,0);
                MyThemeManager.changeSkin(MainActivity.this,theme==0?1:0);
            }
        });
    }

    private void registerBroadReceiver() {
        if (skinReceiver == null) {
            skinReceiver = new SkinBroadCastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    int stringExtra = intent.getIntExtra("MY_SKIN_ACTION",0);

                    Log.i("wjx","theme:"+stringExtra);
                    sp.edit().putInt(SKIN_KEY,stringExtra).apply();
                    for (int i = 0; i < ActivityCollector.activities.size(); i++) {

                        ActivityCollector.activities.get(i).recreate();
                    }
                }
            };
            IntentFilter filter = new IntentFilter();
            filter.addAction(MY_SKIN_ACTION);
            registerReceiver(skinReceiver, filter);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.item_home:
                // 主页
                index = 0;
//                    setShowingFragment(fragments[0]);
                item.setChecked(true);
//                    mToolbar.setLogo(R.drawable.ic_bili_logo_white);
//                    mToolbar.setTitle("");
                setMenuShow(false);
                return true;

            case R.id.item_download:
                // 离线缓存
                item.setChecked(true);
//                    startActivity(new Intent(MainActivity.this, OffLineDownloadActivity.class));
                return true;

            case R.id.item_favourite:
                // 我的收藏
                index = 2;
//                    setShowingFragment(fragments[2]);
                item.setChecked(true);
//                    mToolbar.setTitle("我的收藏");
//                    mToolbar.setLogo(null);
//                    setMenuShow(true);
                return true;

            case R.id.item_history:
                // 历史记录
                index = 3;
//                    setShowingFragment(fragments[3]);
                item.setChecked(true);
//                    mToolbar.setTitle("历史记录");
//                    mToolbar.setLogo(null);
//                    setMenuShow(true);
                return true;

            case R.id.item_group:
                // 关注的人
                index = 4;
//                    setShowingFragment(fragments[4]);
                item.setChecked(true);
//                    mToolbar.setTitle("关注的人");
//                    mToolbar.setLogo(null);
//                    setMenuShow(true);
                return true;

            case R.id.item_tracker:
                // 消费记录
                index = 5;
//                    setShowingFragment(fragments[5]);
                item.setChecked(true);
//                    mToolbar.setTitle("消费记录");
//                    mToolbar.setLogo(null);
//                    setMenuShow(true);
                return true;

            case R.id.item_theme:
                // 主题选择

                return true;

            case R.id.item_app:
                // 应用推荐

                return true;

            case R.id.item_settings:
                // 设置中心
                index = 1;
//                    setShowingFragment(fragments[1]);
                item.setChecked(true);
//                    mToolbar.setTitle("设置与帮助");
//                    mToolbar.setLogo(null);
//                    setMenuShow(true);
                return true;
        }

        return false;
    }

    private class DrawerListener implements DrawerLayout.DrawerListener {

        @Override
        public void onDrawerOpened(View drawerView) {

            if (mDrawerToggle != null) {
                mDrawerToggle.onDrawerOpened(drawerView);
            }
        }

        @Override
        public void onDrawerClosed(View drawerView) {

            if (mDrawerToggle != null) {
                mDrawerToggle.onDrawerClosed(drawerView);
            }
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {

            if (mDrawerToggle != null) {
                mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
            }
        }

        @Override
        public void onDrawerStateChanged(int newState) {

            if (mDrawerToggle != null) {
                mDrawerToggle.onDrawerStateChanged(newState);
            }
        }
    }

    @Override
    public void initToolBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        这句是什么意思呢？
//        允许窗口扩展到屏幕之外。 但这样会把ToolBar顶到最上面去，这时候再给ToolBar设置一个MarginTop就好了。
//        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
//        param.setMargins(0, ScreenUtils.getStatusHeight(this), 0, 0);
//        toolbar.setLayoutParams(param);

        toolbar.setLogo(R.drawable.ic_bili_logo);
        setSupportActionBar(toolbar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayUseLogoEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.open, R.string.close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    /**
     * flase 显示 true不显示
     *
     * @param isShow
     */
    public void setMenuShow(boolean isShow) {
        //切换fragment时改变menu的显示
//        isShowMenu = isShow;
        getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                DialogUtils.showToast(this, "再按一次退出程序", 0);
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
                // finish();
                // ActivityCollector.finishAll();
                // System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
