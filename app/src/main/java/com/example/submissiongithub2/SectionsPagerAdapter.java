package com.example.submissiongithub2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SectionsPagerAdapter extends FragmentStateAdapter {

    public SectionsPagerAdapter(AppCompatActivity activity) {
        super(activity);
    }

    String username = null;

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
            fragment = FollowersFragment.newInstance(username.toString());
            break;
            case 1:
            fragment = FollowingFragment.newInstance(username.toString());
            break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
