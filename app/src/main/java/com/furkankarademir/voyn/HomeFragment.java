package com.furkankarademir.voyn;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.furkankarademir.voyn.ProfileClasses.Profile;
import com.furkankarademir.voyn.Transportation.TransportationActivity;

public class HomeFragment extends Fragment {

    private Profile thisUsersProfile;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            thisUsersProfile = (Profile) getArguments().getSerializable("thisUsersProfile");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button transportation = view.findViewById(R.id.button2);
        transportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), TransportationActivity.class);
                startActivity(intent);
                //burda normalde finish yazıyorduk ama finish tanımıyor

            }
        });
    }
}