package com.example.br3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class aplicationview extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listdata;
    private List<Applications> listTemp;
    private DatabaseReference mBase;
    private String Key = "Applications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplicationview);
        init();
        setOnClickItem();
    }

    private void init() {
        listView = findViewById(R.id.listview);
        listdata = new ArrayList<>();
        listTemp = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listdata);
        listView.setAdapter(adapter);
        mBase = FirebaseDatabase.getInstance().getReference(Key);
        getDataFromDB();
    }
    private void getDataFromDB() {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listdata.size() > 0) listdata.clear();
                if (listTemp.size() > 0) listTemp.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Applications app =  ds.getValue(Applications.class);
                    assert app != null;
                    if(!Roles.role.equals("isAdmin")) {
                        if (Roles.Id_Users.equals(app.idUsers)) {
                            listdata.add(app.car);
                            listTemp.add(app);
                        }
                    }
                    else{
                        listdata.add(app.car);
                        listTemp.add(app);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mBase.addValueEventListener(vListener);
    }
    private void setOnClickItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Applications applications = listTemp.get(position);
                Intent intent = new Intent(aplicationview.this, applicationpodrobno.class);
                intent.putExtra("app_id", applications.id);
                intent.putExtra("app_car", applications.car);
                intent.putExtra("app_date", applications.date);
                intent.putExtra("app_descriptions", applications.descriptions);
                intent.putExtra("app_equipment", applications.equipment);
                intent.putExtra("app_price", applications.price);
                intent.putExtra("app_idUsers", applications.idUsers);
                intent.putExtra("app_img1", applications.img1);
                intent.putExtra("app_img2", applications.img2);
                intent.putExtra("app_img3", applications.img3);
                intent.putExtra("app_komment", applications.komment);
                intent.putExtra("app_status", applications.status);
                startActivity(intent);
                finish();
            }
        });
    }

    public void ClickGlavnBtn(View view) {
        Intent intent = new Intent(this, glavnaya.class);
        startActivity(intent);
        finish();
    }

    public void ClickNewsBtn(View view) {
        Intent intent = new Intent(this, news_activ.class);
        startActivity(intent);
        finish();
    }

    public void ClickProfilebtn(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        finish();
    }
}