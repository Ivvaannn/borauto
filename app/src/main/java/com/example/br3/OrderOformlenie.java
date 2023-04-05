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

import java.util.Calendar;

public class OrderOformlenie extends AppCompatActivity {
    private TextView Order_id, Order_idCar, Order_idUser, Order_date, Order_price, Order_address,Order_nameCar;
    private DatabaseReference mBase;
    private FirebaseAuth mautch;
    private Button btnperehod, btnoforml;
    private String idOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_oformlenie);
        mautch = FirebaseAuth.getInstance();
        Order_id = findViewById(R.id.idOrdered);
        Order_idCar = findViewById(R.id.idCarOrdered);
        Order_idUser = findViewById(R.id.idUserOrdered);
        Order_date = findViewById(R.id.dateOrdered);
        Order_price = findViewById(R.id.priceOrdered);
        Order_address = findViewById(R.id.addressOrdered);
        Order_nameCar = findViewById(R.id.nameCarordered);
        btnperehod = findViewById(R.id.button17);
        btnoforml = findViewById(R.id.button11);
        mBase = FirebaseDatabase.getInstance().getReference("Orders");
        idOrder = mBase.push().getKey();
        getIntentMain();
    }

    private void getIntentMain(){
        Intent intent = getIntent();
        if(intent != null){
            Order_id.setText(idOrder);
            Order_nameCar.setText(intent.getStringExtra("Order_nameCar"));
            Order_idCar.setText(intent.getStringExtra("Order_idCar"));
            Order_idUser.setText(intent.getStringExtra("Order_idUser"));
            Order_date.setText(intent.getStringExtra("Order_date"));
            Order_price.setText(intent.getStringExtra("Order_price"));
        }
    }

    public void ClickBackBtn(View view) {
        Intent intent = new Intent(this, Car_podrobno.class);
        startActivity(intent);
        finish();
    }

    public void ClickRedactorCar(View view) {
        String id = mBase.getKey();
        String currentTime = Calendar.getInstance().getTime().toString();
        String id_user = mautch.getCurrentUser().getUid();
        Order order = new Order(id, Order_nameCar.getText().toString(), idOrder, id_user, Order_idCar.getText().toString(), currentTime, Order_address.getText().toString(), Order_price.getText().toString(), "Новый");
        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(Order_idCar.getText().toString()) && !TextUtils.isEmpty(id_user) && !TextUtils.isEmpty(currentTime) && !TextUtils.isEmpty(Order_address.getText().toString()) && !TextUtils.isEmpty(Order_price.getText().toString())) {
            mBase.push().setValue(order);
            Toast.makeText(OrderOformlenie.this, "Добавлено в корзину!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(OrderOformlenie.this, "Ошибка! Возможно, поля пустые!", Toast.LENGTH_SHORT).show();
        }
        btnperehod.setVisibility(View.VISIBLE);
        btnoforml.setVisibility(View.GONE);
    }

    public void ClickAddressBtn(View view) {
        Order_address.setText(Roles.Address);
    }

    public void PerehodOrderBtn(View view) {
        Intent intent = new Intent(this, orders.class);
        startActivity(intent);
        finish();
    }
}