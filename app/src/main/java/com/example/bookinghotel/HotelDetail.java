package com.example.bookinghotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.bookinghotel.Adapter.CommentAdapter;
import com.example.bookinghotel.entity.Comment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class HotelDetail extends AppCompatActivity {
    MaterialToolbar topAppBar;
    RecyclerView rvComment;
    ArrayList<Comment> data = new ArrayList<Comment>();
    CommentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        getSupportActionBar().hide();

        topAppBar = findViewById(R.id.topAppBar);
        rvComment = findViewById(R.id.rvComment);

        topAppBar.setNavigationOnClickListener(v ->  {
            // Handle navigation icon press
            finish();
        });

        topAppBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            item.setIcon(R.drawable.ic_baseline_favorite_24_red);

            return true;
        });
        for (int i = 1; i <= 5; i++) {
            Comment comment = new Comment("User"+i,"Comment"+i,i);
            data.add(comment);
        }
        adapter = new CommentAdapter(this, data);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvComment.setLayoutManager(manager);
        rvComment.setAdapter(adapter);

    }
}