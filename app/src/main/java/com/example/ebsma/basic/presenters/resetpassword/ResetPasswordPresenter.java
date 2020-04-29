package com.example.ebsma.basic.presenters.resetpassword;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.ebsma.basic.contracts.ResetPasswordContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordPresenter implements ResetPasswordContract.Presenter {
    final ResetPasswordContract.View mView;
    final FirebaseAuth mFirebaseAuth;
    final Context mContext;

    public ResetPasswordPresenter(Context mContext){
        this.mView = (ResetPasswordContract.View) mContext;
        this.mFirebaseAuth = FirebaseAuth.getInstance();
        this.mContext = mContext;
    }

    @Override
    public void resetPassword(String userEmail) {


        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(mContext, "Please write your email address!", Toast.LENGTH_SHORT).show();
        }
        else {
            mFirebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(mContext, "Please check your email.", Toast.LENGTH_SHORT).show();
                        mView.sendUserToLoginActivity();
                    }else {
                        String mesage = task.getException().getMessage();
                        Toast.makeText(mContext, "Error: " + mesage, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
