/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.Activity;

import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.administrator.designdemo.R;
import com.example.administrator.designdemo.adapter.MyPagerAdapter;
import com.example.administrator.designdemo.uitle.DialogUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyw.myapplication.Constant;
import com.gyw.myapplication.RollHeaderView;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Bind(R.id.abl)
    public AppBarLayout abl;
    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    //    @Bind(R.id.fab)
//    FloatingActionButton fab;
    @Bind(R.id.sliding_tabs)
    public  SlidingTabLayout slidingTabs;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.coor)
    CoordinatorLayout coor;
    @Bind(R.id.rollHeadr)
    RollHeaderView rollHeadr;
    private ActionBarDrawerToggle mDrawerToggle;
    private int index = 0;
    private long exitTime = 0;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

//    private Snackbar snackbar;

    @Override
    public void initViews(Bundle savedInstanceState) {
//        fab.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                if (snackbar != null && snackbar.isShown()) {
//                    snackbar.dismiss();
//                    return;
//                }
//                snackbar = Snackbar.make(view, "测试弹出提示", Snackbar.LENGTH_LONG);
//                snackbar.show();
//                snackbar.setAction("取消", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        snackbar.dismiss();
//                    }
//                });
//            }
//        });
        List<String> imgUrlList = Arrays.asList(Constant.imgUrls);

        rollHeadr.setImgUrlData(imgUrlList);
        rollHeadr.setOnHeaderViewClickListener(new RollHeaderView.HeaderViewClickListener() {
            @Override
            public void HeaderViewClick(int position) {
                Toast.makeText(MainActivity.this, "点击 : " + position, Toast.LENGTH_SHORT).show();
            }
        });

//        ll.setOnScrollChangeListener(new LinearLayout.OnScrollChangeListener() {
//
//            @Override
//            public void onScrollChange(View view, int x, int y, int i2, int i3) {
//                if (y >=0 && y <= 255) {
//                    toolbar.setBackgroundColor(Color.argb(y, 0xFA, 0x71, 0x98));
//                }
//            }
//        });

        disableNavigationViewScrollbars(navigationView);
        drawerLayout.addDrawerListener(new DrawerListener());

        navigationView.setNavigationItemSelectedListener(this);

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), this));
        viewPager.setOffscreenPageLimit(4);
        slidingTabs.setViewPager(viewPager);
//      slidingTabs2.setViewPager(viewPager);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
            drawerLayout.closeDrawer(GravityCompat.START);
            switch (item.getItemId())
            {
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
    public void setMenuShow(boolean isShow)
    {
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
