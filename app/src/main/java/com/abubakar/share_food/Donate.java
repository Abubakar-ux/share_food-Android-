package com.abubakar.share_food;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
//import androidx.navigation.compose.BackStackEntryIdViewModel;

public class Donate extends AppCompatActivity{
    //private TextView donate,cancel;
    private EditText editname,editlocation,editamount,editexpire;
    CircleImageView img;
    Uri imgURI = null;
    String encodedImage;
    Bitmap bitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        editname=(EditText)findViewById(R.id.donate_name);
        editlocation=(EditText)findViewById(R.id.donate_location);
        editamount=(EditText)findViewById(R.id.donate_amount);
        editexpire=(EditText)findViewById(R.id.donate_expire);
        img = findViewById(R.id.img);

        Button confirm=findViewById(R.id.donate_button);
        Button cancel=findViewById(R.id.donate_cancel_button);

        confirm.setOnClickListener(view -> {
            donation();
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(Donate.this)
                        .galleryOnly()
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)*/
                        //.saveDir(new File(getFilesDir(), "ImagePicker"))
                        .start();
            }
        });

        cancel.setOnClickListener(view -> {
            startActivity(new Intent(Donate.this, Home.class));
        });

    }

    public void donation(){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String userEmail= user.getEmail();
        String foodname,location,amount,expire,recipe;
        foodname=editname.getText().toString().trim();
        location=editlocation.getText().toString().trim();
        amount=editamount.getText().toString().trim();
        expire=editexpire.getText().toString().trim();


        String url = Id.getIp()+"insertFood.php";
        RequestQueue requestQueue = Volley.newRequestQueue(Donate.this);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject x=new JSONObject(response);
                            Id.setId(x.getString("id"));
                            Id.setPath(x.getString("img"));
                            Toast.makeText(Donate.this,"Data Inserted", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Donate.this,Home.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Donate.this,"Data not Inserted", Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("name",foodname);
                params.put("amount",amount);
                params.put("donor",userEmail);
                params.put("recipient","none");
                params.put("expiry",expire);
                params.put("location", location);
                params.put("img", encodedImage);
                return params;
            }
        };
        requestQueue.add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imgURI = data.getData();
        img.setImageURI(imgURI);

        try {
            InputStream inputStream = getContentResolver().openInputStream(imgURI);
            bitmap = BitmapFactory.decodeStream(inputStream);


            imageStore(bitmap);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void imageStore(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);

        byte[] imageBytes = stream.toByteArray();

        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);

    }
}