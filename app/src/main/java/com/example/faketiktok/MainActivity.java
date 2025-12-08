package com.example.faketiktok;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.faketiktok.model.VideoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.header.ClassicsHeader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvVideoList;
    private VideoAdapter videoAdapter;
    private List<VideoBean> videoList = new ArrayList<>();
    //刷新布局变量
    private RefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 初始化视图
        initView();
        initData();
    }
    private void initData() {
        try {
            // 1. 读取 assets 目录下的 data.json 文件
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            // 2. 使用 Gson 将 JSON 字符串转换为 List 对象
            Gson gson = new Gson();
            // TypeToken 是 Gson 用来处理泛型 List 的一种方式
            List<VideoBean> list = gson.fromJson(json, new TypeToken<List<VideoBean>>(){}.getType());

            // 3. 将数据添加到数据源，并刷新适配器
            if (list != null && !list.isEmpty()) {
                videoList.addAll(list);
                // 关键一步：告诉适配器数据变了，赶紧更新界面！
                videoAdapter.notifyDataSetChanged();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "数据加载失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        rvVideoList = findViewById(R.id.rv_video_list);
        refreshLayout = findViewById(R.id.refreshLayout);
        // 设置下拉刷新监听器
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                // 这里执行刷新逻辑
                // 在真实项目中，这里会重新请求网络接口

                // === 模拟：我们通过打乱现有列表顺序来模拟“新推荐” ===
                if (videoList != null && !videoList.isEmpty()) {
                    Collections.shuffle(videoList); // 打乱顺序
                    videoAdapter.notifyDataSetChanged(); // 刷新列表
                }

                // 结束刷新动画 (传入延迟时间，比如 1000毫秒，让用户看清动画)
                refreshLayout.finishRefresh(1000);
            }
        });


        ClassicsHeader.REFRESH_HEADER_PULLING = "下拉推荐更多视频";
        ClassicsHeader.REFRESH_HEADER_RELEASE = "松开立即刷新";
        ClassicsHeader.REFRESH_HEADER_REFRESHING = "正在刷新数据...";
        ClassicsHeader.REFRESH_HEADER_FINISH = "刷新成功！";

        // 核心知识点：这里使用了瀑布流布局管理器
        // 参数说明：2 代表列数，VERTICAL 代表垂直方向滚动
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        // 防止瀑布流滑动时 item 位置跳动（答辩加分细节）
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        rvVideoList.setLayoutManager(layoutManager);

        // 初始化适配器
        videoAdapter = new VideoAdapter(this, videoList);
        rvVideoList.setAdapter(videoAdapter);

        // 设置点击事件（跳转逻辑后面加）

        videoAdapter.setOnItemClickListener(video -> {
            // 1. 创建 Intent
            android.content.Intent intent = new android.content.Intent(MainActivity.this, VideoDetailActivity.class);
            // 2. 传递数据
            // 传递当前点击的视频对象
            intent.putExtra("video_data", video);
            // === 新增：为了实现无限滑动 ===
            // A. 传递整个视频列表
            intent.putExtra("video_list", (java.io.Serializable) videoList);
            // B. 传递当前点击的是第几个 (索引位置)
            int position = videoList.indexOf(video);
            intent.putExtra("video_position", position);
            // 3. 启动页面
            startActivity(intent);
        });

    }
}