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
import com.example.faketiktok.model.CommentBean;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context context;
    private List<CommentBean> commentList;

    public CommentAdapter(Context context, List<CommentBean> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 加载评论的单条布局 item_comment.xml
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentBean comment = commentList.get(position);

        holder.tvUser.setText(comment.getNickname());
        holder.tvContent.setText(comment.getContent());

        // 使用 Glide 加载圆形头像
        // 逻辑：如果资源名存在，加载本地资源；否则当做网络图片
        int resourceId = context.getResources().getIdentifier(comment.getAvatarUrl(), "mipmap", context.getPackageName());
        if (resourceId != 0) {
            Glide.with(context).load(resourceId).circleCrop().into(holder.ivAvatar);
        } else {
            Glide.with(context).load(comment.getAvatarUrl()).circleCrop().into(holder.ivAvatar);
        }
    }

    @Override
    public int getItemCount() {
        return commentList == null ? 0 : commentList.size();
    }

    // 内部类 ViewHolder
    static class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvUser;
        TextView tvContent;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_comment_avatar);
            tvUser = itemView.findViewById(R.id.tv_comment_user);
            tvContent = itemView.findViewById(R.id.tv_comment_content);
        }
    }
}