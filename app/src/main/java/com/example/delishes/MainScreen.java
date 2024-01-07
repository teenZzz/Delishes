package com.example.delishes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.delishes.databinding.ActivityMainScreenBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainScreen extends AppCompatActivity {

    private ActivityMainScreenBinding binding;
    private static final String MY_SETTINGS = "my_settings";
    private RecycAdapter adapter;
    private List<RecipeItem> originalRecipeItems = new ArrayList<>();
    private List<RecipeItem> likedRecipes = new ArrayList<>();

    // Добавьте ваш адаптер для второго RecyclerView
    private RecycAdapter loveReceptAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMainScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.topAppBar);

        RecyclerView recyclerView = binding.allrecept;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<RecipeItem> recipeItems = new ArrayList<>();
        adapter = new RecycAdapter(recipeItems);
        recyclerView.setAdapter(adapter);

        RecyclerView loveReceptRecyclerView = binding.loverecept; // Замените на ваш фактический идентификатор
        LinearLayoutManager loveReceptLayoutManager = new LinearLayoutManager(this);
        loveReceptRecyclerView.setLayoutManager(loveReceptLayoutManager);

        // Инициализация адаптера для второго RecyclerView
        loveReceptAdapter = new RecycAdapter(new ArrayList<>());
        loveReceptRecyclerView.setAdapter(loveReceptAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference recipesCollection = db.collection("Recepts");

        recipesCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String text = document.getString("Text");
                    String imageUrl = document.getString("Image");
                    String receptText = document.getString("ReceptText");

                    receptText = receptText.replace("\\n", System.getProperty("line.separator"));

                    Log.d("TAG", "Text: " + text + ", ImageUrl: " + imageUrl);

                    RecipeItem recipeItem = new RecipeItem(text, imageUrl, receptText);
                    recipeItems.add(recipeItem);
                    originalRecipeItems.add(recipeItem);
                }

                adapter.notifyDataSetChanged();
            } else {
                Log.e("TAG", "Error getting documents: ", task.getException());
            }
        });

        adapter.setOnItemClickListener(position -> {
            RecipeItem selectedRecipe = recipeItems.get(position);

            Intent intent = new Intent(MainScreen.this, ReceptDetailActivity.class);
            intent.putExtra("text", selectedRecipe.getText());
            intent.putExtra("imageUrl", selectedRecipe.getImageUrl());
            intent.putExtra("receptText", selectedRecipe.getReceptText());
            startActivity(intent);
        });

        adapter.setOnLikeButtonClickListener(new RecycAdapter.OnLikeButtonClickListener() {
            @Override
            public void onLikeButtonClick(int position, boolean isLiked) {
                RecipeItem recipeItem = recipeItems.get(position);

                if (isLiked) {
                    addToSecondRecyclerView(recipeItem);
                } else {
                    removeFromSecondRecyclerView(recipeItem);
                }
            }
        });

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }
        });

        setSupportActionBar(binding.topAppBar);

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.inbox_item_info) {
                    Intent intent2 = new Intent(MainScreen.this, StartActivity.class);
                    startActivity(intent2);
                } else if (id == R.id.outbox_item) {
                    Intent intent3 = new Intent(MainScreen.this, InfoActivity.class);
                    startActivity(intent3);
                }

                return true;
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

        Intent intent1 = new Intent(this, AllReceptActivity.class);
        Intent intent4 = new Intent(this, loveReceptActivity.class);

        binding.saladCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, CategoriesActivity.class);
                intent.putExtra("text", "Салаты");
                intent.putExtra("categories", 1);
                startActivity(intent);
            }
        });
        binding.delishCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, CategoriesActivity.class);
                intent.putExtra("text", "Закуски");
                intent.putExtra("categories", 2);
                startActivity(intent);
            }
        });
        binding.hotCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, CategoriesActivity.class);
                intent.putExtra("text", "Горячее");
                intent.putExtra("categories", 3);
                startActivity(intent);
            }
        });
        binding.soupCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, CategoriesActivity.class);
                intent.putExtra("text", "Супы");
                intent.putExtra("categories", 4);
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

    private void addToSecondRecyclerView(RecipeItem recipeItem) {
        if (!likedRecipes.contains(recipeItem)) {
            likedRecipes.add(recipeItem);
            updateSecondRecyclerView();
        }
    }

    private void removeFromSecondRecyclerView(RecipeItem recipeItem) {
        likedRecipes.remove(recipeItem);
        updateSecondRecyclerView();
    }

    private void updateSecondRecyclerView() {
        loveReceptAdapter.setData(likedRecipes);
        loveReceptAdapter.notifyDataSetChanged();
    }

    private void performSearch(String query) {
        List<RecipeItem> filteredList = new ArrayList<>();

        for (RecipeItem recipe : originalRecipeItems) {
            if (recipe.getText().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(recipe);
            }
        }

        adapter.setData(filteredList);
    }
}