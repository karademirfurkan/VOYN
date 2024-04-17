package com.furkankarademir.voyn.Sport;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.furkankarademir.voyn.databinding.RecyclerSportRowBinding;
import com.furkankarademir.voyn.myactivitiesclasses.mySportsActivityDetails;

import java.util.ArrayList;
import java.util.HashMap;

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.SportHolder> {

    private ArrayList<HashMap<String, Object>> sportActivities;

    private int sportAdapterOption;
    public  SportAdapter(ArrayList<HashMap<String, Object>> sportActivities, int i)
    {
        this.sportActivities = sportActivities;
        sportAdapterOption = i;
    }
    public class SportHolder extends RecyclerView.ViewHolder
    {
        private RecyclerSportRowBinding binding;
        public SportHolder(RecyclerSportRowBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public SportAdapter.SportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        RecyclerSportRowBinding sportRowBinding = RecyclerSportRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SportAdapter.SportHolder(sportRowBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull SportAdapter.SportHolder holder, @SuppressLint("RecyclerView") int position)
    {
        String time = (String) sportActivities.get(position).get("time");
        String place = (String) sportActivities.get(position).get("place");
        String date = (String) sportActivities.get(position).get("date");
        String personLimit = sportActivities.get(position).get("numberOfPlayers").toString();
        String typeInfo = (String) sportActivities.get(position).get("type");

        holder.binding.time.setText(time);
        holder.binding.date.setText(date);
        holder.binding.place.setText(place);
        holder.binding.personLimit.setText(personLimit);
        holder.binding.typeInfo.setText(typeInfo);


        if(sportAdapterOption == 0)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), SportsDetailActivity.class);
                    intent.putExtra("sport", sportActivities.get(position));
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
        else
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), mySportsActivityDetails.class);
                    intent.putExtra("sport", sportActivities.get(position));
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return sportActivities.size();
    }
}
