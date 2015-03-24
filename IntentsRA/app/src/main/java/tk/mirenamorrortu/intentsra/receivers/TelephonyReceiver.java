package tk.intentsra.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TelephonyReceiver extends BroadcastReceiver {

    private final String RECEIVER = "RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(RECEIVER, "TelephonyReceiver onReceive()");
        Log.d(RECEIVER, "ACTION: " + intent.getAction());
    }
}
