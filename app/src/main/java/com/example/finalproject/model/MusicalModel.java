package com.example.finalproject.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MusicalModel {
    final public static MusicalModel instance = new MusicalModel();
    final String BASE_URL = "https://api.londontheatredirect.com/rest/v2/";
    Retrofit retrofit;
    MusicalApi musicalApi;

    private MusicalModel() {
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
}
