package com.example.submissiongithub2;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.submissiongithub2.databinding.ActivityDetailBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ActivityDetail extends AppCompatActivity implements View.OnClickListener {

    private ActivityDetailBinding binding;
    private DetailViewModel detailViewModel;
    private ImageView btnBack, btnShare;
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

        binding.imgDetail.findViewById(R.id.img_detail);
        binding.nameDetail.findViewById(R.id.name_detail);
        binding.tvLocationl.findViewById(R.id.tv_locationl);
        binding.tvCompany.findViewById(R.id.tv_company);
        btnBack = findViewById(R.id.btn_back);
        btnShare = findViewById(R.id.btn_share);

        DataUser dataUser = getIntent().getParcelableExtra(EXTRA_USER);
        Log.d(TAG, dataUser.toString());
        detailViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DetailViewModel.class);
        detailViewModel.setDetail(dataUser.getNameUser());
        detailViewModel.getData().observe(this, it->{
            String name = dataUser.getNameUser();
            String location = dataUser.getLocation();
            String company = dataUser.getCompany();

            Glide.with(this)
                    .load(dataUser.getPhotoUser())
                    .into(binding.imgDetail);
            binding.nameDetail.setText(name);
            binding.tvLocationl.setText(location);
            binding.tvCompany.setText(company);
        });



        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this);
        sectionsPagerAdapter.userName = dataUser.getNameUser();
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        new TabLayoutMediator(tabs, viewPager2,
                (tab, position) -> tab.setText(getResources().getString(TAB_TITLEs[position]))).attach();

        btnBack.setOnClickListener(this);
        btnShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_share:
                break;
        }
    }
}