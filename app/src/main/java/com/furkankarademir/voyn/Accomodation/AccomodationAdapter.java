package com.furkankarademir.voyn.Accomodation;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.databinding.RecyclerAccomodationRowBinding;
import com.furkankarademir.voyn.myactivitiesclasses.myAccommodationActivityDetails;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class AccomodationAdapter extends RecyclerView.Adapter<AccomodationAdapter.AccomodationHolder> {

    private ArrayList<HashMap<String, Object>> accomodationActivities;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private User user;


    private int accommodationAdapterOption;
    public  AccomodationAdapter(ArrayList<HashMap<String, Object>> accomodationActivities, int i)
    {
        this.accomodationActivities = accomodationActivities;
        accommodationAdapterOption = i;
    }


    public class AccomodationHolder extends RecyclerView.ViewHolder
    {
        private RecyclerAccomodationRowBinding binding;
        public AccomodationHolder(RecyclerAccomodationRowBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public AccomodationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        RecyclerAccomodationRowBinding accomodationRowBinding = RecyclerAccomodationRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AccomodationHolder(accomodationRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AccomodationHolder holder, @SuppressLint("RecyclerView") int position)
    {
        HashMap<String, Object> currentActivity = accomodationActivities.get(position);
        if(currentActivity == null)
        {
            return;
        }
        String gender = (String) accomodationActivities.get(position).get("gender");
        String type = (String) accomodationActivities.get(position).get("type");
        String date = (String) accomodationActivities.get(position).get("date");
        String place = (String) accomodationActivities.get(position).get("place");
        ArrayList<String> participants = (ArrayList<String>) accomodationActivities.get(position).get("participantsId");
        String numberOfInhabitants = participants.size() + "/" + accomodationActivities.get(position).get("numberOfInhabitants").toString();
        ArrayList<String> invitedList = (ArrayList<String>) accomodationActivities.get(position).get("invitedId");
        double minStar;
        if (accomodationActivities.get(position).get("minStar") == null)
        {
            minStar = 0;
        }
        else
        {
            minStar = Double.parseDouble(accomodationActivities.get(position).get("minStar").toString());
        }


        boolean isHome = true;
        String home = "home";

        if (home.length() != type.length())
        {
            isHome = false;
        }
        else
        {
            for (int i = 0; i < type.length(); i++) {
                if (    type.charAt(i) != Character.toLowerCase(home.charAt(i)) &&
                        type.charAt(i) != Character.toUpperCase(home.charAt(i))) {
                    isHome = true;
                }
            }
        }
        holder.binding.AccomodationDateID.setText(date);
        holder.binding.placeID.setText(place);
        holder.binding.personLimitID.setText(numberOfInhabitants);
        holder.binding.genderID.setText(gender);
        
        System.out.println("participants: " + participants);
        DocumentReference docRef = db.collection("Users").document(auth.getUid());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    user = documentSnapshot.toObject(User.class);
                    if(accommodationAdapterOption == 0)
                    {
                        if (user.getStar() < minStar || participants.contains(auth.getUid()) || participants.size() == Integer.parseInt(accomodationActivities.get(position).get("numberOfInhabitants").toString()))
                        {
                            holder.binding.accommodationLinearLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.red_row_view));
                        }
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(holder.itemView.getContext(), AccomodationDetailActivity.class);
                                intent.putExtra("accommodation", accomodationActivities.get(position));
                                boolean isRed = user.getStar() < minStar || participants.contains(auth.getUid()) || participants.size() == Integer.parseInt(accomodationActivities.get(position).get("numberOfInhabitants").toString());
                                intent.putExtra("isRed", isRed);
                                holder.itemView.getContext().startActivity(intent);
                            }
                        });
                    }
                    else
                    {
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(holder.itemView.getContext(), myAccommodationActivityDetails.class);
                                intent.putExtra("accommodation", accomodationActivities.get(position));
                                holder.itemView.getContext().startActivity(intent);
                            }
                        });
                    }
                }
            }
        });

        if(isHome == true)
        {
            holder.binding.accommodationLinearLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.blu_row_view));
        }
    }
    @Override
    public int getItemCount() {
        return accomodationActivities.size();
    }
}
