package com.example.br3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class orders extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listdata;
    private List<Order> listTemp;
    private DatabaseReference mBase;
    private String OrderKey = "Orders";
    private FirebaseAuth mautch;
    String id_user;
    private TextView txtkol, txtprice, txtnew, txtobrab, txtotmen, txtzabron;
    int price = 0;
    int newbron = 0;
    int obrabbron = 0;
    int otmenenbron = 0;
    int zabron = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        txtkol = findViewById(R.id.textView10);
        txtprice = findViewById(R.id.textView13);
        listView = findViewById(R.id.listView);
        txtnew = findViewById(R.id.textView15);
        txtobrab = findViewById(R.id.textView16);
        txtotmen = findViewById(R.id.textView17);
        txtzabron = findViewById(R.id.textView18);
        if(!Roles.role.equals("isAdmin")) {
            txtnew.setVisibility(View.INVISIBLE);
            txtobrab.setVisibility(View.INVISIBLE);
            txtotmen.setVisibility(View.INVISIBLE);
            txtzabron.setVisibility(View.INVISIBLE);
        }
        mautch = FirebaseAuth.getInstance();
        listdata = new ArrayList<>();
        listTemp = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listdata);
        listView.setAdapter(adapter);
        mBase = FirebaseDatabase.getInstance().getReference(OrderKey);
        getDataFromDB();
        setOnClickItem();
    }

    public void ClickOrdersBtn(View view) {

    }

    private void getDataFromDB(){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listdata.size()>0)listdata.clear();
                if (listTemp.size()>0)listTemp.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    Order order = ds.getValue(Order.class);
                    id_user = mautch.getCurrentUser().getUid();
                    if(!(Roles.role.equals("isAdmin"))) {
                        if (order.idUser.equals(id_user)) {
                            assert order != null;
                            listdata.add(order.nameCar);
                            listTemp.add(order);
                            price += Integer.parseInt(order.price);
                        }
                    }
                    else{
                        assert order != null;
                        listdata.add(order.nameCar);
                        listTemp.add(order);
                        price += Integer.parseInt(order.price);
                    }
                    if(order.status.equals("Новый")){
                        newbron +=1;
                    }
                    else if(order.status.equals("Обрабатывается")){
                        obrabbron+=1;
                    }
                    else if(order.status.equals("Отменён")){
                        otmenenbron+=1;
                    }
                    else if(order.status.equals("Забронировано")){
                        zabron+=1;
                    }
                    txtkol.setText("Количество забронированных автомобилей: "+listdata.size());
                    txtprice.setText("Цена забронированных автомобилей: "+price);
                }
                adapter.notifyDataSetChanged();
                txtnew.setText("Новая бронь: "+newbron);
                txtobrab.setText("Обрабатывается: "+obrabbron);
                txtotmen.setText("Отменённая бронь: "+otmenenbron);
                txtzabron.setText("Забронировано: "+zabron);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mBase.addValueEventListener(valueEventListener);
    }
    private void setOnClickItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(orders.this);
                builder.setTitle("Перейти к заказу или удалить?");
                builder.setCancelable(false);
                builder.setMessage("Вы можете перейти на страницу с заказом товара, или удалить выбранную позицию из корзины нажав Удалить");
                builder.setPositiveButton("Перейти к заказу",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                Order order = listTemp.get(position);
                                Intent intent = new Intent(orders.this, OrdersPodrobno.class);
                                intent.putExtra("id", order.id);
                                intent.putExtra("Order_nameCar", order.nameCar);
                                intent.putExtra("Order_id", order.idOrder);
                                intent.putExtra("Order_idCar", order.idCar);
                                intent.putExtra("Order_idUser", order.idUser);
                                intent.putExtra("Order_date", order.date);
                                intent.putExtra("Order_price", order.price);
                                intent.putExtra("Order_address", order.address);
                                intent.putExtra("Order_status", order.status);
                                startActivity(intent);
                            }

                        });
                builder.setNegativeButton("Удалить заказ",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                Toast.makeText(orders.this, "Выбранный товар успешно удален", Toast.LENGTH_SHORT).show();
                                mBase.removeValue();
                                adapter.remove(adapter.getItem(position));
                                listView.setAdapter(adapter);
                                txtkol.setText("Количество заказов: 0");
                                txtprice.setText("Цена забронированных автомобилей: 0");
                                txtnew.setText("Новая бронь: 0");
                                txtobrab.setText("Обрабатывается: 0");
                                txtotmen.setText("Отменённая бронь: 0");
                                txtzabron.setText("Забронировано: 0");
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void ClickNewsBtn(View view) {
        Intent intent = new Intent(this, news_activ.class);
        startActivity(intent);
        finish();
    }

    public void ClickGlavnBtn(View view) {
        Intent intent = new Intent(this, glavnaya.class);
        startActivity(intent);
        finish();
    }
}