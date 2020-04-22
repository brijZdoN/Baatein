package com.example.brijj.baatein.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.brijj.baatein.Adapter.ChatAdapter;
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

import java.util.Arrays;
import java.util.Collections;


public class chat extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<chatmodel> arrayList;
    private ChatAdapter adapter;
    private DatabaseReference firebaseDatabase;
    private EditText text;
    private Button submit;
    private String username = null,uname="";
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomchat);
        recyclerView = findViewById(R.id.chatrecycle);
        arrayList = new ArrayList<>();
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        final String matchname=intent.getStringExtra("matchname");
        uname=intent.getStringExtra( "uid" );
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle(matchname);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User us = dataSnapshot.getValue(User.class);
                        username = us.getUsername();
                      FirebaseDatabase.getInstance()
                                        .getReference()
                                        .child("MyConnection")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child(id).setValue(matchname);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Unable to fatch the data", Toast.LENGTH_SHORT).show();
                    }
                });

       if (id != null)
       {
            firebaseDatabase =FirebaseDatabase.getInstance().getReference().child("MyConnection").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(id);

            firebaseDatabase.setValue(matchname);

            firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("chatroom").child(id);
        }
       else
            {
           // firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("chatroom");
        }

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        adapter = new ChatAdapter(getApplicationContext(), arrayList,uname);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(adapter);

        text = findViewById(R.id.text);

        firebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                chatmodel model = dataSnapshot.getValue(chatmodel.class);
                arrayList.add(model);
                adapter.notifyDataSetChanged();
                //adapter.notifyItemRangeInserted(0,arrayList.size()-1);


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
             public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //recyclerView.scrollToPosition(arrayList.size()-1);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = text.getText().toString().trim();
                if(t!=null)
                {
                    chatmodel model = new chatmodel(username, t,uname);
                    firebaseDatabase.push().setValue(model);
                    text.setText("");
                    hidesoftkey(chat.this);
                }
                else
                    {
                        Toast.makeText(chat.this, "Empty message can't send", Toast.LENGTH_SHORT).show();
                    }
            }
        });

    }

    /***
     * This method is used to hide the virtual keyboard after sending the message .
     *
     */
    private void hidesoftkey(Context context)
    {

        InputMethodManager inputMethodManager =(InputMethodManager) context.getSystemService
                (Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

}
