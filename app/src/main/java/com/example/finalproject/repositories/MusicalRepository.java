package com.example.finalproject.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.finalproject.api.MusicalApi;
import com.example.finalproject.model.Model;
import com.example.finalproject.model.Musical;
import com.example.finalproject.model.MusicalsResult;
import com.example.finalproject.model.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MusicalRepository {
    final public static MusicalRepository instance = new MusicalRepository();
    final String BASE_URL = "https://api.londontheatredirect.com/rest/v2/";
    Retrofit retrofit;
    MusicalApi musicalApi;

    private MusicalRepository() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        musicalApi = retrofit.create(MusicalApi.class);
    }

    public LiveData<List<Musical>> getMusicals() {
        MutableLiveData<List<Musical>> data = new MutableLiveData<>();
        Call<MusicalsResult> call = musicalApi.getAllMusicals();
        call.enqueue(new Callback<MusicalsResult>() {

            @Override
            public void onResponse(Call<MusicalsResult> call,
                                   Response<MusicalsResult> response) {
                if(response.isSuccessful()) {
                    MusicalsResult musicalsList = response.body();
                    if (musicalsList.getEvents()!= null){
                        data.setValue(musicalsList.getEvents());
                    }
                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<MusicalsResult> call,
                                  Throwable t) {
                t.printStackTrace();
            }
        });
        return data;
    }

    public static class FbUserRepository {
        CollectionReference usersCollection;

        public FbUserRepository() {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(false).build();
            db.setFirestoreSettings(settings);
            usersCollection  = db.collection("users");
        }

        public void getUserData(String userId, Model.Listener<User> listener) {
            usersCollection.document(userId).get().addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User user = User.fromJson(task.getResult().getData());
                        listener.onComplete(user);
                    }
                }
            });
        }

        public void createUser(User user, Model.Listener<Void> listener) {
            usersCollection.document(user.getUid()).set(user.toJson()).addOnCompleteListener((task) -> {
                listener.onComplete(null);
            });
        }

        public void updateUser(User user, Model.Listener<Void> listener) {
            usersCollection.document(user.getUid()).update(user.toJson()).addOnCompleteListener((task) -> {
                listener.onComplete(null);
            });
        }
    }
}
