package com.example.bookinghotel.DialogFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookinghotel.Fragment.InfoFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.bookinghotel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactDialog extends DialogFragment {

    private static final String TAG = "ContactDialog";

    RadioButton is_checked_male_contact, is_checked_female_contact;
    EditText et_phone_contact, et_email_contact, et_address_contact;
    TextView btn_cancel_contact, btn_save_contact;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_edit_contact, container, false);

        // Ánh xạ
        is_checked_male_contact = rootView.findViewById(R.id.is_checked_male_contact);
        is_checked_female_contact = rootView.findViewById(R.id.is_checked_female_contact);

        et_address_contact = rootView.findViewById(R.id.et_address_contact);
        et_email_contact = rootView.findViewById(R.id.et_email_contact);
        et_phone_contact = rootView.findViewById(R.id.et_phone_contact);

        btn_cancel_contact = (TextView) rootView.findViewById(R.id.btn_cancel_contact);
        btn_save_contact = (TextView) rootView.findViewById(R.id.btn_save_contact);

//
        btn_cancel_contact.setOnClickListener((View v) -> {
            getDialog().dismiss();
        });
        btn_save_contact.setOnClickListener((View v) -> {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            boolean is_checked_male = is_checked_male_contact.isChecked();
            boolean is_checked_female = is_checked_female_contact.isChecked();
            String address = et_address_contact.getText().toString();
            String email = et_email_contact.getText().toString();
            String phone = et_phone_contact.getText().toString();
            String sex = "";
            if (is_checked_male) {
                sex = "Nam";
            }
            else if (is_checked_female) {
                sex = "Nữ";
            }

            if (!is_checked_female && !is_checked_male) {
                Toast.makeText(getActivity(),"Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
            }
            else if (phone.length() < 10) {
                Toast.makeText(getActivity(),"Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                et_phone_contact.requestFocus();
            }
            else if (email.equals("") || !email.trim().matches(emailPattern)) {
                Toast.makeText(getActivity(),"Email không hợp lệ", Toast.LENGTH_SHORT).show();
                et_email_contact.requestFocus();
            }
            else if (address.equals("")) {
                Toast.makeText(getActivity(),"Địa chỉ không hợp lệ", Toast.LENGTH_SHORT).show();
                et_address_contact.requestFocus();
            }
            else {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                mDatabase.child(userId).child("sex").setValue(sex);
                mDatabase.child(userId).child("phone").setValue(phone);
                mDatabase.child(userId).child("email").setValue(email);
                mDatabase.child(userId).child("address").setValue(address);

//                InfoFragment fragment = (InfoFragment) getActivity().getSupportFragmentManager().findFragmentByTag("InfoFragment");

                getDialog().dismiss();
            }
        });
        return rootView;
    }
}
