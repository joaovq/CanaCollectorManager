package canacollector.cc.com.example.android.canacollectormanager.Utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import canacollector.cc.com.example.android.canacollectormanager.Model.Alambique;
import canacollector.cc.com.example.android.canacollectormanager.View.Model.Cachaca;
import canacollector.cc.com.example.android.canacollectormanager.View.Model.ControleFermento;
import canacollector.cc.com.example.android.canacollectormanager.View.Model.Dorna;
import canacollector.cc.com.example.android.canacollectormanager.View.Model.Mosto;
import canacollector.cc.com.example.android.canacollectormanager.View.Model.Talhao;
import canacollector.cc.com.example.android.canacollectormanager.View.Model.Tonel;

public class ParseUtils {
    private static String TAG = ParseUtils.class.getSimpleName();

    public static void verifyParseConfiguration(Context context) {
        if (TextUtils.isEmpty(Config.PARSE_APPLICATION_ID) || TextUtils.isEmpty(Config.PARSE_CLIENT_KEY)) {
            Toast.makeText(context, "Please configure your Parse Application ID and Client Key in AppConfig.java", Toast.LENGTH_LONG).show();
            ((Activity) context).finish();
        }
    }

    public static void registerParse(Context context) {
        // Enable Local Datastore.
        // Parse.enableLocalDatastore(this);

        //Register objects in Parse as well
        ParseObject.registerSubclass(Alambique.class);
        ParseObject.registerSubclass(Mosto.class);
        ParseObject.registerSubclass(Talhao.class);
        ParseObject.registerSubclass(Tonel.class);
        ParseObject.registerSubclass(Cachaca.class);
        ParseObject.registerSubclass(Dorna.class);
        ParseObject.registerSubclass(ControleFermento.class);

        // initializing parse library
        Parse.initialize(context, Config.PARSE_APPLICATION_ID, Config.PARSE_CLIENT_KEY);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground(Config.PARSE_CHANNEL, new SaveCallback() {
            @Override
            public void done(ParseException e) {

            }
        });
    }
}
