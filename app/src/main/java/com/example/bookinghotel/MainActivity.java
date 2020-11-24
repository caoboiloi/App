package com.example.bookinghotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bookinghotel.Screen.Home.Home;
import com.example.bookinghotel.Screen.Login.Login_Signin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// Intro
public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);

        Thread welcomeThread = new Thread() {
//
//            @Override
            public void run() {
                try {
                    super.run();
                    sleep(1000);
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    Intent i;
                    if (currentUser != null){
                        i = new Intent(MainActivity.this,
                                Home.class);
                    }else{
                        i = new Intent(MainActivity.this,
                                Login_Signin.class);
                    }
                    startActivity(i);
                    finish();
                } catch (Exception e) {

                }
            }
        };
        welcomeThread.start();
    }



}