package com.example.bookinghotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookinghotel.Adapter.CommentAdapter;
import com.example.bookinghotel.entity.Booked;
import com.example.bookinghotel.entity.BookedRoom;
import com.example.bookinghotel.entity.Comment;
import com.example.bookinghotel.entity.Hotel;
import com.example.bookinghotel.entity.Rating;
import com.example.bookinghotel.entity.Review;
import com.example.bookinghotel.entity.TimeBooked;
import com.example.bookinghotel.entity.User;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Collectors;

public class HotelDetail extends AppCompatActivity {
    MaterialToolbar topAppBar;
    ProgressBar progressBar_cyclic_detail;
    RecyclerView rvComment;
    TextView tvRatingAve, tvInfoRate, tvInfoRate1,tvHotelName,tvPriceLarge,tvPriceMedium;
    Button btnBookLarge,btnBookMedium,btnComment,addComment;
    ImageView ivMediumPictureMedium, ivMediumPictureLarge,ivHotel;
    EditText etComment;
    RatingBar rateting;
    LinearLayout llComnent,main_content1;

    ArrayList<Comment> comments_data = new ArrayList<>();
    CommentAdapter adapter;
    private DatabaseReference mDatabase;
    Hotel hotel = new Hotel();
    ArrayList<String> favorite = new ArrayList<>();
    Integer id = 0;
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
        tvPriceLarge = findViewById(R.id.tvPriceLarge);
        tvPriceMedium = findViewById(R.id.tvPriceMedium);
        ivMediumPictureLarge = findViewById(R.id.ivMediumPictureLarge);
        ivMediumPictureMedium = findViewById(R.id.ivMediumPictureMedium);
        ivHotel = findViewById(R.id.ivHotel);
        btnComment = findViewById(R.id.btnComment);
        etComment= findViewById(R.id.etComment);
        rateting = findViewById(R.id.rateting);
        addComment= findViewById(R.id.addComment);
        llComnent = findViewById(R.id.llComnent);
        main_content1 = findViewById(R.id.main_content1);

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
                Log.e("Ad", String.valueOf(hotel));
                topAppBar.setTitle(hotel.getName());
                tvHotelName.setText(hotel.getName());
                tvPriceLarge.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(hotel.getRoom().getLarge().getPrice()));
                tvPriceMedium.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(hotel.getRoom().getMedium().getPrice()));
                byte[] decodedString1 = Base64.decode(hotel.getImage(), Base64.DEFAULT);
                Bitmap decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);
                Bitmap bMapScaled1 = Bitmap.createScaledBitmap(decodedByte1, 1000, 400, true);
                ivHotel.setImageBitmap(bMapScaled1);

                if(hotel.getRoom().getLarge().getImage().size()>0){
                    String pictureLarge = hotel.getRoom().getLarge().getImage().get(0);
                    byte[] decodedString = Base64.decode(pictureLarge, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    Bitmap bMapScaled = Bitmap.createScaledBitmap(decodedByte, 800, 300, true);
                    ivMediumPictureLarge.setImageBitmap(bMapScaled);
                }
                if(hotel.getRoom().getMedium().getImage().size()>0){
                    String pictureMedium = hotel.getRoom().getMedium().getImage().get(0);
                    byte[] decodedString = Base64.decode(pictureMedium, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    Bitmap bMapScaled = Bitmap.createScaledBitmap(decodedByte, 800, 300, true);
                    ivMediumPictureMedium.setImageBitmap(bMapScaled);

                }
                //coment and rating
                ArrayList<Rating> rating = hotel.getRating();
                int count =1;
                for (Rating i : rating) {
                    if (i != null) {

                        Comment comment = new Comment(i.getName(), i.getComment(), i.getStar(), userId);
                        comments_data.add(comment);
                        count ++;

                    }
                    if(count == 5){
                        break;
                    }
                }
                Collections.reverse(comments_data);
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
                    tvRatingAve.setText(new DecimalFormat("#.#").format(ave) + "");
                    tvInfoRate.setText("Dựa trên " + (rating.size()) + " nhận xét trên mạng");
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
                String status = intent.getStringExtra("status");

                if(status != null){
                    Snackbar snackbar = Snackbar
                            .make(main_content1, "Đặt phòng thành công", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar_cyclic_detail.setVisibility(View.GONE);
            }
        });

        btnBookLarge.setOnClickListener(v -> {

            Intent intent1 = new Intent(this, BookRoom.class);
            intent1.putExtra("price", hotel.getRoom().getLarge().getPrice());
            ArrayList<TimeBooked> bookedRoom = hotel.getBookedRoom().getLarge();

            Gson gson = new Gson();

            String myJson1 = gson.toJson(bookedRoom);

            intent1.putExtra("bookedRoom", myJson1);
            intent1.putExtra("type", "Large");
            intent1.putExtra("path", intent.getExtras().getString("path", ""));
            intent1.putExtra("numberRoom", hotel.getRoom().getLarge().getTotal());
            intent1.putExtra("image",hotel.getRoom().getLarge().getImage().get(0));
            startActivity(intent1);
        });
        btnBookMedium.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, BookRoom.class);
            intent1.putExtra("price", hotel.getRoom().getMedium().getPrice());
            ArrayList<TimeBooked> bookedRoom = hotel.getBookedRoom().getMedium();
            Gson gson = new Gson();
            String myJson1 = gson.toJson(bookedRoom);
            intent1.putExtra("bookedRoom", myJson1);
            intent1.putExtra("path", path);
            intent1.putExtra("type", "Medium");
            intent1.putExtra("image",hotel.getRoom().getMedium().getImage().get(0));
            intent1.putExtra("numberRoom", hotel.getRoom().getMedium().getTotal());
            startActivity(intent1);
        });
        DatabaseReference UserData = FirebaseDatabase.getInstance().getReference("Users");
        UserData.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                btnComment.setOnClickListener(v -> {
                    String comment = etComment.getText().toString();

                    float rating = rateting.getRating();
                    if (comment.equals("")){
                        Snackbar snackbar = Snackbar
                                .make(main_content1, "Bạn chưa nhập comment", Snackbar.LENGTH_SHORT)
                                .setAction("Comment", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Code khi bấm vào nút thư lại ở đây
                                        etComment.requestFocus();
                                    }
                                });
                        snackbar.show();
                    }else if(rating == 0){
                        Snackbar snackbar = Snackbar
                                .make(main_content1, "Bạn chưa đánh giá", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }else{

                        Integer stt = 0;
                        Integer count =0;
                        boolean commented = false;
                        ArrayList<Rating> ratings = hotel.getRating();
                        if(ratings.size() == 0){
                            stt= 0;
                        }else{

                            for (int i = 0; i < ratings.size(); i++) {
                                if(ratings.get(i)==null){
                                    continue;
                                }
                                if(ratings.get(i).getUserId().equals(userId)){
                                    stt = i;
                                    commented = true;
                                    break;
                                }
                            }
                            if (!commented){
                                stt = ratings.size();
                            }
                        }

                        boolean reviewed = false;
                        ArrayList<Review> reviews = user.getReview();
                        Integer r_stt = 0;
                        if(reviews.size() == 0){
                            r_stt = 0;
                        }else{

                            for (int i = 0; i < reviews.size(); i++) {
                                if(reviews.get(i)==null){
                                    continue;
                                }
                                if(reviews.get(i).getName().equals(hotel.getName())){
                                    r_stt = i;
                                    reviewed = true;
                                    break;
                                }
                            }
                            if (!reviewed){
                                r_stt = reviews.size();
                            }
                        }

                        llComnent.setVisibility(View.GONE);
                        Rating comment1 = new Rating(comment,user.getName(), (int) rating, userId);
                        Review review = new Review(hotel.getName(),hotel.getImage(),comment, (int) rating);

                        FirebaseDatabase.getInstance().getReference("Users/" + userId + "/review/" + r_stt).setValue(review);

                        FirebaseDatabase.getInstance().getReference(path+"/rating/"+stt).setValue(comment1);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("test", "Failed to read value.");
            }
        });

        addComment.setOnClickListener(v -> {
            llComnent.setVisibility(View.VISIBLE);
            ArrayList<Rating> ratings = hotel.getRating();
            for (int i = 0; i < ratings.size(); i++) {
                if(ratings.get(i)==null){
                    continue;
                }
                if(ratings.get(i).getUserId().equals(userId)){
                    id = i;
                    etComment.setText(ratings.get(i).getComment());
                    rateting.setRating(ratings.get(i).getStar());
                    break;
                }
            }
        });



    }
}