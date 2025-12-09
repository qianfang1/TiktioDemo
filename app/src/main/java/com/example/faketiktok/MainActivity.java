package com.example.faketiktok;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.scwang.smart.refresh.header.ClassicsHeader;

public class MainActivity extends AppCompatActivity {

    // 静态代码块：确保在 View 初始化之前修改默认文案
    static {
        ClassicsHeader.REFRESH_HEADER_PULLING = "下拉推荐更多视频";
        ClassicsHeader.REFRESH_HEADER_RELEASE = "松开立即刷新";
        ClassicsHeader.REFRESH_HEADER_REFRESHING = "正在刷新数据...";
        ClassicsHeader.REFRESH_HEADER_FINISH = "刷新成功！";
    }

    private ViewPager2 mainViewPager;
    private TextView tvTabFollow;
    private TextView tvTabRecommend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mainViewPager = findViewById(R.id.main_view_pager);
        tvTabFollow = findViewById(R.id.tv_tab_follow);
        tvTabRecommend = findViewById(R.id.tv_tab_recommend);

        // 1. 设置 ViewPager2 适配器
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(this);
        mainViewPager.setAdapter(pagerAdapter);

        // 2. 默认选中“推荐”页
        mainViewPager.setCurrentItem(1, false);
        updateTabStyle(1);

        // 3. ViewPager 页面切换监听
        mainViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateTabStyle(position);
            }
        });

        // 4. 顶部 Tab 点击事件
        tvTabFollow.setOnClickListener(v -> mainViewPager.setCurrentItem(0));
        tvTabRecommend.setOnClickListener(v -> mainViewPager.setCurrentItem(1));

        // 5. 底部导航栏点击事件
        com.google.android.material.bottomnavigation.BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                return true; // 首页允许选中
            } else {
                Toast.makeText(MainActivity.this, "当前功能尚未开发", Toast.LENGTH_SHORT).show();
                return false; // 其他按钮不产生选中效果
            }
        });
    }

    // 切换 Tab 样式
    private void updateTabStyle(int position) {
        if (position == 0) {
            // 选中“关注”
            tvTabFollow.setAlpha(1.0f);
            tvTabFollow.setTextSize(18);
            
            tvTabRecommend.setAlpha(0.6f);
            tvTabRecommend.setTextSize(16);
        } else {
            // 选中“推荐”
            tvTabFollow.setAlpha(0.6f);
            tvTabFollow.setTextSize(16);
            
            tvTabRecommend.setAlpha(1.0f);
            tvTabRecommend.setTextSize(18);
        }
    }
}