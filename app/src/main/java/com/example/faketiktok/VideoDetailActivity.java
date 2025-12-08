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
        // 注意：这里关联的 xml 布局文件稍后也需要修改，要把里面的 VideoView 换成 ViewPager2
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
        // 绑定 ViewPager2 控件 (XML 改好后，这里 ID 才能对应上)
        viewPager = findViewById(R.id.view_pager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        // === 关键点：设置适配器 ===
        // 我们需要新建一个 VideoPagerAdapter 来管理每一个视频页面的显示和播放
        // 这一行代码现在会爆红，因为我们还没创建这个类，别担心，马上就写！
        VideoPagerAdapter adapter = new VideoPagerAdapter(this, videoList);
        viewPager.setAdapter(adapter);

        // 设置当前要显示的视频 (无动画直接跳转，体验更好)
        viewPager.setCurrentItem(currentPosition, false);

        // 监听 ViewPager 的滑动，实现“滑走自动暂停”等高级功能 (以后再加)
    }
}