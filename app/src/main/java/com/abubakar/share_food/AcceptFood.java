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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AcceptFood extends AppCompatActivity{

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String foodID, foodname, amount, donor, location, expirydate, img;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_food);

        foodname = getIntent().getStringExtra("FOODNAME");
        amount = getIntent().getStringExtra("AMOUNT");
        donor = getIntent().getStringExtra("DONOR");
        location = getIntent().getStringExtra("LOCATION");
        expirydate = getIntent().getStringExtra("EXPIRYDATE");
        foodID = getIntent().getStringExtra("FOODID");
        img = getIntent().getStringExtra("FOODIMG");

        Button confirm = findViewById(R.id.confirm_button);
        Button cancel = findViewById(R.id.cancel_button);
        confirm.setOnClickListener(view -> {
            confirmFood();
            removeFood();
        });
        cancel.setOnClickListener(view -> {
            startActivity(new Intent(this, Home.class));
        });

        CircleImageView image = findViewById(R.id.img);
        Picasso.get().load(Id.getIp()+img).fit().centerCrop().into(image);
        TextView nameText = findViewById(R.id.confirm_name);
        nameText.setText(foodname);
        TextView amountText = findViewById(R.id.confirm_amount);
        amountText.setText(amount);
        TextView locationText = findViewById(R.id.confirm_location);
        locationText.setText(location);
        TextView expirydateText = findViewById(R.id.confirm_expirydate);
        expirydateText.setText(expirydate);

    }

    public void confirmFood(){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String recipientEmail= user.getEmail();

        String url = Id.getIp()+"insertConfirmedFood.php";
        RequestQueue requestQueue = Volley.newRequestQueue(AcceptFood.this);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject x=new JSONObject(response);
                            Id.setId(x.getString("id"));
                            Id.setPath(x.getString("img"));
                            Toast.makeText(AcceptFood.this,"confirmedFood Inserted", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AcceptFood.this,"confirmedFood not Inserted", Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("name",foodname);
                params.put("amount",amount);
                params.put("donor", donor);
                params.put("recipient",recipientEmail);
                params.put("expiry",expirydate);
                params.put("location", location);
                params.put("img", img);
                return params;
            }
        };
        requestQueue.add(request);
    }

    public void removeFood(){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String recipientEmail= user.getEmail();

        String url = Id.getIp()+"removeFood.php";
        RequestQueue requestQueue = Volley.newRequestQueue(AcceptFood.this);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject x=new JSONObject(response);
                            Id.setId(x.getString("id"));
                            Toast.makeText(AcceptFood.this,"Food Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(AcceptFood.this,Home.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AcceptFood.this,"Food not Deleted", Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id",foodID);
                return params;
            }
        };
        requestQueue.add(request);
    }

}