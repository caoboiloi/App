package com.example.bookinghotel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookinghotel.HotelDetail;
import com.example.bookinghotel.R;
import com.example.bookinghotel.entity.Hotel;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class BookedAdapter extends RecyclerView.Adapter<BookedAdapter.MyHolder> {

    private List<Hotel> data;
    private Context context;
    private View view;
    Geocoder geocoder;
    public BookedAdapter(Context context, List<Hotel> data) {
        this.data = data;
        this.context = context;
        geocoder = new Geocoder(context, Locale.getDefault());
    }



    @NonNull
    @Override
    public BookedAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custome_hotel_row, parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
//        Hotel h = data.get(position);

//        holder.name_hotel.setText(h.getName());
//        Integer price = h.getRoom().getLarge().getPrice();
//        Locale locale = new Locale("vi", "VN");
//        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
//        holder.price_hotel.setText(fmt.format(price)+" ");
//
//
////        SET BASE64 IMG
//
//        byte[] decodedString = Base64.decode(h.getImage(), Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        Bitmap bMapScaled = Bitmap.createScaledBitmap(decodedByte, 600, 800, true);
//        holder.image_hotel.setImageBitmap(bMapScaled);
//
//
//        holder.hotel_rating.setRating(h.getAveRating());
//
//        try {
//            List<Address> addresses = geocoder.getFromLocation(h.getLat(), h.getLongitude(), 1);
//            holder.hotel_location.setText(addresses.get(0).getAddressLine(0));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, HotelDetail.class);
//                intent.putExtra("path",  h.getPath());
//                intent.putExtra("hotelname",  h.getName());
//                context.startActivity(intent);
//            }
//        });

    }


    @Override
    public int getItemCount() {
//        return data.size();
        return 15;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {


        View view;
        TextView name_hotel;
        TextView type_hotel;
        TextView price_hotel, hotel_location;
        ImageView image_hotel;
        RatingBar hotel_rating;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name_hotel = itemView.findViewById(R.id.name_hotel);
            type_hotel = itemView.findViewById(R.id.type_hotel);
            price_hotel = itemView.findViewById(R.id.price_hotel);
            image_hotel = itemView.findViewById(R.id.image_hotel);
            hotel_location = itemView.findViewById(R.id.hotel_location);
            hotel_rating = itemView.findViewById(R.id.hotel_rating);
            this.view = itemView;
        }
    }
}
