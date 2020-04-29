package com.example.ebsma.basic.contracts;

import com.example.ebsma.basic.models.findfriend.FindFriends;
import com.example.ebsma.basic.views.findfriend.FindFriendsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public interface FindFriendContract {
    interface View {
        void setSearchResultAdapter( FirebaseRecyclerAdapter<FindFriends, FindFriendsViewHolder> firebaseRecyclerAdapter);
    }

    interface Presenter {
        void searchFriends(String searchBoxInput);
    }
}
