package com.abubakar.share_food;


import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.navigation.compose.BackStackEntryIdViewModel;

public class Donate extends AppCompatActivity{
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private DatabaseReference mDatabase;
    //private TextView donate,cancel;
    private EditText editname,editlocation,editamount,editexpire;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        editname=(EditText)findViewById(R.id.donate_name);
        editlocation=(EditText)findViewById(R.id.donate_location);
        editamount=(EditText)findViewById(R.id.donate_amount);
        editexpire=(EditText)findViewById(R.id.donate_expire);

        Button confirm=findViewById(R.id.donate_button);
        Button cancel=findViewById(R.id.donate_cancel_button);

        confirm.setOnClickListener(view -> {
            donation();
        });
        cancel.setOnClickListener(view -> {
            startActivity(new Intent(Donate.this, Home.class));
        });


        database=FirebaseDatabase.getInstance();
        ref=database.getReference();


        mDatabase=FirebaseDatabase.getInstance().getReference();
    }

    public void donation(){
        DatabaseReference usersRef=ref.child("Food");
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String user_name= user.getEmail();
        //System.out.println(user_name);
        String foodname,location,amount,expire,recipe;
        foodname=editname.getText().toString().trim();
        location=editlocation.getText().toString().trim();
        amount=editamount.getText().toString().trim();
        expire=editexpire.getText().toString().trim();

        usersRef.push().setValue(new Food(foodname,amount,user_name,"none",expire,location));
        finish();
    }

}