package com.example.br3;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class aplication extends AppCompatActivity {
    private DatabaseReference mBase;
    private TextView nametxt, descriptiontxt, datetxt, equipmentedtxt, pricetxt;
    private Uri uploadUri, uploadUri2, uploadUri3;
    private StorageReference mStorageRef;
    private ImageView img;
    private String key = "Applications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplication);
        nametxt = findViewById(R.id.Nameed);
        descriptiontxt = findViewById(R.id.Descripted);
        datetxt = findViewById(R.id.Dateed);
        equipmentedtxt = findViewById(R.id.Komplekted);
        pricetxt = findViewById(R.id.Priceed);
        img = findViewById(R.id.imged);
        mStorageRef = FirebaseStorage.getInstance().getReference("Source");
    }
    private void uploadImage1() {
        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
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
                if(uploadUri == null){
                    uploadUri = task.getResult();
                    Toast.makeText(aplication.this,"Добавлена первая картинка!!",Toast.LENGTH_SHORT).show();
                }
                else if(uploadUri2 == null) {
                    uploadUri2 = task.getResult();
                    Toast.makeText(aplication.this,"Добавлена вторая картинка!!",Toast.LENGTH_SHORT).show();
                }
                else if(uploadUri3 == null){
                    uploadUri3 = task.getResult();
                    Toast.makeText(aplication.this,"Добавлена третья картинка!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveData() {
            String name = nametxt.getText().toString();
            String description = descriptiontxt.getText().toString();
            String date = datetxt.getText().toString();
            String equipment = equipmentedtxt.getText().toString();
            String price = pricetxt.getText().toString();
            mBase = FirebaseDatabase.getInstance().getReference(key);
            String Appkey = mBase.push().getKey();
            String id_user = Roles.Id_Users;
            if (!TextUtils.isEmpty(Appkey) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(equipment) && !TextUtils.isEmpty(price) &&  uploadUri != null && uploadUri2 != null && uploadUri3 != null) {
                Applications app = new Applications(Appkey, name, date, description, equipment, price, id_user, uploadUri.toString(), uploadUri2.toString(), uploadUri3.toString(), "новая", " ");
                mBase.child(Appkey).setValue(app);
                Toast.makeText(aplication.this, "Заявка успешно добавлена!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, aplicationview.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(aplication.this, "Необходимо заполнить все поля и добавить 3 фото автомобиля!", Toast.LENGTH_SHORT).show();
            }
        }
    public void ClickNewImg(View view) {
        Intent intentChooser = new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1&&data !=null && data.getData () != null);
        {
            if (resultCode == RESULT_OK)
            {
                Log.d("Mylog","Image URI : "+ data.getData());
                img.setImageURI(data.getData());

            }
        }
    }

    public void AddClick(View view) {
        saveData();
    }

    public void ClickNewImgUri(View view) {
        uploadImage1();
    }

    public void ClickBack(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        finish();
    }
}