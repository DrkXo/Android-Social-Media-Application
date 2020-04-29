package com.example.ebsma.basic.views.findfriend;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ebsma.basic.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindFriendsViewHolder extends RecyclerView.ViewHolder{

        public View mView;

        public FindFriendsViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
        }

        public void setProfileimage(String profileimage){
            CircleImageView image = mView.findViewById(R.id.all_users_profile_image);
            Picasso.get().load(profileimage).into(image);
        }

        public void setFullname(String fullname){
            TextView username = mView.findViewById(R.id.all_users_profile_full_name);
            username.setText(fullname);
        }

        public void setStatus(String status){
            TextView myStatus = mView.findViewById(R.id.all_users_profile_status);
            myStatus.setText(status);
        }


        public void setDefaultProfileimage() {
            CircleImageView image = mView.findViewById(R.id.all_users_profile_image);
            image.setImageResource(R.drawable.profile);
        }
}

