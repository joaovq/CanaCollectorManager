package canacollector.cc.com.example.android.canacollectormanager.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Breno on 1/12/2016.
 */
public class AppUtils {

    //Verifica se o usuário está conectado à internet
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static void recoverDataInBackgroung() {
        AppQuery.getEstoqueTotalFromServer();
        AppQuery.getProducaoTotalFromServer();
        AppQuery.getProducaoTotalFromServer();
        AppQuery.getTalhaoFromServer();
        AppQuery.getMostoTotalFromServer();
    }
}

