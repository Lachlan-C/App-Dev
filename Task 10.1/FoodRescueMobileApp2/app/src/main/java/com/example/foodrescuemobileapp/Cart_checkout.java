package com.example.foodrescuemobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.foodrescuemobileapp.data.DatabaseHelper;
import com.example.foodrescuemobileapp.model.Cart;
import com.example.foodrescuemobileapp.util.PaymentsUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart_checkout extends AppCompatActivity {
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 199;
    DatabaseHelper db;
    ListView listview;
    TextView TotalItemsView;
    ArrayList<String> cartArrayList;
    ArrayAdapter<String> adapter;
    Button googlePayButtonCart;
    PaymentsClient paymentsClient;
    int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        db = new DatabaseHelper(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userid = extras.getInt("user_id");
        }
        paymentsClient = PaymentsUtil.createPaymentsClient(this);
        listview = findViewById(R.id.ListViewCart);
        TotalItemsView = findViewById(R.id.TotalItems);
        googlePayButtonCart = findViewById(R.id.GooglePayButtonCart);
        db = new DatabaseHelper(Cart_checkout.this);
        cartArrayList = new ArrayList<>();

        List<Cart> cartItemList =db.fetchAllCarts(userid);
        TotalItemsView.setText(String.valueOf(cartItemList.size()));
        for(Cart cart :cartItemList)
        {
            cartArrayList.add(cart.getFood_title());

        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cartArrayList);
        listview.setAdapter(adapter);

        googlePayButtonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPayment(v);
            }
        });
    }
    private void requestPayment(View v) {

        String price = "1000";

        Optional paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(15635);

        PaymentDataRequest request =
                PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());
        //val data = paymentsClient.loadPaymentData(request);
        AutoResolveHelper.resolveTask(
                paymentsClient.loadPaymentData(request),
                this,
                LOAD_PAYMENT_DATA_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // value passed in AutoResolveHelper
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {

                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        //handlePaymentSuccess(paymentData);
                        break;

                    case Activity.RESULT_CANCELED:
                        // The user cancelled the payment attempt
                        break;

                    case AutoResolveHelper.RESULT_ERROR:
                        //Status status = AutoResolveHelper.getStatusFromIntent(data);
                        //handleError(status.getStatusCode());
                        break;
                }
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    };
}