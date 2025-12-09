# FakeTiktok - 高仿抖音 Android 客户端



## 📖 项目简介

**FakeTiktok** 是一个基于 Android 原生开发的高仿短视频应用（Demo）。
本项目旨在还原抖音的核心交互体验，包括沉浸式全屏视频播放、双击点赞、瀑布流列表等功能。

---

## ✨ 核心功能 (Key Features)

### 1. 首页双流切换
- 实现了 **"关注"** 与 **"推荐"** 两个频道的左右滑动切换。
- **技术实现**：使用 `ViewPager2` + `Fragment` (RecommendFragment/FollowFragment) 搭建主框架，配合顶部 Tab 实现联动动画。

### 2. 瀑布流推荐列表
- 首页采用双列交错的瀑布流布局，支持高度自适应。
- **下拉刷新**：集成 `SmartRefreshLayout`，支持炫酷的下拉刷新动画，模拟数据随机打乱更新。
- **技术实现**：`RecyclerView` + `StaggeredGridLayoutManager`。

### 3. 沉浸式视频播放 (内流)
- 点击列表封面无缝进入全屏播放页，支持**无限垂直下滑**切换视频。
- **无黑屏优化**：采用“封面图占位 + 渲染首帧隐藏”的策略，完美解决了视频起播时的黑屏闪烁问题，体验丝滑。
- **手势交互**：
    - **双击点赞**：屏幕双击触发红心动画，点赞数 +1。
    - **单击控制**：单击屏幕暂停/播放，并在屏幕中央显示播放状态图标。
- **技术实现**：`ViewPager2` (Vertical) + `VideoView` + `GestureDetector`。

### 4. 互动组件
- **评论弹窗**：使用 `BottomSheetDialogFragment` 实现半屏评论窗口，支持输入并发布新评论（自动插入顶部并滚动）。
- **点赞系统**：支持 UI 状态即时反馈（变红、数字跳动）。

---

## 🛠 技术栈 (Tech Stack)

- **编程语言**: Java
- **核心架构**: MVC (Model-View-Controller) / Fragment 模块化
- **UI 组件**:
    - `RecyclerView` (列表核心)
    - `ViewPager2` (全屏翻页/Tab切换)
    - `ConstraintLayout` (复杂布局)
    - `BottomSheetDialogFragment` (底部弹窗)
- **开源库**:
    - [Glide](https://github.com/bumptech/glide): 图片加载与缓存。
    - [SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout): 强大的下拉刷新库。
    - [Gson](https://github.com/google/gson): JSON 数据解析。

---

## 📂 项目结构 (Project Structure)

```text
com.example.faketiktok
├── model/                  # 数据模型层 (Java Bean)
│   ├── VideoBean.java      # 视频实体类
│   └── CommentBean.java    # 评论实体类
├── MainActivity.java       # APP 主入口 (包含 ViewPager2 和 Tab 逻辑)
├── RecommendFragment.java  # 推荐页 Fragment (瀑布流列表逻辑)
├── FollowFragment.java     # 关注页 Fragment
├── VideoDetailActivity.java# 视频详情页容器
├── VideoAdapter.java       # 首页列表适配器
├── VideoPagerAdapter.java  # 详情页全屏视频适配器 (核心播放逻辑)
└── CommentDialog.java      # 评论弹窗逻辑
```

--

## 📏 开发规范 (Development Guidelines)

为了保持代码的可维护性和一致性，本项目遵循以下开发规范：

### 1. 命名规范
- **包名**: 全小写，域名反写 (e.g., `com.example.faketiktok`).
- **类名**: 大驼峰命名法 (PascalCase)，如 `VideoAdapter`.
- **变量/方法**: 小驼峰命名法 (camelCase)，如 `initView()`, `videoList`.
- **资源文件 (XML)**:
    - 布局: `模块_功能.xml` (e.g., `activity_main.xml`, `item_video_card.xml`).
    - 图标: `ic_功能.xml` (e.g., `ic_heart.xml`, `ic_play_arrow.xml`).

### 2. 代码风格
- **UI 与逻辑分离**: 尽量将 UI 初始化逻辑放在 `initView()`，数据处理放在 `initData()`。
- **注释**: 核心逻辑（如手势监听、视频预加载）必须包含清晰的中文注释，说明“为什么这么做”。

### 3. Git 提交规范
提交 Message 需遵循 `<type>: <subject>` 格式：
- `feat`: 新增功能 (e.g., `feat: add double tap to like`)
- `fix`: 修复 Bug
- `docs`: 文档修改
- `style`: 代码格式调整 (不影响逻辑)
- `refactor`: 代码重构

---

## 🚀 快速开始 (Getting Started)

1. **克隆项目**
   ```bash
   git clone https://github.com/your-repo/FakeTiktok.git
   ```

2. **导入 Android Studio**
   - 打开 Android Studio，选择 `Open an Existing Project`。
   - 等待 Gradle Sync 完成。

3. **运行**
   - 连接 Android 真机或启动模拟器。
   - 点击 `Run` (绿色三角形按钮)。

---

## 📝 待办事项 (TODO)

- [ ] 接入后端 API，替换本地 mock 数据。
- [ ] 实现视频预加载缓存，提升秒开率。
- [ ] 增加用户登录注册功能。
- [ ] 完善“关注”页面逻辑。

---
**Date**: 2025-12-09
