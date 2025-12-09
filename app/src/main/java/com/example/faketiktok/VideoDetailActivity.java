package com.example.faketiktok;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.faketiktok.model.VideoBean;
import java.util.List;

public class VideoDetailActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private List<VideoBean> videoList;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        initData();
        initView();
    }

    private void initData() {
        // 1. 接收整个视频列表
        videoList = (List<VideoBean>) getIntent().getSerializableExtra("video_list");
        // 2. 接收当前点击的位置
        currentPosition = getIntent().getIntExtra("video_position", 0);
    }

    private void initView() {
        // 绑定 ViewPager2 控件
        viewPager = findViewById(R.id.view_pager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        // 适配器
        //  VideoPagerAdapter 来管理每一个视频页面的显示和播放
        VideoPagerAdapter adapter = new VideoPagerAdapter(this, videoList);
        viewPager.setAdapter(adapter);
        // 设置当前要显示的视频
        viewPager.setCurrentItem(currentPosition, false);

    }
}