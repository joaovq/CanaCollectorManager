package canacollector.cc.com.example.android.canacollectormanager.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import canacollector.cc.com.example.android.canacollectormanager.Model.Alambique;

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
        AppQuery.getProducaoFromServer();
        AppQuery.getAreaTotalFromServer();
    }
}

