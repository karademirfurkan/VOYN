package com.furkankarademir.voyn.Chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.furkankarademir.voyn.Classes.User;
import com.furkankarademir.voyn.MenuPage;
import com.furkankarademir.voyn.ProfileFragment;
import com.furkankarademir.voyn.R;
import com.furkankarademir.voyn.Transportation.TransportationAdapter;
import com.furkankarademir.voyn.Transportation.TransportationDetailActivity;
import com.furkankarademir.voyn.databinding.ChattedUsersListRowBinding;
import com.furkankarademir.voyn.databinding.RecyclerTransportationRowBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatUserListAdapter extends RecyclerView.Adapter<ChatUserListAdapter.ChatUserListHolder> {

    private ArrayList<User> users;
    public  ChatUserListAdapter(ArrayList<User> users)
    {
        this.users = users;
    }

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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


        ImageView profilePicture = holder.binding.imageView18;

        DocumentReference documentReference = db.collection("Users").document(users.get(position).getId());
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                HashMap<String, Object> data = (HashMap<String, Object>) documentSnapshot.getData();
                if (data != null) {
                    String name = (String) data.get("name");
                    String surname = (String) data.get("surname");
                    String mail = (String) data.get("mail");
                    String profilePhotoUrl = (String) data.get("profilePhotoUrl");
                    System.out.println("Profile Photo URL: " + profilePhotoUrl);
                    if (profilePhotoUrl != null && !profilePhotoUrl.isEmpty()) {
                        Glide.with(holder.itemView.getContext())
                                .load(profilePhotoUrl)
                                .into(profilePicture);
                    } else {
                        profilePicture.setImageResource(R.drawable.profile_photo);
                    }
                    holder.binding.mail.setText(mail);
                }
            }
        });

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
