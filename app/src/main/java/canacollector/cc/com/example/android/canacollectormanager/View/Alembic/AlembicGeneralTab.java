package canacollector.cc.com.example.android.canacollectormanager.View.Alembic;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import canacollector.cc.com.example.android.canacollectormanager.Model.Alambique;
import canacollector.cc.com.example.android.canacollectormanager.R;
import canacollector.cc.com.example.android.canacollectormanager.Utils.AppQuery;
import canacollector.cc.com.example.android.canacollectormanager.Utils.AppUtils;
import canacollector.cc.com.example.android.canacollectormanager.Model.Cachaca;
import canacollector.cc.com.example.android.canacollectormanager.Model.Mosto;
import canacollector.cc.com.example.android.canacollectormanager.Model.Talhao;

public class AlembicGeneralTab extends Fragment{
    private Toolbar toolbar;
    private final Alambique alambique = AppQuery.getAlambique();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.content_alembic_general_tab, container, false);

        setToolbar();
        setTextIpunt();

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            toolbar.setTitle("Geral");
        }
    }

    private void setTextIpunt(){

//        getAcreage("Talhao");
//        getRendimentoMedioIndustrial("Cachaca", "Mosto");

        TextView dailyProduction           = (TextView) getActivity().findViewById(R.id.dailyProductionTextInput);
        TextView estoqueTotal              = (TextView) getActivity().findViewById(R.id.inventoryTextInput);
        Log.w("Valor do estoque total:", AppQuery.getEstoqueTotal().toString());
        //estoqueTotal.setText(AppQuery.getEstoqueTotal().toString());
        Log.w("Producao diaria media", AppQuery.getProducaoMedia().toString());
        Log.w("Area total dos talhoes", AppQuery.getAreaTotal().toString());

    }
//    //Retorna a area total de totos os talhoes cadastrados no alambique
//    private void getAcreage(String className){
//        final Handler handler = new Handler();
//
//        ParseQuery query = ParseQuery.getQuery(className);
//        query.whereEqualTo("alambique", alambique);
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> scoreList, ParseException e) {
//                if (e == null) {
//                    // Results were successfully found, looking first on the
//                    // network and then on disk.
//
//                    double tempArea = 0.0;
//                    for(ParseObject parseObject : scoreList) {
//                            Talhao temp = (Talhao) parseObject;
//                            tempArea += temp.getArea();
//                    }
//
//                    final String finalResult = "" + tempArea;
//
//                    TextView acreage = (TextView) getActivity().findViewById(R.id.acreageTextInput);
//                    acreage.setText(finalResult + " hectare");
//
////                    handler.post(new Runnable() {
////                        @Override
////                        public void run() {
////                            TextView acreage = (TextView) getActivity().findViewById(R.id.acreageTextInput);
////                            acreage.setText(finalResult + " hectare");
////                        }
////                    });
//
//                } else {
//                    //TODO
//                    //Show error on a dialog box
//                    // The network was inaccessible and we have no cached data
//                    // for this query.
//                }
//            }
//        });
//    }
//
//    private void getRendimentoMedioIndustrial(String className1, String className2){
//        double quantidadeCachaca = 0;
//        int quantidadeCanaMoida = 0;
//
//        List<ParseObject> cachacas;
//        List<ParseObject> moagens;
//
//        ParseQuery query1 = ParseQuery.getQuery(className1);
//        query1.whereEqualTo("alambique", alambique);
//        try {
//            cachacas = query1.find();
//            for (ParseObject parseObject : cachacas) {
//                Cachaca temp = (Cachaca) parseObject;
//                quantidadeCachaca += temp.getQuantidade();
//            }
//        } catch (ParseException e)
//        {
//
//        }
//
//        ParseQuery query2 = ParseQuery.getQuery(className2);
//        query2.whereEqualTo("alambique", alambique);
//        try {
//            moagens = query2.find();
//            for (ParseObject parseObject : moagens) {
//                Mosto temp = (Mosto) parseObject;
//                quantidadeCanaMoida += temp.getCana();
//            }
//        } catch (ParseException e)
//        {
//
//        }
//
//        TextView industrialAverageIncome   = (TextView) getActivity().findViewById(R.id.industrialAverageIncomeTextInput);
//        industrialAverageIncome.setText("" + (quantidadeCachaca/quantidadeCanaMoida));
//
//    }
//
//    private void atualizaRendimento(int tipo){
//
//    }

    private void setToolbar() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        if(!toolbar.getMenu().hasVisibleItems())
            toolbar.inflateMenu(R.menu.menu_alembic);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.button_refresh:
                        Log.w("Alembic General Tab", "Refresh button clicked");
                        return true;
                }
                return false;
            }
        });
    }
}
