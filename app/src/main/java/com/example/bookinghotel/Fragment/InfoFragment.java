package com.example.bookinghotel.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bookinghotel.R;
import com.example.bookinghotel.entity.Hotel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.bookinghotel.entity.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    LinearLayout personalinfo, booking_details, review;
    TextView personalinfobtn, booking_details_btn, reviewbtn;

    TextView name_user, des_user, phone_user, email_user, address_user;

    User user;

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

        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        personalinfo = rootView.findViewById(R.id.personalinfo);
        booking_details = rootView.findViewById(R.id.booking_details);
        review = rootView.findViewById(R.id.review);
        personalinfobtn = rootView.findViewById(R.id.personalinfobtn);
        booking_details_btn = rootView.findViewById(R.id.booking_details_btn);
        reviewbtn = rootView.findViewById(R.id.reviewbtn);
        /*making personal info visible*/
        personalinfo.setVisibility(View.VISIBLE);
        booking_details.setVisibility(View.GONE);
        review.setVisibility(View.GONE);

        name_user = rootView.findViewById(R.id.name_user);
        phone_user = rootView.findViewById(R.id.phone_user);
        des_user = rootView.findViewById(R.id.des_user);
        email_user = rootView.findViewById(R.id.email_user);
        address_user = rootView.findViewById(R.id.address_user);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        personalinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.VISIBLE);
                booking_details.setVisibility(View.GONE);
                review.setVisibility(View.GONE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.blue));
                booking_details_btn.setTextColor(getResources().getColor(R.color.grey));
                reviewbtn.setTextColor(getResources().getColor(R.color.grey));

            }
        });

        booking_details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.GONE);
                booking_details.setVisibility(View.VISIBLE);
                review.setVisibility(View.GONE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.grey));
                booking_details_btn.setTextColor(getResources().getColor(R.color.blue));
                reviewbtn.setTextColor(getResources().getColor(R.color.grey));

            }
        });

        reviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.GONE);
                booking_details.setVisibility(View.GONE);
                review.setVisibility(View.VISIBLE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.grey));
                booking_details_btn.setTextColor(getResources().getColor(R.color.grey));
                reviewbtn.setTextColor(getResources().getColor(R.color.blue));

            }
        });

        DatabaseReference UserData = FirebaseDatabase.getInstance().getReference("Users");

//      Load user from id
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        UserData.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                user = dataSnapshot.getValue(User.class);

//              set data in TextView
                name_user.setText(user.getName());
                des_user.setText(user.getLove());
                email_user.setText(user.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("test", "Failed to read value.");
            }
        });

    }
}