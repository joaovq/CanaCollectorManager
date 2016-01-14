package canacollector.cc.com.example.android.canacollectormanager.Utils;

import android.app.Activity;

/**
 * Created by joaovq on 14/01/16.
 */
public class MyProgressDialog {
    public static android.app.ProgressDialog getProgressDialog(Activity caller,String message){
        android.app.ProgressDialog pDialog = new android.app.ProgressDialog(caller);
        pDialog.setMessage(message);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        return pDialog;
    }
}
