package com.example.bookinghotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;

public class HotelDetail extends AppCompatActivity {
    MaterialToolbar topAppBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        getSupportActionBar().hide();

        topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setNavigationOnClickListener(v ->  {
            // Handle navigation icon press
            finish();
        });

        topAppBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            item.setIcon(R.drawable.ic_baseline_favorite_24_red);

            return true;
        });


    }
}