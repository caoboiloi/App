package com.example.bookinghotel.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookinghotel.R;
import com.example.bookinghotel.entity.Comment;
import com.example.bookinghotel.entity.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder>{
    private Context context;
    private ArrayList<Review> data;
    public ReviewAdapter(Context context, ArrayList<Review> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custome_review_row, parent, false);

        ReviewAdapter.ReviewHolder reviewHolder = new ReviewAdapter.ReviewHolder(view);
        return reviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewHolder holder, int position) {
        Review review = data.get(position);

        byte[] decodedString1 = Base64.decode(review.getImg(), Base64.DEFAULT);
        Bitmap decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);
        Bitmap bMapScaled1 = Bitmap.createScaledBitmap(decodedByte1, 1000, 400, true);

        holder.img_hotel_review.setImageBitmap(bMapScaled1);
        holder.comment_hotel_review.setText(review.getComment());
        holder.name_hotel_review.setText(review.getName());
        holder.review_hotel_rating.setRating(review.getRating());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ReviewHolder extends RecyclerView.ViewHolder{
        ImageView img_hotel_review;
        TextView name_hotel_review;
        TextView comment_hotel_review;
        RatingBar review_hotel_rating;
        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            img_hotel_review = itemView.findViewById(R.id.img_hotel_review);
            name_hotel_review = itemView.findViewById(R.id.name_hotel_review);
            comment_hotel_review = itemView.findViewById(R.id.comment_hotel_review);
            review_hotel_rating = itemView.findViewById(R.id.review_hotel_rating);
        }
    }
}
