package com.example.delishes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.delishes.databinding.ActivityMainScreenBinding;
import com.example.delishes.databinding.ActivityReceptDetailBinding;
import com.squareup.picasso.Picasso;

public class ReceptDetailActivity extends AppCompatActivity {
    private ActivityReceptDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityReceptDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ImageView imageView = findViewById(R.id.detail_imgrecept);
        TextView textViewTitle = findViewById(R.id.detail_recepttext);
        TextView textViewText = findViewById(R.id.detail_recept_text);

        // Получение данных из Intent
        Intent intent = getIntent();
        String text = intent.getStringExtra("text");
        String imageUrl = intent.getStringExtra("imageUrl");
        String receptText = intent.getStringExtra("receptText"); // Получение receptText

        // Установка данных в интерфейс
        textViewTitle.setText(text);

        // Загрузка изображения с использованием Picasso
        Picasso.get().load(imageUrl)
                .fit()
                .centerCrop()
                .into(imageView);

        // Установка receptText
        textViewText.setText(receptText);


        Intent intent2 = new Intent(this, MainScreen.class);
        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });
    }
}
