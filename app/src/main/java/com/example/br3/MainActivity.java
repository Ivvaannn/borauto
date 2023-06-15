package com.example.br3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private EditText email_login;
    private EditText password_login;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email_login = findViewById(R.id.editTextTextEmailAddress);
        password_login = findViewById(R.id.editTextTextPassword);
        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();
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
    public void ClickBtnVhod(View view) {
        mAuth.signOut();
        if (email_login.getText().toString().isEmpty() || password_login.getText().toString().isEmpty()){
            Toast.makeText(MainActivity.this, "Введите почту и пароль!",Toast.LENGTH_SHORT).show();
        }else{
            mAuth.signInWithEmailAndPassword(email_login.getText().toString(),password_login.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference ref = db.getReference("Users");
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            Users user = ds.getValue(Users.class);
                                            if(user.email.equals(email_login.getText().toString())) {
                                                Roles.role = user.role;
                                                Roles.Address = user.city;
                                                Roles.Email = user.email;
                                                Roles.Name = user.name;
                                                Roles.Phone = user.phone;
                                                Roles.Password = user.password;
                                                Roles.City = user.city;
                                                Roles.Id_Users = user.id;
                                                if(!user.img.equals("")) {
                                                    Roles.Img = user.img;
                                                }
                                                Intent intent = new Intent(MainActivity.this, glavnaya.class);
                                                startActivity(intent);
                                                finish();
                                                break;
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            } else {
                                Toast.makeText(MainActivity.this, "Неверный логин или пароль, попробуйте заново.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void ClickBackBtn(View view) {
        Intent intent = new Intent(MainActivity.this, avtoriz.class);
        startActivity(intent);
        finish();
    }
}