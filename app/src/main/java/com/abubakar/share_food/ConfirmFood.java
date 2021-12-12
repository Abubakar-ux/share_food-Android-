package com.abubakar.share_food;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ConfirmFood extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_food);


        Button home = findViewById(R.id.home);
        home.setOnClickListener(view -> {
            startActivity(new Intent(this, Home.class));
        });
    }

}
