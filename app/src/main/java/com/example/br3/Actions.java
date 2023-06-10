package com.example.br3;

import android.provider.ContactsContract;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Actions {
    int Image;
    String Nazvanie;
    String Date;

    public Actions(int image, String nazvanie, String date) {
        Image = image;
        Nazvanie = nazvanie;
        Date = date;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getNazvanie() {
        return Nazvanie;
    }

    public void setNazvanie(String nazvanie) {
        Nazvanie = nazvanie;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
