package com.example.boravto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.boravto.ui.classes.InfoUser;
import com.example.boravto.ui.classes.Users;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class UpdateProfile extends AppCompatActivity {
    public ImageView avatImg;
    private Uri uploadUri;
    private DatabaseReference mBase;
    private String User = "Users";
    private StorageReference mStorageRef;
    public TextView name, email, land, sity, phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        avatImg = findViewById(R.id.AvatarImage);
        mStorageRef = FirebaseStorage.getInstance().getReference("Source");
        name = findViewById(R.id.NameEditView);
        email = findViewById(R.id.EmailEditView);
        password = findViewById(R.id.PasswordEditView);
        phone = findViewById(R.id.PhoneEditView);
        sity = findViewById(R.id.CityEditUser);
        land = findViewById(R.id.LandEditView);
    }

    public void LoadImageClick(View view) {
        Intent intentChooser = new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser, 1);
    }

    private void uploadImage1() {
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
                if (uploadUri == null) {
                    uploadUri = task.getResult();
                    Toast.makeText(UpdateProfile.this, "Картинка добавлена!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void saveData() {

        String nametxt = name.getText().toString();
        String passwordtxt = password.getText().toString();
        String sitytxt = sity.getText().toString();
        String landtxt = land.getText().toString();
        String phonetxt = phone.getText().toString();
        String emailtxt = password.getText().toString();
        mBase = FirebaseDatabase.getInstance().getReference(User);
        String id = mBase.getKey();
        String userid = mBase.push().getKey();

        Users users = new Users(emailtxt, id, nametxt, passwordtxt, phonetxt, InfoUser.role, sitytxt, landtxt, uploadUri.toString());
        if (!TextUtils.isEmpty(emailtxt)&&!TextUtils.isEmpty(id)&&!TextUtils.isEmpty(nametxt)&&!TextUtils.isEmpty(passwordtxt)&&!TextUtils.isEmpty(phonetxt)&&!TextUtils.isEmpty(sitytxt)&&!TextUtils.isEmpty(landtxt)){
            if (userid != null)mBase.child(userid).setValue(users);

        }else{
            Toast.makeText(UpdateProfile.this,"Некоторые поля пустые!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && data.getData() != null) ;
        {
            if (resultCode == RESULT_OK) {
                Log.d("Mylog", "Image URI : " + data.getData());
                avatImg.setImageURI(data.getData());
                uploadImage1();
            }
        }
    }

    public void UpdateClick(View view) {
        saveData();
    }

}