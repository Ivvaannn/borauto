package com.example.br3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class UpdateProfile extends AppCompatActivity {
    private DatabaseReference mBase;
    private String key = "Users", image;
    private Uri uploadUri;
    private ImageView avatImg;
    private StorageReference mStorageRef;
    public TextView name, city, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        mBase = FirebaseDatabase.getInstance().getReference(key);
        name = findViewById(R.id.NameEditView);
        phone = findViewById(R.id.PhoneEditView);
        city = findViewById(R.id.CityEditUser);
        avatImg = findViewById(R.id.avatarImg);
        name.setText(Roles.Name);
        phone.setText(Roles.Phone);
        city.setText(Roles.City);
        mStorageRef = FirebaseStorage.getInstance().getReference("Source");
        if(Roles.Img != null) {
            Picasso.get().load(Roles.Img).into(avatImg);
        }
    }

    public void UpdateImageClick (View view) {
        Intent intentChooser = new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data !=null && data.getData () != null);
        {
            if (resultCode == RESULT_OK)
            {
                Log.d("Mylog","Image URI : "+ data.getData());
                avatImg.setImageURI(data.getData());
                uploadImage1();
            }
        }
    }

    private void uploadImage1() {
        Toast.makeText(UpdateProfile.this,"Загрузка фото...",Toast.LENGTH_SHORT).show();
        Bitmap bitmap = ((BitmapDrawable) avatImg.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        StorageReference mRef = mStorageRef.child(System.currentTimeMillis() + "MyImg");
        UploadTask up = mRef.putBytes(byteArray);
        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return mRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                uploadUri = task.getResult();
                Toast.makeText(UpdateProfile.this,"Фото загружено!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SaveData(){
        String id = Roles.Id_Users;
        String nametxt = name.getText().toString();
        String emailtxt =  Roles.Email;
        String passwordtxt = Roles.Password;
        String phonetxt = phone.getText().toString();
        String citytxt = city.getText().toString();
        if(uploadUri == null){
            image = Roles.Img;
        }
        else{
            image = uploadUri.toString();
            Roles.Img = uploadUri.toString();
        }

        if (!TextUtils.isEmpty(nametxt)&&!TextUtils.isEmpty(emailtxt)&&!TextUtils.isEmpty(passwordtxt)&&!TextUtils.isEmpty(image)&& !TextUtils.isEmpty(Roles.role)){
            Users user = new Users(id,nametxt,emailtxt,passwordtxt,phonetxt,citytxt,Roles.role,image);
            if (id != null) {
                mBase.child(id).setValue(user);
                Roles.Name = nametxt;
                Roles.Phone = phonetxt;
                Roles.City = citytxt;
                Toast.makeText(UpdateProfile.this, "Данные успешно изменены!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Profile.class);
                startActivity(intent);
            }
        }else{
            Toast.makeText(UpdateProfile.this,"Необходимо заполнить все поля!",Toast.LENGTH_SHORT).show();
        }
    }

    public void UpdateClick(View view) {
        SaveData();
    }

    public void ClickBackBtn(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        finish();
    }
}