package com.example.finalproject.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class Musical implements Parcelable {

    @SerializedName("EventId")
    public Integer id;

    @SerializedName("Name")
    public String title;

    @SerializedName("TagLine")
    public String description;

    @SerializedName("RunningTime")
    public String time;

    @SerializedName("Description")
    public String longDescription;

    @SerializedName("CurrentPrice")
    public Double price;

    @SerializedName("MainImageUrl")
    public String img;

    public Musical(Integer id, String title, String description,
                   String time, String longDescription,
                   Double price){
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
        this.longDescription = longDescription;
        this.price = price;
    }

    protected Musical(Parcel in) {
        this(in.readInt() ,in.readString(), in.readString(),
                in.readString(), in.readString(),
                in.readDouble());
    }

    public static final Creator<Musical> CREATOR = new Creator<Musical>() {
        @Override
        public Musical createFromParcel(Parcel in) {
            return new Musical(in);
        }

        @Override
        public Musical[] newArray(int size) {
            return new Musical[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(time);
        dest.writeString(longDescription);
        dest.writeDouble(price);
        dest.writeString(img);
    }
}
