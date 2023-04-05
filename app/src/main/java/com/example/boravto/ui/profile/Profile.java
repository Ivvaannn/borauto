package com.example.boravto.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.boravto.R;
import com.example.boravto.UpdateProfile;
import com.example.boravto.ui.classes.InfoUser;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class Profile extends Fragment {

    private ProfileViewModel mViewModel;
    public TextView phonetw, emailtw, addresstw, nametw;
    public ImageView avatar;
    public Button button1;

    public static Profile newInstance() {
        return new Profile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile,
                container, false);

        button1=(Button)rootView.findViewById(R.id.button4);

        button1.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), UpdateProfile.class);
                startActivity(intent);
            }});



        return inflater.inflate(R.layout.fragment_profile, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        phonetw = view.findViewById(R.id.PhoneTextView);
        emailtw = view.findViewById(R.id.EmailTextView);
        addresstw = view.findViewById(R.id.SityLandTextView);
        nametw = view.findViewById(R.id.NameTextView);
        avatar = view.findViewById(R.id.AvatarImageView);
        InfoUser user = new InfoUser();
        phonetw.setText(user.Phone);
        emailtw.setText(user.Email);
        addresstw.setText(user.Address);
        nametw.setText(user.Name);
       // Picasso.get().load(user.A.into(img1);



    }
}