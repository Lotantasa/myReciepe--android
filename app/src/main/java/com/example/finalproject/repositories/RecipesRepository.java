package com.example.finalproject.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.finalproject.api.RecipesApi;
import com.example.finalproject.model.Model;
import com.example.finalproject.model.Recipe;
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

public class RecipesRepository {
    final public static RecipesRepository instance = new RecipesRepository();
    final String BASE_URL = "https://api.edamam.com/api/recipes/v2";
    Retrofit retrofit;
    RecipesApi recipesApi;

    private RecipesRepository() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        recipesApi = retrofit.create(RecipesApi.class);
    }

    public LiveData<List<Recipe>> getRecipes() {
        MutableLiveData<List<Recipe>> data = new MutableLiveData<>();
        Call<MusicalsResult> call = recipesApi.getAllRecipes();
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
