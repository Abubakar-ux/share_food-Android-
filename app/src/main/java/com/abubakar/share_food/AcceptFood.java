package com.abubakar.share_food;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class AcceptFood extends AppCompatActivity{

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String foodID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_food);

        String foodname = getIntent().getStringExtra("FOODNAME");
        String amount = getIntent().getStringExtra("AMOUNT");
        String location = getIntent().getStringExtra("LOCATION");
        String expirydate = getIntent().getStringExtra("EXPIRYDATE");
        String ID = getIntent().getStringExtra("FOODID");
        foodID = ID;

        Button confirm = findViewById(R.id.confirm_button);
        Button cancel = findViewById(R.id.cancel_button);
        confirm.setOnClickListener(view -> {
            startActivity(new Intent(this, ConfirmFood.class));
        });
        cancel.setOnClickListener(view -> {
            startActivity(new Intent(this, Home.class));
        });

        TextView nameText = findViewById(R.id.confirm_name);
        nameText.setText(foodname);
        TextView amountText = findViewById(R.id.confirm_amount);
        amountText.setText(amount);
        TextView locationText = findViewById(R.id.confirm_location);
        locationText.setText(location);
        TextView expirydateText = findViewById(R.id.confirm_expirydate);
        expirydateText.setText(expirydate);

    }

}