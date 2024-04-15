package com.furkankarademir.voyn.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.furkankarademir.voyn.Transportation.TransportationAdapter;
import com.furkankarademir.voyn.databinding.ChatInBetweenRowBinding;
import com.furkankarademir.voyn.databinding.ChattedUsersListRowBinding;
import com.furkankarademir.voyn.databinding.RecyclerTransportationRowBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatInBetweenAdapter extends RecyclerView.Adapter<ChatInBetweenAdapter.ChatInBetweenHolder>
{
    private ArrayList<Message> messages;
    private FirebaseAuth auth;
    public ChatInBetweenAdapter(ArrayList<Message> messages)
    {
        auth = FirebaseAuth.getInstance();
        this.messages = messages;
    }
    @NonNull
    @Override
    public ChatInBetweenAdapter.ChatInBetweenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChatInBetweenRowBinding chatInBetweenRowBinding = ChatInBetweenRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChatInBetweenAdapter.ChatInBetweenHolder(chatInBetweenRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatInBetweenAdapter.ChatInBetweenHolder holder, int position)
    {

        if (messages.get(position).getSenderId().equals(auth.getUid())) {
            holder.binding.leftChatLayout.setVisibility(View.GONE);
            holder.binding.rightChatLayout.setVisibility(View.VISIBLE);
            holder.binding.myMessage.setText(messages.get(position).getMessageText());
            System.out.println("ben");
        } else {
            System.out.println("sen");
            holder.binding.rightChatLayout.setVisibility(View.GONE);
            holder.binding.leftChatLayout.setVisibility(View.VISIBLE);
            holder.binding.otherMessage.setText(messages.get(position).getMessageText());
        }



    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public class ChatInBetweenHolder extends RecyclerView.ViewHolder
    {
        private ChatInBetweenRowBinding binding;
        public ChatInBetweenHolder(ChatInBetweenRowBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
