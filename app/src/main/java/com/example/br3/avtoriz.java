package com.example.br3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class avtoriz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avtoriz);
    }

    public void ClickVhodBtn(View view) {
        Intent intent = new Intent(avtoriz.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void ClickRegBtn(View view) {
        Intent intent = new Intent(avtoriz.this, registr.class);
        startActivity(intent);
        finish();
    }
}