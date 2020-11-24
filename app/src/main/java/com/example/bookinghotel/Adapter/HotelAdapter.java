package com.example.bookinghotel.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookinghotel.R;
import com.example.bookinghotel.entity.Hotel;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.MyHolder> {

    private List<Hotel> data;
    private Context context;
    private View view;

    public HotelAdapter(Context context, List<Hotel> data) {
        this.data = data;
        this.context = context;
    }
    @NonNull
    @Override
    public HotelAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custome_hotel_row, parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Hotel h = data.get(position);

//        holder.name_hotel.setText(h.getName());
//        holder.type_hotel.setText(h.getRoom().getLarge().getPrice());
//        holder.price_hotel.setText(h.getRoom().getLarge().getPrice());

//        byte[] decodedString = Base64.decode(h.getImage(), Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//
//        holder.image_hotel.setImageBitmap(decodedByte);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {


        View view;
        TextView name_hotel;
        TextView type_hotel;
        TextView price_hotel;
        ImageView image_hotel;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name_hotel = itemView.findViewById(R.id.name_hotel);
            type_hotel = itemView.findViewById(R.id.type_hotel);
            price_hotel = itemView.findViewById(R.id.price_hotel);
            image_hotel = itemView.findViewById(R.id.image_hotel);
            this.view = itemView;
        }
    }
}
