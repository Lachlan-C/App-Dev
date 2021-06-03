package com.example.foodrescuemobileapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.foodrescuemobileapp.data.DatabaseHelper;
import com.example.foodrescuemobileapp.model.Cart;
import com.example.foodrescuemobileapp.model.Food;
import com.example.foodrescuemobileapp.util.PaymentsUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;

import java.util.Optional;


public class FoodItem extends FragmentActivity implements OnMapReadyCallback {
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 199;
    int foodid, userid;
    DatabaseHelper db;
    String latlng, lat, lng;
    Button AddToCart, googlePayButton;
    ImageView foodImage;
    TextView titleView, descView, dateview, pickuptimesview, numview;
    Food food;
    GoogleMap mMap;
    PaymentsClient paymentsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            foodid = extras.getInt("food_id");
            userid = extras.getInt("user_id");
        }
        paymentsClient = PaymentsUtil.createPaymentsClient(this);
        db = new DatabaseHelper(FoodItem.this);

        foodImage = findViewById(R.id.foodImage);
        AddToCart = findViewById(R.id.AddtoCart);
        titleView = findViewById(R.id.TitleTextView);
        descView = findViewById(R.id.DescTextView);
        pickuptimesview = findViewById(R.id.PickupTimesTextView);
        numview = findViewById(R.id.QuanityTextViewNum);
        dateview = findViewById(R.id.DateTextView);
        googlePayButton = findViewById(R.id.googlepay_button);

        food = db.fetchFood(foodid);

        byte[] image = food.getImage();
        Bitmap bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);
        foodImage.setImageBitmap(bmp);

        titleView.setText(food.getTitle());
        descView.setText(food.getDesc());
        pickuptimesview.setText(food.getPick_up_times());
        numview.setText(String.valueOf(food.getQuantity()));
        dateview.setText(food.getDate());
        latlng = food.getLocation();
        String[] separated = latlng.split(",");
        lat = separated[0];
        lng = separated[1];

        googlePayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPayment(v);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long result = db.insertToCart (new Cart(userid,foodid,food.getTitle()));
                if (result > 0)
                {
                    Toast.makeText(FoodItem.this, "Successfully Added to cart!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(FoodItem.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void requestPayment(View v) {

        String price = "1000";

        Optional paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(15635);

        PaymentDataRequest request =
                PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());
        AutoResolveHelper.resolveTask(
                paymentsClient.loadPaymentData(request),
                this,
                LOAD_PAYMENT_DATA_REQUEST_CODE);
        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Add a marker to the food
        LatLng marker = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
        mMap.addMarker(new MarkerOptions().position(marker).title(food.getTitle()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // value passed in AutoResolveHelper
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {

                    case Activity.RESULT_OK:
                        break;

                    case Activity.RESULT_CANCELED:
                        break;

                    case AutoResolveHelper.RESULT_ERROR:
                        break;
                }
        }
    }
}