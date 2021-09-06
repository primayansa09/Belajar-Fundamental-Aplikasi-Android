package com.example.submissiongithub2;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submissiongithub2.databinding.ActivityDetailBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ActivityDetail extends AppCompatActivity implements View.OnClickListener {

    private ActivityDetailBinding binding;
    private MainViewModel mainViewModel;
    public static final String EXTRA_USER = "extra_user";
    public static final String TAG = ActivityDetail.class.getSimpleName();

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

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        binding.imgDetail.findViewById(R.id.img_detail);
        binding.nameDetail.findViewById(R.id.name_detail);
        binding.locationDetail.findViewById(R.id.location_detail);
        binding.companyDetail.findViewById(R.id.company_detail);
        ImageView btnBack = findViewById(R.id.btn_back);

        DataUser data = getIntent().getParcelableExtra(EXTRA_USER);
        String photo = data.getPhotoUser();
        String name = data.getNameUser();
        String location = data.getLocation();
        String company = data.getCompany();

        Glide.with(this)
                .load(data.getPhotoUser())
                .apply(new RequestOptions().override(150, 150))
                .into(binding.imgDetail);
        binding.nameDetail.setText(name);
        binding.locationDetail.setText(location);
        binding.companyDetail.setText(company);

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