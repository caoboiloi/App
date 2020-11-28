package com.example.bookinghotel.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bookinghotel.Adapter.BookedAdapter;
import com.example.bookinghotel.Adapter.CanceledAdapter;
import com.example.bookinghotel.Adapter.HotelAdapter;
import com.example.bookinghotel.R;
import com.example.bookinghotel.entity.Booked;
import com.example.bookinghotel.entity.Hotel;
import com.example.bookinghotel.entity.Ticket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    LinearLayout booked, canceled;
    TextView booked_btn, canceled_btn;

    RecyclerView rc_booked, rc_canceled;

    private BookedAdapter bookedAdapter;
    private CanceledAdapter canceledAdapter;

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
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        booked = rootView.findViewById(R.id.booked);
        canceled = rootView.findViewById(R.id.cancel);

        booked_btn = rootView.findViewById(R.id.booked_btn);
        canceled_btn = rootView.findViewById(R.id.cancel_btn);


        // Recycler View
        rc_booked = rootView.findViewById(R.id.list_booked);
        rc_canceled = rootView.findViewById(R.id.list_canceled);

        rc_booked.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rc_booked.setHasFixedSize(true);
        rc_booked.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        rc_canceled.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rc_canceled.setHasFixedSize(true);
        rc_canceled.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/"+userId+"/ticket");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<Ticket>> t = new GenericTypeIndicator<List<Ticket>>() {};
                List<Ticket> tickets = snapshot.getValue(t);
                final ArrayList<Ticket>[] tickets1 = new ArrayList[]{(ArrayList<Ticket>) tickets.stream().filter(p -> p.getStatus()).collect(Collectors.toList())};
                bookedAdapter = new BookedAdapter(getActivity(), tickets1[0]);
                rc_booked.setAdapter(bookedAdapter);

                booked_btn.setOnClickListener((View v) -> {
                    tickets1[0] = (ArrayList<Ticket>) tickets.stream().filter(p->p.getStatus()).collect(Collectors.toList());
                    booked_btn.setTextColor(getResources().getColor(R.color.blue));
                    bookedAdapter = new BookedAdapter(getActivity(), tickets1[0]);
                    rc_booked.setAdapter(bookedAdapter);
                    bookedAdapter.notifyDataSetChanged();
                    canceled_btn.setTextColor(getResources().getColor(R.color.grey));
                });

                canceled_btn.setOnClickListener((View v) -> {
                    tickets1[0] = (ArrayList<Ticket>) tickets.stream().filter(p->!p.getStatus()).collect(Collectors.toList());
                    canceledAdapter = new CanceledAdapter(getActivity(), tickets1[0]);
                    rc_booked.setAdapter(canceledAdapter);
                    bookedAdapter.notifyDataSetChanged();
                    canceled_btn.setTextColor(getResources().getColor(R.color.blue));
                    booked_btn.setTextColor(getResources().getColor(R.color.grey));
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}