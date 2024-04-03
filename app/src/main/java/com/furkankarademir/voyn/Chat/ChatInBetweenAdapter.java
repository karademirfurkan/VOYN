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

import java.util.ArrayList;

public class ChatInBetweenAdapter extends RecyclerView.Adapter<ChatInBetweenAdapter.ChatInBetweenHolder>
{
    private ArrayList<Message> messages;
    public ChatInBetweenAdapter(ArrayList<Message> messages)
    {
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
        if(messages.get(position).isMine())
        {
            holder.binding.otherMessage.setVisibility(View.INVISIBLE);
            holder.binding.myMessage.setText(messages.get(position).getMessageText());
        }
        else
        {
            holder.binding.myMessage.setVisibility(View.INVISIBLE);
            holder.binding.otherMessage.setText(messages.get(position).getMessageText());
        }


    }

    @Override
    public int getItemCount() {
        return messages.size();
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
