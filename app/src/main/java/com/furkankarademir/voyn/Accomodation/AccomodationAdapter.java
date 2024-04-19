package com.furkankarademir.voyn.Accomodation;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.furkankarademir.voyn.databinding.RecyclerAccomodationRowBinding;
import com.furkankarademir.voyn.myactivitiesclasses.myAccommodationActivityDetails;

import java.util.ArrayList;
import java.util.HashMap;

public class AccomodationAdapter extends RecyclerView.Adapter<AccomodationAdapter.AccomodationHolder> {

    private ArrayList<HashMap<String, Object>> accomodationActivities;

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
        String numberOfInhabitants = accomodationActivities.get(position).get("numberOfInhabitants").toString();

        holder.binding.AccomodationDateID.setText(date);
        holder.binding.placeID.setText(place);
        holder.binding.personLimitID.setText(numberOfInhabitants);
        holder.binding.genderID.setText(gender);


        if(accommodationAdapterOption == 0)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), AccomodationDetailActivity.class);
                    intent.putExtra("accommodations", accomodationActivities.get(position));
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

    @Override
    public int getItemCount() {
        return accomodationActivities.size();
    }
}
