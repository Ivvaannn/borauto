package com.example.br3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Car_podrobno extends AppCompatActivity {
    TextView tvName, tvDescription, tvdate, tvEquipment,tvBody,tvPrice;
    private String Orders_Key = "Orders", id_car, price_car, namecar;
    private DatabaseReference mBase;
    private FirebaseAuth mautch;
    private ImageView img1, img2, img3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_podrobno);
        img1 = findViewById(R.id.imgcar1);
        img2 = findViewById(R.id.imgcar2);
        img3 = findViewById(R.id.imgcar3);
        mautch = FirebaseAuth.getInstance();
        tvName = findViewById(R.id.appNameCar);
        tvDescription = findViewById(R.id.appEquipmentCar);
        tvdate = findViewById(R.id.appDescriptionsCar);
        tvEquipment = findViewById(R.id.tvEquipment);
        tvBody = findViewById(R.id.appDateCar);
        tvPrice = findViewById(R.id.appCarPrice);
        getIntentMain();
        mBase = FirebaseDatabase.getInstance().getReference(Orders_Key);
    }
    private void getIntentMain(){
        Intent intent = getIntent();
        if(intent != null){
            namecar = intent.getStringExtra("Car_name");
            tvName.setText("Название: "+intent.getStringExtra("Car_name"));
            tvDescription.setText("Описание авто: "+intent.getStringExtra("Car_description"));
            tvdate.setText("Дата выпуска авто: "+intent.getStringExtra("Car_date"));
            tvEquipment.setText("Комплектация авто: "+intent.getStringExtra("Car_equipment"));
            tvBody.setText("Тип кузова: "+intent.getStringExtra("Car_body"));
            tvPrice.setText("Цена: "+intent.getStringExtra("Car_price"));
            Picasso.get().load(intent.getStringExtra("Car_img1")).into(img1);
            Picasso.get().load(intent.getStringExtra("Car_img2")).into(img2);
            Picasso.get().load(intent.getStringExtra("Car_img3")).into(img3);
            price_car = intent.getStringExtra("Car_price");
            id_car = intent.getStringExtra("Car_id");
        }
    }

    public void ClickBackBtn(View view) {
        Intent intent = new Intent(this, glavnaya.class);
        startActivity(intent);
        finish();
    }

    public void ClickBronBtn(View view) {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String id_user = mautch.getCurrentUser().getUid();
        Intent intent = new Intent(this, OrderOformlenie.class);
        intent.putExtra("Order_nameCar", namecar);
        intent.putExtra("Order_idCar", id_car);
        intent.putExtra("Order_idUser", id_user);
        intent.putExtra("Order_date", currentDate);
        intent.putExtra("Order_price", price_car);
        startActivity(intent);
        finish();
    }
}