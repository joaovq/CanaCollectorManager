package canacollector.cc.com.example.android.canacollectormanager.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Breno on 1/12/2016.
 */
public class AppUtils {
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
