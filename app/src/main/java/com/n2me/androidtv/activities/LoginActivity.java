package com.n2me.androidtv.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.n2me.androidtv.R;
import com.n2me.androidtv.common.pref.N2MEPreference;
import com.n2me.androidtv.common.rest.model.User;

import java.util.ArrayList;

public class LoginActivity extends Activity {

    LinearLayout firstUser;
    LinearLayout secondUser;
    LinearLayout thirdUser;
    LinearLayout addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();
    }

    private void initialize() {
        firstUser = (LinearLayout) findViewById(R.id.firstUser);
        secondUser = (LinearLayout) findViewById(R.id.secondUser);
        thirdUser = (LinearLayout) findViewById(R.id.thirdUser);
        addUser = (LinearLayout) findViewById(R.id.addUser);

        ArrayList<User> recentUsers = N2MEPreference.getRecentUsersList();
        if ((recentUsers == null) || (recentUsers.size() == 0)) {
            firstUser.setVisibility(View.GONE);
            secondUser.setVisibility(View.GONE);
            thirdUser.setVisibility(View.GONE);
        }
        else if (recentUsers.size() == 1) {
            firstUser.setVisibility(View.VISIBLE);
            secondUser.setVisibility(View.GONE);
            thirdUser.setVisibility(View.GONE);
        }
        else if (recentUsers.size() == 2) {
            firstUser.setVisibility(View.VISIBLE);
            secondUser.setVisibility(View.VISIBLE);
            thirdUser.setVisibility(View.GONE);
        }
        else if (recentUsers.size() >= 3) {
            firstUser.setVisibility(View.VISIBLE);
            secondUser.setVisibility(View.VISIBLE);
            thirdUser.setVisibility(View.VISIBLE);
        }

        firstUser.findViewById(R.id.ib_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        secondUser.findViewById(R.id.ib_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        thirdUser.findViewById(R.id.ib_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addUser.findViewById(R.id.ib_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, EnterPasswordActivity.class);
                startActivity(intent);
            }
        });
    }



}