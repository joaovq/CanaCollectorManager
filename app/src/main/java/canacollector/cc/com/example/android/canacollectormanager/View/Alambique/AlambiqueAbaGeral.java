package canacollector.cc.com.example.android.canacollectormanager.View.Alambique;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import canacollector.cc.com.example.android.canacollectormanager.Model.Alambique;
import canacollector.cc.com.example.android.canacollectormanager.R;
import canacollector.cc.com.example.android.canacollectormanager.Utils.AppQuery;
import canacollector.cc.com.example.android.canacollectormanager.Utils.AppUtils;

public class AlambiqueAbaGeral extends Fragment{
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
                R.layout.content_alambique_aba_geral, container, false);

        setToolbar();

        TextView areaTotal = (TextView) rootView.findViewById(R.id.areaTotalTalhoesGeralInput);
//        areaTotal.setText(AppQuery.geta().toString());

        TextView estoqueTotal = (TextView) rootView.findViewById(R.id.estoqueTotalGeralInput);
        estoqueTotal.setText(AppQuery.getEstoqueTotal().toString());

        TextView producaoDiaria = (TextView) rootView.findViewById(R.id.producaoDiariaMediaInput);
        producaoDiaria.setText(AppQuery.getProducaoMedia().toString());

        TextView rendimentoIndustrialMedio = (TextView) rootView.findViewById(R.id.rendimentoIndustrialMedioInput);
//        rendimentoIndustrialMedio.setText(AppQuery.get);

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
