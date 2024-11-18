package com.example.eateasy.Network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        Intent networkStatusIntent = new Intent("com.example.eateasy.NETWORK_STATUS");

        if (networkInfo != null && networkInfo.isConnected()) {
            // Gửi Broadcast thông báo có kết nối
            networkStatusIntent.putExtra("status", "connected");
        } else {
            // Gửi Broadcast thông báo không có kết nối
            networkStatusIntent.putExtra("status", "disconnected");
        }

        // Gửi Intent đi
        context.sendBroadcast(networkStatusIntent);
    }
}
