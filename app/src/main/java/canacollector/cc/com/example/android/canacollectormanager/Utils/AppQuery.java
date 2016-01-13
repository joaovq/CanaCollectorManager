package canacollector.cc.com.example.android.canacollectormanager.Utils;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Breno on 1/13/2016.
 */
public class AppQuery {
    final static String ESTOQUE_TOTAL = "estoqueTotal";


// Query for the latest objects from Parse.


    private static void setEstoqueTotalFromServer() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tonel");
        query.whereEqualTo("alambique", AppUtils.getAlambique());

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> scoreList, ParseException e) {
                if (e != null) {
                    // There was an error or the network wasn't available.
                    return;
                }

                // Release any objects previously pinned for this query.
                ParseObject.unpinAllInBackground(ESTOQUE_TOTAL, scoreList, new DeleteCallback() {
                    public void done(ParseException e) {
                        if (e != null) {
                            // There was some error.
                            return;
                        }

                        // Add the latest results for this query to the cache.
                        ParseObject.pinAllInBackground(ESTOQUE_TOTAL, scoreList);
                    }
                });
            }
        });
    }

   // private static Double getAreaTotal() {}

   // private static Double getProducaoMedia() {

   // }


}
