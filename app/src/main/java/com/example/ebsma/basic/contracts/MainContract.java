package com.example.ebsma.basic.contracts;

import com.example.ebsma.basic.models.main.Posts;
import com.example.ebsma.basic.views.main.PostsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public interface MainContract {
    interface View {
        void setPostListAdapter(FirebaseRecyclerAdapter<Posts, PostsViewHolder> firebaseRecyclerAdapter);
        void setNavProfileUserName(String fullname);
        void setNavProfileImage(String myProfileImage);
        void sendUserToLoginActivity();
        void sendUserToSetupActivity();
        void sendUserToProfileActivity(String userId);
    }

    interface Presenter {
        void displayAllUsersPosts();
        void validateUser();
        void signOut();

        void sendUserToOwnProfile();
    }

}
