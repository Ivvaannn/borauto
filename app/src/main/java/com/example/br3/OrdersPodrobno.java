package com.example.br3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class OrdersPodrobno extends AppCompatActivity {
    private TextView  Order_date, Order_price, Order_address,Order_nameCar;
    private DatabaseReference mBase;
    String id, Order_id, Order_idCar, Order_idUser, status;
    private Button btn;
    RadioButton newerb, odobrrb, otklrb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_podrobno);
        mBase = FirebaseDatabase.getInstance().getReference("Orders");
        Order_date = findViewById(R.id.dateOrdered);
        Order_price = findViewById(R.id.priceOrdered);
        Order_address = findViewById(R.id.addressOrdered);
        Order_nameCar = findViewById(R.id.nameCarordered);
        newerb = findViewById(R.id.newstatusbtn);
        odobrrb = findViewById(R.id.odobrenostatusbtn);
        otklrb = findViewById(R.id.otklonenostatusbtn);
        btn = findViewById(R.id.button11);
        getIntentMain();
        if(!Roles.role.equals("isAdmin")){
            btn.setVisibility(View.GONE);
            newerb.setEnabled(false);
            odobrrb.setEnabled(false);
            otklrb.setEnabled(false);
        }
    }

    private void saveData()
    {
        String date = Order_date.getText().toString();
        String price = Order_price.getText().toString();
        String address = Order_address.getText().toString();
        if (newerb.isChecked()) {
            status = "Новый";
        }
        else if (odobrrb.isChecked()){
            status = "Одобрено";
        }
        else {
            status = "Отклонено";
        }
        String nameCar = Order_nameCar.getText().toString();
        Order order = new Order(id, nameCar, Order_id, Order_idUser, Order_idCar, date, address, price, status);
        if (!TextUtils.isEmpty(id)&&!TextUtils.isEmpty(Order_idCar)&&!TextUtils.isEmpty(Order_idUser)&&!TextUtils.isEmpty(date)&&!TextUtils.isEmpty(price)&&!TextUtils.isEmpty(address)&&!TextUtils.isEmpty(status)){
            if (Order_id != null){
                mBase.child(Order_id).setValue(order);
                Toast.makeText(OrdersPodrobno.this,"Изменения приняты успешно!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, orders.class);
                startActivity(intent);
                finish();
            }
        }else{
            Toast.makeText(OrdersPodrobno.this,"Возникла непредвиденная ошибка. Повторите попытку позже.", Toast.LENGTH_SHORT).show();
        }
    }
    private void getIntentMain() {
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            Order_nameCar.setText(intent.getStringExtra("Order_nameCar"));
            Order_date.setText(intent.getStringExtra("Order_date"));
            Order_price.setText(intent.getStringExtra("Order_price"));
            Order_address.setText(intent.getStringExtra("Order_address"));
            Order_id = intent.getStringExtra("Order_id");
            Order_idCar = intent.getStringExtra("Order_idCar");
            Order_idUser = intent.getStringExtra("Order_idUser");
            newerb.setChecked(intent.getStringExtra("Order_status").equals("Новый"));
            otklrb.setChecked(intent.getStringExtra("Order_status").equals("Отклонено"));
            odobrrb.setChecked(intent.getStringExtra("Order_status").equals("Одобрено"));
        }
    }

    public void ClickBackBtn(View view) {
        Intent intent = new Intent(this, orders.class);
        startActivity(intent);
        finish();
    }

    public void ClickRedactorCar(View view) {
        saveData();
    }

    public void ClickRemovebtn(View view) {
        if (Order_id != null){
            mBase.child(Order_id).removeValue();
            Toast.makeText(OrdersPodrobno.this,"Бронь успешно удалена!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, orders.class);
            startActivity(intent);
            finish();
        }
    }
}
