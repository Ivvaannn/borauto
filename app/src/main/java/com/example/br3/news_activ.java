package com.example.br3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class news_activ extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listdata;
    private List<News> listTemp;
    private DatabaseReference mBase;
    private String NewsKey = "News";
    private TextView txt;
    private ImageView img;
    public int TOTAL_LIST_ITEMS = 6;
    public int NUM_ITEMS_PAGE = 5;
    private int noOfBtns;
    private Button[] btns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        init();
        setOnClickItem();
        ListViewUpdate();
    }

    private void init() {
        listView = findViewById(R.id.listView);
        listdata = new ArrayList<>();
        listTemp = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listdata);
        listView.setAdapter(adapter);
        mBase = FirebaseDatabase.getInstance().getReference(NewsKey);
        txt = findViewById(R.id.textView8);
        img = findViewById(R.id.imageView16);
        getDataFromDB();
        String t = Roles.role;
        if(!t.equals("isAdmin")){
            txt.setVisibility(View.GONE);
            img.setVisibility(View.GONE);
        }
    }
    private void Btnfooter() {
        ListViewUpdate();
    }
    public void ListViewUpdate() {
        int val = TOTAL_LIST_ITEMS % NUM_ITEMS_PAGE;
        val = val == 0 ? 0 : 1;
        noOfBtns = TOTAL_LIST_ITEMS / NUM_ITEMS_PAGE + val;
        LinearLayout ll = (LinearLayout) findViewById(R.id.btnLay);
        btns = new Button[noOfBtns];
        for (int i = 0; i < noOfBtns; i++) {
            btns[i] = new Button(this);
            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText("" + (i + 1));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(btns[i], lp);
            final int j = i;
            btns[j].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    loadList(j);
                    CheckBtnBackGroud(j);
                }
            });
        }
    }
    private void CheckBtnBackGroud(int index) {
        for (int i = 0; i < noOfBtns; i++) {
            if (i == index) {
                btns[i].setTextColor(getResources().getColor(android.R.color.black));
            } else {
                btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btns[i].setTextColor(getResources().getColor(android.R.color.white));
            }
        }
    }

    private void loadList(int number) {
        ArrayList<String> sort = new ArrayList<String>();
        int start = number * NUM_ITEMS_PAGE;
        for (int i = start; i < (start) + NUM_ITEMS_PAGE; i++) {
            if (i < listdata.size()) {
                sort.add(listdata.get(i));
            } else {
                break;
            }
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,sort);
        listView.setAdapter(adapter);
    }
    private void getDataFromDB() {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String date = "";
                Date docDate = new Date();
                Date currentTime = Calendar.getInstance().getTime();
                if (listdata.size() > 0) listdata.clear();
                if (listTemp.size() > 0) listTemp.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {

                        News news = ds.getValue(News.class);
                        date = news.date;
                        SimpleDateFormat format = new SimpleDateFormat();
                        format.applyPattern("dd.MM.yyyy");
                        try {
                            docDate = format.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        assert news != null;
                        if (docDate.getTime() > currentTime.getTime()) {
                            listdata.add(news.title);
                            listTemp.add(news);
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
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News news = listTemp.get(i);
                Intent intent = new Intent(news_activ.this, newspodrobno.class);
                intent.putExtra("News_title", news.title);
                intent.putExtra("News_text", news.text);
                intent.putExtra("News_img", news.img);
                intent.putExtra("News_date", news.date);
                startActivity(intent);
                finish();
            }
        });
    }
    public void ClickAddNewsBtn (View view){
        Intent intent = new Intent(news_activ.this, addnews.class);
        startActivity(intent);
        finish();
    }

    public void ClickGlavnBtnClick(View view) {
        Intent intent = new Intent(this, glavnaya.class);
        startActivity(intent);
        finish();
    }

    public void ClickOrderBtn(View view) {
        Intent intent = new Intent(this, orders.class);
        startActivity(intent);
        finish();
    }
}

