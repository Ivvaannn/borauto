package com.example.br3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class newspodrobno extends AppCompatActivity {
    TextView tvText, tvTitle, tvDate;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspodrobno);
        tvText = findViewById(R.id.tvText);
        tvTitle = findViewById(R.id.tvTitle);
        img = findViewById(R.id.imagenews);
        tvDate = findViewById(R.id.tvDate);
        getIntentMain();
    }
    private void getIntentMain(){
        Intent intent = getIntent();
        if(intent != null){
            tvTitle.setText(intent.getStringExtra("News_title"));
            tvText.setText(intent.getStringExtra("News_text"));
            Picasso.get().load(intent.getStringExtra("News_img")).into(img);
            tvDate.setText(intent.getStringExtra("News_date"));
        }
    }

    public void ClickBackBtn(View view) {
        Intent intent = new Intent(newspodrobno.this, news_activ.class);
        startActivity(intent);
        finish();
    }
}