package canacollector.cc.com.example.android.canacollectormanager.View.Alambique;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import canacollector.cc.com.example.android.canacollectormanager.Model.Alambique;
import canacollector.cc.com.example.android.canacollectormanager.R;
import canacollector.cc.com.example.android.canacollectormanager.Utils.AppQuery;
import canacollector.cc.com.example.android.canacollectormanager.Utils.MyProgressDialog;

public class AlambiqueAbaGeral extends Fragment{
    private Toolbar toolbar;
    private ProgressDialog pDialog;
    private TextView areaTotal;
    private TextView estoqueTotal;
    private TextView producaoDiaria;
    private TextView rendimentoIndustrialMedio;

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
                R.layout.content_alambique_aba_geral, container, false);

        setToolbar();

        areaTotal = (TextView) rootView.findViewById(R.id.areaTotalTalhoesGeralInput);

        estoqueTotal = (TextView) rootView.findViewById(R.id.estoqueTotalGeralInput);

        producaoDiaria = (TextView) rootView.findViewById(R.id.producaoDiariaMediaInput);

        rendimentoIndustrialMedio = (TextView) rootView.findViewById(R.id.rendimentoIndustrialMedioInput);

        setTextView();

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

    private void setTextView(){
        areaTotal.setText(String.format("%.2f", AppQuery.getAreaTotal()));
        estoqueTotal.setText(String.format("%.2f", AppQuery.getEstoqueTotal()));
        producaoDiaria.setText(String.format("%.2f", AppQuery.getProducaoMedia()));
        rendimentoIndustrialMedio.setText(String.format("%.2f", AppQuery.getRendimentoIndustrial()));
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
                        new RunThread().execute();
                        return true;
                }
                return false;
            }
        });
    }

    public class RunThread extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = MyProgressDialog.getProgressDialog(AlambiqueAbaGeral.this.getActivity(), "Atualizando! Aguarde");
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            AppQuery.getEstoqueTotalFromServer();
            AppQuery.getProducaoTotalFromServer();
            AppQuery.getProducaoTotalFromServer();
            AppQuery.getTalhaoFromServer();
            AppQuery.getMostoTotalFromServer();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            pDialog.dismiss();
            setTextView();
        }
    }
}
