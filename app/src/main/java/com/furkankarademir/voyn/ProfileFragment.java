package com.furkankarademir.voyn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.ProfileClasses.EditProfile;
import com.furkankarademir.voyn.databinding.ActivitySignUpPageBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private User user;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DocumentReference docRef = db.collection("Users").document(auth.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    user = documentSnapshot.toObject(User.class);
                    TextView name = view.findViewById(R.id.nameInProfile);
                    name.setText(user.getName() + " " + user.getSurname());

                    TextView email = view.findViewById(R.id.mailInProfile);
                    email.setText(user.getMail());

                    TextView age = view.findViewById(R.id.ageInProfile);
                    age.setText(user.getAge());

                    TextView department = view.findViewById(R.id.departmentInProfile);
                    department.setText(user.getDepartment());

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);
                    Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

                    Canvas canvas = new Canvas(mutableBitmap);

                    Paint paint = new Paint();
                    paint.setColor(Color.RED); // change color as needed
                    paint.setTextSize(100); // change text size as needed

                    String starText = String.valueOf(user.getStar());
                    canvas.drawText(starText, 200, 300, paint); // change position as needed

                    ImageView imageView = view.findViewById(R.id.imageView14); 
                    imageView.setImageBitmap(mutableBitmap);
                }
            }
        });

        Button editProfile = view.findViewById(R.id.editProfileButton);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
            }
        });

        Button logOut = view.findViewById(R.id.logOutButton);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}