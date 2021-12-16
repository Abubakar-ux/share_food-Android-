package com.abubakar.share_food;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private final String user_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    Toolbar toolbar;
    SearchView filter;
    FloatingActionButton donate;
    ArrayList<Food> foodList = new ArrayList<>();
    ImageView profile;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database;
    DatabaseReference reference;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // fragment title tool bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Welcome to UNICHEF");

        // side bar
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // display user email on drawer
        String user_email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        //Uri user_profile=FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();

        View headerView = navigationView.getHeaderView(0);
        TextView textViewToChange = headerView.findViewById(R.id.username);
        profile = headerView.findViewById(R.id.imageView);
        preferences = getSharedPreferences("UserData", MODE_PRIVATE);


        textViewToChange.setText(user_email);

        //ImageView imageViewToChange=headerView.findViewById(R.id.imageView);
        //imageViewToChange.setImageURI(user_profile);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(sidenavListener);

        filter = findViewById(R.id.filter);
        showList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Picasso.get().load(preferences.getString("img", "yolo")).into(profile);
    }

    public void showList() {
        ListView listView;
        listView = findViewById(R.id.list_view);
        FoodAdapter adapter = new FoodAdapter(Home.this, foodList);
        listView.setAdapter(adapter);

        filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
        });

        // convert firebase data -> Food class and then store in the global arraylist
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String s) {
                Food value = snapshot.getValue(Food.class);
                // only if not taken, add to the list
                if (value.getRecipient().equals("none")) {
                    foodList.add(value);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String s) {
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Food take out
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Home.this, AcceptFood.class);
                Food selectedFood = (Food) listView.getAdapter().getItem(position);
                intent.putExtra("FOODNAME", selectedFood.getName());
                intent.putExtra("AMOUNT", selectedFood.getAmount());
                intent.putExtra("LOCATION", selectedFood.getFridge());
                intent.putExtra("EXPIRYDATE", selectedFood.getExpiryDate());
                intent.putExtra("FOODID", selectedFood.getID());
                startActivity(intent);
            }
        });

        // Food donation
        donate = findViewById(R.id.donate);
        donate.setOnClickListener(new View.OnClickListener() {
            boolean click = true;

            @Override
            public void onClick(View v) {
                if (click) {
                    Toast.makeText(Home.this, "Hello?", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Home.this, Donate.class));
                    click = false;
                } else {
                    click = true;
                }
            }
        });
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private final NavigationView.OnNavigationItemSelectedListener sidenavListener=
            new NavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            startActivity(new Intent(Home.this, Home.class));
                            break;
                        case R.id.nav_profile:
                            startActivity(new Intent(Home.this, Profile.class));
                            break;
                        case R.id.nav_map:
                            startActivity(new Intent(Home.this, Maps.class));
                            break;
                        case R.id.nav_logout:
                            startActivity(new Intent(Home.this, Welcome.class));
                            break;
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            };

    public void logout(MenuItem item) {
        startActivity(new Intent(Home.this, Welcome.class));
        //preferences.clearData(this);
        finish();
    }
}