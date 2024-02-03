package com.example.finalproject.model;

import android.graphics.Bitmap;

public class ImageModel {
    final public static ImageModel instance = new ImageModel();
    private  FbImgModel fbImgModel = new FbImgModel();


    public interface UploadImageListener{
        void onComplete(String uri);
    }
    public void uploadImage(String name, Bitmap bitmap, UploadImageListener callback) {
        fbImgModel.uploadImage(name, bitmap, callback);
    }

}
