package com.example.bookinghotel.Screen.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bookinghotel.MainActivity;
import com.example.bookinghotel.R;
import com.example.bookinghotel.Screen.Login.Login_Signin;
import com.example.bookinghotel.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {
    Button btnLogoout, btnShowUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_home);
        btnLogoout = findViewById(R.id.btnLogout);

        btnShowUser = findViewById(R.id.showUser);
        btnLogoout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(Home.this,
                        Login_Signin.class);
                startActivity(i);
                finish();
            }
        });

        //read data
        btnShowUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                mDatabase.child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        User user = dataSnapshot.getValue(User.class);

                        Log.e("test", "User name: " + user.getName() + ", email " + user.getEmail());
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.e("test", "Failed to read value.");
                    }
                });
            }
        });
    }
}