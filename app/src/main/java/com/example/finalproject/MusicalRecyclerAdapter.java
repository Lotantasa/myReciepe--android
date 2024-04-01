package com.example.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalproject.model.Recipe;
import com.squareup.picasso.Picasso;
import java.util.List;

class MusicalViewHolder extends RecyclerView.ViewHolder{
    TextView titleTv;
    ImageView imgIv;
    List<Recipe> data;

    public MusicalViewHolder(@NonNull View itemView, List<Recipe> data, MusicalRecyclerAdapter.OnItemClickListener listener) {
        super(itemView);
        this.data = data;

        titleTv = itemView.findViewById(R.id.musical_title_tv);
        imgIv = itemView.findViewById(R.id.musical_img_iv);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }

    public void bind(Recipe recipe, int pos) {
        titleTv.setText(recipe.getTitle());
        if(recipe.getImg() != null) {
            Picasso.get().load(recipe.getImg()).placeholder(R.drawable.default_pic).into(imgIv);
        } else {
            imgIv.setImageResource(R.drawable.default_pic);
        }
    }
}

public class MusicalRecyclerAdapter extends RecyclerView.Adapter<MusicalViewHolder>{

    MusicalRecyclerAdapter.OnItemClickListener listener;
    LayoutInflater inflater;
    List<Recipe> data;

    public void setData(List<Recipe> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public MusicalRecyclerAdapter(LayoutInflater inflater, List<Recipe> data){
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
        Recipe recipe = data.get(position);
        holder.bind(recipe, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public void setOnItemClickListener(MusicalRecyclerAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}
