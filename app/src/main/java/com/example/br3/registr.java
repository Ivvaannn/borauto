package com.example.br3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registr extends AppCompatActivity {
    private TextView password1, password2, mail, telefon, nameed, landed, sityed;
    private FirebaseAuth mAuth;
    private DatabaseReference mBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);
        mail = findViewById(R.id.editTextTextEmailAddress2);
        password1 = findViewById(R.id.editTextTextPassword3);
        password2 = findViewById(R.id.editTextTextPassword2);
        telefon = findViewById(R.id.editTextPhone);
        nameed = findViewById(R.id.editTextTextPersonName);
        landed = findViewById(R.id.editTextTextPersonName2);
        sityed = findViewById(R.id.editTextTextPersonName3);
        String USER_KEY = "Users";
        mBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        mAuth = FirebaseAuth.getInstance();
    }

    public void ClickRegBtn(View view) {
        if (mail.getText().toString().isEmpty()|| password1.getText().toString().isEmpty() || password2.getText().toString().isEmpty()) {
            Toast.makeText(registr.this, "Поля пустые", Toast.LENGTH_SHORT).show();
        }
        else if (password1.getText().equals(password2.getText())){
            Toast.makeText(registr.this, "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(mail.getText().toString(), password1.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String id = mBase.getKey();
                                String email = mail.getText().toString();
                                String password = password1.getText().toString();
                                String phone = telefon.getText().toString();
                                String name = nameed.getText().toString();
                                String land = landed.getText().toString();
                                String city = sityed.getText().toString();
                                String role = "isUser";
                                if (!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(land)&&!TextUtils.isEmpty(city)&&!TextUtils.isEmpty(name)){
                                    Users useradd = new Users(id,name,email,password,phone,land,city,role);
                                    mBase.push().setValue(useradd);
                                    Toast.makeText(registr.this,"Добавлено!",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(registr.this, glavnaya.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(registr.this,"Возможно некоторые поля пустые!",Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(registr.this,"Ошибка",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }
}