package com.example.faketiktok.model;



public class CommentBean {
    private String avatarUrl; // 头像资源名或链接
    private String nickname;  // 昵称
    private String content;   // 评论内容

    public CommentBean(String avatarUrl, String nickname, String content) {
        this.avatarUrl = avatarUrl;
        this.nickname = nickname;
        this.content = content;
    }

    // Getter 方法
    public String getAvatarUrl() { return avatarUrl; }
    public String getNickname() { return nickname; }
    public String getContent() { return content; }
}