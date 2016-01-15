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

import canacollector.cc.com.example.android.canacollectormanager.Model.Tonel;
import canacollector.cc.com.example.android.canacollectormanager.R;
import canacollector.cc.com.example.android.canacollectormanager.Utils.AppQuery;

/**
 * Created by joaovq on 11/01/16.
 */
public class AlambiqueAbaEstoque extends Fragment implements AdapterView.OnItemSelectedListener {
    private static TextView estoqueTotal;
    private static TextView estoqueEmToneis;
    private static TextView estoqueEmGarrafa;
    private static TextView estoqueNoTonel;
    private static Spinner toneis;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.content_alambique_aba_estoque, container, false);

        //List<String> itens = getToneis(AppQuery.getAllTonel());
       // Log.w("Estoque Aba", itens.size() + "");
        //toneis = (Spinner) rootView.findViewById(R.id.listaDeToneisSpinner);
        //toneis.setOnItemSelectedListener(this);
       // spinnerSetup(itens);

        estoqueTotal        = (TextView) rootView.findViewById(R.id.estoqueTotalInput);
        estoqueEmToneis     = (TextView) rootView.findViewById(R.id.estoqueEmToneisInput);
        estoqueEmGarrafa    = (TextView) rootView.findViewById(R.id.estoqueEmGarrafaInput);
        estoqueNoTonel      = (TextView) rootView.findViewById(R.id.estoqueNoTonelXInput);

        setTextView();

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("Estoque");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        if(parent.getChildAt(0) != null) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            ((TextView) parent.getChildAt(0)).setTextSize(22);
        }

        String tonel = (String)parent.getSelectedItem();
        estoqueNoTonel.setText(AppQuery.getEstoqueNoTonel(tonel).toString());

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        toneis.setSelection(0);
    }

    public void spinnerSetup(List<String> itens)
    {
        ArrayAdapter<String> talhaoAdapter;
        talhaoAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, itens);

        talhaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toneis.setAdapter(talhaoAdapter);
    }

    public List<String> getToneis(List<Tonel> toneis){
        List<String> nomeToneis = new ArrayList<String>();
        for(Tonel tonel : toneis)
            nomeToneis.add(tonel.getName());

        return nomeToneis;
    }

    public static void setTextView(){
        String litros = " litros";
        estoqueTotal.setText(AppQuery.getEstoqueTotal().toString() + litros);
        estoqueEmToneis.setText(AppQuery.getEstoqueEmToneis().toString() + litros);
        estoqueEmGarrafa.setText(AppQuery.getEstoqueEmGarrafa().toString() + litros);

//        String tonel = (String) toneis.getSelectedItem();
//        estoqueNoTonel.setText(AppQuery.getEstoqueNoTonel(tonel).toString() + litros);
    }
}
