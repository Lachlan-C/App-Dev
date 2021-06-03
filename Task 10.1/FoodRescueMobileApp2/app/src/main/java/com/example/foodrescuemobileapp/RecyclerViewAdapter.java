package com.example.foodrescuemobileapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrescuemobileapp.model.Food;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    List<Food> foodList;
    Context context;
    OnClickListener mOnClickListener;
    ImageButton share;

    public RecyclerViewAdapter(Context ct, List<Food> _foodList, OnClickListener onClickListener) {
        context = ct;
        foodList = _foodList;
        mOnClickListener = onClickListener;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.places_to_go, parent,false);
        return new MyViewHolder(view, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.title.setText(foodList.get(position).getTitle());
        holder.desc.setText(foodList.get(position).getDesc());

        byte[] image = foodList.get(position).getImage();
        Bitmap bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);
        holder.myImage.setImageBitmap(bmp);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((home_screen)context).shareActivity(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, desc;
        ImageView myImage;
        OnClickListener onClickListener;

        public MyViewHolder(@NonNull View itemView, OnClickListener onClickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.TextViewCityName);
            desc = itemView.findViewById(R.id.TextViewCityInfo);
            myImage = itemView.findViewById(R.id.CityImage);
            share = itemView.findViewById(R.id.shareButton);
            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(getAdapterPosition());
        }
    }

    public interface OnClickListener {
        void onClick(int position);
    }
}
