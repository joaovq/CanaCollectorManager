package canacollector.cc.com.example.android.canacollectormanager.View.Alambique;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import canacollector.cc.com.example.android.canacollectormanager.Model.Talhao;
import canacollector.cc.com.example.android.canacollectormanager.R;
import canacollector.cc.com.example.android.canacollectormanager.Utils.AppQuery;

/**
 * Created by joaovq on 11/01/16.
 */
public class AlambiqueAbaArea extends Fragment implements AdapterView.OnItemSelectedListener {
    private static TextView areaReserva;
    private static TextView areaTotalTalhoes;
    private static TextView areaPorTalhao;
    private static Spinner talhoes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.content_alambique_aba_area, container, false);
//
//        //Carrega talhoes armazenados no banco da fazenda e insere no spinner
//        List<String> itens = getTalhoes(AppQuery.getTalhoes());
//        talhoes = (Spinner) rootView.findViewById(R.id.listaDeToneisSpinner);
//        talhoes.setOnItemSelectedListener(this);
//        spinnerSetup(itens);
//
//        areaTotalTalhoes = (TextView) rootView.findViewById(R.id.areaTotalTalhoesInput);
//        areaReserva      = (TextView) rootView.findViewById(R.id.areaReservaInput);

//        setTextView();

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("Área");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        if(parent.getChildAt(0) != null) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            ((TextView) parent.getChildAt(0)).setTextSize(22);
        }

        String talhao = (String)parent.getSelectedItem();
        areaPorTalhao.setText(AppQuery.getAreaNoTalhao(talhao).toString());

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        talhoes.setSelection(0);
    }

    public List<String> getTalhoes(List<Talhao> talhoes){
        List<String> nomeTalhoes = new ArrayList<String>();
        for(Talhao talhao : talhoes)
            nomeTalhoes.add(talhao.getName());

        return nomeTalhoes;
    }

    public void spinnerSetup(List<String> itens)
    {
        ArrayAdapter<String> talhaoAdapter;
        talhaoAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, itens);

        talhaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        talhoes.setAdapter(talhaoAdapter);
    }

    public static void setTextView(){
        String hectare = " hectare";
        areaReserva.setText(AppQuery.getAreaReserva().toString() + hectare);
        areaTotalTalhoes.setText(AppQuery.getAreaTotal().toString() + hectare);

        String talhao = (String)talhoes.getSelectedItem();
        areaPorTalhao.setText(AppQuery.getAreaNoTalhao(talhao).toString() + hectare);

    }

}