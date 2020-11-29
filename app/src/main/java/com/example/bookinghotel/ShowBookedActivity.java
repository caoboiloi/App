package com.example.bookinghotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

import java.text.NumberFormat;
import java.util.Locale;

public class ShowBookedActivity extends AppCompatActivity {

    MaterialToolbar topAppBar;
    TextView name_hotel_booked, address_booked_hotel, date_hotel_booked_start;
    TextView date_hotel_booked_finish, price_hotel_booked, type_room_hotel_booked, num_room_hotel_booked;
    ImageView img_hotel_booked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_booked);

        getSupportActionBar().hide();

        topAppBar = findViewById(R.id.topAppBar);
        name_hotel_booked = findViewById(R.id.name_hotel_booked);
        address_booked_hotel = findViewById(R.id.address_booked_hotel);
        date_hotel_booked_start = findViewById(R.id.date_hotel_booked_start);
        date_hotel_booked_finish = findViewById(R.id.date_hotel_booked_finish);
        price_hotel_booked = findViewById(R.id.price_hotel_booked);
        type_room_hotel_booked = findViewById(R.id.type_room_hotel_booked);
        num_room_hotel_booked = findViewById(R.id.num_room_hotel_booked);
        img_hotel_booked = findViewById(R.id.img_hotel_booked);

        topAppBar.setNavigationOnClickListener(v -> {
            // Handle navigation icon press
            finish();
        });

        Intent intent = getIntent();

        String address_hotel_booked_S = intent.getExtras().getString("address_hotel_booked","");
        String name_hotel_booked_S = intent.getExtras().getString("name_hotel_booked","");
        String date_hotel_booked_start_S = intent.getExtras().getString("date_hotel_booked_start","");
        String date_hotel_booked_finish_S = intent.getExtras().getString("date_hotel_booked_finish","");
        String price_hotel_booked_S = intent.getExtras().getString("price_hotel_booked","");
        String type_room_hotel_booked_S = intent.getExtras().getString("type_room_hotel_booked","");
        String num_room_hotel_booked_S = intent.getExtras().getString("num_room_hotel_booked","");

        name_hotel_booked.setText(name_hotel_booked_S);
        address_booked_hotel.setText(address_hotel_booked_S);
        date_hotel_booked_start.setText(date_hotel_booked_start_S);
        date_hotel_booked_finish.setText(date_hotel_booked_finish_S);
        price_hotel_booked.setText(price_hotel_booked_S);
        type_room_hotel_booked.setText("Loại phòng: " + type_room_hotel_booked_S);
        num_room_hotel_booked.setText("Số phòng: " + num_room_hotel_booked_S);

        Singleton imgPath = Singleton.getInstance();

        byte[] decodedString1 = Base64.decode(imgPath.getBaseImg(), Base64.DEFAULT);
        Bitmap decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);
        Bitmap bMapScaled1 = Bitmap.createScaledBitmap(decodedByte1, 1000, 400, true);

        img_hotel_booked.setImageBitmap(bMapScaled1);

    }
}