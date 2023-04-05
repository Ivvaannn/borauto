package com.example.br3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class OrdersPodrobno extends AppCompatActivity {
    private TextView Order_id, Order_idCar, Order_idUser, Order_date, Order_price, Order_address, Order_status,Order_nameCar;
    private DatabaseReference mBase;
    String id;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_podrobno);
        mBase = FirebaseDatabase.getInstance().getReference("Orders");
        Order_id = findViewById(R.id.idOrdered);
        Order_idCar = findViewById(R.id.idCarOrdered);
        Order_idUser = findViewById(R.id.idUserOrdered);
        Order_date = findViewById(R.id.dateOrdered);
        Order_price = findViewById(R.id.priceOrdered);
        Order_address = findViewById(R.id.addressOrdered);
        Order_status = findViewById(R.id.statusOrdered);
        Order_nameCar = findViewById(R.id.nameCarordered);
        btn = findViewById(R.id.button11);
        getIntentMain();
        if(!Roles.role.equals("isAdmin")){
            btn.setVisibility(View.GONE);
        }
    }

    private void saveData()
    {
        String id_order = Order_id.getText().toString();
        String car = Order_idCar.getText().toString();
        String user = Order_idUser.getText().toString();
        String date = Order_date.getText().toString();
        String price = Order_price.getText().toString();
        String address = Order_address.getText().toString();
        String status = Order_status.getText().toString();
        String nameCar = Order_nameCar.getText().toString();
        Order order = new Order(id, nameCar, id_order,user,car,date,address,price,status);
        if (!TextUtils.isEmpty(id)&&!TextUtils.isEmpty(car)&&!TextUtils.isEmpty(user)&&!TextUtils.isEmpty(date)&&!TextUtils.isEmpty(price)&&!TextUtils.isEmpty(address)&&!TextUtils.isEmpty(status)){
            if (id_order != null)mBase.child(id_order).setValue(order);

        }else{
            Toast.makeText(OrdersPodrobno.this,"Возможно некоторые поля пустые!", Toast.LENGTH_SHORT).show();
        }
    }
    private void getIntentMain() {
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            Order_id.setText(intent.getStringExtra("Order_id"));
            Order_nameCar.setText(intent.getStringExtra("Order_nameCar"));
            Order_idCar.setText(intent.getStringExtra("Order_idCar"));
            Order_idUser.setText(intent.getStringExtra("Order_idUser"));
            Order_date.setText(intent.getStringExtra("Order_date"));
            Order_price.setText(intent.getStringExtra("Order_price"));
            Order_address.setText(intent.getStringExtra("Order_address"));
            Order_status.setText(intent.getStringExtra("Order_status"));
        }
    }

    public void ClickBackBtn(View view) {
        Intent intent = new Intent(this, Order.class);
        startActivity(intent);
        finish();
    }

    public void ClickRedactorCar(View view) {
        saveData();
    }

}
