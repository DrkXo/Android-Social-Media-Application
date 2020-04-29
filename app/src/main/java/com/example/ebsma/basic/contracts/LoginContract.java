package com.example.ebsma.basic.contracts;

public interface LoginContract {
    interface View {
        void loginFailed(String errorMessage);
        void loginSuccess();
        void sendUserToLoginActivity();
        void sendUserToMainActivity();
        void showLoadingBar(String title, String message);
        void dismissLoadingBar();
    }

    interface Presenter {
        void login(String email, String password);
    }
}
