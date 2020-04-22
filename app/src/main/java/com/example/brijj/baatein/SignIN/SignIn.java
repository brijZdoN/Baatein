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
import android.widget.TextView;
import android.widget.Toast;
import com.example.brijj.baatein.main.MainActivity;
import com.example.brijj.baatein.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity  implements View.OnClickListener {
    TextInputEditText mail,pass;
    Button signin,signup;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView forgot;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        mail=findViewById(R.id.mail);
        pass=findViewById(R.id.password);
        signin=findViewById(R.id.signin);
        signup=findViewById(R.id.signup);
        signin.setOnClickListener(this);
        signup.setOnClickListener(this);
        forgot=findViewById(R.id.reset);
        forgot.setOnClickListener(this);
        dialog=new ProgressDialog(SignIn.this);
       /* if(!firebaseUser.isEmailVerified())
        {
            Intent intent = new Intent(SignIn.this, Varification.class);
            startActivity(intent);
            finish();
        }*/
        if (firebaseAuth.getCurrentUser() != null )
        {
            Intent intent = new Intent(SignIn.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signin:
                             String email=mail.getText().toString().trim();
                             String password=pass.getText().toString().trim();
                             signingin(email,password);
                           break;
            case R.id.signup: Intent intent=new Intent(this,SignUp.class);
                              startActivity(intent);
                              break;
            case R.id.reset:
                            mail.setError(null);
                            pass.setError(null);
                             getSupportFragmentManager()
                                     .beginTransaction()
                                     .replace(android.R.id.content,new ForgotPassword())
                                     .addToBackStack(null)
                                     .commit();
        }
    }

    private void signingin(String email, String password)
    {
        boolean b=checkforvalid(email,password);
        if(b)
        {
            dialog.setTitle("Logging In Please Wait...");
            dialog.setMessage("processing...");
            dialog.show();
            firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    if (!firebaseAuth.getCurrentUser().isEmailVerified())
                                    {
                                        Intent intent=new Intent(SignIn.this,Varification.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {

                                        Intent intent=new Intent(SignIn.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    dialog.dismiss();
                                }
                                else
                                    {
                                        dialog.dismiss();
                                        Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    e.printStackTrace();
                }
            });

        }

    }

    private boolean checkforvalid(String email, String password)
    {   if (TextUtils.isEmpty(email))
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
