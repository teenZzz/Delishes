package com.example.delishes;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class RecycAdapter extends RecyclerView.Adapter<RecycAdapter.ViewHolder> {

    private List<RecipeItem> data;
    private OnItemClickListener onItemClickListener;
    private OnLikeButtonClickListener onLikeButtonClickListener;

    public RecycAdapter(List<RecipeItem> data) {
        this.data = data;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnLikeButtonClickListener {
        void onLikeButtonClick(int position, boolean isLiked);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public void setOnLikeButtonClickListener(OnLikeButtonClickListener listener) {
        onLikeButtonClickListener = listener;
    }

    public void setData(List<RecipeItem> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.receptview, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeItem item = data.get(position);

        holder.textView.setText(item.getText());

        Picasso.get().load(item.getImageUrl())
                .transform(new RoundedCornersTransformation(25, 10))
                .resize(125, 125)
                .centerCrop()
                .into(holder.imageView);

        if (item.isLiked()) {
            holder.likeButton.setColorFilter(Color.RED);
        } else {
            holder.likeButton.setColorFilter(null);
        }

        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });

        holder.likeButton.setOnClickListener(view -> {
            if (onLikeButtonClickListener != null) {
                onLikeButtonClickListener.onLikeButtonClick(position, !item.isLiked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        private View itemView;
        public ImageButton likeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            textView = itemView.findViewById(R.id.recepttext);
            imageView = itemView.findViewById(R.id.imgrecept);
            likeButton = itemView.findViewById(R.id.like_btn);
        }
    }
}
