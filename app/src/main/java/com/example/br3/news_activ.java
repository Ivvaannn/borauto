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
    private List<News> listTemp;
    private DatabaseReference mBase;
    private String NewsKey = "News";
    private Button bt;
    public ArrayList<Actions> arrayList;
    public AdapterList adapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        init();
        setOnClickItem();
    }

    private void init() {
        bt = findViewById(R.id.button18);
        listView = findViewById(R.id.listView);
        listTemp = new ArrayList<>();
        arrayList = new ArrayList<>();
        adapters = new AdapterList(this, R.layout.list_row, arrayList);
        listView.setAdapter(adapters);
        mBase = FirebaseDatabase.getInstance().getReference(NewsKey);
        getDataFromDB();
        if (!Roles.role.equals("isAdmin")) {
            bt.setVisibility(View.GONE);
        }
    }

    private void getDataFromDB() {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String date;
                Date docDate = new Date();
                Date currentTime = Calendar.getInstance().getTime();
                if (arrayList.size() > 0) arrayList.clear();
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
                        Date currentDate = new Date();
                        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                        String thisdate = dateFormat.format(currentDate);
                        String datenews = dateFormat.format(docDate);
                        assert news != null;
                        if (docDate.getTime() > currentTime.getTime() || thisdate.equals(datenews)) {
                            arrayList.add(new Actions(R.drawable.newsicon, news.title, "Дата: "+news.date));
                            listTemp.add(news);
                        }

                }
                adapters.notifyDataSetChanged();
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
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        finish();
    }
}

