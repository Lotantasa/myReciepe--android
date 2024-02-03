package com.example.finalproject.model;


public class Model {
    private static final Model _instance = new Model();

    public static Model instance() {
        return _instance;
    }

    private Model() {
    }

    public interface Listener<T> {
        void onComplete(T data);
    }
}