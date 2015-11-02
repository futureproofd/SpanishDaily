package to.marcus.rxtesting.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by marcus on 10/30/2015
 * Start the Notification Service upon boot
 * Receiver is declared to start on intent BOOT_COMPLETED
 */
public class StartNotificationService extends BroadcastReceiver {
    private static final String TAG = StartNotificationService.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "received");
        ServiceController.startService(context.getApplicationContext());
    }
}
