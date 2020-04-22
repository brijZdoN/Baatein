package com.example.brijj.baatein.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brijj.baatein.About_Fragment;
import com.example.brijj.baatein.ConnectivityReceiver;
import com.example.brijj.baatein.Models.User;
import com.example.brijj.baatein.MyApplication;
import com.example.brijj.baatein.R;
import com.example.brijj.baatein.RenameUsename;
import com.example.brijj.baatein.SignIN.SignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ConnectivityReceiver.ConnectivityReceiverListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    TextView headerusername,headeremail;
    String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_call_black_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportFragmentManager().beginTransaction().add(R.id.m,new Home()).commit();

        //checkConnection();
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
                        String email=user.getEmailid();
                        View header=navigationView.getHeaderView(0);
                        headerusername=header.findViewById(R.id.headeruname);
                        headerusername.setText(uname);
                        headeremail=header.findViewById(R.id.headeremail);
                        headeremail.setText(email);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (toggle.onOptionsItemSelected(item))
        {
            return true;
        }
       if(item.getItemId()==R.id.signout)
       {
           FirebaseAuth.getInstance().signOut();
           Intent intent=new Intent(MainActivity.this,SignIn.class);
           startActivity(intent);
          //finish();
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.home :
                //Toast.makeText(getBaseContext(),"home clicked ",Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().add(R.id.m,new Home()).commit();
                break;
            case R.id.setting: //this is editusername section
                      getSupportFragmentManager().beginTransaction()
                                                 .add(R.id.n,new RenameUsename())
                                                 .addToBackStack(null)
                                                 .commit();

                break;
            case R.id.share: Intent intent=new Intent(Intent.ACTION_SEND);
                                    intent.setType("text/plain");
                             String sharebody="share this app to your friens";
                                    intent.putExtra(Intent.EXTRA_SUBJECT,"Baatein");
                                    intent.putExtra(Intent.EXTRA_TEXT,sharebody);
                                    startActivity(Intent.createChooser(intent,"Share via"));
                                    break;
            case R.id.about:
                getSupportFragmentManager().beginTransaction().add(R.id.m,new About_Fragment()).addToBackStack(null).commit();

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
       // MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        if (isConnected) {
            setContentView(R.layout.activity_main);

        } else {
            setContentView(R.layout.connection);
        }
    }

}
