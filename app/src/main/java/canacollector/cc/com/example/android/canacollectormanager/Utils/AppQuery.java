package canacollector.cc.com.example.android.canacollectormanager.Utils;

import android.util.Log;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import canacollector.cc.com.example.android.canacollectormanager.Model.Cachaca;
import canacollector.cc.com.example.android.canacollectormanager.Model.Tonel;


/**
 * Created by Breno on 1/13/2016.
 */
public class AppQuery {
    final static String ESTOQUE_TOTAL = "estoqueTotal";
    final static String PRODUCAO_TOTAL = "producaoTotal";

    public static void getEstoqueTotalFromServer() {
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

    public static Double getEstoqueTotal() {
        Tonel tonel = new Tonel();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tonel");
        query.fromLocalDatastore();

        try {
            tonel = (Tonel)query.getFirst();
        } catch (ParseException e) {
            Log.e("AppQuery", e.toString());
        }
        return tonel.getEstoque();
    }

    public static void getProducaoFromServer() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cachaca");
        query.whereEqualTo("alambique", AppUtils.getAlambique());

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> prodList, ParseException e) {
                if (e != null) {
                    Log.e("AppQuery", e.toString());
                    return;
                }

                Double total = 0.0;
                Cachaca prodResult = new Cachaca();
                for (ParseObject parseObject : prodList) {
                    prodResult = (Cachaca) parseObject;
                    total += prodResult.getQuantidade();
                }

                prodResult.setQuantidade(total/prodList.size());

                prodList.clear();
                prodList.add(prodResult);
                // Release any objects previously pinned for this query.
                ParseObject.unpinAllInBackground(PRODUCAO_TOTAL, prodList, new DeleteCallback() {
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e("AppQuery", e.toString());
                            return;
                        }

                        // Add the latest results for this query to the cache.
                        ParseObject.pinAllInBackground(PRODUCAO_TOTAL, prodList);
                    }
                });
            }
        });
    }

    public static Double getProducaoMedia() {
        Cachaca prod = new Cachaca();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cachaca");
        query.fromLocalDatastore();

        try {
            prod = (Cachaca)query.getFirst();
        } catch (ParseException e) {
            Log.e("AppUtils", "Erro ao executar getAlambique");
        }
        return prod.getQuantidade();
    }

}
