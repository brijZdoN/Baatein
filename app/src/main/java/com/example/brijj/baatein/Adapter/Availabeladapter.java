package com.example.brijj.baatein.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brijj.baatein.R;
import com.example.brijj.baatein.main.chat;

import java.util.ArrayList;

public class Availabeladapter extends RecyclerView.Adapter<Availabeladapter.myviewholder> {
    ArrayList<String> arrayList;
    Context context;

    public Availabeladapter(Context context, ArrayList<String> arrayList)
    {  this.arrayList=arrayList;
       this.context=context;
    }

    @Override
    public Availabeladapter.myviewholder onCreateViewHolder(ViewGroup parent, int viewType)
     {   View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.availablerow,parent,false);
        return new myviewholder(v);
    }


    @Override
    public void onBindViewHolder(Availabeladapter.myviewholder holder, int position)
    {
            holder.textView.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }
    public class myviewholder extends RecyclerView.ViewHolder
    {   TextView textView;
        public myviewholder(View itemView)
        {
            super(itemView);
            textView=itemView.findViewById(R.id.name);

        }
    }
}
