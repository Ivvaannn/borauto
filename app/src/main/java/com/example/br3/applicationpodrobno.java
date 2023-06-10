package com.example.br3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class applicationpodrobno extends AppCompatActivity {
    TextView tvName, tvDescription, tvEquipment,tvDate,tvPrice, tvkomment;
    private String App_Key = "Applications", id_user, id, stimg1, stimg2, stimg3, status, name, description, date, equipment, price;
    private DatabaseReference mBase;
    private FirebaseAuth mautch;
    private ImageView img1, img2, img3;
    private RadioButton rbodobreno, rbotkloneno;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicationpodrobno);
        img1 = findViewById(R.id.imgcar1);
        img2 = findViewById(R.id.imgcar2);
        img3 = findViewById(R.id.imgcar3);
        mautch = FirebaseAuth.getInstance();
        tvName = findViewById(R.id.appNameCar);
        tvPrice = findViewById(R.id.appCarPrice);
        tvDescription = findViewById(R.id.appDescriptionsCar);
        tvEquipment = findViewById(R.id.appEquipmentCar);
        tvDate = findViewById(R.id.appDateCar);
        rbotkloneno = findViewById(R.id.rbOtkloneno);
        rbodobreno = findViewById(R.id.rbOdobreno);
        tvkomment = findViewById(R.id.editTextTextMultiLine);
        bt = findViewById(R.id.button8);
        if(!Roles.role.equals("isAdmin")){
            bt.setVisibility(View.INVISIBLE);
            rbotkloneno.setEnabled(false);
            rbodobreno.setEnabled(false);
            tvkomment.setVisibility(View.INVISIBLE);
        }
        getIntentMain();
        mBase = FirebaseDatabase.getInstance().getReference(App_Key);
        getIntentMain();
    }
    private void getIntentMain(){
        Intent intent = getIntent();
        if(intent != null){
            tvName.setText("Название: "+intent.getStringExtra("app_car"));
            tvDescription.setText("Описание авто: "+intent.getStringExtra("app_descriptions"));
            tvDate.setText("Дата выпуска авто: "+intent.getStringExtra("app_date"));
            tvEquipment.setText("Комплектация авто: "+intent.getStringExtra("app_equipment"));
            tvPrice.setText("Цена: "+intent.getStringExtra("app_price"));
            tvkomment.setText(intent.getStringExtra("app_komment"));
            String status = intent.getStringExtra("app_status");
            if(status != null) {
               rbodobreno.setChecked(intent.getStringExtra("app_status").equals("Одобрено"));
               rbotkloneno.setChecked(intent.getStringExtra("app_status").equals("Отклонено"));
            }
            Picasso.get().load(intent.getStringExtra("app_img1")).into(img1);
            Picasso.get().load(intent.getStringExtra("app_img2")).into(img2);
            Picasso.get().load(intent.getStringExtra("app_img3")).into(img3);
            stimg1 = intent.getStringExtra("app_img1");
            stimg2 = intent.getStringExtra("app_img2");
            stimg3 = intent.getStringExtra("app_img3");
            id = intent.getStringExtra("app_id");
            id_user = intent.getStringExtra("app_idUsers");
             name = intent.getStringExtra("app_car");
             description = intent.getStringExtra("app_descriptions");
             date = intent.getStringExtra("app_date");
             equipment = intent.getStringExtra("app_equipment");
             price = intent.getStringExtra("app_price");
        }
    }

    public void ClickSaveApp(View view) {
        String komment = tvkomment.getText().toString();
        if(rbodobreno.isChecked()){
            status = "Одобрено";
        }
        else if(rbotkloneno.isChecked()){
            status = "Отклонено";
        }

        Applications applications = new Applications(id, name, date, description, equipment, price, id_user, stimg1, stimg2, stimg3,status,komment);
        if (id != null) {
            mBase.child(id).setValue(applications);
            Toast.makeText(applicationpodrobno.this, "Изменения успешно приняты!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, aplicationview.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(applicationpodrobno.this, "Возможно некоторые поля пустые!", Toast.LENGTH_SHORT).show();
        }
    }

    public void ClickBackBtn (View view){
        Intent intent = new Intent(this, aplicationview.class);
        startActivity(intent);
        finish();
    }
}
