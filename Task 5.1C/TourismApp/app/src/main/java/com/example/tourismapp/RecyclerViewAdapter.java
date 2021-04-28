package com.example.tourismapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    String cityList[], cityInfo[];
    int images[];
    Context context;
    OnClickListenerBottom mOnClickListenerBottom;

    public RecyclerViewAdapter(Context ct, String title[], String info[], int img[], OnClickListenerBottom onClickListenerBottom) {
        context = ct;
        cityList = title;
        cityInfo = info;
        images = img;
        mOnClickListenerBottom = onClickListenerBottom;

    }


    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.places_to_go, parent,false);
        return new MyViewHolder(view, mOnClickListenerBottom);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.title.setText(cityList[position]);
        holder.desc.setText(cityInfo[position]);
        holder.myImage.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return cityList.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, desc;
        ImageView myImage;
        OnClickListenerBottom onClickListenerBottom;

        public MyViewHolder(@NonNull View itemView, OnClickListenerBottom onClickListenerBottom) {
            super(itemView);
            title = itemView.findViewById(R.id.TextViewCityName);
            desc = itemView.findViewById(R.id.TextViewCityInfo);
            myImage = itemView.findViewById(R.id.CityImage);
            this.onClickListenerBottom = onClickListenerBottom;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListenerBottom.onClick(getAdapterPosition());
        }
    }

    public interface OnClickListenerBottom {
        void onClick(int position);
    }
}
