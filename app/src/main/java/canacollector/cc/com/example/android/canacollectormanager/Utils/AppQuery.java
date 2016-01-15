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
import canacollector.cc.com.example.android.canacollectormanager.Model.Mosto;
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

    //Consulta o estoque total do alambique
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

        List<ParseObject> objectList = new ArrayList<>();
        try {
            objectList = query.find();
        } catch (ParseException e) {
            Log.e("AppQuery", e.toString());
        }

        Double total = 0.0;
        Tonel tonelResult = new Tonel();
        for (ParseObject parseObject : objectList) {
            tonelResult = (Tonel) parseObject;
            total += tonelResult.getEstoque();
        }
        return total;
    }

//    //Consulta todas as anotacoes de producao de cachaca do server
//    public static void getProducaoFromServer() {
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cachaca");
//        query.whereEqualTo("alambique", getAlambique());
//
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(final List<ParseObject> prodList, ParseException e) {
//                if (e != null) {
//                    Log.e("AppQuery", e.toString());
//                    return;
//                }
//                // Release any objects previously pinned for this query.
//                ParseObject.unpinAllInBackground(PRODUCAO_TOTAL, prodList, new DeleteCallback() {
//                    public void done(ParseException e) {
//                        if (e != null) {
//                            Log.e("AppQuery", e.toString());
//                            return;
//                        }
//                        // Add the latest results for this query to the cache.
//                        ParseObject.pinAllInBackground(PRODUCAO_TOTAL, prodList);
//                    }
//                });
//            }
//        });
//    }

    public static Double getProducaoMedia() {
        List<ParseObject> objectList = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cachaca");
        query.fromLocalDatastore();

        try {
            objectList = query.find();
        } catch (ParseException e) {
            Log.e("AppUtils", "Erro ao executar getAlambique");
        }

        Double total = 0.0;
        Cachaca prodResult = new Cachaca();
        for (ParseObject parseObject : objectList) {
            prodResult = (Cachaca) parseObject;
            total += prodResult.getQuantidade();
        }
        return total/objectList.size();
    }

    //RETORNA TODOS OS TALHOES DO ALAMBIQUE NO SERVER
    public static void getTalhaoFromServer() {
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
        query.orderByAscending("createdAt");

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

    //Retorna uma lista com todas as Cachacas cadastradas
    public static List<ParseObject> getAllCachaca () {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cachaca");
        query.fromLocalDatastore();
        query.orderByAscending("createdAt");

        List<ParseObject> objectList = new ArrayList<>();
        try {
            objectList = query.find();
        } catch (ParseException e) {
            Log.e("AppQuery:getAllCachaca", e.toString());
        }
        return objectList;
    }

    //Retorna o total de cachaca produzida
    public static Double getCachacaTotal () {
        Double total = 0.0;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cachaca");
        query.fromLocalDatastore();

        List<ParseObject> objectList;
        try {
            objectList = query.find();
            if (objectList.isEmpty())
                return total;

            Cachaca prodResult;
            for (ParseObject parseObject : objectList) {
                prodResult = (Cachaca) parseObject;
                total += prodResult.getQuantidade();
            }
        } catch (ParseException e) {
            Log.e("AppQuery:TotalCachaca", e.toString());
        }
        return total;
    }

    public static Double getCanaMoidaTotal () {
        Double total = 0.0;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Mosto");
        query.fromLocalDatastore();

        List<ParseObject> objectList;
        try {
            objectList = query.find();
            if (objectList.isEmpty())
                return total;

            Mosto prodResult;
            for (ParseObject parseObject : objectList) {
                prodResult = (Mosto) parseObject;
                total += prodResult.getCana();
            }
        } catch (ParseException e) {
            Log.e("AppQuery:TotalCanaMoida", e.toString());
        }
        return total;
    }

    //Retorna a area total de todos os talhoes
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

    //Retorna o rendimento industrial do alambique (Litros produzidos por tonelada de cana)
    public static Double getRendimentoIndustrial() {
        Double cana, cachaca;
        cachaca = getCachacaTotal();
        cana = getCanaMoidaTotal();
        return (cachaca*1000)/cana;
    }

    public static Double getAreaReserva(){
        Double areaTotal = getAreaTotal();
        return areaTotal*0.2;
    }

    public static Double getAreaNoTalhao(String nomeTalhao){
        Talhao talhao = new Talhao();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Talhao");
        query.fromLocalDatastore();
        query.whereEqualTo("nome", nomeTalhao);

        try {
            talhao = (Talhao) query.getFirst();
        } catch (ParseException e) {
            Log.e("AppQuerie::getAreaTotal", e.toString());
        }

        return talhao.getArea();
    }

    public static List<ParseObject> getAllTalhoes(){
        List<Talhao> talhoes = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Talhao");
        query.fromLocalDatastore();

        List<ParseObject> talhaoList = new ArrayList<>();
        try {
            talhaoList = query.find();
        } catch (ParseException e) {
            Log.e("AppQuerie::getTalhoes", e.toString());
        }
        return talhaoList;
    }

    public static Double getEstoqueEmToneis(){
        return getEstoqueTotal();
    }

    public static Double getEstoqueEmGarrafa(){
        return 0.0;
    }

    public static Double getEstoqueNoTonel(String nomeTonel){
        Tonel tonel = new Tonel();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tonel");
        query.fromLocalDatastore();
        query.whereEqualTo("nome", nomeTonel);

        try {
            tonel = (Tonel) query.getFirst();
        } catch (ParseException e) {
            Log.e("AppQuerie::EstoqueTonel", e.toString());
        }
        return tonel.getEstoque();
    }

    public static List<ParseObject> getAllTonel(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tonel");
        query.fromLocalDatastore();

        List<ParseObject> toneisList = new ArrayList<>();
        try {
            toneisList = query.find();
        } catch (ParseException e) {
            Log.e("AppQuerie::EstoqueTonel", e.toString());
        }
        return toneisList;
    }

    public static Talhao findTalhao(String nome) {
        Talhao talhao = new Talhao();
        ParseObject talhaoObject;

        //Recupera o talhao do usuario logado com o nome fornecido
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Talhao");
        query.fromLocalDatastore();
        query.whereEqualTo("nome", nome);

        try {
            talhaoObject = query.getFirst();
            talhao = (Talhao)talhaoObject;
        }
        catch (ParseException e) {
            Log.e("FindTalhao", e.toString());
        }
        return talhao;
    }
}
