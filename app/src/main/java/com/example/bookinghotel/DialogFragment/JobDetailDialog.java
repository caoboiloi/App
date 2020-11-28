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

public class JobDetailDialog extends DialogFragment {
    private static final String TAG = "JobDetailDialog";

    EditText et_job, et_workplace_job;
    TextView btn_cancel_job, btn_save_job;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_edit_job, container, false);

        et_job = rootView.findViewById(R.id.et_job);
        et_workplace_job = rootView.findViewById(R.id.et_workplace_job);

        btn_cancel_job = rootView.findViewById(R.id.btn_cancel_job);
        btn_save_job = rootView.findViewById(R.id.btn_save_job);

        btn_cancel_job.setOnClickListener((View v) -> {
            getDialog().dismiss();
        });

        btn_save_job.setOnClickListener((View v) -> {
            String job = et_job.getText().toString();
            String workplace = et_workplace_job.getText().toString();

            if (job.equals("")) {
                Toast.makeText(getActivity(),"Nghề nghiệp không hợp lệ", Toast.LENGTH_SHORT).show();
                et_job.requestFocus();
            }
            else if (workplace.equals("")) {
                Toast.makeText(getActivity(),"Nơi làm việc không hợp lệ", Toast.LENGTH_SHORT).show();
                et_workplace_job.requestFocus();
            }
            else {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                mDatabase.child(userId).child("job").setValue(job);
                mDatabase.child(userId).child("workplace").setValue(workplace);

                getDialog().dismiss();
            }
        });

        return rootView;
    }
}
