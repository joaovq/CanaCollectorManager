package canacollector.cc.com.example.android.canacollectormanager.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import canacollector.cc.com.example.android.canacollectormanager.Model.Alambique;

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

    public static Alambique getAlambique() {
        Alambique alambique = new Alambique();
        ParseObject alambiqueResult;

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.include("alambique");

        try {
            ParseUser user = query.getFirst();
            alambiqueResult = user.getParseObject("alambique");
            alambique = (Alambique) alambiqueResult;
            Log.d("Alambique em uso", alambique.getName());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return alambique;
    }
}
