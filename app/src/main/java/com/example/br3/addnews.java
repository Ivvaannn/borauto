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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class addnews extends AppCompatActivity {
    private DatabaseReference mBase;
    private TextView titleed, dataed, texted;
    private String News_Key = "News";
    private Uri uploadUri;
    private ImageView img;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnews);
        titleed = findViewById(R.id.titleedit);
        dataed = findViewById(R.id.dataedit);
        texted = findViewById(R.id.textedit);
        img = findViewById(R.id.imgnews);
        mBase = FirebaseDatabase.getInstance().getReference(News_Key);
        mStorageRef = FirebaseStorage.getInstance().getReference("Source");
    }
    public void ClickAddImg(View view) {
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
                img.setImageURI(data.getData());
                uploadImage1();
            }
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
                uploadUri = task.getResult();
            }
        });
    }
    public void SaveData() {
        String id = mBase.push().getKey();
        String title = titleed.getText().toString();
        String date = dataed.getText().toString();
        String text = texted.getText().toString();
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(text) && uploadUri != null) {
            News newsadd = new News(id, title, text, uploadUri.toString(), date);
            mBase.child(id).setValue(newsadd);
            Toast.makeText(addnews.this, "Новость успешно добавлена!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(addnews.this, news_activ.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(addnews.this, "Необходимо заполнить все поля и выбрать фото!", Toast.LENGTH_SHORT).show();
        }
    }
    public void ClickBackBtn(View view) {
        Intent intent = new Intent(addnews.this, news_activ.class);
        startActivity(intent);
        finish();
    }

    public void ClickthisDate(View view) {
        dataed.setText(new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date()));
    }

    public void ClickAddBtn(View view) {
        SaveData();
    }
}
