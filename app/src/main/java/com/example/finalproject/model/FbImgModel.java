package com.example.finalproject.model;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;

public class FbImgModel {
    FirebaseStorage storage;

    FbImgModel() {
        storage = FirebaseStorage.getInstance();
    }

    void uploadImage(String name, Bitmap bitmap, ImageModel.UploadImageListener listener ){
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images/"+name +".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    listener.onComplete(uri.toString());
                });
            }
        });
    }
}
