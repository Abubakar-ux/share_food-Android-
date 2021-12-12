package com.abubakar.share_food;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Food {
    private String name;
    private String amount;
    private String donor;
    private String recipient;
    private String expiryDate;
    private String fridge;

    public Food (String name, String amount, String donor, String recipient,
                 String expiryDate, String fridge) {
        this.name = name;
        this.amount = amount;
        this.donor = donor;
        this.recipient = recipient;
        this.expiryDate = expiryDate;
        this.fridge = fridge;
    }

    public Food () {
    }

    public String getID() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Food");
        String foodname = this.name;
        String location = this.fridge;
        final String[] id = new String[1];

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    if (item.child("name").equals(foodname) && item.child("name").equals(location))
                        id[0] = item.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return id[0];
    }

    public String getName() {
        return this.name;
    }

    public String getAmount() {
        return this.amount;
    }

    public String getDonor() {
        return this.donor;
    }

    public String getRecipient() {
        return this.recipient;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public String getFridge() {
        return this.fridge;
    }

}
