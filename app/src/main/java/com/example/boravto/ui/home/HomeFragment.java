package com.example.boravto.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.boravto.R;
import com.example.boravto.databinding.FragmentHomeBinding;
import com.example.boravto.ui.classes.Auto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ListView listView;
    private List<String> listdata;
    private List<Auto> listTemp;
    private DatabaseReference mBase;
    private String CarKeyBit = "Avto_New";
    private ArrayAdapter adapter;
    public Auto auto;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        this.listView = view.findViewById(R.id.listview);
        listdata = new ArrayList<>();
        listTemp = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), R.layout.listviewlayout, listdata);
        mBase = FirebaseDatabase.getInstance().getReference(CarKeyBit);
        listView.setAdapter(adapter);
        getDataFromDB();
        setOnClickItem();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getDataFromDB() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listdata.size()>0)listdata.clear();
                if (listTemp.size()>0)listTemp.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    auto = ds.getValue(auto.getClass());
                    assert auto != null;
                    listdata.add(auto.name);
                    listTemp.add(auto);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
        mBase.addValueEventListener(valueEventListener);
    }

    private void setOnClickItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "ke", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

