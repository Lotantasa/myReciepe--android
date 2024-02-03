package com.example.finalproject.model;
import androidx.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

@Entity
public class Review implements Parcelable {
    @PrimaryKey
    @NonNull
    public String docId;
    public String seat;
    public Float stars;
    public String content;
    public String userId;
    public Integer eventId;
    public String imgUrl;
    public Long lastUpdated;

    public Review() {}

    public Review(String seat, Float stars, String content, String userId,String docId,Integer eventId) {
        this.seat = seat;
        this.stars = stars;
        this.content = content;
        this.userId = userId;
        this.docId = docId;
        this.eventId = eventId;
    }

    public Review(String seat, Float stars, String content, String userId, String docId,Integer eventId, String ImageUrl) {
        this(seat,stars,content,userId,docId,eventId);
        setImgUrl(ImageUrl);
    }

    public Review(Parcel parcel) {
        // seat, stars,content,userId,eventId,docId,imgUrl
        this( parcel.readString(),parcel.readFloat(),parcel.readString(),parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readString());
    }

    public static Review fromJson(Map<String, Object> json, String docId) {
        String seat = (String)json.get("Seat");
        Double rateDouble = (Double)json.get("Stars");
        Float rate = rateDouble.floatValue();
        String text = (String)json.get("Content");
        String user = (String)json.get("UserId");
        String id = docId;
        Long d = (Long) json.get("EventId");
        Integer eventId = d.intValue();
        String ImageUrl = (String)json.get("ImageUrl");

        Review rv = new Review(seat, rate, text, user, id, eventId, ImageUrl);

        try{
            Timestamp time = (Timestamp)json.get("lastUpdated");
            rv.setLastUpdated(time.getSeconds());
        } catch (Exception e) {

        }

        return rv;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("Stars", getStars());
        json.put("Content", getContent());
        json.put("Seat", getSeat());
        json.put("UserId", getUserId());
        json.put("EventId", getEventId());
        json.put("ImageUrl", getImgUrl());
        json.put("lastUpdated", FieldValue.serverTimestamp());
        return json;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    // Getters
    public String getDocId() {
        return docId;
    }

    public String getUserId() {
        return userId;
    }

    public Float getStars() {
        return stars;
    }

    public String getSeat() {
        return seat;
    }

    public String getContent() {
        return content;
    }

    public Integer getEventId() { return eventId; };

    public Long getLastUpdated() {
        return lastUpdated;
    }

    // Setters
    public String getImgUrl() { return imgUrl; }

    public void setDocId(@Nonnull String docId) {
        this.docId = docId;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public void setStars(Float stars) {
        this.stars = stars;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(docId);
        dest.writeString(seat);
        dest.writeFloat(stars);
        dest.writeString(content);
        dest.writeString(userId);
        dest.writeString(imgUrl);
        dest.writeInt(eventId);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public void setLastUpdated(Long time) {
        this.lastUpdated = time;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
