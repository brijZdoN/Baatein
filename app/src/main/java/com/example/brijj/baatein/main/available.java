package com.example.brijj.baatein.main;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.brijj.baatein.Adapter.Availabeladapter;
import com.example.brijj.baatein.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class available extends Fragment
{
    ArrayList<String> arrayList;
    ArrayList<String> matchname;
    RecyclerView recyclerView;
    Availabeladapter adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference con, myreference;
    Random random;
    public available(){ }
    String matchid;
    boolean match=false;
    int check=0;
    String name=FirebaseAuth.getInstance().getCurrentUser().getUid();
    String mname="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v= inflater.inflate(R.layout.fragment_available, container, false);
        recyclerView=v.findViewById(R.id.recycle);
        random=new Random();

        RecyclerView.LayoutManager manager= new LinearLayoutManager(getActivity());
        arrayList=new ArrayList<>();
        matchname=new ArrayList<>();
        adapter=new Availabeladapter( getContext(),arrayList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        firebaseDatabase=FirebaseDatabase.getInstance();
        myreference = firebaseDatabase.getReference().child("activeusers");
        con=myreference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
       try {
           load();
       }catch (Exception e){e.printStackTrace();}


        return v;
    }
    private void load()
    {
      FirebaseDatabase.getInstance().getReference().child("activeusers").addChildEventListener(new ChildEventListener()
      {
          @Override
          public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
             // String s1 = dataSnapshot.getValue(String.class);
              String s1=dataSnapshot.getKey();
              String s2=dataSnapshot.getValue(String.class);
              Log.d("availableusers", s1);
              if (arrayList.contains(s1))
              {
              }
              else
                  {
                  arrayList.add(s1);
                  matchname.add(s2);
                 // adapter.notifyDataSetChanged();
                  if (arrayList.size()>1) {
                      if (check == 0) {
                          check = 1;
                          match();

                      }

                  } else { }
              }
          }

          @Override
          public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
          {

          }

          @Override
          public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
          {
              arrayList.remove(dataSnapshot.getKey());
             adapter.notifyDataSetChanged();

          }

          @Override
          public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) { }

      });
    }

    private void matchmore()
    {
        String id1="user1",id2="user2",matchid;
        int size=arrayList.size();
        int rand=random.nextInt(size);



            }

    private void match()
    {
        switch (arrayList.size())
        {
            case 1:
                   break;
            case 2:
                if (arrayList.get(0)==name)
                         {
                             matchid=arrayList.get(1);
                             mname=matchname.get(1);

                                 done(matchid);


                         }
                       if (arrayList.get(1)==name)
                         {
                          matchid=arrayList.get(0);
                          mname=matchname.get(0);
                          done(matchid);

                         }
                         break;
        }

    }

    private void done(String matchid)
    {
        String id=name+matchid;
        String s= remodify(id);
        Intent intent=new Intent(getContext(),chat.class);
        intent.putExtra("id",s);
        intent.putExtra( "uid",name );
        intent.putExtra("matchname",mname);
        getContext().startActivity(intent);
    }

    private String remodify(String idt)
    {
        ArrayList<Integer> arrayList=new ArrayList<>();
        char[] a=idt.toCharArray();
        String rtrnid="";
        for(char c:a)
        {
            if(Character.isDigit(c))
            {
                //c-48 because during conversion we get the of '1' is 49
                arrayList.add((int)c-48);
            }
        }
        Collections.sort(arrayList);
        for(int list:arrayList)
        {
            rtrnid=rtrnid +String.valueOf(list);

        }
        idt=idt.replaceAll("[^A-Za-z]","");
        String input = idt;
        char[] arr = input.toCharArray();
        Arrays.sort(arr);
        String sorted = new String(arr);
        rtrnid+=sorted;
        return rtrnid;


    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onPause()
    {
        super.onPause();
        con.removeValue();

    }

}
