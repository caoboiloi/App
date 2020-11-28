package com.example.bookinghotel.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bookinghotel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AboutMeDialog extends DialogFragment {
    private static final String TAG = "AboutMeDialog";

    EditText et_about_me;
    TextView btn_cancel_about_me, btn_save_about_me;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_edit_aboutme, container, false);

        et_about_me = rootView.findViewById(R.id.et_about_me);

        btn_cancel_about_me = rootView.findViewById(R.id.btn_cancel_about_me);
        btn_save_about_me = rootView.findViewById(R.id.btn_save_about_me);

        btn_cancel_about_me.setOnClickListener((View v) -> {
            getDialog().dismiss();
        });

        btn_save_about_me.setOnClickListener((View v) -> {
            String about_me = et_about_me.getText().toString();

            if (about_me.equals("")) {
                Toast.makeText(getActivity(),"Giới thiệu không hợp lệ", Toast.LENGTH_SHORT).show();
            }
            else {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                mDatabase.child(userId).child("love").setValue(about_me);

                getDialog().dismiss();
            }
        });
        return rootView;
    }
}
