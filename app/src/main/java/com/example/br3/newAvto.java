package com.example.br3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class newAvto extends AppCompatActivity {
    private DatabaseReference mBase;
    private TextView nameed, descriptioned, dateed, equipmented, bodyed, priceed;
    private Uri uploadUri, uploadUri2, uploadUri3;
    private StorageReference mStorageRef;
    private ImageView img;
    private RadioButton rb1, rb2,rb3;
    private String AVTO_KEY = "Avto_New";
    private Button btn;
    String Carid;
    private  String category = "Новые авто";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_avto);
        rb1 = findViewById(R.id.radioButton);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        nameed = findViewById(R.id.NameEdView);
        descriptioned = findViewById(R.id.DescriptionEdView);
        dateed = findViewById(R.id.DataEdView);
        equipmented = findViewById(R.id.KomplektatsiyaEdView);
        bodyed = findViewById(R.id.BodyEdView);
        priceed = findViewById(R.id.PriceEdView);
        img = findViewById(R.id.imgViewAdd);
        mStorageRef = FirebaseStorage.getInstance().getReference("Source");
        getIntentMain();
    }
    private void getIntentMain(){
        Intent intent = getIntent();
        if(intent != null){
            Carid = intent.getStringExtra("Car_id");
            nameed.setText(intent.getStringExtra("Car_name"));
            descriptioned.setText(intent.getStringExtra("Car_description"));
            dateed.setText(intent.getStringExtra("Car_date"));
            equipmented.setText(intent.getStringExtra("Car_equipment"));
            bodyed.setText(intent.getStringExtra("Car_body"));
            priceed.setText(intent.getStringExtra("Car_price"));
        }
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
                    Toast.makeText(newAvto.this,"Добавлена первая картинка!!",Toast.LENGTH_SHORT).show();
                }
                else if(uploadUri2 == null) {
                    uploadUri2 = task.getResult();
                    Toast.makeText(newAvto.this,"Добавлена вторая картинка!!",Toast.LENGTH_SHORT).show();
                }
                else if(uploadUri3 == null){
                    uploadUri3 = task.getResult();
                    Toast.makeText(newAvto.this,"Добавлена третья картинка!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveData() {
        if(uploadUri3 == null && uploadUri2 == null && uploadUri == null){
            Toast.makeText(newAvto.this,"Добавте три картинки!",Toast.LENGTH_SHORT).show();
        }
        else {
            String name = nameed.getText().toString();
            String description = descriptioned.getText().toString();
            String date = dateed.getText().toString();
            String equipment = equipmented.getText().toString();
            String body = bodyed.getText().toString();
            String price = priceed.getText().toString();
            if (rb3.isChecked()) {
                AVTO_KEY = "Avto_Bitoe";
                category = "Битые авто";
            } else if (rb2.isChecked()) {
                AVTO_KEY = "Avto_BU";
                category = "Б/У авто";
            }
            mBase = FirebaseDatabase.getInstance().getReference(AVTO_KEY);
            String id = mBase.getKey();
            String carid = mBase.push().getKey();

            Car avtoadd = new Car(id, category, carid, name, uploadUri.toString(), uploadUri2.toString(), uploadUri3.toString(), description, date, equipment, body, price);
            if (!TextUtils.isEmpty(category) && !TextUtils.isEmpty(id) && !TextUtils.isEmpty(carid) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(carid) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(equipment) && !TextUtils.isEmpty(body) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(uploadUri.toString()) && !TextUtils.isEmpty(uploadUri2.toString()) && !TextUtils.isEmpty(uploadUri3.toString())) {
                mBase.push().setValue(avtoadd);
                Toast.makeText(newAvto.this, "Добавлено!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(newAvto.this, "Возможно некоторые поля пустые!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void ClickAddImg1(View view) {
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

    public void ClickAddBtn(View view) {
        saveData();
    }

    public void ClickAddImgBtn(View view) {
        uploadImage1();
    }

    public void clickBackBtn(View view) {
        Intent intent = new Intent(this, glavnaya.class);
        startActivity(intent);
        finish();
    }
}