package com.abubakar.share_food;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView register = findViewById(R.id.signup);
        Button login = findViewById(R.id.login);

        register.setOnClickListener(this);
        login.setOnClickListener(this);
    };

    // move to sign-up (Registration) or sign-in (Login) page
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.login:
                startActivity(new Intent(this, Login.class));
                break;
        }
    };
}