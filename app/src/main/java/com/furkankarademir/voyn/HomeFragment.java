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

//import com.furkankarademir.voyn.Accomodation.AccomodationActivity;
import com.furkankarademir.voyn.Accomodation.AccommodationActivity;
import com.furkankarademir.voyn.Sport.SportsActivity;
import com.furkankarademir.voyn.Transportation.TransportationActivity;

public class HomeFragment extends Fragment {

    private String userID;

    private ImageView imageView;
    private ImageView imageView2;
    private int currentImageIndex = 0;
    private int[] images = {R.drawable.advitisement_1, R.drawable.advertisement_2, R.drawable.advertisement_3};
    private int[] images2 = {R.drawable.first_order_icon, R.drawable.second_order_icon, R.drawable.third_order_icon};
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
        imageView = view.findViewById(R.id.imageView);
        imageView2 = view.findViewById(R.id.orderImage);
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
                Intent intent = new Intent(view.getContext(), AccommodationActivity.class);
                startActivity(intent);
            }
        });

        Button sports = view.findViewById(R.id.sportsButton);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), com.furkankarademir.voyn.Sport.SportsActivity.class);
                startActivity(intent);
            }
        });

        Button myActivities = view.findViewById(R.id.button8);
        myActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), com.furkankarademir.voyn.myactivitiesclasses.myActivities.class);
                startActivity(intent);
            }
        });




        //for animation
        imageView.setImageResource(images[0]);
        imageView2.setImageResource(images2[0]);

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                imageView.setImageResource(images[currentImageIndex % 3]);
                imageView2.setImageResource(images2[currentImageIndex % 3]);

                currentImageIndex++;

                handler.postDelayed(this, 2500);
            }
        };

        handler.post(runnable);
    }
}