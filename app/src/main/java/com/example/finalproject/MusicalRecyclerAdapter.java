package com.example.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalproject.model.Musical;
import com.squareup.picasso.Picasso;
import java.util.List;

class MusicalViewHolder extends RecyclerView.ViewHolder{
    TextView titleTv;
    TextView descriptionTv;
    ImageView imgIv;
    List<Musical> data;

    public MusicalViewHolder(@NonNull View itemView, List<Musical> data, MusicalRecyclerAdapter.OnItemClickListener listener) {
        super(itemView);
        this.data = data;

        titleTv = itemView.findViewById(R.id.musical_title_tv);
        descriptionTv = itemView.findViewById(R.id.musical_description_tv);
        imgIv = itemView.findViewById(R.id.musical_img_iv);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }

    public void bind(Musical musical, int pos) {
        titleTv.setText(musical.getTitle());
        descriptionTv.setText(musical.getDescription());
        if(musical.getImg() != null) {
            Picasso.get().load(musical.getImg()).placeholder(R.drawable.default_pic).into(imgIv);
        } else {
            imgIv.setImageResource(R.drawable.default_pic);
        }
    }
}

public class MusicalRecyclerAdapter extends RecyclerView.Adapter<MusicalViewHolder>{

    MusicalRecyclerAdapter.OnItemClickListener listener;
    LayoutInflater inflater;
    List<Musical> data;

    public void setData(List<Musical> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public MusicalRecyclerAdapter(LayoutInflater inflater, List<Musical> data){
        this.inflater = inflater;
        this.data = data;
    }

    @NonNull
    @Override
    public MusicalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_musical_row,parent,false);
        return new MusicalViewHolder(view, data, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicalViewHolder holder, int position) {
        Musical musical = data.get(position);
        holder.bind(musical, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static interface OnItemClickListener {
        void onItemClick(int pos);
    }

    void setOnItemClickListener(MusicalRecyclerAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}
