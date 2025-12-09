package com.example.faketiktok;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainPagerAdapter extends FragmentStateAdapter {

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new FollowFragment(); // 第一页：关注
        } else {
            return new RecommendFragment(); // 第二页：推荐
        }
    }

    @Override
    public int getItemCount() {
        return 2; // 总共两页
    }
}