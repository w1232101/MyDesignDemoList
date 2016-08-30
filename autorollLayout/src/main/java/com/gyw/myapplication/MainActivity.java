package com.gyw.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> imgUrlList = Arrays.asList(Constant.imgUrls);

        RollHeaderView rollHeaderView = new RollHeaderView(this);
        rollHeaderView.setImgUrlData(imgUrlList);
        rollHeaderView.setOnHeaderViewClickListener(new RollHeaderView.HeaderViewClickListener() {
            @Override
            public void HeaderViewClick(int position) {
                Toast.makeText(MainActivity.this, "点击 : " + position, Toast.LENGTH_SHORT).show();
            }
        });


        setContentView(rollHeaderView);
    }

}
