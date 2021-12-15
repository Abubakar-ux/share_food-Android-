package com.abubakar.share_food;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Food {
    private String id;
    private String name;
    private String amount;
    private String donor;
    private String recipient;
    private String expiryDate;
    private String location;
    private String img;

    public Food (String id, String name, String amount, String donor, String recipient,
                 String expiryDate, String location, String img) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.donor = donor;
        this.recipient = recipient;
        this.expiryDate = expiryDate;
        this.location = location;
        this.img = img;
    }

    public Food () {
    }

    public String getId() { return this.id; }

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
        return this.location;
    }

    public String getImg() { return this.img; }
}
