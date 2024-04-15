package com.furkankarademir.voyn.Chat;

import android.annotation.SuppressLint;
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
        System.out.println("sanırım bu user " + users.get(position).getName());
        final User o = users.get(position);
        System.out.println(o.getId() + "jkenfljdnl");

        System.out.println(o);

        holder.binding.surname.setText(users.get(position).getSurname());

        //we cannot read mail and password!!!!!!!!!!! Whyyyyyy????
        holder.binding.mail.setText(users.get(position).getMail());
        holder.binding.password.setText(users.get(position).getPassword());


        //should be updated
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("oldu2 " + o.getId());

                Intent intent = new Intent(v.getContext(), ChatInBetweenPage.class);
                intent.putExtra("selectedUser", o);
                v.getContext().startActivity(intent);
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
