package com.example.simplist.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkReceiver extends BroadcastReceiver  {
    private static final String TAG = "NetworkReceiver";
    private NetworkReceiverInterface networkInterface;

    public NetworkReceiver(NetworkReceiverInterface networkInterface) {
        this.networkInterface = networkInterface;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(TAG, String.valueOf(isConnected(context)));
        if (isConnected(context)) {
            networkInterface.networkOn();
        } else {
            networkInterface.networkOff();
        }
    }

    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnected();
    }
}
