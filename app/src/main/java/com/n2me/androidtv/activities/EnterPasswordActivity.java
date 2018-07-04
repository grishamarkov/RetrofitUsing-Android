package com.n2me.androidtv.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.n2me.androidtv.R;
import com.n2me.androidtv.common.bus.BusProvider;
import com.n2me.androidtv.common.event.UserLoginFailedEvent;
import com.n2me.androidtv.common.event.UserLoginSuccessEvent;
import com.n2me.androidtv.common.rest.N2meWebService;
import com.squareup.otto.Subscribe;

public class EnterPasswordActivity extends Activity {

    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnSignIn;
    private static final String SAVED_EMAIL = "SAVED_EMAIL";
    private static final String SAVED_PASSWORD = "SAVED_PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        BusProvider.get().register(this);

        initialize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        BusProvider.get().unregister(this);
    }

    private void initialize() {
        final SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        String savedEmail = sharedPref.getString(SAVED_EMAIL, "");
        String savedPassword = sharedPref.getString(SAVED_PASSWORD, "");

        txtEmail = (EditText) findViewById(R.id.etUserName);
        txtEmail.setText(savedEmail);
        txtPassword = (EditText) findViewById(R.id.etPassword);
        txtPassword.setText(savedPassword);
        btnSignIn = (Button) findViewById(R.id.btnSingIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(SAVED_EMAIL, String.valueOf(txtEmail.getText()));
                editor.putString(SAVED_PASSWORD, String.valueOf(txtPassword.getText()));
                editor.apply();
                if (txtEmail.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(EnterPasswordActivity.this, "Please input the email.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (txtPassword.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(EnterPasswordActivity.this, "Please input the password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                N2meWebService.getInstance().session(txtEmail.getText().toString(), txtPassword.getText().toString());
            }
        });
    }

    @Subscribe
    public void onUserLoginSuccessEvent(UserLoginSuccessEvent successEvent) {
        Intent intent = new Intent(EnterPasswordActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Subscribe
    public void onUserLoginFailedEvent(UserLoginFailedEvent failedEvent) {
        Toast.makeText(EnterPasswordActivity.this, "The username and password is incorrect", Toast.LENGTH_SHORT).show();
    }

}