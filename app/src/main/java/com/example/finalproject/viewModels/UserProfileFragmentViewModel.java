package com.example.finalproject.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.finalproject.model.Review;
import com.example.finalproject.repositories.ReviewRepository;
import java.util.List;

public class UserProfileFragmentViewModel extends ViewModel {
    private LiveData<List<Review>> reviewList = ReviewRepository.instance.getUserReviews();

    public LiveData<List<Review>> getReviewListData(){
        return reviewList;
    }
}
