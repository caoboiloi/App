package com.example.bookinghotel.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.bookinghotel.HotelDetail;
import com.example.bookinghotel.R;
import com.example.bookinghotel.Screen.Login.Login_Signin;
import com.example.bookinghotel.entity.Hotel;
import com.example.bookinghotel.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnShowUser, btnLogoout, btnHotel, btnShowHotel;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView ivImage;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        btnLogoout = rootView.findViewById(R.id.btnLogout);
        btnShowUser = rootView.findViewById(R.id.btnUser);
        btnHotel = rootView.findViewById(R.id.btnHotel);
        btnShowHotel = rootView.findViewById(R.id.btnShowHotel);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        //dang xuat
        btnLogoout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), Login_Signin.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        //read data user
        btnShowUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                mDatabase.child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        User user = dataSnapshot.getValue(User.class);

                        Log.e("test", user.toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.e("test", "Failed to read value.");
                    }
                });

            }
        });
        //   show all hotel
        btnHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Hotel/HoChiMinh");
//                mDatabase.child("1").child("name").setValue("Hotel bigger123");
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Hotel> hotels = new ArrayList<Hotel>();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Hotel hotel = postSnapshot.getValue(Hotel.class);
                            hotels.add(hotel);
                        }
                        for (Hotel i : hotels) {
                            Log.e("test", i.toString());
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        btnShowHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HotelDetail.class);
                getActivity().startActivity(intent);
            }
        });
    }
}