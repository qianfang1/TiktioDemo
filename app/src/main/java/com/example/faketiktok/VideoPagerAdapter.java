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
        // 🚨 思考题：这里需要加载每一页的布局文件。
        // 我们刚才把 activity_video_detail.xml 改成了只放 ViewPager2。
        // 那么，原本那些用来显示视频、头像、点赞按钮的 XML 代码，我们需要怎么处理？
        // 答案：我们需要新建一个布局文件（比如 item_video_pager.xml），把那些代码搬过去。

        View view = LayoutInflater.from(context).inflate(R.layout.item_video_pager, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoBean video = videoList.get(position);

        // 1. 设置标题
        holder.tvTitle.setText(video.getTitle());
        holder.tvLikeCount.setText(String.valueOf(video.getLikeCount()));

        // 2. 加载封面 (和之前一样的逻辑)
        int resourceId = context.getResources().getIdentifier(video.getCoverUrl(), "mipmap", context.getPackageName());
        if (resourceId != 0) {
            Glide.with(context).load(resourceId).into(holder.ivCover);
        } else {
            Glide.with(context).load(video.getCoverUrl()).into(holder.ivCover);
        }

        // 3. 设置视频播放路径
        // 这里需要解析视频地址
        int videoResId = context.getResources().getIdentifier(video.getVideoUrl(), "raw", context.getPackageName());
        String videoPath;
        if (videoResId != 0) {
            videoPath = "android.resource://" + context.getPackageName() + "/" + videoResId;
        } else {
            videoPath = video.getVideoUrl();
        }

        holder.videoView.setVideoURI(Uri.parse(videoPath));

        // 4. 核心逻辑：在这个页面显示的时候，开始播放
        // 注意：实际开发中，我们通常会在 ViewPager 滑动监听里控制播放，
        // 但为了作业简单，我们在这里先预设好。
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

        // 点击暂停/播放
        holder.videoView.setOnClickListener(v -> {
            if (holder.videoView.isPlaying()) {
                holder.videoView.pause();
            } else {
                holder.videoView.start();
            }
        });
        // === 新增：点赞按钮点击事件 ===
        holder.ivLike.setOnClickListener(v -> {
            // 这里可以处理点赞逻辑，比如改变图标颜色、数字+1
            // 暂时先弹个 Toast 测试一下
            android.widget.Toast.makeText(context, "点赞成功 ❤️", android.widget.Toast.LENGTH_SHORT).show();
        });

        // === 新增：评论按钮点击事件 (核心) ===
        holder.ivComment.setOnClickListener(v -> {
            // 1. 获取 FragmentManager (需要从 context 转换)
            if (context instanceof androidx.appcompat.app.AppCompatActivity) {
                androidx.fragment.app.FragmentManager fragmentManager =
                        ((androidx.appcompat.app.AppCompatActivity) context).getSupportFragmentManager();

                // 2. 创建并显示评论弹窗
                CommentDialog commentDialog = new CommentDialog();
                commentDialog.show(fragmentManager, "CommentDialog");
            }
        });
        // === 新增：返回按钮点击事件 ===
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
        TextView tvTitle;
        TextView tvLikeCount;
        // 你还可以在这里添加 ivComment 等其他控件
        // === 新增：添加这两个按钮的声明 ===
        ImageView ivLike;    // 点赞
        ImageView ivComment; // 评论
        // === 新增：声明返回按钮 ===
        ImageView ivBack;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            // 这里的 ID 必须和 item_video_pager.xml 里的 ID 对应
            videoView = itemView.findViewById(R.id.video_view);
            ivCover = itemView.findViewById(R.id.iv_detail_cover);
            tvTitle = itemView.findViewById(R.id.tv_detail_title);
            tvLikeCount = itemView.findViewById(R.id.tv_like_count);
            // === 新增：绑定 ID (确保和 xml 里的 id 一致) ===
            ivLike = itemView.findViewById(R.id.iv_like);
            ivComment = itemView.findViewById(R.id.iv_comment);
            ivBack = itemView.findViewById(R.id.iv_back);
        }
    }
}