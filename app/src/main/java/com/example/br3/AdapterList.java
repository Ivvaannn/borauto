package com.example.br3;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterList extends ArrayAdapter<Actions>
{
    private Context mContext;
    private int mResource;

    public AdapterList(@NonNull Context context, int resource, @NonNull ArrayList<Actions> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource,parent,false);
        ImageView imageView = convertView.findViewById(R.id.image);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtSubtitle = convertView.findViewById(R.id.txtSubtitle);
        imageView.setImageResource(getItem(position).getImage());
        txtName.setText(getItem(position).getNazvanie());
        txtSubtitle.setText(getItem(position).getDate());
        return convertView;
    }
}
