package com.example.finalproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.finalproject.databinding.FragmentReviewsListBinding;
import com.example.finalproject.model.LiveDataEvents;
import com.example.finalproject.model.Review;
import java.util.ArrayList;
import java.util.List;

public class ReviewsListFragment extends Fragment {
    List<Review> data;
    ReviewRecyclerAdapter.OnItemClickListener rowClickListener;
    ReviewRecyclerAdapter adapter;
    FragmentReviewsListBinding binding;

    public void setParameters(List<Review> reviews, ReviewRecyclerAdapter.OnItemClickListener listener) {
        data = reviews;
        rowClickListener = listener;
        if(adapter != null) {
            adapter.setData(reviews);
            adapter.notifyDataSetChanged();
            binding.progressBar.setVisibility(View.GONE);
            binding.swipeRefresh.setRefreshing(false);
        }
    }

    public static ReviewsListFragment newInstance(ArrayList<Review> data,ReviewRecyclerAdapter.OnItemClickListener listener ) {
        ReviewsListFragment fragment = new ReviewsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("reviews", data);
        args.putParcelable("listener",listener);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReviewsListBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        binding.progressBar.setVisibility(View.VISIBLE);

        binding.reviewList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.reviewList.setHasFixedSize(true);

        Bundle args = getArguments();
        if (args != null) {
            setParameters(args.getParcelableArrayList("reviews"), args.getParcelable("listener"));
        }

        adapter = new ReviewRecyclerAdapter(getLayoutInflater(),data);
        binding.reviewList.setAdapter(adapter);

        adapter.setOnItemClickListener( rowClickListener );

        binding.swipeRefresh.setOnRefreshListener(()->{
            LiveDataEvents.instance().EventReviewListReload.setValue(null);
        });

        return view;
    }

}