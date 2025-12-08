package com.example.faketiktok.model;

import java.io.Serializable;

/**
 * 视频实体类
 * 对应 assets/data.json 中的数据结构
 */
public class VideoBean implements Serializable {

    // 字段名必须与 JSON 中的 key 完全一致
    private int id;
    private String title;
    private String videoUrl;
    private String coverUrl;
    private int likeCount;
    private String nickname;
    private String avatarUrl;

    public VideoBean() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }

    public int getLikeCount() { return likeCount; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}