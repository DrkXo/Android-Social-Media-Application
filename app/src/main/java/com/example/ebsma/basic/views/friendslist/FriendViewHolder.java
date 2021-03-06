package com.example.ebsma.basic.views.friendslist;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ebsma.basic.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendViewHolder extends RecyclerView.ViewHolder{

    public View mView;

    public FriendViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setProfileimage(String profileimage){
        CircleImageView image = mView.findViewById(R.id.all_users_profile_image);
        Picasso.get().load(profileimage).placeholder(R.drawable.profile).into(image);
    }

    public void setFullname(String fullname){
        TextView username = mView.findViewById(R.id.all_users_profile_full_name);
        username.setText(fullname);
    }
    public void setDate(String date){
        TextView friendDate = mView.findViewById(R.id.all_users_profile_status);
        friendDate.setText("Friend since: " + date);
    }
}
