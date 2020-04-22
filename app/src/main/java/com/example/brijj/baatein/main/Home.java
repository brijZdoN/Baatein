package com.example.brijj.baatein.main;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.brijj.baatein.Adapter.connect_home_adapter;
import com.example.brijj.baatein.Models.HomeModel;
import com.example.brijj.baatein.Models.User;
import com.example.brijj.baatein.Models.chatmodel;
import com.example.brijj.baatein.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Home extends Fragment implements View.OnClickListener{
    Button search;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference con,myreference,myreference1;
    String uname="";
    ArrayList<HomeModel> arrayList;
    connect_home_adapter adapter;
    RecyclerView recyclerView;
    public Home() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View v=inflater.inflate(R.layout.fragment_home, container, false);
             search=v.findViewById(R.id.match);
             search.setOnClickListener(this);
             firebaseDatabase=FirebaseDatabase.getInstance();
             myreference = firebaseDatabase.getReference("activeusers");
             arrayList=new ArrayList<>();
             final String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();

             myreference1=FirebaseDatabase
                                     .getInstance()
                                     .getReference()
                                     .child("MyConnection")
                                     .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

              load();
          FirebaseDatabase
                  .getInstance()
                  .getReference()
                  .child("Users")
                  .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                  .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                User user=dataSnapshot.getValue(User.class);
                   uname=user.getUsername();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        adapter=new connect_home_adapter( getContext(),arrayList,uid);
        recyclerView=v.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        return v;
    }

    private void load()
    {
        myreference1.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
        {
            String name=dataSnapshot.getValue(String.class);
            String nameid=dataSnapshot.getKey();
            //HashMap<String,String> map=new HashMap<>();
            //map.put(nameid,name);
            HomeModel model=new HomeModel(nameid,name);
            arrayList.add(model);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
        {
            arrayList.remove(dataSnapshot.getKey());
            adapter.notifyDataSetChanged();

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId()==R.id.match)
        {
            con=myreference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            con.setValue(uname);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.m,new available())
                    .addToBackStack(null)
                    .commit();
        }

    }



    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();


    }

}
