package canacollector.cc.com.example.android.canacollectormanager.Utils;

import android.util.Log;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import canacollector.cc.com.example.android.canacollectormanager.View.Model.Tonel;

/**
 * Created by Breno on 1/13/2016.
 */
public class AppQuery {
    final static String ESTOQUE_TOTAL = "estoqueTotal";


// Query for the latest objects from Parse.


    private static void setEstoqueTotalFromServer() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tonel");
        query.whereEqualTo("alambique", AppUtils.getAlambique());
        query.whereGreaterThan("estoque", 0.0);

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> tonelList, ParseException e) {
                if (e != null) {
                    Log.e("AppQuery", e.toString());
                    return;
                }

                Double total = 0.0;
                Tonel tonelResult = new Tonel();
                for (ParseObject parseObject : tonelList) {
                    tonelResult = (Tonel) parseObject;
                    total += tonelResult.getEstoque();
                }

                tonelResult.setName("Temp");
                tonelResult.setEstoque(total);

                tonelList.clear();
                tonelList.add(tonelResult);
                // Release any objects previously pinned for this query.
                ParseObject.unpinAllInBackground(ESTOQUE_TOTAL, tonelList, new DeleteCallback() {
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e("AppQuery", e.toString());
                            return;
                        }

                        // Add the latest results for this query to the cache.
                        ParseObject.pinAllInBackground(ESTOQUE_TOTAL, tonelList);
                    }
                });
            }
        });
    }

//    private static Double getAreaTotal() {
//
//    }

   // private static Double getProducaoMedia() {

   // }


}
