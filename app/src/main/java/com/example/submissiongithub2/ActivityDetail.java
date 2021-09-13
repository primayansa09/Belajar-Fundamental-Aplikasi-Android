package com.example.submissiongithub2;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.bumptech.glide.Glide;
import com.example.submissiongithub2.databinding.ActivityDetailBinding;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ActivityDetail extends AppCompatActivity implements View.OnClickListener {

    private ActivityDetailBinding binding;
    private DetailViewModel detailViewModel;
    public static final String EXTRA_USER = "extra_user";
    public static final String TAG = ActivityDetail.class.getSimpleName();

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.followers,
            R.string.following
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.detailContainer.imgDetail.findViewById(R.id.img_detail);
        binding.detailContainer.nameDetail.findViewById(R.id.name_detail);
        binding.detailContainer.username.findViewById(R.id.username);
        binding.detailContainer.tvLocation.findViewById(R.id.tv_location);
        binding.detailContainer.tvCompany.findViewById(R.id.tv_company);
        binding.detailContainer.tvFollowers.findViewById(R.id.tv_followers);
        binding.detailContainer.tvFollowing.findViewById(R.id.tv_following);
        binding.detailContainer.tvRepository.findViewById(R.id.tv_repository);
        binding.detailContainer.btnBack.findViewById(R.id.btn_back);
        binding.detailContainer.btnShare.findViewById(R.id.btn_share);

        binding.progressBarDetail.findViewById(R.id.progressBar_detail);
        Sprite threeBounce = new ThreeBounce();
        binding.progressBarDetail.setIndeterminateDrawable(threeBounce);

        showLoading(true);

        DataUser dataUser = getIntent().getParcelableExtra(EXTRA_USER);
        Log.d(TAG, dataUser.toString());
        detailViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DetailViewModel.class);
        detailViewModel.setDetail(dataUser.getNameUser());
        detailViewModel.getData().observe(this, dataDetail->{
            String mName = dataDetail.getNameUser();
            String mUsername = dataDetail.getUserName();
            String mLocation = dataDetail.getLocation();
            String mCompany = dataDetail.getCompany();
            String mFollowers = dataDetail.getFollower();
            String mFollowing = dataDetail.getFollowing();
            String mRepos = dataDetail.getRepository();

            Glide.with(this)
                    .load(dataUser.getPhotoUser())
                    .into(binding.detailContainer.imgDetail);
            binding.detailContainer.nameDetail.setText(mName);
            binding.detailContainer.username.setText(mUsername);
            binding.detailContainer.tvLocation.setText(mLocation);
            binding.detailContainer.tvCompany.setText(mCompany);
            binding.detailContainer.tvFollowers.setText(mFollowers);
            binding.detailContainer.tvFollowing.setText(mFollowing);
            binding.detailContainer.tvRepository.setText(mRepos);

            showLoading(false);
        });

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this);
        sectionsPagerAdapter.userName = dataUser.getNameUser();
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        new TabLayoutMediator(tabs, viewPager2,
                (tab, position) -> tab.setText(getResources().getString(TAB_TITLES[position]))).attach();

        binding.detailContainer.btnBack.setOnClickListener(this);
        binding.detailContainer.btnShare.setOnClickListener(this);
    }

    private void showLoading(Boolean state) {
        if (state){
          binding.progressBarDetail.setVisibility(View.VISIBLE);
        }else{
            binding.progressBarDetail.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_share:
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_TEXT, binding.detailContainer.nameDetail.getText().toString() +"\n" + binding.detailContainer.tvLocation.getText().toString() +
                        "\n" + binding.detailContainer.tvCompany.getText().toString() +"\n"+ binding.detailContainer.tvFollowers.getText().toString() +"\n"+ binding.detailContainer.tvFollowing.getText().toString() +
                        "\n" + binding.detailContainer.tvRepository.getText().toString());
                share.setType("text/plain");

                Intent shareIntent = Intent.createChooser(share, "Share to");
                startActivity(shareIntent);
                break;
        }
    }
}