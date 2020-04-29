package com.example.ebsma.basic.contracts;

public interface ResetPasswordContract {
    interface View {
        void sendUserToLoginActivity();
    }

    interface Presenter {

        void resetPassword(String userEmail);
    }
}
