package com.example.administrator.designdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.designdemo.R;
import com.example.administrator.designdemo.adapter.ImageShowPagerAdapter;
import com.example.administrator.designdemo.bean.GankEntity;
import com.example.administrator.designdemo.bean.HttpResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/30.
 */
public class ImageShowActivity extends BaseActivity {
    @Bind(R.id.vp_image_show)
    ViewPager vp;
    @Bind(R.id.tv_image_show)
    TextView tv;
     List<View> views ;
    private int pos;
    private HttpResult result1;
    private List<GankEntity> datas;

    @Override
    public int getLayoutId() {
        return R.layout.image_show;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            views = new ArrayList<View>();
            pos = intent.getIntExtra("pos", -1);
            HttpResult result1 = (HttpResult) intent.getSerializableExtra("datas");
            datas = result1.getResults();
            for (int i=0;i<datas.size();i++) {
                View view = getLayoutInflater().inflate(R.layout.image_show_item, null);
                views.add(view);
            }
            vp.setAdapter(new ImageShowPagerAdapter(this, views, datas));
            vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    tv.setText((position + 1) + "/" + datas.size());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            if (pos!=-1)
            vp.setCurrentItem(pos);

        }
    }

    @Override
    public void initToolBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
