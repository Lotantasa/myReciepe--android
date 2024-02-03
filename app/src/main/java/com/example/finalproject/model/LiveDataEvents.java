package com.example.finalproject.model;

import androidx.lifecycle.MutableLiveData;

public class LiveDataEvents {
    private static final LiveDataEvents _instance = new LiveDataEvents();

    public static LiveDataEvents instance() {
        return _instance;
    }

    private LiveDataEvents(){
    }

    final public MutableLiveData<Void> EventReviewListReload = new MutableLiveData<Void>(null);

}
