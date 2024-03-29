package com.furkankarademir.voyn;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.ProfileClasses.Profile;
import com.furkankarademir.voyn.Transportation.TransportationActivity;

import java.io.Serializable;

public class HomeFragment extends Fragment {

    private String userID;

    private ImageView imageView;
    private int currentImageIndex = 0;
    private int[] images = {R.drawable.advitisement_1, R.drawable.advertisement_2, R.drawable.advertisement_3};
    private Handler handler;
    private Runnable runnable;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            userID = (String) getArguments().getSerializable("UserID");
        }

    }

    /*
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove callbacks to avoid memory leaks
        handler.removeCallbacks(runnable);
    }
     */



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imageView = view.findViewById(R.id.imageView); // Initialize imageView
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button transportation = view.findViewById(R.id.button2);
        transportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), TransportationActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                //burda normalde finish yazıyorduk ama finish tanımıyor

            }



        });


        Button homeAndDorm = view.findViewById(R.id.button5);
        homeAndDorm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(view.getContext(), HomeAndDorm.class);
                //startActivity(intent);
            }
        });

        Button sports = view.findViewById(R.id.button7);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(view.getContext(), Sports.class);
                //startActivity(intent);
            }
        });


        //for animation
        imageView.setImageResource(images[0]);

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                imageView.setImageResource(images[currentImageIndex % 3]);

                currentImageIndex++;

                handler.postDelayed(this, 4000);

            }
        };

        handler.post(runnable);
    }
}