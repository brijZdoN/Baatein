package com.example.brijj.baatein.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.brijj.baatein.Models.HomeModel;
import com.example.brijj.baatein.Models.chatmodel;
import com.example.brijj.baatein.R;
import com.example.brijj.baatein.main.MainActivity;
import com.example.brijj.baatein.main.chat;

import java.util.ArrayList;
import java.util.HashMap;

public class connect_home_adapter extends RecyclerView.Adapter<connect_home_adapter.Myholder>
{
   ArrayList<HomeModel> arrayList;
   Context context;
   String uid;
    public connect_home_adapter(Context context, ArrayList<HomeModel> arrayList, String uid)
    {
        this.context=context;
        this.arrayList=arrayList;
        this.uid=uid;


    }

    @Override
    public connect_home_adapter.Myholder onCreateViewHolder(ViewGroup parent, int viewType)
    {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_adapter,parent,false);
        return new Myholder(v);
    }

    @Override
    public void onBindViewHolder(Myholder holder, final int position)
    { final HomeModel model=arrayList.get(position);

     holder.text.setText(model.getName());
      holder.text.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent=new Intent(context,chat.class);
              intent.putExtra("id",model.getId());
              intent.putExtra("matchname",model.getName());
              intent.putExtra( "uid",uid );
              context.startActivity(intent);
          }


      });

    }


    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public class Myholder extends RecyclerView.ViewHolder
    {    TextView text;
        public Myholder(View itemView)
        {
            super(itemView);
            text=itemView.findViewById(R.id.name);

        }
    }
}
