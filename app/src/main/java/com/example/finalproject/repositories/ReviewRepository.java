package com.example.finalproject.repositories;

import androidx.lifecycle.LiveData;

import com.example.finalproject.model.AppLocalDb;
import com.example.finalproject.model.Model;
import com.example.finalproject.model.Review;
import com.example.finalproject.model.User;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ReviewRepository {

    final public static ReviewRepository instance = new ReviewRepository();
    private Executor executor = Executors.newSingleThreadExecutor();
    FbReviewRepository fbReviewModel = new FbReviewRepository();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public void getAllMusicalReviews(String eventId,
                                     Model.Listener<ArrayList<Review>> callback) {
        fbReviewModel.getAllMusicalReviews(eventId,callback);
    }

    private LiveData<List<Review>> userReviewList;
    public LiveData<List<Review>> getUserReviews() {
        if(userReviewList == null){
            userReviewList = localDb.reviewDao().getUserReviews(UserRepository.instance.getUserId());
            refreshAllUserReviews();
        }
        return userReviewList;
    }
    public void refreshAllUserReviews() {
        Long localLastUSerReviewUpdate = User.getLocalLastReviewUpdate();
        fbReviewModel.getUserReviewsSince(localLastUSerReviewUpdate, UserRepository.instance.getUserId() , list -> {
            executor.execute(()-> {
                Long time = localLastUSerReviewUpdate;
                for (Review rv : list) {
                    localDb.reviewDao().insertAll(rv);

                    if (time < rv.getLastUpdated()) {
                        time = rv.getLastUpdated();
                    }
                }

                User.setLocalLastReviewUpdate(time);
            });
        });

    }

    public void addReview(Review review, Model.Listener<Void> callback) {
        fbReviewModel.addReview(review, (unused) -> {
            refreshAllUserReviews();
            callback.onComplete(null);
        });
    }

    public void updateReview(Review review, Model.Listener<Void> callback) {
        fbReviewModel.updateReview(review, callback);
    }

    public static class FbReviewRepository {
        FirebaseFirestore db;

        public FbReviewRepository() {
            db = FirebaseFirestore.getInstance();
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(false).build();
            db.setFirestoreSettings(settings);
        }

        public void getAllMusicalReviews(String eventId,
                                         Model.Listener<ArrayList<Review>> callback) {
            db.collection("reviews").whereEqualTo("EventId", eventId).get().addOnCompleteListener((task) -> {
                ArrayList<Review> list = new ArrayList<>();
                if(task.isSuccessful()) {
                    QuerySnapshot jsonsList = task.getResult();
                    for (DocumentSnapshot json: jsonsList) {

                        Review rv = Review.fromJson(json.getData(), json.getId());
                        list.add(rv);
                    }
                }
                callback.onComplete(list);
            });
        }

        public void getUserReviewsSince(Long since, String user, Model.Listener<ArrayList<Review>> callback) {
            db.collection("reviews").whereEqualTo("UserId", user).whereGreaterThanOrEqualTo("lastUpdated", new Timestamp(since,0)).get().addOnCompleteListener((task) -> {
                ArrayList<Review> list = new ArrayList<>();
                if(task.isSuccessful()) {
                    QuerySnapshot jsonsList = task.getResult();
                    for (DocumentSnapshot json: jsonsList) {
                        Review rv = Review.fromJson(json.getData(), json.getId());
                        list.add(rv);
                    }
                }
                callback.onComplete(list);
            });
        }

        public void addReview(Review review, Model.Listener<Void> listener) {
            db.collection("reviews").document(review.getDocId()).set(review.toJson())
                    .addOnCompleteListener((task) -> {

                        listener.onComplete(null);
                    });
        }

        public void updateReview(Review review, Model.Listener<Void> listener) {
            db.collection("reviews").document(review.getDocId()).update(review.toJson())
                    .addOnCompleteListener(task ->{
                listener.onComplete(null);
            });
        }

    }
}
