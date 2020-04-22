package com.example.brijj.baatein;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityReceiver extends BroadcastReceiver
{
    public static ConnectivityReceiverListener conlistener;
   Context context;
    public ConnectivityReceiver()
    {
        super();
    }
    @Override
    public void onReceive(Context context, Intent intent)
    {     this.context=context;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (conlistener != null) {
            conlistener.onNetworkConnectionChanged(isConnected);
        }
    }
    public static boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) MyApplication
                                   .getInstance()
                                   .getApplicationContext()

                                   .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
    public interface ConnectivityReceiverListener
    {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
