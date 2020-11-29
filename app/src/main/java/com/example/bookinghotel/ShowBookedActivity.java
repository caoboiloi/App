package com.example.bookinghotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

public class ShowBookedActivity extends AppCompatActivity {

    MaterialToolbar topAppBar;
//    TextView name_hotel_booked, address_booked_hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_booked);

        getSupportActionBar().hide();

        topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setNavigationOnClickListener(v -> {
            // Handle navigation icon press
            finish();
        });
    }
}