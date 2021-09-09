package com.example.submissiongithub2;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.submissiongithub2.databinding.ActivityDetailBinding;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
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
        binding.valueFollowers.findViewById(R.id.value_followers);
        binding.valueFollowing.findViewById(R.id.value_following);
        binding.valueRepos.findViewById(R.id.value_repos);
        btnBack = findViewById(R.id.btn_back);
        btnShare = findViewById(R.id.btn_share);
        binding.progressBarDetail.findViewById(R.id.parent_matrix);
        Sprite threeBounce = new ThreeBounce();
        binding.progressBarDetail.setIndeterminateDrawable(threeBounce);

        showLoading(true);

        DataUser dataUser = getIntent().getParcelableExtra(EXTRA_USER);
        Log.d(TAG, dataUser.toString());
        detailViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DetailViewModel.class);
        detailViewModel.setDetail(dataUser.getNameUser());
        detailViewModel.getData().observe(this, dataDetail->{
            String name = dataDetail.getNameUser();
            String location = dataDetail.getLocation();
            String company = dataDetail.getCompany();
            String followers = dataDetail.getFollower();
            String following = dataDetail.getFollowing();
            String repos = dataDetail.getRepository();

            Glide.with(this)
                    .load(dataUser.getPhotoUser())
                    .into(binding.imgDetail);
            binding.nameDetail.setText(name);
            binding.tvLocationl.setText(location);
            binding.tvCompany.setText(company);
            binding.valueFollowers.setText(followers);
            binding.valueFollowing.setText(following);
            binding.valueRepos.setText(repos);
            showLoading(false);
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
                share.putExtra(Intent.EXTRA_TEXT, binding.nameDetail.getText().toString() +"\n" + binding.tvLocationl.getText().toString() +
                        "\n" + binding.tvCompany.getText().toString() +"\n"+ binding.valueFollowers.getText().toString() +"\n"+ binding.valueFollowing.getText().toString() +
                        "\n" + binding.valueRepos.getText().toString());
                share.setType("text/plain");

                Intent shareIntent = Intent.createChooser(share, null);
                startActivity(shareIntent);
                break;
        }
    }
}