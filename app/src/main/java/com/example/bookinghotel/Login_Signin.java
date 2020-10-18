package com.example.bookinghotel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Login_Signin extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    LoginFragment loginFragment;
    SignupFragment signupFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__signin);

        tabLayout = findViewById(R.id.tlLogin);
        viewPager = findViewById(R.id.pager);


        loginFragment = new LoginFragment();
        signupFragment = new SignupFragment();
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),0);
        viewPagerAdapter.addFragment(loginFragment, "Login");
        viewPagerAdapter.addFragment(signupFragment,"signup");
        viewPager.setAdapter(viewPagerAdapter);
    }
    private class ViewPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> fragments =new ArrayList<Fragment>();
        private List<String> fragmentsTitle =new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }
        public void addFragment (Fragment fragment, String title){
             fragments.add(fragment);
             fragmentsTitle.add(title);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentsTitle.get(position);
        }
    }

}

