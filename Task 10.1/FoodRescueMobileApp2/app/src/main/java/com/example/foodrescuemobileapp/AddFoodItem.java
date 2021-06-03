package com.example.foodrescuemobileapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodrescuemobileapp.data.DatabaseHelper;
import com.example.foodrescuemobileapp.model.Food;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AddFoodItem extends AppCompatActivity {
    Button saveButton;
    ImageButton image;
    String lng, lati;
    EditText titleText, descText, pickupText, quantityText;
    TextView locationText;
    CalendarView datepicker;
    String title, desc, pickup_time, location, date;
    int quantity;
    byte[] imageByte;
    DatabaseHelper db;
    int userid;

    private static final int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);
        image = findViewById(R.id.AddPhotoButton);
        saveButton = findViewById(R.id.SaveFoodButton);
        titleText = findViewById(R.id.TitleText);
        descText = findViewById(R.id.DescText);
        pickupText = findViewById(R.id.PickUpText);
        quantityText = findViewById(R.id.QuanityText);
        locationText = findViewById(R.id.LocationText);
        datepicker = findViewById(R.id.calendarView);

        db = new DatabaseHelper(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userid = extras.getInt("user_id");
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                title = titleText.getText().toString();
                desc = descText.getText().toString();
                pickup_time = pickupText.getText().toString();
                location = locationText.getText().toString();
                quantity = Integer.parseInt(quantityText.getText().toString());

                long result = db.insertFood (new Food(userid,title,desc,date,pickup_time,quantity,location,imageByte));
                if (result > 0)
                {
                    Toast.makeText(AddFoodItem.this, "Successfully Saved!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    setResult(1, intent);;
                    finish();
                }
                else
                {
                    Toast.makeText(AddFoodItem.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });

        locationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddFoodItem.this, Search.class);
                startActivityForResult(intent, 0);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(
                        getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            AddFoodItem.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1
                    );
                } else {
                    selectImage();
                }
            }
        });
        datepicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" +  (month +1) + "/" + year;
            }
        });
    }
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"), SELECT_PICTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == SELECT_PICTURE && grantResults.length > 0) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            if(data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        image.setImageBitmap(bitmap);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        imageByte = baos.toByteArray();
                    } catch (Exception exception){
                        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        else if (requestCode == 0)
        {
            String message = data.getStringExtra("longlat");
            message = message.replace("lat/lng: (", "");
            message = message.replace(")", "");
            String[] longlat = message.split(",");
            lati = longlat[0];
            lng = longlat[1];
            locationText.setText(lati+ "," + lng);
        }
    }
}