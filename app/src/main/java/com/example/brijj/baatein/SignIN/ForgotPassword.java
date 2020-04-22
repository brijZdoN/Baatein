package com.example.brijj.baatein.SignIN;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brijj.baatein.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends Fragment {

    TextView textView;
    EditText editText;
    Button button;
    String email;
    public ForgotPassword() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_forgot_password, container, false);
       textView=v.findViewById(R.id.text);
       editText=v.findViewById(R.id.forogtemail);
       button=v.findViewById(R.id.sendmail);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               email = editText.getText().toString();
               if (email.isEmpty()) {
                   editText.setError("Enter email Address");
               } else {
                   FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()) {
                                       Toast.makeText(getContext(), "Email Sent to " + email, Toast.LENGTH_SHORT).show();
                                       getActivity().getSupportFragmentManager().beginTransaction().remove(ForgotPassword.this).commit();
                                   } else {
                                       Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                   }

                               }
                           }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           e.printStackTrace();
                       }
                   });
               }
           }
       });


       return v;
       
    }


}
