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
    private TextView password1, password2, mail, telefon, nameed, cityed;
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
        cityed = findViewById(R.id.editTextTextPersonName2);
        String USER_KEY = "Users";
        mBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        mAuth = FirebaseAuth.getInstance();
        Roles.City = "";
        Roles.Id_Users = "";
        Roles.role = "";
        Roles.Phone = "";
        Roles.Password = "";
        Roles.Email = "";
        Roles.Address = "";
        Roles.Img = null;
        Roles.Name = "";
    }

    public void ClickRegBtn(View view) {
        if (mail.getText().toString().isEmpty() || password1.getText().toString().isEmpty() || password2.getText().toString().isEmpty()  || telefon.getText().toString().isEmpty()
                || nameed.getText().toString().isEmpty() ||  cityed.getText().toString().isEmpty()) {
            Toast.makeText(registr.this, "Необходимо заполнить все поля!", Toast.LENGTH_SHORT).show();
        }
        if (!password1.getText().toString().equals(password2.getText().toString())){
            Toast.makeText(registr.this, "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
        }
        else if(password1.getText().toString().length()<5){
            Toast.makeText(registr.this, "Пароль не может быть меньше 6 символов!", Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(mail.getText().toString(), password1.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String id = mBase.push().getKey();
                                String email = mail.getText().toString();
                                String password = password1.getText().toString();
                                String phone = telefon.getText().toString();
                                String name = nameed.getText().toString();
                                String city = cityed.getText().toString();
                                String role = "isUser";
                                Roles.role = role;
                                Roles.Address = city;
                                Roles.Email = email;
                                Roles.Name = name;
                                Roles.Phone = phone;
                                Roles.Password = password;
                                Roles.City = city;
                                Roles.Id_Users = id;
                                Users useradd = new Users(id,name,email,password,phone,city,role,"");
                                mBase.child(id).setValue(useradd);
                                Toast.makeText(registr.this,"Регистрация прошла успешно!",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(registr.this, glavnaya.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(registr.this,"Ошибка регистрации! Проверьте правильность почты и повторите попытку.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void ClickBackBtn(View view) {
        Intent intent = new Intent(registr.this, avtoriz.class);
        startActivity(intent);
        finish();
    }
}