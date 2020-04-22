package com.example.brijj.baatein.SignIN;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.brijj.baatein.Models.User;
import com.example.brijj.baatein.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText nme,mail,pass;
    Button signin,signup;
    RadioGroup group;
    RadioButton male,female;
    FirebaseAuth firebaseAuth;
    String gender="Male";
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);
        dialog=new ProgressDialog(SignUp.this);
        nme=findViewById(R.id.name);
        mail=findViewById(R.id.mail);
        pass=findViewById(R.id.password);
        signin=findViewById(R.id.signin);
        signup=findViewById(R.id.signup);
        signin.setOnClickListener(this);
        signup.setOnClickListener(this);
        group=findViewById(R.id.radiogroup);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        firebaseAuth=FirebaseAuth.getInstance();
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i)
                {
                    case R.id.male:
                                     gender="Male";
                                     break;
                    case R.id.female:
                                     gender="Female";
                                     break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signin:
                             finish();
                             break;
            case R.id.signup:
                             String name=nme.getText().toString().trim();
                             String email=mail.getText().toString().trim();
                             String password=pass.getText().toString().trim();
                             usersignup(name,email,password);

                             break;
        }
    }

    private void usersignup(final String name, final String email, String password)
    {
        if(checkforvalid(name,email,password))
        {
            dialog.setTitle("Registering Please Wait...");
            dialog.setMessage("processing...");
            dialog.show();
          firebaseAuth.createUserWithEmailAndPassword(email,password)
                      .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>()
                      {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task)
                          {
                              if (task.isSuccessful())
                              {
                                  User user=new User(name,email,gender);
                                  FirebaseDatabase.getInstance().getReference().child("Users")
                                          .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                          .setValue(user)
                                          .addOnCompleteListener(new OnCompleteListener<Void>() {
                                              @Override
                                              public void onComplete(@NonNull Task<Void> task)
                                              {
                                                  if (task.isSuccessful())
                                                  {
                                                      Intent intent=new Intent(SignUp.this,Varification.class);
                                                      startActivity(intent);
                                                      finish();
                                                      dialog.dismiss();
                                                  }
                                                  else
                                                  {
                                                      Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                      dialog.dismiss();
                                                  }

                                              }
                                          });
                              }
                              else
                              {
                                  dialog.dismiss();
                               Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT)
                                     .show();

                              }


                          }
                      });
        }
    }

    private boolean checkforvalid(String name, String email, String password)
    {
        if (TextUtils.isEmpty(name))
        {
            nme.setError("Enter User Name");
            return false;
        }
        if (TextUtils.isEmpty(email))
    {
        mail.setError("Enter Email address");
        return  false;
    }
        if (TextUtils.isEmpty(password))
        {
            pass.setError("Enter Password");
            return false;
        }

        if (password.length()<8)
        {
            pass.setError("Min 8 characters");
            return false;

        }

        return true;
    }
}
