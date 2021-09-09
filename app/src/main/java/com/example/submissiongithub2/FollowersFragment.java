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


public class FollowersFragment extends Fragment {

    private RecyclerView rvFollowers;
    private FragmentListAdapter adapter;
    private MainViewModel mainViewModel;
    private static final String ARG_FOLLOWERS = "followers";
    public static final String TAG = FollowersFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mFollowers;

    public FollowersFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FollowersFragment newInstance(String followers) {
        FollowersFragment fragment = new FollowersFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_FOLLOWERS, followers);
        fragment.setArguments(bundle);
        return fragment;
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
        mFollowers = getArguments().getString(ARG_FOLLOWERS);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        rvFollowers = view.findViewById(R.id.rv_followers);
        rvFollowers.setHasFixedSize(true);

        showFragmentList();
        showUserFollowers();
        showViewModel();
    }

    private void showUserFollowers() {
        mainViewModel.setFollowers(mFollowers);
    }

    private void showViewModel() {
        mainViewModel.getData().observe(getViewLifecycleOwner(), list ->{
            if (list != null){
                adapter.setFragment(list);
            }
        });
    }

    private void showFragmentList() {
        rvFollowers.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FragmentListAdapter();
        rvFollowers.setAdapter(adapter);
    }
}