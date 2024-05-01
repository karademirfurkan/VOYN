package com.furkankarademir.voyn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.furkankarademir.voyn.Chat.ChatInBetweenAdapter;
import com.furkankarademir.voyn.Chat.Message;
import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.databinding.ChatInBetweenRowBinding;
import com.furkankarademir.voyn.databinding.EvaluatingUsersRowBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class EvaluationPageUserListAdapter extends RecyclerView.Adapter<EvaluationPageUserListAdapter.EvaluationPageUserListHolder>
{
    private ArrayList<String> users;
    public EvaluationPageUserListAdapter(ArrayList<String> users)
    {
        this.users = users;
    }
    @NonNull
    @Override
    public EvaluationPageUserListAdapter.EvaluationPageUserListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EvaluatingUsersRowBinding binding = EvaluatingUsersRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EvaluationPageUserListAdapter.EvaluationPageUserListHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EvaluationPageUserListAdapter.EvaluationPageUserListHolder holder, int position)
    {
        String name = users.get(position);
        holder.binding.nameSurname.setText("id: " + name);

        holder.binding.star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.star1.setImageResource(R.drawable.stars);
            }
        });

        holder.binding.star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.star1.setImageResource(R.drawable.stars);
                holder.binding.star2.setImageResource(R.drawable.stars);
            }
        });

        holder.binding.star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.star1.setImageResource(R.drawable.stars);
                holder.binding.star2.setImageResource(R.drawable.stars);
                holder.binding.star3.setImageResource(R.drawable.stars);
            }
        });

        holder.binding.star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.star1.setImageResource(R.drawable.stars);
                holder.binding.star2.setImageResource(R.drawable.stars);
                holder.binding.star3.setImageResource(R.drawable.stars);
                holder.binding.star4.setImageResource(R.drawable.stars);
            }
        });

        holder.binding.star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.star1.setImageResource(R.drawable.stars);
                holder.binding.star2.setImageResource(R.drawable.stars);
                holder.binding.star3.setImageResource(R.drawable.stars);
                holder.binding.star4.setImageResource(R.drawable.stars);
                holder.binding.star5.setImageResource(R.drawable.stars);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public class EvaluationPageUserListHolder extends RecyclerView.ViewHolder
    {
        private EvaluatingUsersRowBinding binding;
        public EvaluationPageUserListHolder(EvaluatingUsersRowBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}