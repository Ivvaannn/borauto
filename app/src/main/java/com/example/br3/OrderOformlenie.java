package com.example.br3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OrderOformlenie extends AppCompatActivity {
    private TextView Order_date, Order_price, Order_address,Order_nameCar;
    private DatabaseReference mBase;
    private FirebaseAuth mautch;
    private Button btnperehod, btnoforml;
    private String Order_idCar, Order_idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_oformlenie);
        mautch = FirebaseAuth.getInstance();
        Order_date = findViewById(R.id.dateOrdered);
        Order_price = findViewById(R.id.priceOrdered);
        Order_address = findViewById(R.id.addressOrdered);
        Order_nameCar = findViewById(R.id.nameCarordered);
        btnperehod = findViewById(R.id.button17);
        btnoforml = findViewById(R.id.button11);
        mBase = FirebaseDatabase.getInstance().getReference("Orders");
        getIntentMain();
    }

    private void getIntentMain(){
        Intent intent = getIntent();
        if(intent != null){
            Order_nameCar.setText(intent.getStringExtra("Order_nameCar"));
            Order_idCar = intent.getStringExtra("Order_idCar");
            Order_idUser = intent.getStringExtra("Order_idUser");
            Order_date.setText(intent.getStringExtra("Order_date"));
            Order_price.setText(intent.getStringExtra("Order_price"));
        }
    }

    public void ClickBackBtn(View view) {
        Intent intent = new Intent(this, glavnaya.class);
        startActivity(intent);
        finish();
    }

    public void ClickRedactorCar(View view) {
        String id = mBase.push().getKey();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String id_user = mautch.getCurrentUser().getUid();
        Order order = new Order(id, Order_nameCar.getText().toString(), id, id_user, Order_idCar, currentDate, Order_address.getText().toString(), Order_price.getText().toString(), "Новый");
        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(Order_idCar) && !TextUtils.isEmpty(id_user) && !TextUtils.isEmpty(currentDate) && !TextUtils.isEmpty(Order_address.getText().toString()) && !TextUtils.isEmpty(Order_price.getText().toString())) {
            mBase.child(id).setValue(order);
            Toast.makeText(OrderOformlenie.this, "Добавлено в корзину!", Toast.LENGTH_SHORT).show();
            btnperehod.setVisibility(View.VISIBLE);
            btnoforml.setVisibility(View.GONE);
        } else {
            Toast.makeText(OrderOformlenie.this, "Заполните все поля и повторите попытку!", Toast.LENGTH_SHORT).show();
        }
    }

    public void ClickAddressBtn(View view) {
        Order_address.setText(Roles.Phone);
    }

    public void PerehodOrderBtn(View view) {
        Intent intent = new Intent(this, orders.class);
        startActivity(intent);
        finish();
    }
}