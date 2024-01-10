package com.example.delishes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.delishes.databinding.ActivityCategoriesBinding;
import com.example.delishes.databinding.ActivityMainScreenBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private ActivityCategoriesBinding binding;
    private RecycAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_categories);
        binding = ActivityCategoriesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        String text2 = intent.getStringExtra("text");
        int categoriesValue = intent.getIntExtra("categories", 0);
        binding.nameCategories.setText(text2);


        RecyclerView recyclerView = binding.receptforcategor;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<RecipeItem> recipeItems = new ArrayList<>();
        adapter = new RecycAdapter(recipeItems, true);
        recyclerView.setAdapter(adapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference recipesCollection = db.collection("Recepts");
        recipesCollection.whereEqualTo("Categories", categoriesValue)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String text = document.getString("Text");
                            String imageUrl = document.getString("Image");
                            String receptText = document.getString("ReceptText");

                            receptText = receptText.replace("\\n", System.getProperty("line.separator"));

                            recipeItems.add(new RecipeItem(text, imageUrl, receptText));
                        }

                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("TAG", "Error getting documents: ", task.getException());
                    }
                });
        adapter.setOnItemClickListener(position -> {
            // Обработка нажатия
            RecipeItem selectedRecipe = recipeItems.get(position);

            // Создание Intent для перехода на ReceptDetailActivity и передача данных
            Intent intent3 = new Intent(CategoriesActivity.this, ReceptDetailActivity.class);
            intent3.putExtra("text", selectedRecipe.getText());
            intent3.putExtra("imageUrl", selectedRecipe.getImageUrl());
            intent3.putExtra("receptText", selectedRecipe.getReceptText());
            startActivity(intent3);
        });


        Intent intent2 = new Intent(this, MainScreen.class);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
                finish();
            }
        });
    }
}