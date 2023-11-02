package com.example.delishes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.delishes.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    //Переменные
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, slogan;

    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(R.layout.activity_splash_screen);

        //Анимации
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        //Присваивание
        image = findViewById(R.id.imageSplash);
        logo = findViewById(R.id.splashtext);
        slogan = findViewById(R.id.textsplash2);

        //Установил анимации
        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,MainScreen.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);


    }
}