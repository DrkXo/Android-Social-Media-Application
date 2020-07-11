package com.example.ebsma.basic.views.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ebsma.basic.R;
import com.example.ebsma.basic.contracts.RegisterContract;
import com.example.ebsma.basic.presenters.register.RegisterPresenter;
import com.example.ebsma.basic.views.setup.SetupActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View, View.OnClickListener {

    EditText UserEmail, UserPassword, UserConfirmPassword;
    Button CreateAccountButton;
    ProgressDialog loadingBar;

    RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        presenter = new RegisterPresenter(this);

        initViews();

        CreateAccountButton.setOnClickListener(this);

    }

    private void initViews() {
        UserEmail = findViewById(R.id.register_email);
        UserPassword = findViewById(R.id.register_password);
        UserConfirmPassword = findViewById(R.id.register_confirm_password);
        CreateAccountButton = findViewById(R.id.register_create_account);
        loadingBar = new ProgressDialog(this);
    }


    private void sendUserToSetupActivity() {
        Intent setupIntent = new Intent(RegisterActivity.this, SetupActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }

    @Override
    public void showLoadingBar(String title, String message) {
        loadingBar.setTitle(title);
        loadingBar.setMessage(message);
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
    }

    @Override
    public void dismissLoadingBar() {
        loadingBar.dismiss();
    }

    @Override
    public void onRegisterSuccess() {
        sendUserToSetupActivity();
        Toast.makeText(RegisterActivity.this, "Register Successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterFailed(String errorMessage) {
        Toast.makeText(RegisterActivity.this, "Register Failed: " +  errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_create_account:
                String email = UserEmail.getText().toString();
                String pass = UserPassword.getText().toString();
                String confirmPass = UserConfirmPassword.getText().toString();
                presenter.createNewAccount(email, pass, confirmPass);
                break;
        }
    }
}
