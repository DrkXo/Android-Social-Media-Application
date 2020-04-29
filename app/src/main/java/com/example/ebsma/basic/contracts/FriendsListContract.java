package com.example.ebsma.basic.contracts;

import com.example.ebsma.basic.models.friendslist.Friends;
import com.example.ebsma.basic.views.friendslist.FriendViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public interface FriendsListContract {
    interface View {
        void setFriendListAdapter(FirebaseRecyclerAdapter<Friends, FriendViewHolder> firebaseRecyclerAdapter);
        void SendUserToProfileActivity(String userId);
        void SendUserToChatActivity(String userId, String userName);
    }

    interface Presenter {

        void displayAllFriends();
    }
}
