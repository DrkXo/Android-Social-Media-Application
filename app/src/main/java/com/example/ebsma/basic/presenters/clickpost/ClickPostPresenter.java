package com.example.ebsma.basic.presenters.clickpost;

import android.content.Context;

import com.example.ebsma.basic.contracts.ClickPostContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClickPostPresenter implements ClickPostContract.Presenter {
    final ClickPostContract.View mView;
    final Context mContext;

    DatabaseReference ClickPostRef, CommentsRef, UsersRef, LikeRef;
    FirebaseAuth mAuth;

    String PostKey, currentUserID, description, genre;
    Boolean likeChecker = false;

    public ClickPostPresenter(Context mContext, String Post_Key) {
        this.mView = (ClickPostContract.View) mContext;
        this.mContext = mContext;
        this.PostKey = Post_Key;
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        ClickPostRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(PostKey);
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        LikeRef = FirebaseDatabase.getInstance().getReference().child("Likes");
        CommentsRef = ClickPostRef.child("Comments");

    }
}
