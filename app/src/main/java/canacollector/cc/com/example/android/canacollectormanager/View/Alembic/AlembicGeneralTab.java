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
import canacollector.cc.com.example.android.canacollectormanager.Utils.AppUtils;
import canacollector.cc.com.example.android.canacollectormanager.View.Model.Cachaca;
import canacollector.cc.com.example.android.canacollectormanager.View.Model.Talhao;

public class AlembicGeneralTab extends Fragment{
    private Toolbar toolbar;
    private final Alambique alambique = AppUtils.getAlambique();

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

    private void setTextIpunt(){

        getAcreage("Talhao");
        getIndustrialAverageIncome("Cachaca","Moagem");

        TextView industrialAverageIncome   = (TextView) getActivity().findViewById(R.id.industrialAverageIncomeTextInput);
        TextView dailyProduction           = (TextView) getActivity().findViewById(R.id.dailyProductionTextInput);
        TextView inventory                 = (TextView) getActivity().findViewById(R.id.inventoryTextInput);


    }

    private void getAcreage(String className){
        final Handler handler = new Handler();

        ParseQuery query = ParseQuery.getQuery(className);
        query.whereEqualTo("alambique", alambique);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    // Results were successfully found, looking first on the
                    // network and then on disk.

                    double tempArea = 0.0;
                    for(ParseObject parseObject : scoreList) {
                            Talhao temp = (Talhao) parseObject;
                            tempArea += temp.getArea();
                    }

                    final String finalResult = "" + tempArea;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            TextView acreage = (TextView) getActivity().findViewById(R.id.acreageTextInput);
                            acreage.setText(finalResult + " hectare");
                        }
                    });

                } else {
                    //TODO
                    //Show error on a dialog box
                    // The network was inaccessible and we have no cached data
                    // for this query.
                }
            }
        });
    }

    private void getIndustrialAverageIncome(String className1, String classname2){
        final Handler handler = new Handler();

        double quantidadeCachaca = 0;
        int quantidadeCanaMoida = 0;

        ParseQuery query1 = ParseQuery.getQuery(className1);
        query1.whereEqualTo("alambique", alambique);
        query1.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    // Results were successfully found, looking first on the
                    // network and then on disk.

                    double quantidade = 0;

                    for (ParseObject parseObject : scoreList) {
                        Cachaca temp = (Cachaca) parseObject;
                        quantidade += temp.getQuantidade();
                    }

                    final double finalResult = quantidade;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });

                } else {
                    //TODO
                    //Show error on a dialog box
                    // The network was inaccessible and we have no cached data
                    // for this query.
                }
            }
        });

        ParseQuery query2 = ParseQuery.getQuery(className1);
        query2.whereEqualTo("alambique", alambique);
        query2.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    // Results were successfully found, looking first on the
                    // network and then on disk.

                    double tempArea = 0.0;
                    for (ParseObject parseObject : scoreList) {
                        Talhao temp = (Talhao) parseObject;
                        tempArea += temp.getArea();
                    }

                    final String finalResult = "" + tempArea;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            TextView acreage = (TextView) getActivity().findViewById(R.id.acreageTextInput);
                            acreage.setText(finalResult + " ");
                        }
                    });

                } else {
                    //TODO
                    //Show error on a dialog box
                    // The network was inaccessible and we have no cached data
                    // for this query.
                }
            }
        });
    }

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            toolbar.setTitle("Geral");
        }
    }
}
