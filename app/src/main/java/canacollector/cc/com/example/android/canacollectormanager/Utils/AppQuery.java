package canacollector.cc.com.example.android.canacollectormanager.Utils;

import android.util.Log;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import canacollector.cc.com.example.android.canacollectormanager.Model.Alambique;
import canacollector.cc.com.example.android.canacollectormanager.Model.Cachaca;
import canacollector.cc.com.example.android.canacollectormanager.Model.Talhao;
import canacollector.cc.com.example.android.canacollectormanager.Model.Tonel;


/**
 * Created by Breno on 1/13/2016.
 */
public class AppQuery {
    final static String ESTOQUE_TOTAL = "estoqueTotal";
    final static String PRODUCAO_TOTAL = "producaoTotal";
    final static String AREA_TOTAL = "areaTotal";
    final static String MOSTO_TOTAL = "mostoTotal";
    final static String CACHACA_TOTAL = "cachacaTotal";

    //Consulta no servidor o alambique onde o usuário está cadastrado e salva local
    public static void getAlambiqueFromParse() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.include("alambique");

        query.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(final ParseUser object, ParseException e) {
                if (e != null) {
                    Log.d("AppUtils", "Nao foi possivel recuperar o alambique do usuario");
                    return;
                }
                else {
                    final String user_alambique = "alambique";

                    final List<ParseObject> alambiqueResult = new ArrayList<ParseObject>();
                    //object.getParseObject("alambique");
                    alambiqueResult.add(object.getParseObject("alambique"));
                    final List<ParseObject> alambique = new ArrayList<>();
                    alambique.add(alambiqueResult.get(0));



                    // Release any objects previously pinned for this query.
                    ParseUser.unpinAllInBackground(user_alambique, alambiqueResult, new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                // There was some error.
                                return;
                            }
                            Log.w("App Utils", "Ta aqui");
                            // Add the latest results for this query to the cache.
                            ParseObject.pinAllInBackground(user_alambique,alambique );
                        }
                    });

                }
            }
        });
    }

    //Consulta local sobre o alambique
    public static Alambique getAlambique() {
        Alambique alambique = new Alambique();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Alambique");
        query.fromLocalDatastore();

        try {
            alambique = (Alambique)query.getFirst();
        } catch (ParseException e) {
            Log.e("AppUtils", "Erro ao executar getAlambique");
        }
        return alambique;
    }

    public static void getEstoqueTotalFromServer() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tonel");
        query.whereEqualTo("alambique", getAlambique());
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
        query.whereEqualTo("alambique", getAlambique());

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

                prodResult.setQuantidade(total / prodList.size());

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

    //RETORNA TODOS OS TALHOES DO ALAMBIQUE NO SERVER
    public static void getAreaTotalFromServer() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Talhao");
        query.whereEqualTo("alambique", getAlambique());

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> talhaoList, ParseException e) {
                if (e != null) {
                    Log.e("AppQuery", e.toString());
                    return;
                }

                // Release any objects previously pinned for this query.
                ParseObject.unpinAllInBackground(AREA_TOTAL, talhaoList, new DeleteCallback() {
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e("AppQuery", e.toString());
                            return;
                        }

                        // Add the latest results for this query to the cache.
                        ParseObject.pinAllInBackground(AREA_TOTAL, talhaoList);
                    }
                });
            }
        });
    }

    //RETORNA TODOS OS DADOS REFERENTES A MOAGEM/MOSTO NO SERVER
    public static void getMostoTotalFromServer() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Mosto");
        query.whereEqualTo("alambique", getAlambique());

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> mostoList, ParseException e) {
                if (e != null) {
                    Log.e("AppQuery", e.toString());
                    return;
                }

                // Release any objects previously pinned for this query.
                ParseObject.unpinAllInBackground(MOSTO_TOTAL, mostoList, new DeleteCallback() {
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e("AppQuery", e.toString());
                            return;
                        }

                        // Add the latest results for this query to the cache.
                        ParseObject.pinAllInBackground(MOSTO_TOTAL, mostoList);
                    }
                });
            }
        });
    }

    public static List<ParseObject> getAllMosto () {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Mosto");
        query.fromLocalDatastore();

        List<ParseObject> mostoList = new ArrayList<>();
        try {
            mostoList = query.find();
        } catch (ParseException e) {
            Log.e("AppQueria::getAllMosto", e.toString());
        }
        return mostoList;
    }

    //RETORNA TODOS OS DADOS REFERENTES A PRODUCAO DE CACHACA NO SERVER
    public static void getProducaoTotalFromServer() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cachaca");
        query.whereEqualTo("alambique", getAlambique());

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> objectList, ParseException e) {
                if (e != null) {
                    Log.e("AppQuery", e.toString());
                    return;
                }
                // Release any objects previously pinned for this query.
                ParseObject.unpinAllInBackground(CACHACA_TOTAL, objectList, new DeleteCallback() {
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e("AppQuery", e.toString());
                            return;
                        }
                        // Add the latest results for this query to the cache.
                        ParseObject.pinAllInBackground(CACHACA_TOTAL, objectList);
                    }
                });
            }
        });
    }

    public static List<ParseObject> getAllCachaca () {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cachaca");
        query.fromLocalDatastore();

        List<ParseObject> objectList = new ArrayList<>();
        try {
            objectList = query.find();
        } catch (ParseException e) {
            Log.e("AppQuery:getAllCachaca", e.toString());
        }
        return objectList;
    }

    public static List<ParseObject> getAllProducao () {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cachaca");
        query.fromLocalDatastore();

        List<ParseObject> parseObjects = new ArrayList<>();
        try {
            parseObjects = query.find();
        } catch (ParseException e) {
            Log.e("AppQueria::getAllMosto", e.toString());
        }
        return parseObjects;
    }

    public static Double getAreaTotal() {
        Talhao talhao = new Talhao();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Talhao");
        query.fromLocalDatastore();

        List<ParseObject> talhaoList = new ArrayList<>();
        try {
            talhaoList = query.find();
        } catch (ParseException e) {
            Log.e("AppQuerie::getAreaTotal", e.toString());
        }

        //IMPLEMENTAR O CASO DE ERRO QUANDO O TALHAO TEM AREA = NULL ---------
        Double areaTotal = 0.0;
        for (ParseObject parseObject : talhaoList ) {
            talhao = (Talhao) parseObject;
            areaTotal += talhao.getArea();
        }

        return areaTotal;
    }
}
