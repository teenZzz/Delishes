package com.example.delishes;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class RecycAdapter extends RecyclerView.Adapter<RecycAdapter.ViewHolder> {

    private List<RecipeItem> data;
    private OnItemClickListener onItemClickListener;

    public RecycAdapter(List<RecipeItem> data) {
        this.data = data;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
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


        String receptText = item.getReceptText();

        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recepttext);
            imageView = itemView.findViewById(R.id.imgrecept);
        }

    }
}
