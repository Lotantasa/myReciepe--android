package com.example.finalproject.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class Recipe implements Parcelable {

    @SerializedName("uri")
    public String id;

    @SerializedName("label")
    public String title;

    @SerializedName("image")
    public String img;

    public Recipe(String id, String title){
        this.id = id;
        this.title = title;
    }

    protected Recipe(Parcel in) {
        this(in.readString() ,in.readString());
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(img);
    }
}
