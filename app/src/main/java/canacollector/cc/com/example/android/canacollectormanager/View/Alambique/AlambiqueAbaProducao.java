package canacollector.cc.com.example.android.canacollectormanager.View.Alambique;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;
import com.parse.ParseObject;

import java.util.Arrays;
import java.util.List;

import canacollector.cc.com.example.android.canacollectormanager.Model.Cachaca;
import canacollector.cc.com.example.android.canacollectormanager.Model.Mosto;
import canacollector.cc.com.example.android.canacollectormanager.R;
import canacollector.cc.com.example.android.canacollectormanager.Utils.AppQuery;
import canacollector.cc.com.example.android.canacollectormanager.Utils.MyProgressDialog;

/**
 * Created by joaovq on 11/01/16.
 */
public class AlambiqueAbaProducao extends Fragment {
    private XYPlot grafico1;
    private XYPlot grafico2;
    private XYPlot grafico3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.content_alambique_aba_producao, container, false);

        grafico1 = (XYPlot) rootView.findViewById(R.id.graficoQtdMostoPorTempo);
        grafico2 = (XYPlot) rootView.findViewById(R.id.graficoMoagemPorTempo);
        grafico3 = (XYPlot) rootView.findViewById(R.id.graficoProducaoDiariaPorTempo);
        gerarGraficoMosto(grafico1);
        gerarGraficoMoagem(grafico2);
        gerarGraficoProducaoDiaria(grafico3);

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("Produção");
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
    }

    private void gerarGraficoMosto(XYPlot grafico){
        List<ParseObject> mostos = AppQuery.getAllMosto();

        Number[] series1Numbers = new Number[mostos.size()];
        for(int i = 0; i < mostos.size(); i++)
            series1Numbers[i] = i+1;

        Number[] series2Numbers = new Number[mostos.size()];
        for(int i = 0; i < mostos.size(); i++){
            Mosto mosto = (Mosto) mostos.get(i);
            series2Numbers[i] = mosto.getCaldo();
        }

        XYSeries series = new SimpleXYSeries(
                Arrays.asList(series1Numbers),
                Arrays.asList(series2Numbers),
                null);

        grafico.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
        grafico.setRangeStep(XYStepMode.INCREMENT_BY_VAL,250);
        grafico.getLegendWidget().setVisible(false);
        grafico.getGraphWidget().getDomainGridLinePaint().setColor(Color.TRANSPARENT);
        grafico.getGraphWidget().getRangeGridLinePaint().setColor(Color.TRANSPARENT);
        grafico.getGraphWidget().getRangeOriginLinePaint().setColor(Color.WHITE);

        LineAndPointFormatter seriesFormat = new LineAndPointFormatter();
        seriesFormat.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
        seriesFormat.getLinePaint().setColor(Color.WHITE);
        seriesFormat.getFillPaint().setColor(Color.rgb(0,132,132));
        seriesFormat.getVertexPaint().setColor(Color.BLACK);

        // add a new series' to the xyplot:
        grafico.addSeries(series, seriesFormat);

        // reduce the number of range labels
        grafico.setTicksPerRangeLabel(2);

        // rotate domain labels 45 degrees to make them more compact horizontally:
        grafico.getGraphWidget().setDomainLabelOrientation(-45);
    }

    private void gerarGraficoMoagem(XYPlot grafico){
        List<ParseObject> mostos = AppQuery.getAllMosto();

        Number[] series1Numbers = new Number[mostos.size()];
        for(int i = 0; i < mostos.size(); i++)
            series1Numbers[i] = i+1;

        Number[] series2Numbers = new Number[mostos.size()];
        for(int i = 0; i < mostos.size(); i++){
            Mosto mosto = (Mosto) mostos.get(i);
            series2Numbers[i] = mosto.getCana();
        }

        XYSeries series = new SimpleXYSeries(
                Arrays.asList(series1Numbers),
                Arrays.asList(series2Numbers),
                "Moagem vs Tempo");


        grafico.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
        grafico.setRangeStep(XYStepMode.INCREMENT_BY_VAL,500);
        grafico.getLegendWidget().setVisible(false);
        grafico.getGraphWidget().getDomainGridLinePaint().setColor(Color.TRANSPARENT);
        grafico.getGraphWidget().getRangeGridLinePaint().setColor(Color.TRANSPARENT);
        grafico.getGraphWidget().getRangeOriginLinePaint().setColor(Color.WHITE);

        LineAndPointFormatter seriesFormat = new LineAndPointFormatter();
        seriesFormat.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
        seriesFormat.getLinePaint().setColor(Color.WHITE);
        seriesFormat.getFillPaint().setColor(Color.rgb(255,127,120));
        seriesFormat.getVertexPaint().setColor(Color.BLACK);

        // add a new series' to the xyplot:
        grafico.addSeries(series, seriesFormat);

        // reduce the number of range labels
        grafico.setTicksPerRangeLabel(2);

        // rotate domain labels 45 degrees to make them more compact horizontally:
        grafico.getGraphWidget().setDomainLabelOrientation(-45);
    }

    private void gerarGraficoProducaoDiaria(XYPlot grafico){
        List<ParseObject> allCachaca = AppQuery.getAllCachaca();




        Number[] series1Numbers = new Number[allCachaca.size()];
        for(int i = 0; i < allCachaca.size(); i++)
            series1Numbers[i] = i+1;

        Number[] series2Numbers = new Number[allCachaca.size()];
        for(int i = 0; i < allCachaca.size(); i++){
            Cachaca cachaca = (Cachaca) allCachaca.get(i);
            series2Numbers[i] = cachaca.getQuantidade();
        }

        XYSeries series = new SimpleXYSeries(
                Arrays.asList(series1Numbers),
                Arrays.asList(series2Numbers),
                "Produção Diária vs Tempo");

        grafico.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
        grafico.setRangeStep(XYStepMode.INCREMENT_BY_VAL,10);
        grafico.getLegendWidget().setVisible(false);
        grafico.getGraphWidget().getDomainGridLinePaint().setColor(Color.TRANSPARENT);
        grafico.getGraphWidget().getRangeGridLinePaint().setColor(Color.TRANSPARENT);
        grafico.getGraphWidget().getRangeOriginLinePaint().setColor(Color.WHITE);

        LineAndPointFormatter seriesFormat = new LineAndPointFormatter();
        seriesFormat.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
        seriesFormat.getLinePaint().setColor(Color.WHITE);
        seriesFormat.getFillPaint().setColor(Color.GRAY);
        seriesFormat.getVertexPaint().setColor(Color.BLACK);

        // add a new series' to the xyplot:
        grafico.addSeries(series, seriesFormat);

        // reduce the number of range labels
        grafico.setTicksPerRangeLabel(2);

        // rotate domain labels 45 degrees to make them more compact horizontally:
        grafico.getGraphWidget().setDomainLabelOrientation(-45);
    }

    public class RunThread extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = MyProgressDialog.getProgressDialog(AlambiqueAbaProducao.this.getActivity(), "Atualizando! Aguarde");
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
            gerarGraficoMosto(grafico1);
            gerarGraficoMoagem(grafico2);
            gerarGraficoProducaoDiaria(grafico3);
        }
    }
}