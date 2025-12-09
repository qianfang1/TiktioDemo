package com.example.faketiktok;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.faketiktok.model.VideoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecommendFragment extends Fragment {

    private RecyclerView rvVideoList;
    private VideoAdapter videoAdapter;
    private List<VideoBean> videoList = new ArrayList<>();
    private RefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initView(View view) {
        rvVideoList = view.findViewById(R.id.rv_video_list);
        refreshLayout = view.findViewById(R.id.refreshLayout);

        // 设置下拉刷新监听器
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                // 模拟数据更新
                if (videoList != null && !videoList.isEmpty()) {
                    Collections.shuffle(videoList);
                    videoAdapter.notifyDataSetChanged();
                }
                refreshLayout.finishRefresh(1000);
            }
        });
        
        // 瀑布流布局
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rvVideoList.setLayoutManager(layoutManager);

        // 初始化适配器
        videoAdapter = new VideoAdapter(getContext(), videoList);
        rvVideoList.setAdapter(videoAdapter);

        // 点击事件
        videoAdapter.setOnItemClickListener(video -> {
            Intent intent = new Intent(getContext(), VideoDetailActivity.class);
            intent.putExtra("video_data", video);
            intent.putExtra("video_list", (java.io.Serializable) videoList);
            int position = videoList.indexOf(video);
            intent.putExtra("video_position", position);
            startActivity(intent);
        });
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (getContext() == null) return;
                    
                    InputStream is = getContext().getAssets().open("data.json");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    String json = new String(buffer, StandardCharsets.UTF_8);

                    Gson gson = new Gson();
                    List<VideoBean> list = gson.fromJson(json, new TypeToken<List<VideoBean>>(){}.getType());

                    if (list != null && !list.isEmpty()) {
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    videoList.addAll(list);
                                    videoAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "数据加载失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }).start();
    }
}