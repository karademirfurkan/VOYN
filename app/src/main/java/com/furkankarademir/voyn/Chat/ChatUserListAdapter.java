package com.furkankarademir.voyn.Chat;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.MenuPage;
import com.furkankarademir.voyn.Transportation.TransportationAdapter;
import com.furkankarademir.voyn.Transportation.TransportationDetailActivity;
import com.furkankarademir.voyn.databinding.ChattedUsersListRowBinding;
import com.furkankarademir.voyn.databinding.RecyclerTransportationRowBinding;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatUserListAdapter extends RecyclerView.Adapter<ChatUserListAdapter.ChatUserListHolder> {

    private ArrayList<User> users;
    public  ChatUserListAdapter(ArrayList<User> users)
    {
        this.users = users;
    }

    @NonNull
    @Override
    public ChatUserListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChattedUsersListRowBinding chattedUsersListRowBinding = ChattedUsersListRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChatUserListAdapter.ChatUserListHolder(chattedUsersListRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatUserListHolder holder, int position)
    {
        holder.binding.name.setText(users.get(position).getName());
        holder.binding.surname.setText(users.get(position).getSurname());

        //we cannot read mail and password!!!!!!!!!!! Whyyyyyy????
        holder.binding.mail.setText(users.get(position).getMail());
        holder.binding.password.setText(users.get(position).getPassword());



        //should be updated
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), MenuPage.class);
                Toast.makeText(v.getContext(), "be patient, we did not prepared this page yet", Toast.LENGTH_LONG).show();
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class ChatUserListHolder extends RecyclerView.ViewHolder
    {
        private ChattedUsersListRowBinding binding;
        public ChatUserListHolder(ChattedUsersListRowBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void updateData(ArrayList<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

}
