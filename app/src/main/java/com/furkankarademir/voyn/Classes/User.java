package com.furkankarademir.voyn.Classes;

import static android.content.ContentValues.TAG;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.furkankarademir.voyn.ProfileClasses.Profile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class User implements Serializable{
    private double star;
    private String name;
    private String surname;
    private String mail;
    private String password;

    private  String age;

    private  String department;
    private String id;

    private Profile userProfile;

    private ArrayList<String> myActivities;

    private ArrayList<String> mySportActivities;

    private ArrayList<String> myAccommodationActivities;
    private ArrayList<String> attendedActivities;


    public User()
    {
        myActivities = new ArrayList<String>();
        mySportActivities = new ArrayList<String>();
        myAccommodationActivities = new ArrayList<String>();
        attendedActivities = new ArrayList<String>();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User  (String name, String surname, String id, String age, String department)
    {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.age = age;
        this.department = department;
        myActivities = new ArrayList<String>();
        mySportActivities = new ArrayList<String>();
        myAccommodationActivities = new ArrayList<String>();
        attendedActivities = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Profile getProfile()
    {
        userProfile = new Profile(name, surname, mail,"department", "gender"); // replace "department" with the actual department
        return userProfile;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public ArrayList<String> getAttendedActivities() {
        return attendedActivities;
    }

    public void setAttendedActivities(ArrayList<String> attendedActivities) {
        this.attendedActivities = attendedActivities;
    }

    public void addActivity(String activity)
    {
        myActivities.add(activity);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        DocumentReference userDocRef = db.collection("Users").document(currentUser.getUid());

        userDocRef.update("myActivities", FieldValue.arrayUnion(activity))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    public void addSportsActivity(String activity)
    {
        mySportActivities.add(activity);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        DocumentReference userDocRef = db.collection("Users").document(currentUser.getUid());

        userDocRef.update("mySportActivities", FieldValue.arrayUnion(activity))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    public void removeSportsActivity(String activity)
    {
        mySportActivities.remove(activity);
    }

    public void addAccommodationActivity(String activity)
    {
        myAccommodationActivities.add(activity);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        DocumentReference userDocRef = db.collection("Users").document(currentUser.getUid());

        userDocRef.update("myAccommodationActivities", FieldValue.arrayUnion(activity))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    public void addAttendedActivity(String activity)
    {
        attendedActivities.add(activity);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        DocumentReference userDocRef = db.collection("Users").document(currentUser.getUid());

        userDocRef.update("attendedActivities", FieldValue.arrayUnion(activity))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }


    public void removeActivity(String activity)
    {
        myActivities.remove(activity);
    }

    public ArrayList<String> getMyActivities() {
        return myActivities;
    }

    public ArrayList<String> getMySportActivities() {
        return mySportActivities;
    }

    public ArrayList<String> getMyAccommodationActivities() {
        return myAccommodationActivities;
    }

    public void setMyActivities(ArrayList<String> myActivities) {
        this.myActivities = myActivities;
    }

    public void setMySportActivities(ArrayList<String> mySportActivities) {
        this.mySportActivities = mySportActivities;
    }

    public void setMyAccommodationActivities(ArrayList<String> myAccommodationActivities) {
        this.myAccommodationActivities = myAccommodationActivities;
    }

}
