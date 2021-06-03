package com.example.foodrescuemobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.example.foodrescuemobileapp.data.DatabaseHelper;
import com.example.foodrescuemobileapp.model.Food;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class home_screen extends AppCompatActivity implements RecyclerViewAdapter.OnClickListener {
    ListView foodArrayList;
    RecyclerView recyclerView;
    DatabaseHelper db;
    FloatingActionButton AddFoodItem;
    ImageButton popupButton;
    int userid, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_home_screen);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userid = extras.getInt("user_id");
        }
        Render();
        home = 1;
        popupButton = findViewById(R.id.ThePopupButton);
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(home_screen.this, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.homelist:
                                    Render();
                                    home = 1;
                                return true;
                            case R.id.mylist:
                                    RenderMINE();
                                    home = 0;
                                return true;
                            case R.id.cart:
                                Intent intent = new Intent(home_screen.this, Cart_checkout.class);
                                intent.putExtra("user_id", userid);
                                startActivity(intent);
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if (home == 1) {
                Render();
            } else {
                RenderMINE();
            }
        }
    }

    private void Render() {
        db = new DatabaseHelper(home_screen.this);
        recyclerView = findViewById(R.id.recyclerView);
        AddFoodItem = findViewById(R.id.AddFoodButton);
        List<Food> foodList = db.fetchAllFood();

        RecyclerViewAdapter Adapter = new RecyclerViewAdapter(this, foodList, this);
        recyclerView.setAdapter(Adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AddFoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_screen.this, AddFoodItem.class);
                intent.putExtra("user_id", userid);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void RenderMINE() {
        db = new DatabaseHelper(home_screen.this);
        recyclerView = findViewById(R.id.recyclerView);
        AddFoodItem = findViewById(R.id.AddFoodButton);
        List<Food> foodList = db.fetchAllMyFood(userid);

        RecyclerViewAdapter Adapter = new RecyclerViewAdapter(this, foodList, this);
        recyclerView.setAdapter(Adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AddFoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_screen.this, AddFoodItem.class);
                intent.putExtra("user_id", userid);
                startActivityForResult(intent, 1);
            }
        });
    }

    public void shareActivity(int position)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        db = new DatabaseHelper(home_screen.this);

        if (home == 1){
            List<Food> foodList = db.fetchAllFood();
            intent.putExtra(Intent.EXTRA_TEXT, foodList.get(position).getTitle());
        } else {
            List<Food> foodList = db.fetchAllMyFood(userid);
            intent.putExtra(Intent.EXTRA_TEXT, foodList.get(position).getTitle());
        }
        intent.setType("text/plain");
        Intent.createChooser(intent,"Share via");
        startActivity(intent);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(home_screen.this, FoodItem.class);
        List<Food> foodList;
        if (home == 1){
            foodList = db.fetchAllFood();
        } else {
            foodList = db.fetchAllMyFood(userid);
        }
        intent.putExtra("food_id", foodList.get(position).getFood_id());
        intent.putExtra("user_id",userid);
        startActivity(intent);
    }
}