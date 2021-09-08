package com.example.submissiongithub2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SectionsPagerAdapter extends FragmentStateAdapter {

    public SectionsPagerAdapter(AppCompatActivity activity) {
        super(activity);
    }

    String userName = null;

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
            fragment = FollowersFragment.newInstance(userName.toString());
            break;
            case 1:
            fragment = FollowingFragment.newInstance(userName.toString());
            break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
