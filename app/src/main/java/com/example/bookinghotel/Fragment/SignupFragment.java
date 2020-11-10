package com.example.bookinghotel.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookinghotel.R;
import com.example.bookinghotel.Screen.Home.Home;
import com.example.bookinghotel.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {
        TextView btn ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentActivity myContext;
    EditText etConfirm, etName, etPhone, etEmail, etPass;
    TextView tvError;
    Button btnSignup;
    RadioButton rbMale, rbFemale;


    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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
        View rootView =  inflater.inflate(R.layout.fragment_signup, container, false);
//        etDate = rootView.findViewById(R.id.etDate);
        etName = rootView.findViewById(R.id.etName);
        etPass = rootView.findViewById(R.id.etPass);
        etPhone = rootView.findViewById(R.id.etPhone);
        etEmail = rootView.findViewById(R.id.etEmail);
        btnSignup = rootView.findViewById(R.id.btnSignup);
        rbMale  = rootView.findViewById(R.id.rbMale);
        rbFemale  = rootView.findViewById(R.id.rbFemale);
        etConfirm = rootView.findViewById(R.id.etConfirm);
        tvError = rootView.findViewById(R.id.tverror);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etEmail.getText().toString();
                final String name = etName.getText().toString();
                final String pass = etPass.getText().toString();
                final String confirmPass = etConfirm.getText().toString();
                final String phone = etPhone.getText().toString();
                final FirebaseAuth mAuth;
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(name.equals("")){
                    Toast.makeText(myContext, "Tên không được để trống", Toast.LENGTH_SHORT).show();
                    etName.requestFocus();
                }
                else if(phone.length() < 10){
                    Toast.makeText(myContext, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                    etPhone.requestFocus();
                }
                else if(email.equals("") || !email.trim().matches(emailPattern)){
                    Toast.makeText(myContext, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                }else if (pass.length() < 8){
                    Toast.makeText(myContext, "Mật khẩu phải trên 7 kí tự", Toast.LENGTH_SHORT).show();
                    etPass.requestFocus();
                }
                else if(!pass.equals(confirmPass)){
                    Toast.makeText(myContext, "Xác nhận mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    etConfirm.requestFocus();
                }
                else if(!rbFemale.isChecked() && !rbMale.isChecked()){
                    Toast.makeText(myContext, "Bạn phải chọn giới tính", Toast.LENGTH_SHORT).show();
                }
                else{
                    final ProgressDialog progress = new ProgressDialog(getActivity());
                    progress.setMessage("Đang đăng kí...");
                    progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                    progress.show();
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        progress.dismiss();

                                        User u = new User(name, email);
                                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                                        FirebaseUser userID = FirebaseAuth.getInstance().getCurrentUser();
                                        if (userID != null) {
                                            String id = user.getUid();
                                            mDatabase.child(id).setValue(u);
                                            Intent intent = new Intent(getActivity(), Home.class);
                                            startActivity(intent);
                                            getActivity().finish();
                                        } else {
                                            // No user is signed in
                                        }

                                    } else {
                                        progress.dismiss();
                                        tvError.setText("Email đã được đăng kí");
                                    }

                                }
                            });

                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        myContext=(FragmentActivity) context;
        super.onAttach(context);
    }


}