package com.example.tourismapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Display extends Fragment {

    TextView TitleTextView, InfoTextView;
    ImageView Image;
    View view;

    public Display() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Display newInstance(String param1, String param2) {
        Display fragment = new Display();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_display, container, false);
        if (getArguments() != null) {
            TitleTextView = (TextView) view.findViewById(R.id.TitleView);
            InfoTextView = (TextView) view.findViewById(R.id.InfoView);
            Image = (ImageView) view.findViewById(R.id.imageView);

            String Title = getArguments().getString("Title");
            String Info = getArguments().getString("Info");
            Integer image = getArguments().getInt("Image");

            TitleTextView.setText(Title);
            InfoTextView.setText(Info);
            Image.setImageResource(image);
        }
        return view;
    }
}