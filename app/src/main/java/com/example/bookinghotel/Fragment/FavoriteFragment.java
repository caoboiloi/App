package com.example.bookinghotel.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bookinghotel.Adapter.BookedAdapter;
import com.example.bookinghotel.Adapter.HotelAdapter;
import com.example.bookinghotel.HotelDetail;
import com.example.bookinghotel.R;
import com.example.bookinghotel.Screen.Home.Home;
import com.example.bookinghotel.Screen.Login.Login_Signin;
import com.example.bookinghotel.entity.Hotel;
import com.example.bookinghotel.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ProgressBar progressBar_cyclic;
    TextView favorite_btn;

    private ArrayList<Hotel> hotels = new ArrayList<Hotel>();
    private HotelAdapter adapter;
    private SharedPreferences pref;
    private RecyclerView recyclerView;

    int temp = 0;

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

        recyclerView = rootView.findViewById(R.id.list_hotel_favourite);
        progressBar_cyclic = rootView.findViewById(R.id.progressBar_cyclic);
        favorite_btn = rootView.findViewById(R.id.favourite_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        pref = this.getActivity().getSharedPreferences("FavoriteHotelPef", Context.MODE_PRIVATE);
        return rootView;
    }

//    Tạo hướng đi giả cho dữ liệu

    public interface OnGetDataListener {
        void onSuccess(DataSnapshot dataSnapshot);
        void onStart();
        void onFailure();
    }

    public interface OnGetArrayHotels {
        void onSuccess(ArrayList<Hotel> temp_hotels);
        void onStart();
        void onFailure();
    }

//    hàm load dữ liệu từ database
//    Chạy vòng lặp vòng đời của firebase sẽ được load từ bất đồng bộ thành đồng bộ và lưu màng array list hotels ở hàm onSuccess

    public void readDataStringFavorite(String userId, final OnGetArrayHotels Favoritelistener) {
        Favoritelistener.onStart();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                if (user.getFavorite().size() != 0) {
                    for (String hotelPath : user.getFavorite()) {

                        readDataOneHotel(hotelPath, new OnGetDataListener() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                temp += 1;
                                Hotel hotel = dataSnapshot.getValue(Hotel.class);
                                hotel.setPath(dataSnapshot.getRef().toString().replace("https://hotelbooking-5a74a.firebaseio.com/",""));
                                hotels.add(hotel);
                                if (temp == user.getFavorite().size()) {
                                    Favoritelistener.onSuccess(hotels);
                                    temp = 0;
                                }
                            }
                            @Override
                            public void onStart() {
                                //when starting
                                Log.d("onStart", "Started");
                            }

                            @Override
                            public void onFailure() {
                                Log.d("onFailure", "Failed");
                            }
                        });
                    }
                }
                else {
                    progressBar_cyclic.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("test", "Failed to read value.");
                Favoritelistener.onFailure();
            }
        });
    }

//    hàm load dữ liệu từ database - path favorite

    public void readDataOneHotel(String hotelPath, final OnGetDataListener listener) {
        listener.onStart();
        FirebaseDatabase.getInstance().getReference(hotelPath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailure();
            }
        });
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        progressBar_cyclic.setVisibility(View.VISIBLE);
        adapter = new HotelAdapter(getActivity(), hotels);
        recyclerView.setAdapter(adapter);



    }

    @Override
    public void onResume() {
        super.onResume();
        hotels.clear();
        adapter.notifyDataSetChanged();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        readDataStringFavorite(userId, new OnGetArrayHotels() {
            @Override
            public void onSuccess(ArrayList<Hotel> temp_hotels) {
                if (temp_hotels.size() != 0) {
                    adapter.notifyDataSetChanged();
                    progressBar_cyclic.setVisibility(View.GONE);
                }
            }
            @Override
            public void onStart() {
                //when starting
                Log.d("onStart", "Started");
            }

            @Override
            public void onFailure() {
                Log.d("onFailure", "Failed");
            }
        });
        adapter.notifyDataSetChanged();

    }
}