package com.example.submissiongithub2;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.submissiongithub2.databinding.ActivityDetailBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ActivityDetail extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_USER = "extra_user";
    private ActivityDetailBinding binding;

    @StringRes
    private final int[] TAB_TITLEs = new int[]{
            R.string.followers,
            R.string.following
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgDetail.findViewById(R.id.img_detail);
        binding.nameDetail.findViewById(R.id.name_detail);
        binding.userDetail.findViewById(R.id.user_detail);
        binding.valueLocation.findViewById(R.id.value_location);
        binding.valueCompany.findViewById(R.id.value_company);
        binding.valueFollower.findViewById(R.id.value_follower);
        binding.valueFollowing.findViewById(R.id.value_following);
        binding.valueRepository.findViewById(R.id.value_repository);
        ImageView btnBack = findViewById(R.id.btn_back);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        new TabLayoutMediator(tabs, viewPager2,
                (tab, position) -> tab.setText(getResources().getString(TAB_TITLEs[position]))).attach();

        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
}