package com.furkankarademir.voyn.Transportation;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.furkankarademir.voyn.databinding.RecyclerTransportationRowBinding;
import com.furkankarademir.voyn.myactivitiesclasses.myTransportationActivityDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class TransportationAdapter extends RecyclerView.Adapter<TransportationAdapter.TransportationHolder> {

    private ArrayList<HashMap<String, Object>> transportationActivities;

    private int transportationAdapterOption;
    public  TransportationAdapter(ArrayList<HashMap<String, Object>> transportationActivities, int i)
    {
        this.transportationActivities = transportationActivities;
        transportationAdapterOption = i;
    }


    public class TransportationHolder extends RecyclerView.ViewHolder
    {
        private RecyclerTransportationRowBinding binding;
        public TransportationHolder(RecyclerTransportationRowBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public TransportationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        RecyclerTransportationRowBinding transportationRowBinding = RecyclerTransportationRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TransportationHolder(transportationRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransportationHolder holder, int position)
    {
        String time = (String) transportationActivities.get(position).get("time");
        String departure = (String) transportationActivities.get(position).get("departure");
        String date = (String) transportationActivities.get(position).get("date");
        String destination = (String) transportationActivities.get(position).get("destination");
        String personLimit = transportationActivities.get(position).get("seats").toString();

        holder.binding.time.setText(time);
        holder.binding.date.setText(date);
        holder.binding.whereFrom.setText(departure);
        holder.binding.whereTo.setText(destination);
        holder.binding.personLimit.setText(personLimit);


        if(transportationAdapterOption == 0)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), TransportationDetailActivity.class);
                    intent.putExtra("transportation", transportationActivities.get(position));
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
        else
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), myTransportationActivityDetails.class);
                    intent.putExtra("transportation", transportationActivities.get(position));
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return transportationActivities.size();
    }
}
