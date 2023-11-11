package com.example.delishes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.delishes.databinding.ActivityMainScreenBinding;
import com.google.android.material.navigation.NavigationView;

public class MainScreen extends AppCompatActivity {

    private ActivityMainScreenBinding binding; //добавление binding
    private static final String MY_SETTINGS = "my_settings";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //убираем часть экрана
        setContentView(R.layout.activity_main_screen);
        binding = ActivityMainScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.topAppBar); //для установки appbar

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Обработка события поиска (нажатие Enter или кнопки поиска)
                // Вызов метода для выполнения поиска
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Обработка изменения текста в поле поиска
                return false;
            }
        });
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) { //обработка кнопок в navigationview
                int id = item.getItemId();

                if (id == R.id.inbox_item_info) {
                    // Обработка нажатия на пункт "Показать информационное окно"
                    Intent intent2 = new Intent(MainScreen.this, StartActivity.class);
                    startActivity(intent2);
                } else if (id == R.id.outbox_item) {
                    Intent intent3 = new Intent(MainScreen.this, InfoActivity.class);
                    startActivity(intent3);

                }


                return true;
            }
        });

        SharedPreferences sp = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE); //Для показа экрана, который при первом запуске
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
        Intent intent4 = new Intent(this, loveReceptActivity.class);
        //Обработчики кнопок
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

        binding.viewAllBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent4);
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit) { // Проверка, что была нажата нужная кнопка
            DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
            drawerLayout.openDrawer(GravityCompat.START); // Открывает Navigation Drawer с левой стороны
            return true; // Возвращаем true, чтобы обработать событие нажатия
        }
        return super.onOptionsItemSelected(item);
    }

    private void performSearch(String query) {
        // Ваш код для выполнения поиска
    }

}