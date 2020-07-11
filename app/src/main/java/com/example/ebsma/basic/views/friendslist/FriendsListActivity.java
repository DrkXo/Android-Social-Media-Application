package com.example.ebsma.basic.views.friendslist;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebsma.basic.R;
import com.example.ebsma.basic.contracts.FriendsListContract;
import com.example.ebsma.basic.models.friendslist.Friends;
import com.example.ebsma.basic.presenters.friendslist.FriendsListPresenter;
import com.example.ebsma.basic.views.chat.ChatActivity;
import com.example.ebsma.basic.views.profile.ProfileActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class FriendsListActivity extends AppCompatActivity implements FriendsListContract.View{

    RecyclerView friendsList;
    FriendsListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        initViews();
        presenter = new FriendsListPresenter(this);

        presenter.displayAllFriends();
    }

    private void initViews() {
        friendsList = findViewById(R.id.friends_list);
        friendsList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        friendsList.setLayoutManager(linearLayoutManager);
    }


    public void SendUserToProfileActivity(String userId) {
        Intent profileIntent = new Intent(FriendsListActivity.this, ProfileActivity.class);
        profileIntent.putExtra("userId", userId);
        startActivity(profileIntent);
    }

    public void SendUserToChatActivity(String userId, String userName) {
        Intent chatIntent = new Intent(FriendsListActivity.this, ChatActivity.class);
        chatIntent.putExtra("userId", userId);
        chatIntent.putExtra("userName", userName);
        startActivity(chatIntent);
    }

    @Override
    public void setFriendListAdapter(FirebaseRecyclerAdapter<Friends, FriendViewHolder> firebaseRecyclerAdapter) {
        this.friendsList.setAdapter(firebaseRecyclerAdapter);
    }
}
