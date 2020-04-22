package com.example.brijj.baatein;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.brijj.baatein.Models.User;
import com.example.brijj.baatein.main.Home;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class RenameUsename extends Fragment implements View.OnClickListener {

  EditText usename;
  Button btn;
  String email,gender;
    public RenameUsename() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_rename_usename, container, false);
           usename=v.findViewById(R.id.urname);
           btn=v.findViewById(R.id.setusername);
           btn.setOnClickListener(this);
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
                        email=user.getEmailid();
                        gender=user.getGender();

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });

        return v;
    }
    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.setusername)
        {
            try {
                String a = usename.getText().toString().trim();
                if (!a.isEmpty()) {
                    User model = new User(a, email, gender);
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child("Users")
                            .child(FirebaseAuth.getInstance()
                                    .getCurrentUser().getUid())
                            .setValue(model);
                    Toast.makeText(getContext(), "Username changed", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction().remove(RenameUsename.this).commit();
                } else {
                    Toast.makeText(getContext(), "Enter username", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e){e.printStackTrace();}
        }
    }
}
