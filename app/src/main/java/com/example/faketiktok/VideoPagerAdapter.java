package com.example.faketiktok;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faketiktok.model.VideoBean;

import java.util.List;

public class VideoPagerAdapter extends RecyclerView.Adapter<VideoPagerAdapter.VideoViewHolder> {

    private Context context;
    private List<VideoBean> videoList;

    public VideoPagerAdapter(Context context, List<VideoBean> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_video_pager, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoBean video = videoList.get(position);

        // 1. 设置标题
        holder.tvTitle.setText(video.getTitle());
        holder.tvLikeCount.setText(String.valueOf(video.getLikeCount()));

        // 2. 加载封面
        int resourceId = context.getResources().getIdentifier(video.getCoverUrl(), "mipmap", context.getPackageName());
        if (resourceId != 0) {
            Glide.with(context).load(resourceId).into(holder.ivCover);
        } else {
            Glide.with(context).load(video.getCoverUrl()).into(holder.ivCover);
        }

        // 3. 设置视频播放路径
        // 解析视频地址
        int videoResId = context.getResources().getIdentifier(video.getVideoUrl(), "raw", context.getPackageName());
        String videoPath;
        if (videoResId != 0) {
            videoPath = "android.resource://" + context.getPackageName() + "/" + videoResId;
        } else {
            videoPath = video.getVideoUrl();
        }

        holder.videoView.setVideoURI(Uri.parse(videoPath));

        // 4. 核心逻辑：在这个页面显示的时候，开始播放
        holder.videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true); // 循环播放
            // 隐藏封面图
            mp.setOnInfoListener((mp1, what, extra) -> {
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    holder.ivCover.setVisibility(View.GONE);
                    return true;
                }
                return false;
            });

            // 默认自动播放
            holder.videoView.start();
        });

        // 初始化点赞状态
        if (video.isLiked()) {
            holder.ivLike.setColorFilter(android.graphics.Color.RED);
        } else {
            holder.ivLike.clearColorFilter();
        }

        // 双击点赞手势处理
        final android.view.GestureDetector gestureDetector = new android.view.GestureDetector(context, new android.view.GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(android.view.MotionEvent e) {
                if (!video.isLiked()) {
                    // 1. 数据更新
                    video.setLiked(true);
                    video.setLikeCount(video.getLikeCount() + 1);

                    // 2. UI 更新
                    holder.ivLike.setColorFilter(android.graphics.Color.RED);
                    holder.tvLikeCount.setText(String.valueOf(video.getLikeCount()));

                    android.widget.Toast.makeText(context, "双击点赞成功 ❤️", android.widget.Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(android.view.MotionEvent e) {
                if (holder.videoView.isPlaying()) {
                    holder.videoView.pause();
                    holder.ivPlayIcon.setVisibility(View.VISIBLE);
                } else {
                    holder.videoView.start();
                    holder.ivPlayIcon.setVisibility(View.GONE);
                }
                return true;
            }
        });

        // 将手势监听绑定到 videoView
        holder.videoView.setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            return true;
        });

        // 点赞按钮点击事件
        holder.ivLike.setOnClickListener(v -> {
            if (!video.isLiked()) {
                video.setLiked(true);
                video.setLikeCount(video.getLikeCount() + 1);
                holder.ivLike.setColorFilter(android.graphics.Color.RED);
                holder.tvLikeCount.setText(String.valueOf(video.getLikeCount()));
                android.widget.Toast.makeText(context, "点赞成功 ❤️", android.widget.Toast.LENGTH_SHORT).show();
            }
        });

        // 评论按钮点击事件
        holder.ivComment.setOnClickListener(v -> {
            // 1. 获取 FragmentManager
            if (context instanceof androidx.appcompat.app.AppCompatActivity) {
                androidx.fragment.app.FragmentManager fragmentManager =
                        ((androidx.appcompat.app.AppCompatActivity) context).getSupportFragmentManager();

                // 2. 创建并显示评论弹窗
                CommentDialog commentDialog = new CommentDialog();
                commentDialog.show(fragmentManager, "CommentDialog");
            }
        });
        //返回按钮点击事件
        holder.ivBack.setOnClickListener(v -> {
            // 判断 context 是不是 Activity，如果是，就关闭它
            if (context instanceof android.app.Activity) {
                ((android.app.Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList == null ? 0 : videoList.size();
    }

    // ViewHolder 类：绑定控件
    static class VideoViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        ImageView ivCover;
        ImageView ivPlayIcon;
        TextView tvTitle;
        TextView tvLikeCount;
        ImageView ivLike;    // 点赞
        ImageView ivComment; // 评论
        ImageView ivBack;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.video_view);
            ivCover = itemView.findViewById(R.id.iv_detail_cover);
            ivPlayIcon = itemView.findViewById(R.id.iv_play_icon);
            tvTitle = itemView.findViewById(R.id.tv_detail_title);
            tvLikeCount = itemView.findViewById(R.id.tv_like_count);
            ivLike = itemView.findViewById(R.id.iv_like);
            ivComment = itemView.findViewById(R.id.iv_comment);
            ivBack = itemView.findViewById(R.id.iv_back);
        }
    }
}