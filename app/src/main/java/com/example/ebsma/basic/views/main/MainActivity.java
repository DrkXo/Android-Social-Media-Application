package com.example.ebsma.basic.views.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebsma.basic.PermissionUtils;
import com.example.ebsma.basic.R;
import com.example.ebsma.basic.chatroom.chat_room_start;
import com.example.ebsma.basic.contracts.MainContract;
import com.example.ebsma.basic.models.main.Posts;
import com.example.ebsma.basic.presenters.main.MainPresenter;
import com.example.ebsma.basic.views.findfriend.FindFriendsActivity;
import com.example.ebsma.basic.views.friendslist.FriendsListActivity;
import com.example.ebsma.basic.views.login.LoginActivity;
import com.example.ebsma.basic.views.post.PostActivity;
import com.example.ebsma.basic.views.profile.ProfileActivity;
import com.example.ebsma.basic.views.setting.SettingsActivity;
import com.example.ebsma.basic.views.setup.SetupActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements MainContract.View,View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    RecyclerView postList;
    Toolbar mToolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView navProfileUserName;
    CircleImageView navProfileImage;
    ImageButton AddNewPostButton;
    ImageButton ProfileButton;
    ImageButton MsgButton;
    MainPresenter presenter;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        initViews();

        AddNewPostButton.setOnClickListener(this);
        MsgButton.setOnClickListener(this);
        ProfileButton.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);

        presenter.displayAllUsersPosts();
////////////////////Check Map Permission//////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private void checkmappermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    private void initViews() {
        //toolbar
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Home");

        //drawerlayout
        drawerLayout = findViewById(R.id.drawable_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.navigation_view);

        //display post list
        postList = findViewById(R.id.all_users_post_list);
        postList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postList.setLayoutManager(linearLayoutManager);

        //navigation view
        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);
        navProfileImage = navView.findViewById(R.id.nav_profile_image);
        navProfileUserName = navView.findViewById(R.id.nav_user_full_name);

        //post button
        AddNewPostButton = findViewById(R.id.add_new_post_button);
        ProfileButton =findViewById(R.id.profile_button);
        MsgButton = findViewById(R.id.msg_button);
        checkmappermission();

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.validateUser();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_post:
                sendUserToPostActivity();
                break;
            case R.id.nav_friends:
                SendUserToFriendsActivity();
                break;
            case R.id.nav_profile:
                presenter.sendUserToOwnProfile();
                break;
            case R.id.nav_home:
                DrawerLayout mDrawerLayout;
                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
                mDrawerLayout.closeDrawers();
                break;

            case R.id.nav_find_friend:
                sendUserToFindFriendsActivity();
                break;
            case R.id.nav_messages:
                SendUserToFriendsActivity();
                break;
            // Quang code
            case R.id.nav_setting:
                SendUserToSettingsActivity();
                break;
            case R.id.nav_logout:
                presenter.signOut();
                sendUserToLoginActivity();
                break;
            case R.id.nav_del:
                sendusertodelactivity();
                break;
            case R.id.live_chat_id:
                sendusertochatroom();
                break;
        }
    }


    private void sendusertodelactivity() {
        Intent del = new Intent(MainActivity.this, delete_account.class);
        startActivity(del);
    }

    private void sendusertochatroom() {
        Intent chatroom = new Intent(MainActivity.this, chat_room_start.class);
        startActivity(chatroom);
    }


    private void SendUserToFriendsActivity() {
        Intent friendsIntent = new Intent(MainActivity.this, FriendsListActivity.class);
        startActivity(friendsIntent);
    }

    public void sendUserToSetupActivity() {
        Intent setupIntent = new Intent(MainActivity.this, SetupActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }


    @Override
    public void sendUserToProfileActivity(String userId) {
        Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
        profileIntent.putExtra("userId", userId);
        startActivity(profileIntent);
    }

    public void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }


    private void sendUserToPostActivity() {
        Intent postIntent = new Intent(MainActivity.this, PostActivity.class);
        startActivity(postIntent);
    }


    private void sendUserToFindFriendsActivity() {
        Intent findFriendsIntent = new Intent(MainActivity.this, FindFriendsActivity.class);
        startActivity(findFriendsIntent);
    }

    // Quang code
    private void SendUserToSettingsActivity() {
        Intent loginIntent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(loginIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_new_post_button:
                sendUserToPostActivity();
                break;
            case R.id.profile_button:
                presenter.sendUserToOwnProfile();
                break;
            case R.id.msg_button:
                sendusertochatroom();
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        UserMenuSelector(item);
        return false;
    }

    @Override
    public void setPostListAdapter(FirebaseRecyclerAdapter<Posts, PostsViewHolder> firebaseRecyclerAdapter) {
        postList.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void setNavProfileUserName(String fullname) {
        navProfileUserName.setText(fullname);
    }

    @Override
    public void setNavProfileImage(String myProfileImage) {
        Picasso.get().load(myProfileImage).placeholder(R.drawable.profile).into(navProfileImage);
    }


}
