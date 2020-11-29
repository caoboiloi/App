package com.example.bookinghotel.DialogFragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.bookinghotel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageDialog extends DialogFragment {

    private static final String TAG = "ImageDialog";
    private static final int GALLERY_REQUEST_CODE = 13331;

    TextView btn_cancel_avatar, btn_save_avatar, choose_gallery_edit_img_avatar;
    ImageView show_edit_avatar;

    String img_base_64 = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_edit_image, container, false);

        btn_cancel_avatar = rootView.findViewById(R.id.btn_cancel_avatar);
        btn_save_avatar = rootView.findViewById(R.id.btn_save_avatar);
        choose_gallery_edit_img_avatar = rootView.findViewById(R.id.choose_gallery_edit_img_avatar);
        show_edit_avatar = rootView.findViewById(R.id.show_edit_avatar);

        choose_gallery_edit_img_avatar.setOnClickListener((View v) -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Pick an image"),GALLERY_REQUEST_CODE);
        });

        btn_cancel_avatar.setOnClickListener((View v) -> {
            getDialog().dismiss();
        });

        btn_save_avatar.setOnClickListener((View v) -> {
            if(img_base_64.equals("")) {
                Toast.makeText(getActivity(), "Vui lòng chọn ảnh đại diện", Toast.LENGTH_SHORT).show();
            }
            else {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                mDatabase.child(userId).child("image").setValue(img_base_64);

                getDialog().dismiss();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri imagePath = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imagePath);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                img_base_64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            show avatart
            show_edit_avatar.setImageURI(imagePath);
        }
    }
}
