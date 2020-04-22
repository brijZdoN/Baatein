package com.example.brijj.baatein;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.brijj.baatein.SignIN.SignIn;
import com.example.brijj.baatein.main.MainActivity;

public class SplashActivity extends AppCompatActivity {
   ImageView image;
   Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        image=findViewById(R.id.splashimage);
        btn=findViewById(R.id.getstarted);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(SplashActivity.this,SignIn.class);
                startActivity(intent);
                finish();
            }
        });

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent intent=new Intent(SplashActivity.this,SignIn.class);
                startActivity(intent);
                finish();
            }
        },3000);*/
    }
}
