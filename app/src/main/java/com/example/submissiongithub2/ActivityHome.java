package com.example.submissiongithub2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.submissiongithub2.databinding.ActivityHomeBinding;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

public class ActivityHome extends AppCompatActivity {

    private UserAdapter adapter;
    private MainViewModel mainViewModel;
    private ActivityHomeBinding binding;
    public static final String TAG = ActivityHome.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        binding.progressBar.findViewById(R.id.progressBar);
        Sprite threeBounce = new ThreeBounce();
        binding.progressBar.setIndeterminateDrawable(threeBounce);

        binding.rvUser.findViewById(R.id.rv_user);
        binding.rvUser.setHasFixedSize(true);


        showRecyclerlist();
        showDataUser();
        showProgressBar(true);
        showViewModel(adapter);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        binding.searchUser.findViewById(R.id.searchUser);
        binding.searchUser.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        binding.searchUser.setQueryHint(getResources().getString(R.string.hint));
        binding.searchUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(ActivityHome.this, query, Toast.LENGTH_SHORT).show();
                showListSearch(query);
                showProgressBar(true);
                binding.searchUser.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void showDataUser() {
        mainViewModel.getDataUser(getApplicationContext());
        showProgressBar(false);
    }

    private void showListSearch(String quey) {
    mainViewModel.ListSearch(quey, getApplicationContext());
    }

    private void showViewModel(UserAdapter adapter) {
        mainViewModel.getData().observe(this, list ->{
            if (list != null){
                adapter.setData(list);
                showProgressBar(false);
            }
        });
    }

    private void showRecyclerlist() {
        binding.rvUser.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new UserAdapter();
        binding.rvUser.setAdapter(adapter);
    }

    private void showProgressBar(Boolean state) {
        if (state){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar.setVisibility(View.GONE);
        }

    }
}