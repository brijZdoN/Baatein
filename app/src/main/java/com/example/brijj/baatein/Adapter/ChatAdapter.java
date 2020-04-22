package com.example.brijj.baatein.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brijj.baatein.Models.chatmodel;
import com.example.brijj.baatein.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int MAIN_USER = 0;
    public static final int ANOTHER_USER = 1;
    String uname;
    Context context;
    private ArrayList<chatmodel> arrayList;

    public ChatAdapter(Context context, ArrayList<chatmodel> arrayList, String uname) {
        this.context = context;
        this.arrayList = arrayList;
        this.uname = uname;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case MAIN_USER:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user1, parent, false);
                return new ViewHolder2(v);
            case ANOTHER_USER:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatsinglerow, parent, false);
                return new ViewHolder1(v);
        }

        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        chatmodel model = arrayList.get(position);

        switch (getItemViewType(position)) {
            case MAIN_USER:
                ViewHolder2 viewHolder2 = (ViewHolder2) holder;
                viewHolder2.username.setText(model.getUsername());
                viewHolder2.chat.setText(model.getText());
                break;
            case ANOTHER_USER:
                ViewHolder1 viewHolder1 = (ViewHolder1) holder;
                viewHolder1.username.setText(model.getUsername());
                viewHolder1.chat.setText(model.getText());
                break;
        }

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        private TextView username, chat;

        public ViewHolder1(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.user2);
            chat = itemView.findViewById(R.id.userchat2);
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        private TextView username, chat;

        public ViewHolder2(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.user1);
            chat = itemView.findViewById(R.id.userchat1);
        }
    }

    @Override
    public int getItemViewType(int position)
    {

            if (arrayList.get(position).getuid().equals(uname))
            {
                return MAIN_USER;
            } else
                return ANOTHER_USER;
    }




}