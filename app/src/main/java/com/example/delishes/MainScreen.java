package com.example.delishes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.delishes.databinding.ActivityMainScreenBinding;
import com.example.delishes.databinding.ActivitySplashScreenBinding;

public class MainScreen extends AppCompatActivity {

    private ActivityMainScreenBinding binding;
    private static final String MY_SETTINGS = "my_settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_screen);
        binding = ActivityMainScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        SharedPreferences sp = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        boolean hasVisited = sp.getBoolean("hasVisited", false);

        if(!hasVisited){
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("hasVisited", true);
            editor.apply();
        }
    }
}