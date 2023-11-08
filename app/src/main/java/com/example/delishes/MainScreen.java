package com.example.delishes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.delishes.databinding.ActivityMainScreenBinding;
import com.google.android.material.navigation.NavigationView;

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
        setContentView(view);

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.inbox_item_info) {
                    // Обработка нажатия на пункт "Показать информационное окно"
                    Intent intent2 = new Intent(MainScreen.this, StartActivity.class);
                    startActivity(intent2);
                } else if (id == R.id.outbox_item) {
                    // Обработка нажатия на пункт "Пися"
                    // Вставьте здесь код, который нужно выполнить при этом событии
                } else if (id == R.id.favourites_item) {
                    // Обработка нажатия на пункт "кака"
                    // Вставьте здесь код, который нужно выполнить при этом событии
                }

                // Возвращаем true, чтобы указать, что событие было обработано
                return true;
            }
        });

        ImageView menuImageView = findViewById(R.id.menubtn);
        menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Откройте Navigation Drawer
                DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
                drawerLayout.openDrawer(GravityCompat.START); // Открывает Navigation Drawer с левой стороны
            }
        });



        SharedPreferences sp = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        boolean hasVisited = sp.getBoolean("hasVisited", false);

        if(!hasVisited){
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("hasVisited", true);
            editor.apply();
        }

        Intent intent = new Intent(this, CategoriesActivity.class);
        Intent intent1 = new Intent(this, AllReceptActivity.class);
        binding.saladCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        binding.delishCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        binding.hotCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        binding.soupCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        binding.viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });

    }
}