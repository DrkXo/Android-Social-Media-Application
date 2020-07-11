package com.example.ebsma.basic.views.findfriend;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebsma.basic.R;
import com.example.ebsma.basic.contracts.FindFriendContract;
import com.example.ebsma.basic.models.findfriend.FindFriends;
import com.example.ebsma.basic.presenters.findfriend.FindFriendPresenter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class FindFriendsActivity extends AppCompatActivity implements FindFriendContract.View,View.OnClickListener {

    Toolbar mToolbar;

    ImageButton searchButton;
    EditText searchInputText;

    RecyclerView searchResultList;

    FindFriendPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        presenter = new FindFriendPresenter(this);
        initViews();

        searchButton.setOnClickListener(this);
    }

    private void initViews() {
        mToolbar = findViewById(R.id.find_friends_app_bar_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Find Friends");

        searchResultList = findViewById(R.id.search_result_list);
        searchResultList.setHasFixedSize(true);
        searchResultList.setLayoutManager(new LinearLayoutManager(this));

        searchButton = findViewById(R.id.search_friends_button);
        searchInputText = findViewById(R.id.search_box_input);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_friends_button:
                String searchBoxInput = searchInputText.getText().toString();
                presenter.searchFriends(searchBoxInput);
                break;
        }
    }

    @Override
    public void setSearchResultAdapter(FirebaseRecyclerAdapter<FindFriends, FindFriendsViewHolder> firebaseRecyclerAdapter) {
        this.searchResultList.setAdapter(firebaseRecyclerAdapter);
    }
}
