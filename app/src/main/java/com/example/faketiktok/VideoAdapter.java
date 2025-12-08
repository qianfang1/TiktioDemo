package com.example.faketiktok;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faketiktok.model.VideoBean;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context context;
    private List<VideoBean> videoList;
    private OnItemClickListener listener;

    public VideoAdapter(Context context, List<VideoBean> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 加载布局 item_video_card
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_card, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoBean video = videoList.get(position);
        // 1. 设置文字
        holder.tvTitle.setText(video.getTitle());
        holder.tvLikeCount.setText(String.valueOf(video.getLikeCount()));

        // 图片加载逻辑：先看是不是本地资源名，如果不是再当做网络链接
        int resourceId = context.getResources().getIdentifier(video.getCoverUrl(), "mipmap", context.getPackageName());
        if (resourceId == 0) {
            resourceId = context.getResources().getIdentifier(video.getCoverUrl(), "drawable", context.getPackageName());
        }

        if (resourceId != 0) {
            Glide.with(context).load(resourceId).into(holder.ivCover);
        } else {
            Glide.with(context).load(video.getCoverUrl()).into(holder.ivCover);
        }

        // 点击事件
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(video);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoList == null ? 0 : videoList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;
        TextView tvTitle;
        TextView tvLikeCount;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvLikeCount = itemView.findViewById(R.id.tv_like_count);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(VideoBean video);
    }
}