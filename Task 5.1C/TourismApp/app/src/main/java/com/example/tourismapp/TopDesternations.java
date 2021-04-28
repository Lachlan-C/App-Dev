package com.example.tourismapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TopDesternations extends RecyclerView.Adapter<TopDesternations.MyViewHolder> {
    int images[];
    Context context;
    OnClickListenerTop mOnClickListenerTop;

    public TopDesternations(Context ct, int img[], OnClickListenerTop onClickListenerTop) {
        context = ct;
        images = img;
        mOnClickListenerTop = onClickListenerTop;
    }

    @NonNull
    @Override
    public TopDesternations.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.top_dest, parent,false);
        return new MyViewHolder(view, mOnClickListenerTop);
    }

    @Override
    public void onBindViewHolder(@NonNull TopDesternations.MyViewHolder holder, int position) {
        holder.myImage.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView myImage;
        OnClickListenerTop onClickListenerTop;

        public MyViewHolder(@NonNull View itemView, OnClickListenerTop onClickListenerTop) {
            super(itemView);
            myImage = itemView.findViewById(R.id.CityImage);
            this.onClickListenerTop = onClickListenerTop;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListenerTop.onClick(getAdapterPosition());
        }
    }
    public interface OnClickListenerTop {
        void onClick(int position);
    }
}
