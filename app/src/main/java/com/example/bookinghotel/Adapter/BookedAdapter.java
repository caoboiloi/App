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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookinghotel.HotelDetail;
import com.example.bookinghotel.R;
import com.example.bookinghotel.entity.Hotel;
import com.example.bookinghotel.entity.Ticket;
import com.example.bookinghotel.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookedAdapter extends RecyclerView.Adapter<BookedAdapter.MyHolder> {

    private List<Ticket> data;
    private Context context;
    private View view;
    Geocoder geocoder;
    public BookedAdapter(Context context, List<Ticket> data) {
        this.data = data;
        this.context = context;
        geocoder = new Geocoder(context, Locale.getDefault());
    }



    @NonNull
    @Override
    public BookedAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custome_booked_row, parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Ticket ticket = data.get(position);
        holder.price_hotel_booked.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(ticket.getPrice()));
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        Date batdau = new Date(ticket.getBegin());
        Date ketthuc = new Date(ticket.getEnd());
        String ngayBatDau = dateFormat.format(batdau);
        String ngayKetThuc = dateFormat.format(ketthuc);
        holder.date_hotel_booked_main.setText(ngayBatDau +"-"+ngayKetThuc);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(ticket.getPath());
        holder.loading.setVisibility(View.VISIBLE);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Hotel hotel = snapshot.getValue(Hotel.class);
                byte[] decodedString1 = Base64.decode(hotel.getImage(), Base64.DEFAULT);
                Bitmap decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);
                Bitmap bMapScaled1 = Bitmap.createScaledBitmap(decodedByte1, 1000, 400, true);
                holder.image_hotel_booked.setImageBitmap(bMapScaled1);
                holder.name_hotel_booked.setText(hotel.getName());
                holder.rating_hotel_booked.setRating(hotel.getAveRating());
                holder.loading.setVisibility(View.GONE);
                try {

                    List<Address> addresses = geocoder.getFromLocation(hotel.getLat(), hotel.getLongitude(), 1);
                    holder.city_hotel_booked.setText(addresses.get(0).getAdminArea());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        return data.size();
//        return 15;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {


        View view;
        TextView  date_hotel_booked_main;
        TextView city_hotel_booked;
        TextView price_hotel_booked,name_hotel_booked;
        ImageView image_hotel_booked;
        RatingBar rating_hotel_booked;
        ProgressBar loading;
        Button btnCancelRoom;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            date_hotel_booked_main = itemView.findViewById(R.id.date_hotel_booked_main);
            city_hotel_booked = itemView.findViewById(R.id.city_hotel_booked);
            price_hotel_booked = itemView.findViewById(R.id.price_hotel_booked);
            image_hotel_booked = itemView.findViewById(R.id.image_hotel_booked);
            rating_hotel_booked = itemView.findViewById(R.id.rating_hotel_booked);
            name_hotel_booked = itemView.findViewById(R.id.name_hotel_booked);
            loading = itemView.findViewById(R.id.loading);
            btnCancelRoom = itemView.findViewById(R.id.btnCancelRoom);
            this.view = itemView;
        }
    }
}
