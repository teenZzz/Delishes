package com.example.delishes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.delishes.databinding.ActivityAllReceptBinding;
import com.example.delishes.databinding.ActivityCategoriesBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllReceptActivity extends AppCompatActivity {

    private ActivityAllReceptBinding binding;
    private RecycAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_all_recept);
        binding = ActivityAllReceptBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        RecyclerView recyclerView = binding.allrecepts;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<RecipeItem> recipeItems = new ArrayList<>();
        adapter = new RecycAdapter(recipeItems);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference recipesCollection = db.collection("Recepts");

        recipesCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String text = document.getString("Text");
                    String imageUrl = document.getString("Image");
                    String receptText = document.getString("ReceptText"); // Добавьте это

                    Log.d("TAG", "Text: " + text + ", ImageUrl: " + imageUrl);
                    recipeItems.add(new RecipeItem(text, imageUrl, receptText));
                }

                // Уведомляем адаптер об изменениях после получения данных
                adapter.notifyDataSetChanged();
            } else {
                Log.e("TAG", "Error getting documents: ", task.getException());
            }
        });

        adapter.setOnItemClickListener(position -> {
            // Обработка нажатия
            RecipeItem selectedRecipe = recipeItems.get(position);

            // Создание Intent для перехода на ReceptDetailActivity и передача данных
            Intent intent = new Intent(AllReceptActivity.this, ReceptDetailActivity.class);
            intent.putExtra("text", selectedRecipe.getText());
            intent.putExtra("imageUrl", selectedRecipe.getImageUrl());
            intent.putExtra("receptText", selectedRecipe.getReceptText());
            startActivity(intent);
        });

        Intent intent = new Intent(this, MainScreen.class);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });
    }
}