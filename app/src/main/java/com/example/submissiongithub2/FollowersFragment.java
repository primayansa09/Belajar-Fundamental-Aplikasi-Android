package com.example.submissiongithub2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class FollowersFragment extends Fragment {

    private RecyclerView rvFollowers;
    private FragmentListAdapter adapter;
    private MainViewModel mainViewModel;
    private SpinKitView progressBar;
    private static final String ARG_FOLLOWERS = "followers";
    public static final String TAG = FollowersFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mFollowers;

    public FollowersFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FollowersFragment newInstance(String Followers) {
        FollowersFragment fragment = new FollowersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FOLLOWERS, Followers);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFollowers = getArguments().getString(ARG_FOLLOWERS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        rvFollowers = view.findViewById(R.id.rv_followers);
        rvFollowers.setHasFixedSize(true);

        progressBar = view.findViewById(R.id.progressbar_follower);
        Sprite treeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(treeBounce);

        showFragmentList();
        showUserFollowers();
        showViewModel();
        showLoading(true);
    }

    private void showLoading(Boolean state) {
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else
            progressBar.setVisibility(View.GONE);
    }

    private void showUserFollowers() {
        mainViewModel.getFollowers("followers");
    }

    private void showViewModel() {
        mainViewModel.getData().observe(getViewLifecycleOwner(), list ->{
            if (list != null){
                adapter.setFragment(list);
                showLoading(false);
            }
        });
    }

    private void showFragmentList() {
        rvFollowers.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FragmentListAdapter();
        rvFollowers.setAdapter(adapter);
    }
}