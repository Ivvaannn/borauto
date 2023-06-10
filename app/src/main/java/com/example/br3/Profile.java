package com.example.br3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class Profile extends AppCompatActivity {
    public TextView phonetw, emailtw, addresstw, nametw;
    public ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        phonetw = findViewById(R.id.PhoneTextView);
        emailtw = findViewById(R.id.EmailTextView);
        addresstw = findViewById(R.id.SityLandTextView);
        nametw = findViewById(R.id.NameTextView);
        avatar = findViewById(R.id.AvatarImageView);
        phonetw.setText(Roles.Phone);
        emailtw.setText(Roles.Email);
        addresstw.setText(Roles.Address);
        nametw.setText(Roles.Name);
        if(Roles.Img != null){
          Picasso.get().load(Roles.Img).into(avatar);
       }
    }

    public void clickUpdateProfile(View view) {
        Intent intent = new Intent(Profile.this, UpdateProfile.class);
        startActivity(intent);
        finish();
    }

    public void AddNewApplications(View view) {
        Intent intent = new Intent(this, aplication.class);
        startActivity(intent);
        finish();
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

    public void ClickOrderBtn(View view) {
        Intent intent = new Intent(this, orders.class);
        startActivity(intent);
        finish();
    }

    public void ClickviewApp(View view) {
        Intent intent = new Intent(this, aplicationview.class);
        startActivity(intent);
        finish();
    }

    public void ExitBtnClick(View view) {
        Intent intent = new Intent(this, avtoriz.class);
        startActivity(intent);
        finish();

    }
}