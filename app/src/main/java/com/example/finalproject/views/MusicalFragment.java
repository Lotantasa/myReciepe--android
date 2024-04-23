package com.example.finalproject.views;

import static java.sql.DriverManager.println;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.finalproject.R;
import com.example.finalproject.ReviewRecyclerAdapter;
import com.example.finalproject.ReviewsListFragment;
import com.example.finalproject.databinding.FragmentMusicalBinding;
import com.example.finalproject.model.LiveDataEvents;
import com.example.finalproject.model.Recipe;
import com.example.finalproject.model.Review;
import com.example.finalproject.repositories.ReviewRepository;
import com.squareup.picasso.Picasso;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class MusicalFragment extends Fragment {
    ArrayList<Review> reviewsList = new ArrayList<>();
    ReviewsListFragment reviewListFragment;
    Recipe currRecipe;
    FragmentMusicalBinding binding;
    ReviewRecyclerAdapter.OnItemClickListener reviewRowOnClickListener;

    public static MusicalFragment newInstance(String musicalId, String param2) {
        MusicalFragment fragment = new MusicalFragment();
        return fragment;
    }

    private void setParameters(Recipe recipe) {
        this.currRecipe = recipe;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMusicalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        reviewListFragment = (ReviewsListFragment) getChildFragmentManager().findFragmentById(R.id.musicalFc);

        reviewRowOnClickListener = new ReviewRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int pos) {}

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {

            }
        };

        if (reviewListFragment != null) {
            reviewListFragment.setParameters(reviewsList, reviewRowOnClickListener);
        }

        setParameters(MusicalFragmentArgs.fromBundle(getArguments()).getRecipe());
        initScreen();

        reloadData();

        LiveDataEvents.instance().EventReviewListReload.observe(getViewLifecycleOwner(), unused -> {
            reloadData();
        });

        ReviewRepository.instance.getAllMusicalReviews(currRecipe.getId(),(reviewsData) -> {
            reviewsList = reviewsData;
            if (reviewListFragment != null) {
                reviewListFragment.setParameters(reviewsList, reviewRowOnClickListener);
            }
        });

        NavDirections action =
                MusicalFragmentDirections.actionMusicalFragmentToNewReviewFragment(null
                        , currRecipe.getId());
        binding.addReviewBtn.setOnClickListener(Navigation.createNavigateOnClickListener(action));

        return view;
    }

    void reloadData() {
        ReviewRepository.instance.getAllMusicalReviews(currRecipe.getId(),(reviewsData) -> {
            reviewsList = reviewsData;
            if (reviewListFragment != null) {
                reviewListFragment.setParameters(reviewsList, reviewRowOnClickListener);
            }
        });
    }

    void initScreen() {
        binding.nameTv.setText(currRecipe.title);
        binding.descTv.setText(currRecipe.ingredients.toString().replaceAll("\\[|\\]", ""));
        binding.taglineTv.setText("Taken From " + currRecipe.takenFrom);
        binding.locationTv.setText(currRecipe.healthLabels.toString().replaceAll("\\[|\\]", ""));
        binding.priceTv.setText(currRecipe.cautions.toString().replaceAll("\\[|\\]", ""));


        if(currRecipe.img != null) {
            Picasso.get().load(currRecipe.img).placeholder(R.drawable.default_pic).into(binding.musicalIv);
        } else {
            binding.musicalIv.setImageResource(R.drawable.default_pic);
        }
    }
}