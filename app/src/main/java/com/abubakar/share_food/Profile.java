package com.abubakar.share_food;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Profile extends AppCompatActivity {
    private TextView nameTxtView, emailTxtView, accountTxtView;
    private ImageView ImageView;
    private final String TAG = this.getClass().getName().toUpperCase();
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    private Button verify_now;
    private String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        verify_now= findViewById(R.id.verify);
//        verify_now.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // go to passport
//            }
//        });

        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("Users");
        user_email= FirebaseAuth.getInstance().getCurrentUser().getEmail();

        nameTxtView=findViewById(R.id.name_textview);
        emailTxtView=findViewById(R.id.email_textview);
        accountTxtView=findViewById(R.id.account_textview);
        ImageView=findViewById(R.id.imageView);
        SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
        Picasso.get().load(preferences.getString("img", "yolo")).into(ImageView);

        databaseRef.addValueEventListener(new ValueEventListener() {
            String fname, email,verify;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId: dataSnapshot.getChildren()) {
                    if (Objects.equals(keyId.child("email").getValue(String.class), user_email)) {
                        fname = keyId.child("name").getValue().toString();
                        email = keyId.child("email").getValue().toString();
                        verify = "Account Verified";
                        break;
                    }
                }
                nameTxtView.setText(fname);
                emailTxtView.setText(email);
                accountTxtView.setText(verify);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}