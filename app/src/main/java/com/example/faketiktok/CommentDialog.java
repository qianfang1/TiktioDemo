package com.example.faketiktok;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faketiktok.model.CommentBean;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class CommentDialog extends BottomSheetDialogFragment {

    private RecyclerView rvCommentList;
    private CommentAdapter commentAdapter;
    private List<CommentBean> commentList = new ArrayList<>();
    private EditText etContent;
    private Button btnSend;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 1. 加载布局 dialog_comment.xml
        return inflater.inflate(R.layout.dialog_comment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 2. 绑定控件
        rvCommentList = view.findViewById(R.id.rv_comment_list);
        etContent = view.findViewById(R.id.et_comment_content);
        btnSend = view.findViewById(R.id.btn_send);
        view.findViewById(R.id.iv_close).setOnClickListener(v -> dismiss());

        // 3. 初始化模拟数据
        commentList.add(new CommentBean("https://p3.itc.cn/q_70/images03/20220325/40e84b2c011e4a07a16e72c841315668.jpeg", "用户 A", "视频拍得真不错！"));
        commentList.add(new CommentBean("https://p3.itc.cn/q_70/images03/20220325/40e84b2c011e4a07a16e72c841315668.jpeg", "用户 B", "学到了学到了 👍"));

        // 4. 设置 RecyclerView
        commentAdapter = new CommentAdapter(getContext(), commentList);
        rvCommentList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCommentList.setAdapter(commentAdapter);

// 5. 处理发送按钮点击事件
        btnSend.setOnClickListener(v -> {
            String content = etContent.getText().toString().trim();
            if (!TextUtils.isEmpty(content)) {
                // 1. 创建新评论对象 (模拟当前用户)
                // 这里我们暂时写死头像和昵称，实际项目中通常从登录用户信息里获取
                CommentBean newComment = new CommentBean(
                        "https://p3.itc.cn/q_70/images03/20220325/40e84b2c011e4a07a16e72c841315668.jpeg",
                        "我",
                        content
                );

                // 2. 把新评论加到列表的最前面 (索引 0)，这样它会显示在第一行
                commentList.add(0, newComment);

                // 3. 关键一步：通知适配器数据变了，赶紧刷新界面！
                commentAdapter.notifyDataSetChanged();

                Toast.makeText(getContext(), "评论发送成功", Toast.LENGTH_SHORT).show();
                etContent.setText(""); // 清空输入框

                // 体验优化：发送完自动滚动到列表顶部，让用户看到自己的评论
                rvCommentList.scrollToPosition(0);
            } else {
                Toast.makeText(getContext(), "请输入评论内容", Toast.LENGTH_SHORT).show();
            }
        });
    }
}