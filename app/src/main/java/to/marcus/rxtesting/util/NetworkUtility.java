package to.marcus.rxtesting.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by marcus on 25/10/15
 */
public class NetworkUtility {

    public static boolean isWiFiEnabled(Context context){
        boolean connected = false;
        ConnectivityManager connMgr =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            if(connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){
                connected = true;
            }
        }else{
            connected = false;
        }
        return connected;
    }

}
