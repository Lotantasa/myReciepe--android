package com.example.finalproject;

import androidx.recyclerview.widget.RecyclerView;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.finalproject.model.Review;
import com.squareup.picasso.Picasso;
import java.util.List;

class ReviewViewHolder extends RecyclerView.ViewHolder{
    TextView seatTv;
    TextView textTv;
    RatingBar ratingBar;
    ImageView reviewIv;

    List<Review> data;
    public ReviewViewHolder(@NonNull View itemView, List<Review> data, ReviewRecyclerAdapter.OnItemClickListener listener) {
        super(itemView);
        this.data = data;
        seatTv = itemView.findViewById(R.id.reviewrow_seat_tv);
        textTv = itemView.findViewById(R.id.reviewrow_review_tv);
        ratingBar = itemView.findViewById(R.id.reviewrow_ratingBar);
        reviewIv = itemView.findViewById(R.id.reviewrow_img);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }

    public void bind(Review review, int pos) {
        seatTv.setText(review.seat);
        textTv.setText(review.content);
        ratingBar.setRating(review.stars);
        if(review.getImgUrl() != null) {
            Picasso.get().load(review.getImgUrl()).placeholder(R.drawable.bear).into(reviewIv);
        } else {
            reviewIv.setImageResource(R.drawable.bear);
        }
    }
}

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewViewHolder>{
    OnItemClickListener listener;
    LayoutInflater inflater;
    List<Review> data;

    public static interface OnItemClickListener extends Parcelable {
        void onItemClick(int pos);
    }

    public void setData(List<Review> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public ReviewRecyclerAdapter(LayoutInflater inflater, List<Review> data){
        this.inflater = inflater;
        this.data = data;
    }

    void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.review_row,parent,false);
        return new ReviewViewHolder(view, data, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = data.get(position);
        holder.bind(review,position);
    }

    @Override
    public int getItemCount() {
        if(data == null) return 0;
        return data.size();
    }

}