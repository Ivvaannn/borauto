package com.example.boravto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boravto.ui.classes.InfoUser;
import com.example.boravto.ui.classes.Users;
import com.example.boravto.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class vhod extends AppCompatActivity {
    private EditText email_login;
    private EditText password_login;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vhod);
        email_login = findViewById(R.id.editTextTextEmailAddress);
        password_login = findViewById(R.id.editTextTextPassword);
        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();
    }
    public void ClickBtnVhod(View view) {
        if (email_login.getText().toString().isEmpty() || password_login.getText().toString().isEmpty()){
            Toast.makeText(vhod.this, "Поля не могут быть пусты",Toast.LENGTH_SHORT).show();
        }else{
            mAuth.signInWithEmailAndPassword(email_login.getText().toString(),password_login.getText().toString()).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(vhod.this, MainActivity.class);
                                startActivity(intent);

                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference ref = db.getReference("Users");

                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            Users user = ds.getValue(Users.class);
                                            if(user.email.equals(email_login.getText().toString())) {
                                                InfoUser.role = user.role;
                                                InfoUser.Address = user.land +", "+user.city;
                                                InfoUser.Id_Users = mAuth.getTenantId();
                                                InfoUser.Email = user.email;
                                                InfoUser.Phone = user.phone;
                                                InfoUser.Name = user.name;
                                                break;
                                            }
                                        }



                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });



                            } else {
                                Toast.makeText(vhod.this, "Неверные данные", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

        }

    }
}