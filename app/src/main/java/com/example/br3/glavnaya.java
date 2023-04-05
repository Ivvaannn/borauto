package com.example.br3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class glavnaya extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listdata;
    private List<Car> listTemp;
    private DatabaseReference mBase;
    private String CarKeyNew = "Avto_New";
    private String CarKeyBU = "Avto_BU";
    private String CarKeyBit = "Avto_Bitoe";
    private SearchView Finder;
    private Button bt;
    String role;
    Integer ids;
    private TextView minpriceed, maxpriceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glavn);
        init();
        setOnClickItem();
    }

    private void init() {
        Intent intent = getIntent();
        if (intent != null) {
            role = intent.getStringExtra("Role");
        }
        Finder = findViewById(R.id.SearchView);
        listView = findViewById(R.id.listView);
        minpriceed = findViewById(R.id.minPriceEdit);
        maxpriceed = findViewById(R.id.MaxPriceEdit);

        listdata = new ArrayList<>();
        listTemp = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listdata);
        listView.setAdapter(adapter);

        bt = findViewById(R.id.button5);
        mBase = FirebaseDatabase.getInstance().getReference(CarKeyNew);
        getDataFromDB();
        Finder.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        if (role != null) {
            if (!role.equals("isAdmin")) {
                bt.setVisibility(View.GONE);
            }
        }
    }

    private void getDataFromDB() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listdata.size() > 0) listdata.clear();
                if (listTemp.size() > 0) listTemp.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (maxpriceed.getText().toString().isEmpty() || minpriceed.getText().toString().isEmpty()) {
                        Car car = ds.getValue(Car.class);
                        assert car != null;
                        listdata.add(car.date);
                        listTemp.add(car);
                    } else {
                        Car car = ds.getValue(Car.class);
                        assert car != null;
                        int price = Integer.parseInt(car.price);
                        int maxprice = Integer.parseInt(maxpriceed.getText().toString());
                        int minprice = Integer.parseInt(minpriceed.getText().toString());
                        if (price >= minprice && price <= maxprice) {
                            listdata.add(car.name);
                            listTemp.add(car);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                ids = listView.getId();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mBase.addValueEventListener(valueEventListener);
    }

    private void setOnClickItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Car car = listTemp.get(i);
                Intent intent = new Intent(glavnaya.this, Car_podrobno.class);
                intent.putExtra("Car_id", car.idCar);
                intent.putExtra("Car_img1", car.img1);
                intent.putExtra("Car_img2", car.img2);
                intent.putExtra("Car_img3", car.img3);
                intent.putExtra("Car_name", car.name);
                intent.putExtra("Car_description", car.description);
                intent.putExtra("Car_date", car.date);
                intent.putExtra("Car_equipment", car.equipment);
                intent.putExtra("Car_body", car.body);
                intent.putExtra("Car_price", car.price);
                startActivity(intent);
                finish();
            }
        });
    }

    public void ClickNewsBtn(View view) {
        Intent intent = new Intent(glavnaya.this, news_activ.class);
        startActivity(intent);
        finish();
    }

    public void ClickAddAvtoBtn(View view) {
        Intent intent = new Intent(glavnaya.this, newAvto.class);
        startActivity(intent);
        finish();
    }

    public void ClickOrdersBtn(View view) {
        Intent intent = new Intent(glavnaya.this, orders.class);
        startActivity(intent);
        finish();
    }

    public void ClickNewAvtoBtn(View view) {
        mBase = FirebaseDatabase.getInstance().getReference(CarKeyNew);
        getDataFromDB();
    }

    public void ClickBUAvtoBtn(View view) {
        mBase = FirebaseDatabase.getInstance().getReference(CarKeyBU);
        getDataFromDB();
    }

    public void ClickBitAvtoBtn(View view) {
        mBase = FirebaseDatabase.getInstance().getReference(CarKeyBit);
        getDataFromDB();
    }

    public void ClickPrimPriceBtn(View view) {
        getDataFromDB();
    }
}