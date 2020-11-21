package com.example.bookinghotel;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class BaseActivity extends AppCompatActivity {

    public static final String LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String CONTACT = Manifest.permission.READ_CONTACTS;
    public static final String CALL = Manifest.permission.CALL_PHONE;

    public static final int ALLOWED = PackageManager.PERMISSION_GRANTED;
    public static final int DENIED = PackageManager.PERMISSION_DENIED;

    public boolean hasPermission(String permission) {
        int result =  ContextCompat.checkSelfPermission(this, permission);
        return result == ALLOWED;
    }

    public void requestPermission(String permission, int code) {
        ActivityCompat.requestPermissions(this,
                new String[] { permission }, code);
    }
}