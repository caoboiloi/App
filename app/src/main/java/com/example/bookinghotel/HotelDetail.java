package com.example.bookinghotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bookinghotel.Adapter.CommentAdapter;
import com.example.bookinghotel.entity.Booked;
import com.example.bookinghotel.entity.Comment;
import com.example.bookinghotel.entity.Hotel;
import com.example.bookinghotel.entity.Rating;
import com.example.bookinghotel.entity.User;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class HotelDetail extends AppCompatActivity {
    MaterialToolbar topAppBar;
    ProgressBar progressBar_cyclic_detail;
    RecyclerView rvComment;
    TextView tvRatingAve, tvInfoRate, tvInfoRate1,tvHotelName;
    Button btnBookLarge,btnBookMedium;
    ArrayList<Comment> comments_data = new ArrayList<>();
    CommentAdapter adapter;
    private DatabaseReference mDatabase;
    Hotel hotel = new Hotel();
    ArrayList<String> favorite = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        getSupportActionBar().hide();
        tvRatingAve = findViewById(R.id.tvRatingAve);
        topAppBar = findViewById(R.id.topAppBar);
        rvComment = findViewById(R.id.rvComment);
        tvInfoRate = findViewById(R.id.tvInfoRate);
        tvInfoRate1 = findViewById(R.id.tvInfoRate1);
        progressBar_cyclic_detail= findViewById(R.id.progressBar_cyclic_detail);
        tvHotelName = findViewById(R.id.tvHotelName);
        btnBookLarge = findViewById(R.id.btnBookLarge);
        btnBookMedium = findViewById(R.id.btnBookMedimum);

        Intent intent = getIntent();
        String path = intent.getExtras().getString("path", "");
        String hotelname = intent.getExtras().getString("hotelname", "Hotel detail");
        topAppBar.setTitle(hotelname);
        progressBar_cyclic_detail.setVisibility(View.VISIBLE);
        adapter = new CommentAdapter(HotelDetail.this, comments_data);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        topAppBar.setNavigationOnClickListener(v -> {
            // Handle navigation icon press
            finish();
        });
        topAppBar.setOnMenuItemClickListener(item -> {
            if(favorite.contains(path)){
                favorite.remove(path);
            }else{
                favorite.add(path);
            }
            FirebaseDatabase.getInstance().getReference("Users/"+userId+"/favorite").setValue(favorite);
            return true;
        });


        mDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                favorite = user.getFavorite();
                if(favorite.contains(path)){
                    topAppBar.getMenu().getItem(0).setIcon(R.drawable.ic_baseline_favorite_24_red);
                }else{
                    topAppBar.getMenu().getItem(0).setIcon(R.drawable.ic_baseline_favorite_border_24);
                }



            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });




        mDatabase = FirebaseDatabase.getInstance().getReference(path);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar_cyclic_detail.setVisibility(View.VISIBLE);
                comments_data.clear();
                hotel = snapshot.getValue(Hotel.class);
                topAppBar.setTitle(hotel.getName());
                tvHotelName.setText(hotel.getName());
                //coment and rating
                ArrayList<Rating> rating = hotel.getRating();
                for (Rating i : rating) {
                    if (i != null) {
                        Comment comment = new Comment(i.getName(), i.getComment(), i.getStar());
                        comments_data.add(comment);
                    }
                }

                LinearLayoutManager manager = new LinearLayoutManager(HotelDetail.this, LinearLayoutManager.HORIZONTAL, false);
                rvComment.setLayoutManager(manager);
                rvComment.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if (rating.size() == 0) {
                    tvRatingAve.setText("");
                    tvInfoRate.setText("Chưa có đánh giá");
                    tvInfoRate1.setText("Chưa có đánh giá");
                } else {
                    Float ave = hotel.getAveRating();
                    tvRatingAve.setText(ave + "");
                    tvInfoRate.setText("Dựa trên " + (rating.size() - 1) + " nhận xét trên mạng");
                    if (ave >= 4.5) {
                        tvInfoRate1.setText("Rất tốt");
                    } else if (ave < 4.5 && ave >= 4) {
                        tvInfoRate1.setText("Tốt");
                    } else if (ave < 4 && ave >= 3) {
                        tvInfoRate1.setText("Bình thường");
                    } else {
                        tvInfoRate1.setText("Không tốt");
                    }
                }
                progressBar_cyclic_detail.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar_cyclic_detail.setVisibility(View.GONE);
            }
        });

        btnBookLarge.setOnClickListener(v -> {

            Intent intent1 = new Intent(this, BookRoom.class);
            intent1.putExtra("price", hotel.getRoom().getLarge().getPrice());
            ArrayList<Booked> bookeds = hotel.getBookeds();
            Gson gson = new Gson();
            String myJson = gson.toJson(bookeds);
            intent1.putExtra("bookeds", myJson);
            intent1.putExtra("numberRoom", hotel.getRoom().getLarge().getTotal());
            startActivity(intent1);
        });
        btnBookMedium.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, BookRoom.class);
            intent1.putExtra("price", hotel.getRoom().getMedium().getPrice());
            ArrayList<Booked> bookeds = hotel.getBookeds();
            Gson gson = new Gson();
            String myJson = gson.toJson(bookeds);
            intent1.putExtra("bookeds", myJson);
            intent1.putExtra("numberRoom", hotel.getRoom().getMedium().getTotal());
            startActivity(intent1);
        });



    }
}