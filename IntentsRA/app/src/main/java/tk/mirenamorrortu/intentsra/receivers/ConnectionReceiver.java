package tk.mirenamorrortu.intentsra.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

public class ConnectionReceiver extends BroadcastReceiver {

    private final String RECEIVER = "RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(RECEIVER, "ConnectionReceiver onReceive()");
        Log.d(RECEIVER, "ACTION: " + intent.getAction());

        if (intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)){
            //
        }else if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){

        }
    }
}
