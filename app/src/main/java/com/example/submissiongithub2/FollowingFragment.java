package com.example.submissiongithub2;

import android.os.Bundle;
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

public class FollowingFragment extends Fragment {

    private RecyclerView rvFollowing;
    private SpinKitView progressBar;
    private FollowingViewModel followingViewModel;
    private FragmentListAdapter adapter;
    public static final String TAG = FollowingFragment.class.getSimpleName();
    private static final String ARG_FOLLOWING = "following";
    private String mFollowing;

    public FollowingFragment() {

    }

    public static FollowingFragment newInstance(String following) {
        FollowingFragment fragment = new FollowingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FOLLOWING, following);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFollowing = getArguments().getString(ARG_FOLLOWING);
        followingViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel.class);

        progressBar = view.findViewById(R.id.progressBar_following);
        Sprite treeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(treeBounce);

        rvFollowing = view.findViewById(R.id.rv_following);
        rvFollowing.setHasFixedSize(true);

        showRecyclerlist();
        showUserFollowing();
        showViewMOdel();
        showLoading(true);
    }

    private void showUserFollowing() {
        followingViewModel.setFollowing(mFollowing);
    }

    private void showViewMOdel() {
        followingViewModel.getData().observe(getViewLifecycleOwner(), list ->{
            if (list != null){
                adapter.setFragment(list);
                showLoading(false);
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else
            progressBar.setVisibility(View.GONE);
    }

    private void showRecyclerlist() {
        rvFollowing.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FragmentListAdapter();
        rvFollowing.setAdapter(adapter);
    }
}